package edu.byu.cs.tweeter.server.dao.dao_helpers.query;

import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;
import edu.byu.cs.tweeter.server.dao.dao_helpers.get.GetUser;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.service.request.FollowingRequest;

public class QueryFollows {

    public static final String PARTITION_KEY = "followee_handle"; // AKA the primary key.
    public static final String SORT_KEY = "follower_handle";  // AKA the range key.
    public static final String TABLE_NAME = "follows";
    public static int QUERY_LIMIT = 10;
    public static String INDEX_NAME = "follows_index";

    public static int getFollowingCount(String getFollowingForThisAlias) {

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#" + SORT_KEY, SORT_KEY);

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":follower_handle_value", getFollowingForThisAlias);

        QuerySpec querySpec;
        String keyConditionExpression = "#" + SORT_KEY + " = :follower_handle_value";

        String lastFollowerHandle = null;
        String lastFolloweeHandle = null;
        boolean done = false;
        int totalFollowing = 0;

        while(!done) {
            if(lastFollowerHandle == null && lastFolloweeHandle == null) {
                // Need the first ten users.
                querySpec = new QuerySpec()
                        .withMaxResultSize(QUERY_LIMIT)
                        .withScanIndexForward(false)
                        .withKeyConditionExpression(keyConditionExpression)
                        .withNameMap(nameMap)
                        .withValueMap(valueMap);
            } else {
                // Need only the next ten users.
                querySpec = new QuerySpec()
                        .withExclusiveStartKey("followee_handle", lastFolloweeHandle, "follower_handle", lastFollowerHandle)
                        .withMaxResultSize(QUERY_LIMIT)
                        .withScanIndexForward(false)
                        .withKeyConditionExpression(keyConditionExpression)
                        .withNameMap(nameMap)
                        .withValueMap(valueMap);
            }

            ItemCollection<QueryOutcome> items = null;
            Iterator<Item> iterator = null;
            List<Item> responseItems = new ArrayList<>();

            try {
                Table table = DB.getDatabase(TABLE_NAME);
                items = table.query(querySpec);

                iterator = items.iterator();
                while (iterator.hasNext()) {
                    responseItems.add(iterator.next());
                }
            } catch (Exception e) {
                System.err.println("Unable to perform the query.");
                System.err.println(e.getMessage());
                return -1;
            }
            totalFollowing += responseItems.size();
            try {
                Item lastItem = responseItems.get(QUERY_LIMIT - 1); // QUERY_LIMIT - 1 to get the last index.
                                                                    // If it's out of range then we are done.
                lastFolloweeHandle = lastItem.getString("followee_handle");
                lastFollowerHandle = lastItem.getString("follower_handle");
            } catch(Exception e) {
                done = true;
            }
        }
        return totalFollowing;
    }

    public static int getFollowersCount(String getFollowersForThisAlias) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#" + PARTITION_KEY, PARTITION_KEY);

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":followee_handle_value", getFollowersForThisAlias);

        QuerySpec querySpec;
        String keyConditionExpression = "#" + PARTITION_KEY + " = :followee_handle_value";

        String lastFollowerHandle = null;
        String lastFolloweeHandle = null;
        boolean done = false;
        int totalFollowers = 0;

        while(!done) {
            if(lastFollowerHandle == null && lastFolloweeHandle == null) {
                // Need the first ten users.
                querySpec = new QuerySpec()
                        .withMaxResultSize(QUERY_LIMIT)
                        .withScanIndexForward(false)
                        .withKeyConditionExpression(keyConditionExpression)
                        .withNameMap(nameMap)
                        .withValueMap(valueMap);
            } else {
                // Need only the next ten users.
                querySpec = new QuerySpec()
                        .withExclusiveStartKey("followee_handle", lastFolloweeHandle, "follower_handle", lastFollowerHandle)
                        .withMaxResultSize(QUERY_LIMIT)
                        .withScanIndexForward(false)
                        .withKeyConditionExpression(keyConditionExpression)
                        .withNameMap(nameMap)
                        .withValueMap(valueMap);
            }

            ItemCollection<QueryOutcome> items = null;
            Iterator<Item> iterator = null;
            List<Item> responseItems = new ArrayList<>();

            try {
                Table table = DB.getDatabase(TABLE_NAME);
                Index index = table.getIndex(INDEX_NAME);
                items = index.query(querySpec);

                iterator = items.iterator();
                while (iterator.hasNext()) {
                    responseItems.add(iterator.next());
                }
            } catch (Exception e) {
                System.err.println("Unable to perform the query.");
                System.err.println(e.getMessage());
                return -1;
            }
            totalFollowers += responseItems.size();
            try {
                Item lastItem = responseItems.get(QUERY_LIMIT - 1); // QUERY_LIMIT - 1 to get the last index.
                // If it's out of range then we are done.
                lastFolloweeHandle = lastItem.getString("followee_handle");
                lastFollowerHandle = lastItem.getString("follower_handle");
            } catch(Exception e) {
                done = true;
            }
        }
        return totalFollowers;
    }

    public static List<User> queryFollowingSorted(FollowingRequest request) {

        String followingAlias = null;
        if(request.getLastFollowee() != null) {
            followingAlias = request.getLastFollowee().getAlias();
        }
        String followerAlias = request.getUser().getAlias();
        int limit = request.getLimit();

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#follower_handle", SORT_KEY);

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":follower_handle_value", followerAlias);

        QuerySpec querySpec;
        String keyConditionExpression = "#" + SORT_KEY + " = :follower_handle_value";

        if(followingAlias == null) {
            // Need the first ten users.
            querySpec = new QuerySpec()
                    .withMaxResultSize(limit)
                    .withScanIndexForward(true)
                    .withKeyConditionExpression(keyConditionExpression)
                    .withNameMap(nameMap)
                    .withValueMap(valueMap);
        } else {
            // Need only the next ten users.
            querySpec = new QuerySpec()
                    .withExclusiveStartKey("followee_handle", followingAlias, "follower_handle", followerAlias)
                    .withMaxResultSize(limit)
                    .withScanIndexForward(true)
                    .withKeyConditionExpression(keyConditionExpression)
                    .withNameMap(nameMap)
                    .withValueMap(valueMap);
        }

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        List<Item> responseItems = new ArrayList<>();

        try {
            Table table = DB.getDatabase(TABLE_NAME);
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                responseItems.add(iterator.next());
            }
        } catch (Exception e) {
            System.err.println("Unable to perform the query.");
            System.err.println(e.getMessage());
            return null;
        }

        List<User> returnMe = new ArrayList<>();
        for(Item item: responseItems) {
            String followeeAlias = (String) item.get("followee_handle");
            User u = GetUser.getUser(followeeAlias);
            returnMe.add(u);
        }
        return returnMe;
    }

    public static List<User> queryFollowersSorted(FollowersRequest request) {

        String followerAlias = null;
        if(request.getLastFollower() != null) {
            followerAlias = request.getLastFollower().getAlias();
        }
        String followeeAlias = request.getUser().getAlias();
        int limit = request.getLimit();

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#" + PARTITION_KEY, PARTITION_KEY);

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":followee_handle_value", followeeAlias);

        QuerySpec querySpec;
        String keyConditionExpression = "#" + PARTITION_KEY + " = :followee_handle_value";

        if(followerAlias == null) {
            // Need the first ten users.
            querySpec = new QuerySpec()
                    .withMaxResultSize(limit)
                    .withScanIndexForward(true)
                    .withKeyConditionExpression(keyConditionExpression)
                    .withNameMap(nameMap)
                    .withValueMap(valueMap);
        } else {
            // Need only the next ten users.
            querySpec = new QuerySpec()
                    .withExclusiveStartKey("followee_handle", followeeAlias, "follower_handle", followerAlias)
                    .withMaxResultSize(limit)
                    .withScanIndexForward(true)
                    .withKeyConditionExpression(keyConditionExpression)
                    .withNameMap(nameMap)
                    .withValueMap(valueMap);
        }

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        List<Item> responseItems = new ArrayList<>();

        try {
            Table table = DB.getDatabase(TABLE_NAME);
            Index index = table.getIndex("follows_index");
            items = index.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                responseItems.add(iterator.next());
            }
        } catch (Exception e) {
            System.err.println("Unable to perform the query.");
            System.err.println(e.getMessage());
            return null;
        }

        List<User> returnMe = new ArrayList<>();
        for(Item item: responseItems) {
            String tempAlias = (String) item.get("follower_handle");
            User u = GetUser.getUser(tempAlias);
            returnMe.add(u);
        }
        return returnMe;
    }
}
