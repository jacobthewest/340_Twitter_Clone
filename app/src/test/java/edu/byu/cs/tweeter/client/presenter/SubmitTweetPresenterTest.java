package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.SubmitTweetService;
import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

public class SubmitTweetPresenterTest {
    private SubmitTweetRequest request;
    private SubmitTweetResponse response;
    private SubmitTweetService mockSubmitTweetService;
    private SubmitTweetPresenter presenter;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User user = new User("Test", "User", MIKE, "password");
        Status status = new Status(user, "This is a tweet", "", getDate(), "@MikeTyson");

        request = new SubmitTweetRequest(user, status);
        response = new SubmitTweetResponse(user, status);

        // Create a mock FollowersService
        mockSubmitTweetService = Mockito.mock(SubmitTweetService.class);
        Mockito.when(mockSubmitTweetService.submitTweet(request)).thenReturn(response);

        // Wrap a FollowersPresenter in a spy that will use the mock edu.byu.cs.tweeter.shared.service.
        presenter = Mockito.spy(new SubmitTweetPresenter(new SubmitTweetPresenter.View() {}));
        Mockito.when(presenter.getSubmitTweetService()).thenReturn(mockSubmitTweetService);
    }

    @Test
    public void testSubmitTweet_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockSubmitTweetService.submitTweet(request)).thenReturn(response);
        Assertions.assertEquals(response, presenter.submitTweet(request));
    }

    @Test
    public void testSubmitTweet_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockSubmitTweetService.submitTweet(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.submitTweet(request);
        });
    }

    private Date createDate(int year, int month, int day, int hour, int minute) {
        Date d = new Date(year - 1900, month, day);
        d.setHours(hour);
        d.setMinutes(minute);
        return d;
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");
        Date d = createDate(2020, 0, 11, 0, 13);
        return dateFormat.format(d);
    }
}
