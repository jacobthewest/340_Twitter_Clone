package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.UUID;

import edu.byu.cs.tweeter.server.dao.RegisterDAO;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.service.response.RegisterResponse;

public class RegisterServiceImplTest {

    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;
    private RegisterResponse correctResponse;
    private RegisterDAO mockRegisterDAO;
    private RegisterServiceImpl registerServiceImplSpy;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private static final int FOLLOWING_COUNT = 29;
    private static final int FOLLOWERS_COUNT = 29;

    @BeforeEach
    public void setup() {
        User user = new User("New", "User", MIKE, "password");
        user.setImageBytesAsString("A legit image byte string... hehe.");
        AuthToken authToken = new AuthToken("@NewUser");
        String id = UUID.randomUUID().toString();
        authToken.setId(id);
        User invalidUser = new User(null, "User", MIKE, "password");

        // Setup a request object to use in the tests
        validRequest = new RegisterRequest(user);
        invalidRequest = new RegisterRequest(invalidUser);

        // Setup a mock RegisterDAO that will return known responses
        correctResponse = new RegisterResponse(user, authToken);
        mockRegisterDAO = Mockito.mock(RegisterDAO.class);
        Mockito.when(mockRegisterDAO.register(validRequest)).thenReturn(correctResponse);

        registerServiceImplSpy = Mockito.spy(RegisterServiceImpl.class);
        Mockito.when(registerServiceImplSpy.getRegisterDAO()).thenReturn(mockRegisterDAO);
    }

    @Test
    public void testGetRegister_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceImplSpy.register(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetRegister_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            registerServiceImplSpy.register(invalidRequest);
        });
    }
}