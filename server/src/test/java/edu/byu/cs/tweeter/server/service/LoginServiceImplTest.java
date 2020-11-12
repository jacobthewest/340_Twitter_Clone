package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.UUID;

import edu.byu.cs.tweeter.server.dao.LoginDAO;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.LoginRequest;
import edu.byu.cs.tweeter.shared.service.response.LoginResponse;

public class LoginServiceImplTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;
    private LoginResponse correctResponse;
    private LoginDAO mockLoginDAO;
    private LoginServiceImpl loginServiceImplSpy;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";

    @BeforeEach
    public void setup() {
        User user = new User("Test", "User", MIKE, "password");
        AuthToken authToken = new AuthToken("@TestUser");
        String id = UUID.randomUUID().toString();
        authToken.setId(id);

        // Setup a request object to use in the tests
        validRequest = new LoginRequest("@TestUser", "password");
        invalidRequest = new LoginRequest("@InvalidUser", "password");

        // Setup a mock LoginDAO that will return known responses
        correctResponse = new LoginResponse(user, authToken);
        mockLoginDAO = Mockito.mock(LoginDAO.class);
        Mockito.when(mockLoginDAO.login(validRequest)).thenReturn(correctResponse);

        loginServiceImplSpy = Mockito.spy(LoginServiceImpl.class);
        Mockito.when(loginServiceImplSpy.getLoginDAO()).thenReturn(mockLoginDAO);
    }

    @Test
    public void testLogin_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceImplSpy.login(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testLogin_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            loginServiceImplSpy.login(invalidRequest);
        });
    }
}

