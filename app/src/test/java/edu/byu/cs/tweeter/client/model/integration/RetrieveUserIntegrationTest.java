package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.RetrieveUserServiceProxy;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public class RetrieveUserIntegrationTest {

    private RetrieveUserRequest validRequest;
    private RetrieveUserRequest invalidRequest;
    private RetrieveUserResponse correctResponse;
    private RetrieveUserServiceProxy retrieveUserServiceProxy;
    private static final String MIKE = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    @BeforeEach
    public void setup() {
        User user = new User("Test", "User", MIKE, "password");

        // Setup a request object to use in the tests
        validRequest = new RetrieveUserRequest("@TestUser");
        invalidRequest = new RetrieveUserRequest(null);

        // Setup a mock RetrieveUserDAO that will return known responses
        correctResponse = new RetrieveUserResponse(user);

        retrieveUserServiceProxy = new RetrieveUserServiceProxy();
    }

    @Test
    public void testGetRetrieveUser_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        RetrieveUserResponse response = retrieveUserServiceProxy.retrieveUser(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetRetrieveUser_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        try {
            retrieveUserServiceProxy.retrieveUser(invalidRequest);
        } catch(TweeterRemoteException e) {
            String error = e.getMessage().toString();
            Assertions.assertTrue(error.contains("[BadRequest]"));
        }
    }
}
