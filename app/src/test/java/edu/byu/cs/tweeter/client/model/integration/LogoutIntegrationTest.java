package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LogoutServiceProxy;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.service.response.LogoutResponse;

public class LogoutIntegrationTest {

    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;
    private LogoutResponse correctResponse;
    private LogoutServiceProxy logoutServiceProxy;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private final String id = "37971095-e717-41ff-b249-1af46eb3c849";

    @BeforeEach
    public void setup() {
        User user = new User("Test", "User", MIKE, "password");
        AuthToken authToken = new AuthToken("@TestUser");
        authToken.setId(id);

        // Setup a request object to use in the tests
        validRequest = new LogoutRequest(user, authToken);
        authToken.setUsername("@InvalidUser");
        invalidRequest = new LogoutRequest(user, authToken);

        // Setup a mock LogoutDAO that will return known responses
        authToken.setUsername("@TestUser");
        correctResponse = new LogoutResponse(user, authToken);

        logoutServiceProxy = new LogoutServiceProxy();
    }

    @Test
    public void testLogout_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceProxy.logout(validRequest);
        response.getAuthToken().setId(id);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testLogout_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        try {
            logoutServiceProxy.logout(invalidRequest);
        } catch(TweeterRemoteException e) {
            String error = e.getMessage().toString();
            Assertions.assertTrue(error.contains("[BadRequest]"));
        }
    }
}