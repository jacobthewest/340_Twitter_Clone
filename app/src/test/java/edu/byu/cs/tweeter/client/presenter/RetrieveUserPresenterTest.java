package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.RetrieveUserService;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public class RetrieveUserPresenterTest {
    private RetrieveUserRequest request;
    private RetrieveUserResponse response;
    private RetrieveUserService mockRetrieveUserService;
    private RetrieveUserPresenter presenter;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User user = new User("Test", "User", MIKE, "password");

        request = new RetrieveUserRequest("@TestUser");
        response = new RetrieveUserResponse(user);

        // Create a mock FollowersService
        mockRetrieveUserService = Mockito.mock(RetrieveUserService.class);
        Mockito.when(mockRetrieveUserService.retrieveUser(request)).thenReturn(response);

        // Wrap a FollowersPresenter in a spy that will use the mock edu.byu.cs.tweeter.shared.service.
        presenter = Mockito.spy(new RetrieveUserPresenter(new RetrieveUserPresenter.View() {}));
        Mockito.when(presenter.getRetrieveUserService()).thenReturn(mockRetrieveUserService);
    }

    @Test
    public void testGetRetrieveUser_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockRetrieveUserService.retrieveUser(request)).thenReturn(response);
        Assertions.assertEquals(response, presenter.retrieveUser(request));
    }

    @Test
    public void testGetRetrieveUser_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockRetrieveUserService.retrieveUser(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.retrieveUser(request);
        });
    }
}
