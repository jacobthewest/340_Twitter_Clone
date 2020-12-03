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

    public static List<Item> queryFollowingSorted(String followingAlias, String followerAlias) {

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#follower_handle", SORT_KEY);

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":follower_handle_value", followingAlias);

        QuerySpec querySpec;
        String keyConditionExpression = "#" + SORT_KEY + " = :follower_handle_value";

        if(followerAlias == null) {
            // Need the first ten users.
            querySpec = new QuerySpec()
                    .withMaxResultSize(10)
                    .withScanIndexForward(false)
                    .withKeyConditionExpression(keyConditionExpression)
                    .withNameMap(nameMap)
                    .withValueMap(valueMap);
        } else {
            // Need only the next ten users.
            querySpec = new QuerySpec()
                    .withExclusiveStartKey("followee_handle", followerAlias, "follower_handle", followingAlias)
                    .withMaxResultSize(10)
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
        }

        /** TODO: Put this code up a layer into the dao's so we know what to use as our lastEvaluatatedKey **/
//        Item lastItem = responseItems.get(responseItems.size() - 1);
//        Map<String, String> lastEvaluatedKey = new HashMap<>();
//        lastEvaluatedKey.put("follower_handle", lastItem.getString("follower_handle"));
//        lastEvaluatedKey.put("followee_handle", lastItem.getString("followee_handle"));

        return responseItems;
    }
}
