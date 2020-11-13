package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import edu.byu.cs.tweeter.client.model.service.LoginServiceProxy;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.LoginRequest;
import edu.byu.cs.tweeter.shared.service.response.LoginResponse;

public class LoginIntegrationTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;
    private LoginResponse correctResponse;
    private LoginServiceProxy loginServiceProxy;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private String id;

    @BeforeEach
    public void setup() {
        User user = new User("Test", "User", MIKE, "password");
        AuthToken authToken = new AuthToken("@TestUser");
        id = UUID.randomUUID().toString();
        authToken.setId(id);

        // Setup a request object to use in the tests
        validRequest = new LoginRequest("@TestUser", "password");
        invalidRequest = new LoginRequest("@InvalidUser", "password");

        // Setup a mock LoginDAO that will return known responses
        correctResponse = new LoginResponse(user, authToken);

        loginServiceProxy = new LoginServiceProxy();
    }

    @Test
    public void testLogin_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceProxy.login(validRequest);
        response.getAuthToken().setId(id);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testLogin_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        try {
            loginServiceProxy.login(invalidRequest);
        } catch(TweeterRemoteException e) {
            String error = e.getMessage().toString();
            Assertions.assertTrue(error.contains("[InternalServerError]"));
        }
    }
}

