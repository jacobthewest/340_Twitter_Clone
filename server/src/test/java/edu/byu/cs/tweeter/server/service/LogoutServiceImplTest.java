package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.UUID;

import edu.byu.cs.tweeter.server.dao.LogoutDAO;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.service.response.LogoutResponse;

public class LogoutServiceImplTest {

    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;
    private LogoutResponse correctResponse;
    private LogoutDAO mockLogoutDAO;
    private LogoutServiceImpl logoutServiceImplSpy;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";

    @BeforeEach
    public void setup() {
        User user = new User("Test", "User", MIKE, "password");
        AuthToken authToken = new AuthToken("@TestUser");
        String id = UUID.randomUUID().toString();
        authToken.setId(id);

        // Setup a request object to use in the tests
        validRequest = new LogoutRequest(user, authToken);
        authToken.setUsername("@InvalidUser");
        invalidRequest = new LogoutRequest(user, authToken);

        // Setup a mock LogoutDAO that will return known responses
        authToken.setUsername("@TestUser");
        correctResponse = new LogoutResponse(user, authToken);
        mockLogoutDAO = Mockito.mock(LogoutDAO.class);
        Mockito.when(mockLogoutDAO.logout(validRequest)).thenReturn(correctResponse);

        logoutServiceImplSpy = Mockito.spy(LogoutServiceImpl.class);
        Mockito.when(logoutServiceImplSpy.getLogoutDAO()).thenReturn(mockLogoutDAO);
    }

    @Test
    public void testLogout_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceImplSpy.logout(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testLogout_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            logoutServiceImplSpy.logout(invalidRequest);
        });
    }
}