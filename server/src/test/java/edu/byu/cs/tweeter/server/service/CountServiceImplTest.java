package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.server.dao.CountDAO;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.CountRequest;
import edu.byu.cs.tweeter.shared.service.response.CountResponse;

public class CountServiceImplTest {

    private CountRequest validRequest;
    private CountRequest invalidRequest;
    private CountResponse correctResponse;
    private CountDAO mockCountDAO;
    private CountServiceImpl countServiceImplSpy;
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

        // Setup a mock CountDAO that will return known responses
        correctResponse = new CountResponse(user, FOLLOWING_COUNT, FOLLOWERS_COUNT);
        mockCountDAO = Mockito.mock(CountDAO.class);
        Mockito.when(mockCountDAO.getCount(validRequest)).thenReturn(correctResponse);

        countServiceImplSpy = Mockito.spy(CountServiceImpl.class);
        Mockito.when(countServiceImplSpy.getCountDAO()).thenReturn(mockCountDAO);
    }

    /**
     * Verify that the {@link CountServiceImpl#getCount(CountRequest)}
     * method returns the same result as the {@link CountDAO} class.
     */
    @Test
    public void testGetCount_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        CountResponse response = countServiceImplSpy.getCount(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetCount_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            countServiceImplSpy.getCount(invalidRequest);
        });
    }
}