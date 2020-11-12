package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.service.response.FollowingResponse;

public class FollowingServiceImplTest {

    private FollowingRequest validRequest;
    private FollowingRequest invalidRequest;
    private FollowingResponse correctResponse;
    private FollowingResponse incorrectResponse;
    private FollowingDAO mockFollowingDAO;
    private FollowingServiceImpl followingServiceImplSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null, "");

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", "");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png", "");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png", "");

        // Setup a request object to use in the tests
        validRequest = new FollowingRequest(currentUser, 3, null);
        invalidRequest = new FollowingRequest(null, -1, null);

        // Setup a mock FollowingDAO that will return known responses
        correctResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        mockFollowingDAO = Mockito.mock(FollowingDAO.class);
        Mockito.when(mockFollowingDAO.getFollowees(validRequest)).thenReturn(correctResponse);
        Mockito.when(mockFollowingDAO.getFollowees(invalidRequest)).thenReturn(incorrectResponse);

        followingServiceImplSpy = Mockito.spy(FollowingServiceImpl.class);
        Mockito.when(followingServiceImplSpy.getFollowingDAO()).thenReturn(mockFollowingDAO);
    }

    /**
     * Verify that the {@link FollowingServiceImpl#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link FollowingDAO} class.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceImplSpy.getFollowees(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetFollowees_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            followingServiceImplSpy.getFollowees(invalidRequest);
        });
    }
}
