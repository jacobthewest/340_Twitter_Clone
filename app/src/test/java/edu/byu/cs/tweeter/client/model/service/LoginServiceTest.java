package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.LoginRequest;
import edu.byu.cs.tweeter.shared.service.response.LoginResponse;

public class LoginServiceTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequestOne;
    private LoginRequest invalidRequestTwo;
    private LoginResponse successResponse;
    private LoginResponse failureResponse;
    private ServerFacade mockServerFacade;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        String username = "@TestUser";
        String password = "password";
        User user = new User("Test", "User", "https://i.imgur.com/VZQQiQ1.jpg", "password");
        AuthToken authToken = new AuthToken(username);

        // Setup request objects to use in the tests
        validRequest = new LoginRequest(username, password);
        invalidRequestOne = new LoginRequest("", "password");
        invalidRequestTwo = new LoginRequest("@TestUser", "");

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LoginResponse(user, authToken);
        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.login(validRequest, "/login")).thenReturn(successResponse);

        failureResponse = new LoginResponse("An exception occured");
        Mockito.when(mockServerFacade.login(invalidRequestOne, "/login")).thenReturn(failureResponse);
        Mockito.when(mockServerFacade.login(invalidRequestTwo, "/login")).thenReturn(failureResponse);
    }

    @Test
    public void testLogin_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = mockServerFacade.login(validRequest, "/login");
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testLogin_invalidRequest_emptyUsername() throws IOException, TweeterRemoteException {
        LoginResponse response = mockServerFacade.login(invalidRequestOne, "/login");
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testLogin_invalidRequest_emptyAuthToken() throws IOException, TweeterRemoteException {
        LoginResponse response = mockServerFacade.login(invalidRequestTwo, "/login");
        Assertions.assertEquals(failureResponse, response);
    }
}
