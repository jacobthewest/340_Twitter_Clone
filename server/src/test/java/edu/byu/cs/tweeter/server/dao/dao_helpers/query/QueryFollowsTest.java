package edu.byu.cs.tweeter.server.dao.dao_helpers.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        // run the function putFollowsDataForTest(); in the DbPopulator class.
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
}
