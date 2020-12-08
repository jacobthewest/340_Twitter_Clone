package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.CountServiceProxy;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.CountRequest;
import edu.byu.cs.tweeter.shared.service.response.CountResponse;

public class CountIntegrationTest {

    private CountRequest validRequest;
    private CountRequest invalidRequest;
    private CountResponse correctResponse;
    private CountServiceProxy countServiceProxy;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private static final int FOLLOWING_COUNT = 29;
    private static final int FOLLOWERS_COUNT = 29;

    @BeforeEach
    public void setup() {
        User user = new User("Test", "User", MIKE, "password");
        User invalidUser = new User("Invalid", "User", MIKE, "password");

        // Setup a request object to use in the tests
        validRequest = new CountRequest(user);
        invalidRequest = new CountRequest(invalidUser);

        correctResponse = new CountResponse(user, FOLLOWING_COUNT, FOLLOWERS_COUNT);

        countServiceProxy = new CountServiceProxy();
    }

    @Test
    public void testGetCount_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        CountResponse response = countServiceProxy.getCount(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetCount_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        try {
            countServiceProxy.getCount(invalidRequest);
        } catch(TweeterRemoteException e) {
            String error = e.getMessage().toString();
            Assertions.assertTrue(error.contains("[InternalServerError]"));
        }
    }
}