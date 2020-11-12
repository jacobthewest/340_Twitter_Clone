package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.byu.cs.tweeter.server.dao.SubmitTweetDAO;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

public class SubmitTweetServiceImplTest {

    private SubmitTweetRequest validRequest;
    private SubmitTweetRequest invalidRequest;
    private SubmitTweetResponse correctResponse;
    private SubmitTweetDAO mockSubmitTweetDAO;
    private SubmitTweetServiceImpl submitTweetServiceImplSpy;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";

    @BeforeEach
    public void setup() {
        User user = new User("Test", "User", MIKE, "password");
        Status status = new Status(user, "This is a tweet", "", getDate(), "@MikeTyson");

        // Setup a request object to use in the tests
        validRequest = new SubmitTweetRequest(user, status);
        invalidRequest = new SubmitTweetRequest(null, null);

        // Setup a mock SubmitTweetDAO that will return known responses
        correctResponse = new SubmitTweetResponse(user, status);
        mockSubmitTweetDAO = Mockito.mock(SubmitTweetDAO.class);
        Mockito.when(mockSubmitTweetDAO.submitTweet(validRequest)).thenReturn(correctResponse);

        submitTweetServiceImplSpy = Mockito.spy(SubmitTweetServiceImpl.class);
        Mockito.when(submitTweetServiceImplSpy.getSubmitTweetDAO()).thenReturn(mockSubmitTweetDAO);
    }

    @Test
    public void testGetSubmitTweet_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        SubmitTweetResponse response = submitTweetServiceImplSpy.submitTweet(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetSubmitTweet_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            submitTweetServiceImplSpy.submitTweet(invalidRequest);
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