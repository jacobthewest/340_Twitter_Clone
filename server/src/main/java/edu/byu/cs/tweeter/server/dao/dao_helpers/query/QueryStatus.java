package edu.byu.cs.tweeter.server.dao.dao_helpers.query;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;
import edu.byu.cs.tweeter.server.dao.dao_helpers.get.GetStatus;
import edu.byu.cs.tweeter.server.dao.dao_helpers.utils.Converter;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.FeedRequest;
import edu.byu.cs.tweeter.shared.service.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.service.request.StoryRequest;
import edu.byu.cs.tweeter.shared.service.response.FollowingResponse;
import edu.byu.cs.tweeter.shared.service.response.StoryResponse;

public class QueryStatus {

    public static final String PARTITION_KEY = "username"; // AKA the primary key.
    public static final String SORT_KEY = "timePosted";  // AKA the range key.
    public static final String TABLE_NAME = "status";
    public static int QUERY_LIMIT = 10;
    public static String INDEX_NAME = "timePosted-username-index";

    public static List<Status> queryStorySorted(StoryRequest request) {

        String timePosted = null;
        if(request.getLastStatus() != null) {
            String requestTimePosted = request.getLastStatus().getTimePosted();
            timePosted = Converter.convertTimePostedToSortableTime(requestTimePosted);
        }
        String storyUsername = request.getUser().getAlias();
        int limit = request.getLimit();

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#username", PARTITION_KEY);

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":username_value", storyUsername);

        QuerySpec querySpec;
        String keyConditionExpression = "#" + PARTITION_KEY + " = :username_value";

        if(timePosted == null) {
            // Need the first ten users.
            querySpec = new QuerySpec()
                    .withMaxResultSize(limit)
                    .withScanIndexForward(false)
                    .withKeyConditionExpression(keyConditionExpression)
                    .withNameMap(nameMap)
                    .withValueMap(valueMap);
        } else {
            // Need only the next ten users.
            querySpec = new QuerySpec()
                    .withExclusiveStartKey("username", storyUsername, "timePosted", timePosted)
                    .withMaxResultSize(limit)
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
            return null;
        }

        List<Status> returnMe = new ArrayList<>();
        for(Item item: responseItems) {
            String tempUsername = (String) item.get("username");
            String tempTimePosted = (String) item.get("timePosted");
            Status s = GetStatus.getStatus(tempUsername, tempTimePosted, false);
            returnMe.add(s);
        }
        return returnMe;
    }

    public static List<Status> queryFeedSorted(FeedRequest request) {

        // I will get all of the statuses that match the following aliases.
        // Then I will sort the statuses by the date. Then I will return the correct statuses.

        List<String> followingAliases = getFollowingAliases(request);
        if(followingAliases == null) {
            return null; // An error happened.
        } else if(followingAliases.size() == 0) {
            return new ArrayList<>();
        }

        // Get the whole status table
        List<FakeStatus> masterFeedList = new ArrayList<>();

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-west-2")
                .build();

        Map<String, AttributeValue> lastKeyEvaluated = null;
        do {
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName("status")
                    .withLimit(5) // idk why, small and frequent is better than big and slow right eh?
                    .withExclusiveStartKey(lastKeyEvaluated);

            ScanResult result = client.scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                // Add only items that have the aliases we follow
                if(followingAliases.contains(item.get("username").getS())) {
                    String timePosted = item.get("timePosted").getS();
                    String username = item.get("username").getS();
                    masterFeedList.add(new FakeStatus(username, timePosted));
                }
            }

            lastKeyEvaluated = result.getLastEvaluatedKey();
        } while (lastKeyEvaluated != null);

        // Sort the masterFeedList;
        Collections.sort(masterFeedList, new SortByTimePosted());

        int i = 0;
        if(request.getLastStatus() != null) {
            String fakeUsername = request.getLastStatus().getUser().getAlias();
            String timePosted = request.getLastStatus().getTimePosted();
            String fakeTimePosted = Converter.convertTimePostedToSortableTime(timePosted);
            FakeStatus lastFakeStatus = new FakeStatus(fakeUsername, fakeTimePosted);
            i = masterFeedList.indexOf(lastFakeStatus);
            i++;
        }

        List<Status> returnMe = new ArrayList<>();
        int endIndex = i + request.getLimit();
        while(i < endIndex) {
            try {
                FakeStatus temp = masterFeedList.get(i);
                Status addMe = GetStatus.getStatus(temp.username, Long.toString(temp.timePosted), false);
                returnMe.add(addMe);
                i++;
            } catch(Exception e) {
                return returnMe;
            }
        }
        return returnMe;
    }

    private static List<String> getFollowingAliases(FeedRequest request) {
        List<String> followingAliases = new ArrayList<>();

        User user = request.getUser();
        User lastFollowee = null;
        int limit = 10;
        boolean hasMorePages = true;

        while(hasMorePages) {
            FollowingRequest followingRequest = new FollowingRequest(user, limit, lastFollowee);
            FollowingResponse followingResponse = new FollowingDAO().getFollowees(followingRequest);
            if(followingResponse == null) {
                return null; // An error happened.
            }
            List<User> followees = followingResponse.getFollowees();
            for(User u: followees) {
                followingAliases.add(u.getAlias());
            }

            if(followees.size() != 0) {
                lastFollowee = followees.get(followees.size() - 1);
            }

            if(followees.size() != limit) {
                hasMorePages = false;
            }
        }

        return followingAliases;
    }

    private static class SortByTimePosted implements Comparator<FakeStatus> {
        @Override
        // Sorts dates descending //
        public int compare(FakeStatus a, FakeStatus b) {
            return Long.compare(b.timePosted, a.timePosted);
        }
    }

    public static class FakeStatus implements Comparable, Serializable {

        public String username;
        public Long timePosted;

        public FakeStatus(String username, String timePosted) {
            this.username = username;
            this.timePosted = Long.parseLong(timePosted);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FakeStatus that = (FakeStatus) o;
            return username.equals(that.username) &&
                    timePosted.equals(that.timePosted);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username, timePosted);
        }

        @Override
        public int compareTo(Object o) {
            return this.toString().compareTo(o.toString());
        }
    }



        private static List<Status> getStatusesFromAlias(String alias) {
        StoryDAO storyDAO = new StoryDAO();
        List<Status> all = new ArrayList<>();

        User user = new User();
        user.setAlias(alias);

        boolean hasMorePages = true;
        Status lastStatus = null;

        while(hasMorePages) {
            StoryRequest request = new StoryRequest(user, 25, lastStatus);
            StoryResponse storyResponse = storyDAO.getStory(request);

            for(Status s: storyResponse.getStatuses()) {
                all.add(s);
            }

            if(!storyResponse.getHasMorePages()) {
                hasMorePages = false;
            }

            lastStatus = storyResponse.getStatuses().get(storyResponse.getStatuses().size() - 1);
        }

        return all;
    }
}
