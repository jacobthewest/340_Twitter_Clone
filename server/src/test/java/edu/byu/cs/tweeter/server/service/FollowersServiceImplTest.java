package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.service.response.FollowersResponse;

public class FollowersServiceImplTest {

    private FollowersRequest validRequest;
    private FollowersRequest invalidRequest;
    private FollowersResponse correctResponse;
    private FollowersResponse incorrectResponse;
    private FollowersDAO mockFollowersDAO;
    private FollowersServiceImpl followersServiceImplSpy;

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
        validRequest = new FollowersRequest(currentUser, 3, null);
        invalidRequest = new FollowersRequest(null, -1, null);

        // Setup a mock FollowersDAO that will return known responses
        correctResponse = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        mockFollowersDAO = Mockito.mock(FollowersDAO.class);
        Mockito.when(mockFollowersDAO.getFollowers(validRequest)).thenReturn(correctResponse);

        followersServiceImplSpy = Mockito.spy(FollowersServiceImpl.class);
        Mockito.when(followersServiceImplSpy.getFollowersDAO()).thenReturn(mockFollowersDAO);
    }

    /**
     * Verify that the {@link FollowersServiceImpl#getFollowers(FollowersRequest)}
     * method returns the same result as the {@link FollowersDAO} class.
     */
    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowersResponse response = followersServiceImplSpy.getFollowers(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetFollowers_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            followersServiceImplSpy.getFollowers(invalidRequest);
        });
    }
}
