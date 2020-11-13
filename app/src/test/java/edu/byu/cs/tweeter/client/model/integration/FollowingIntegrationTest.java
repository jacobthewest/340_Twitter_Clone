package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.service.FollowingServiceProxy;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.service.response.FollowingResponse;

public class FollowingIntegrationTest {

    private FollowingRequest validRequest;
    private FollowingRequest invalidRequest;
    private FollowingResponse correctResponse;
    private FollowingServiceProxy followingServiceProxy;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL, "password");
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL, "password");
    private final User theMedia = new User("the", "Media", MIKE, "password");
    private final User TestUser = new User("Test", "User", MALE_IMAGE_URL, "password");


    @BeforeEach
    public void setup() {
        // Setup a request object to use in the tests
        validRequest = new FollowingRequest(TestUser, 3, null);
        invalidRequest = new FollowingRequest(null, -1, null);

        // Setup a mock FollowingDAO that will return known responses
        correctResponse = new FollowingResponse(Arrays.asList(user1, user2, theMedia), false);

        followingServiceProxy = new FollowingServiceProxy();
    }

    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceProxy.getFollowees(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetFollowees_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        try {
            followingServiceProxy.getFollowees(invalidRequest);
        } catch(TweeterRemoteException e) {
            String error = e.getMessage().toString();
            Assertions.assertTrue(error.contains("[BadRequest]"));
        }
    }
}
