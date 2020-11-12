package edu.byu.cs.tweeter.server.service;

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

import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.FeedRequest;
import edu.byu.cs.tweeter.shared.service.response.FeedResponse;

public class FeedServiceImplTest {

    private FeedRequest validRequest;
    private FeedRequest invalidRequest;
    private FeedResponse correctResponse;
    private FeedResponse incorrectResponse;
    private FeedDAO mockFeedDAO;
    private FeedServiceImpl feedServiceImplSpy;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private final User BillBelichick = new User("Bill", "Belichick", MIKE, "password");
    private final User Rudy = new User("Rudy", "Gobert", MIKE, "password");

    @BeforeEach
    public void setup() {
        User user = new User("Test", "User", MIKE, "password");
        int limit = 2;
        Status lastStatus = null;
        List<Status> statuses = getStatuses();

        // Setup a request object to use in the tests
        validRequest = new FeedRequest(user, limit, lastStatus);
        invalidRequest = new FeedRequest(null, limit, lastStatus);

        // Setup a mock FeedDAO that will return known responses
        correctResponse = new FeedResponse(statuses, true);
        mockFeedDAO = Mockito.mock(FeedDAO.class);
        Mockito.when(mockFeedDAO.getFeed(validRequest)).thenReturn(correctResponse);

        feedServiceImplSpy = Mockito.spy(FeedServiceImpl.class);
        Mockito.when(feedServiceImplSpy.getFeedDAO()).thenReturn(mockFeedDAO);
    }

    /**
     * Verify that the {@link FeedServiceImpl#getFeed(FeedRequest)}
     * method returns the same result as the {@link FeedDAO} class.
     */
    @Test
    public void testGetFeed_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceImplSpy.getFeed(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetFeed_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            feedServiceImplSpy.getFeed(invalidRequest);
        });
    }

    private List<Status> getStatuses() {
        List<Status> feed = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        // --------------------- 1--------------------- //
        String uOne = "multiply.com";
        String mOne = "@JacobWest @RickyMartin";
        Date d = createDate(2020, 0, 11, 0, 13);
        String a = dateFormat.format(d);
        Status s = new Status(BillBelichick, "This is a text @JacobWest @RickyMartin multiply.com", uOne, a, mOne);
        feed.add(s); // # 1

        // --------------------- 2 --------------------- //
        String uTwo = "tinyurl.com";
        d = createDate(2020, 0, 11, 0, 14);
        String b = dateFormat.format(d);
        s = new Status(Rudy, "You should visit tinyurl.com", uTwo, b, "");
        feed.add(s);

        return feed;
    }

    private Date createDate(int year, int month, int day, int hour, int minute) {
        Date d = new Date(year - 1900, month, day);
        d.setHours(hour);
        d.setMinutes(minute);
        return d;
    }
}
