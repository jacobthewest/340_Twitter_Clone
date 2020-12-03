package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.CountRequest;
import edu.byu.cs.tweeter.shared.service.response.CountResponse;

public class CountDaoTest {
    public CountDAO countDAO;
    public CountRequest validRequest;
    public CountRequest invalidRequest;
    public User permanentTestUser;
    public User dneUser;
    public static final int CORRECT_COUNT = 13;
    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";

    @BeforeEach
    /**
     * Do this ony once if you are running this as an isolated test.
     */
    public void setup() {
//        QueryFollowsTest queryFollowsTest = new QueryFollowsTest();
//        queryFollowsTest.putFollowsDataForTest();
        permanentTestUser = new User("Permanent Test", "User", "ImageUrl", "password");
        permanentTestUser.setAlias(PERMANENT_TEST_USER);

        dneUser = new User("I don't", "Exist", "ImageUrl", "password");
        dneUser.setAlias(UUID.randomUUID().toString()); // Make sure it will never exist in the database.


        countDAO = new CountDAO();
        validRequest = new CountRequest(permanentTestUser);
        invalidRequest = new CountRequest(dneUser);
    }

    @Test
    public void testGetCountValidRequest() {
        CountResponse validCountResponse = countDAO.getCount(validRequest);
        Assertions.assertTrue(validCountResponse.getSuccess());
        Assertions.assertEquals(validCountResponse.getFollowersCount(), CORRECT_COUNT);
        Assertions.assertEquals(validCountResponse.getFollowingCount(), CORRECT_COUNT);
    }

    @Test
    public void testGetCountInvalidRequest() {
        CountResponse invalidCountResponse = countDAO.getCount(invalidRequest);
        Assertions.assertEquals(invalidCountResponse.getFollowersCount(), 0);
        Assertions.assertEquals(invalidCountResponse.getFollowingCount(), 0);
    }
}
