package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.service.FollowersServiceProxy;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.service.response.FollowersResponse;

public class FollowersIntegrationTest {

    private FollowersRequest validRequest;
    private FollowersRequest invalidRequest;
    private FollowersResponse correctResponse;
    private FollowersServiceProxy followersServiceProxy;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private final User JacobWest = new User("Jacob", "West", MIKE, "password");
    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL, "password");
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL, "password");
    private final User TestUser = new User("Test", "User", MALE_IMAGE_URL, "password");

    @BeforeEach
    public void setup() {
        // Setup a request object to use in the tests
        validRequest = new FollowersRequest(TestUser, 3, null);
        invalidRequest = new FollowersRequest(null, -1, null);

        // Setup a mock FollowersDAO that will return known responses
        correctResponse = new FollowersResponse(Arrays.asList(user3, JacobWest, user1), false);

        followersServiceProxy = new FollowersServiceProxy();
    }

    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowersResponse response = followersServiceProxy.getFollowers(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetFollowers_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        try {
            followersServiceProxy.getFollowers(invalidRequest);
        } catch(TweeterRemoteException e) {
            String error = e.getMessage().toString();
            Assertions.assertTrue(error.contains("[BadRequest]"));
        }
    }
}
