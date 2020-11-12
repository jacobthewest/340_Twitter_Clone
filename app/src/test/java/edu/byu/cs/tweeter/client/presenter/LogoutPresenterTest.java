package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.UUID;

import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.LogoutService;
import edu.byu.cs.tweeter.shared.service.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.service.response.LogoutResponse;

public class LogoutPresenterTest {
    private LogoutRequest request;
    private LogoutResponse response;
    private LogoutService mockLogoutService;
    private LogoutPresenter presenter;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User user = new User("Test", "User", MIKE, "password");
        AuthToken authToken = new AuthToken("@TestUser");
        String id = UUID.randomUUID().toString();
        authToken.setId(id);

        request = new LogoutRequest(user, authToken);
        authToken.deactivate();
        response = new LogoutResponse(user, authToken);

        // Create a mock FollowersService
        mockLogoutService = Mockito.mock(LogoutService.class);
        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);

        // Wrap a FollowersPresenter in a spy that will use the mock edu.byu.cs.tweeter.shared.service.
        presenter = Mockito.spy(new LogoutPresenter(new LogoutPresenter.View() {}));
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutService);
    }

    @Test
    public void testGetLogout_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);
        Assertions.assertEquals(response, presenter.logout(request));
    }

    @Test
    public void testGetLogout_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockLogoutService.logout(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.logout(request);
        });
    }
}
