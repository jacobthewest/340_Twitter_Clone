package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.server.dao.RetrieveUserDAO;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public class RetrieveUserServiceImplTest {

    private RetrieveUserRequest validRequest;
    private RetrieveUserRequest invalidRequest;
    private RetrieveUserResponse correctResponse;
    private RetrieveUserDAO mockRetrieveUserDAO;
    private RetrieveUserServiceImpl retrieveUserServiceImplSpy;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";

    @BeforeEach
    public void setup() {
        User user = new User("Test", "User", MIKE, "password");

        // Setup a request object to use in the tests
        validRequest = new RetrieveUserRequest("@TestUser");
        invalidRequest = new RetrieveUserRequest(null);

        // Setup a mock RetrieveUserDAO that will return known responses
        correctResponse = new RetrieveUserResponse(user);
        mockRetrieveUserDAO = Mockito.mock(RetrieveUserDAO.class);
        Mockito.when(mockRetrieveUserDAO.retrieveUser(validRequest)).thenReturn(correctResponse);

        retrieveUserServiceImplSpy = Mockito.spy(RetrieveUserServiceImpl.class);
        Mockito.when(retrieveUserServiceImplSpy.getRetrieveUserDAO()).thenReturn(mockRetrieveUserDAO);
    }

    @Test
    public void testGetRetrieveUser_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        RetrieveUserResponse response = retrieveUserServiceImplSpy.retrieveUser(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetRetrieveUser_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            retrieveUserServiceImplSpy.retrieveUser(invalidRequest);
        });
    }
}
