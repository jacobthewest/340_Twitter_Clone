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
import edu.byu.cs.tweeter.shared.service.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.service.response.LogoutResponse;

public class LogoutServiceTest {

    private LogoutRequest validRequest;
    private LogoutRequest invalidRequestOne;
    private LogoutRequest invalidRequestTwo;
    private LogoutResponse successResponse;
    private LogoutResponse failureResponse;
    private ServerFacade mockServerFacade;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        String username = "@TestUser";
        String password = "password";
        User user = new User("Test", "User", "https://i.imgur.com/VZQQiQ1.jpg", "password");
        AuthToken authTokenMatching = new AuthToken(username);
        AuthToken authTokenNotMatching = new AuthToken("@NotMatchingUser");

        // Setup request objects to use in the tests
        validRequest = new LogoutRequest(user, authTokenMatching);
        invalidRequestOne = new LogoutRequest(null, authTokenMatching);
        invalidRequestTwo = new LogoutRequest(user, authTokenNotMatching);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LogoutResponse(user, authTokenMatching);
        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.logout(validRequest, "/logout")).thenReturn(successResponse);

        failureResponse = new LogoutResponse("An exception occured");
        Mockito.when(mockServerFacade.logout(invalidRequestOne, "/logout")).thenReturn(failureResponse);
        Mockito.when(mockServerFacade.logout(invalidRequestTwo, "/logout")).thenReturn(failureResponse);
    }

    @Test
    public void testLogout_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LogoutResponse response = mockServerFacade.logout(validRequest, "/logout");
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testLogout_invalidRequest_emptyUsername() throws IOException, TweeterRemoteException {
        LogoutResponse response = mockServerFacade.logout(invalidRequestOne, "/logout");
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testLogout_invalidRequest_authTokenDoesNotMatch() throws IOException, TweeterRemoteException {
        LogoutResponse response = mockServerFacade.logout(invalidRequestTwo, "/logout");
        Assertions.assertEquals(failureResponse, response);
    }
}
