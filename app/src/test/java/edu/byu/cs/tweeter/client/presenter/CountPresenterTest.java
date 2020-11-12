package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.CountService;
import edu.byu.cs.tweeter.shared.service.request.CountRequest;
import edu.byu.cs.tweeter.shared.service.response.CountResponse;

public class CountPresenterTest {

    private CountRequest request;
    private CountResponse response;
    private CountService mockCountService;
    private CountPresenter presenter;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private static final int FOLLOWING_COUNT = 29;
    private static final int FOLLOWERS_COUNT = 29;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User user = new User("Test", "User", MIKE, "password");

        request = new CountRequest(user);
        response = new CountResponse(user, FOLLOWING_COUNT, FOLLOWERS_COUNT);

        // Create a mock FollowersService
        mockCountService = Mockito.mock(CountService.class);
        Mockito.when(mockCountService.getCount(request)).thenReturn(response);

        // Wrap a FollowersPresenter in a spy that will use the mock edu.byu.cs.tweeter.shared.service.
        presenter = Mockito.spy(new CountPresenter(new CountPresenter.View() {}));
        Mockito.when(presenter.getCountService()).thenReturn(mockCountService);
    }

    @Test
    public void testGetCount_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockCountService.getCount(request)).thenReturn(response);
        Assertions.assertEquals(response, presenter.getCount(request));
    }

    @Test
    public void testGetCount_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockCountService.getCount(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getCount(request);
        });
    }
}
