package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.FeedService;
import edu.byu.cs.tweeter.shared.service.request.FeedRequest;
import edu.byu.cs.tweeter.shared.service.response.FeedResponse;

public class StoryPresenterTest {
    private FeedRequest request;
    private FeedResponse response;
    private FeedService mockFeedService;
    private FeedPresenter presenter;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private final User BillBelichick = new User("Bill", "Belichick", MIKE, "password");
    private final User Rudy = new User("Rudy", "Gobert", MIKE, "password");

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User user = new User("Test", "User", MIKE, "password");
        int limit = 2;
        Status lastStatus = null;
        List<Status> statuses = getStatuses();

        request = new FeedRequest(user, limit, lastStatus);
        response = new FeedResponse(statuses, true);

        // Create a mock FollowersService
        mockFeedService = Mockito.mock(FeedService.class);
        Mockito.when(mockFeedService.getFeed(request)).thenReturn(response);

        // Wrap a FollowersPresenter in a spy that will use the mock edu.byu.cs.tweeter.shared.service.
        presenter = Mockito.spy(new FeedPresenter(new FeedPresenter.View() {}));
        Mockito.when(presenter.getFeedService()).thenReturn(mockFeedService);
    }

    @Test
    public void testGetStory_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFeedService.getFeed(request)).thenReturn(response);
        Assertions.assertEquals(response, presenter.getFeed(request));
    }

    @Test
    public void testGetStory_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFeedService.getFeed(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getFeed(request);
        });
    }

    private List<Status> getStatuses() {
        List<Status> story = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        // --------------------- 1--------------------- //
        String uOne = "multiply.com";
        String mOne = "@JacobWest @RickyMartin";
        Date d = createDate(2020, 0, 11, 0, 13);
        String a = dateFormat.format(d);
        Status s = new Status(BillBelichick, "This is a text @JacobWest @RickyMartin multiply.com", uOne, a, mOne);
        story.add(s); // # 1

        // --------------------- 2 --------------------- //
        String uTwo = "tinyurl.com";
        d = createDate(2020, 0, 11, 0, 14);
        String b = dateFormat.format(d);
        s = new Status(Rudy, "You should visit tinyurl.com", uTwo, b, "");
        story.add(s);

        return story;
    }

    private Date createDate(int year, int month, int day, int hour, int minute) {
        Date d = new Date(year - 1900, month, day);
        d.setHours(hour);
        d.setMinutes(minute);
        return d;
    }
}
