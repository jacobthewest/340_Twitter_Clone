package edu.byu.cs.tweeter.server.dao.dao_helpers.query;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryFollowsTest {

    public static final String FOLLOWER_NAME = "follower_name";
    public static final String FOLLOWEE_NAME = "followee_name";
    public static final String PARTITION_KEY = "followee_handle"; // AKA the primary key.
    public static final String SORT_KEY = "follower_handle";  // AKA the range key.
    public static final String TABLE_NAME = "follows";
    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";

    @BeforeEach
    public void setup() {
        // putFollowsDataForTest(); If we are running this test suite for the first time then we need to
        // uncomment this so we have the requisite data. putFollowsDataForTest(); takes a while to run...
    }

    @Test
    public void testGetFollowingCount() throws Exception {
        String getFollowingCountForThisAlias = "@PermanentTestUser";

        int result = QueryFollows.getFollowingCount(getFollowingCountForThisAlias);
        Assertions.assertNotEquals(result, -1);
        Assertions.assertEquals(result, 13); // <--- We expect to get 13 back.
    }

    @Test
    public void testGetFollowersCount() throws Exception {
        int result = QueryFollows.getFollowersCount(PERMANENT_TEST_USER);
        Assertions.assertNotEquals(result, -1);
        Assertions.assertEquals(result, 13); // <--- We expect to get 13 back.
    }

    public void putFollowsDataForTest() throws Exception {
        List<List<String>> family = getFamily();

        for (List<String> outerList: family) {
            String follower_handle = outerList.get(0);
            String follower_name = outerList.get(1);

            for (List<String> innerList: family) {
                String followee_handle = innerList.get(0);
                String followee_name = innerList.get(1);

                if(!followee_handle.equals(follower_handle)) {

                    final Map<String, Object> infoMap = new HashMap<String, Object>();
                    infoMap.put(FOLLOWER_NAME, follower_name);
                    infoMap.put(FOLLOWEE_NAME, followee_name);

                    put(followee_handle, followee_name, follower_handle, follower_name);
                }
            }
        }
    }

    public Object put(String followeeHandle, String followeeName, String followerHandle, String followerName) throws Exception {

        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put(FOLLOWER_NAME, followerName);
        infoMap.put(FOLLOWEE_NAME, followeeName);

        PutItemOutcome outcome = (PutItemOutcome) putToDatabase(followeeHandle, followerHandle, infoMap);
        return outcome.getPutItemResult();
    }

    public Object putToDatabase(String followeeHandle, String followerHandle, Map<String, Object> infoMap) {
        try {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                    .withRegion("us-west-2")
                    .build();

            DynamoDB dynamoDB = new DynamoDB(client);

            Table table = dynamoDB.getTable(TABLE_NAME);

            /** Adds json information to the object through "info" **/
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey(PARTITION_KEY, followeeHandle, SORT_KEY, followerHandle).withMap("info", infoMap));
            return outcome;
        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + followeeHandle + " " + followerHandle);
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }

    public List<List<String>> getFamily() {

        List<List<String>> family = new ArrayList<>();

        family.add(new ArrayList<>() {{add("@Dad"); add("Brett West"); }});
        family.add(new ArrayList<>() {{add("@Mom"); add("Holly West"); }});
        family.add(new ArrayList<>() {{add(PERMANENT_TEST_USER); add("Permanent Test User"); }});
        family.add(new ArrayList<>() {{add("@Jenny"); add("Jenny West"); }});
        family.add(new ArrayList<>() {{add("@Allison"); add("Allison Monney"); }});
        family.add(new ArrayList<>() {{add("@Trevor"); add("Trevor Monney"); }});
        family.add(new ArrayList<>() {{add("@Kevin"); add("Kevin West"); }});
        family.add(new ArrayList<>() {{add("@Rachel"); add("Rachel West"); }});
        family.add(new ArrayList<>() {{add("@Melissa"); add("Melissa West"); }});
        family.add(new ArrayList<>() {{add("@Kyle"); add("Kyle West"); }});
        family.add(new ArrayList<>() {{add("@Katelyn"); add("Katelyn West"); }});
        family.add(new ArrayList<>() {{add("@Rebecca"); add("Rebecca West"); }});
        family.add(new ArrayList<>() {{add("@Sarah"); add("Sarah West"); }});
        family.add(new ArrayList<>() {{add("@Tyler"); add("Tyler West"); }});

        return family;
    }

}
