package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

public class SubmitTweetServiceTest {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private SubmitTweetRequest validRequest;
    private SubmitTweetRequest invalidRequest1;
    private SubmitTweetRequest invalidRequest2;
    private SubmitTweetRequest invalidRequest3;
    private SubmitTweetResponse successResponse;
    private SubmitTweetResponse failureResponse;
    private ServerFacade mockServerFacade;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User recognizedUser = new User("Test", "User", MALE_IMAGE_URL, "password");
        User unRecognizedUser = new User("Not", "Recognized", MALE_IMAGE_URL, "password");
        unRecognizedUser.setAlias("@TestUser");
        Status recognizedStatus = getRecognizedStatus(recognizedUser);
        Status unRecognizedStatus = getUnRecognizedStatus(unRecognizedUser);

        // Setup request objects to use in the tests
        validRequest = new SubmitTweetRequest(recognizedUser, recognizedStatus);
        invalidRequest1 = new SubmitTweetRequest(recognizedUser, unRecognizedStatus);
        invalidRequest2 = new SubmitTweetRequest(null, recognizedStatus);
        invalidRequest3 = new SubmitTweetRequest(recognizedUser, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new SubmitTweetResponse(recognizedUser, recognizedStatus);
        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.submitTweet(validRequest, "/submittweet")).thenReturn(successResponse);

        failureResponse = new SubmitTweetResponse("An exception occured");
        Mockito.when(mockServerFacade.submitTweet(invalidRequest1, "/submittweet")).thenReturn(failureResponse);
        Mockito.when(mockServerFacade.submitTweet(invalidRequest2, "/submittweet")).thenReturn(failureResponse);
        Mockito.when(mockServerFacade.submitTweet(invalidRequest3, "/submittweet")).thenReturn(failureResponse);
    }

    @Test
    public void testSubmitTweet_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        SubmitTweetResponse response = mockServerFacade.submitTweet(validRequest, "/submittweet");
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testSubmitTweet_invalidRequest_userDoesNotMatchStatus() throws IOException, TweeterRemoteException {
        SubmitTweetResponse response = mockServerFacade.submitTweet(invalidRequest1, "/submittweet");
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testSubmitTweet_invalidRequest_userIsNull() throws IOException, TweeterRemoteException {
        SubmitTweetResponse response = mockServerFacade.submitTweet(invalidRequest2, "/submittweet");
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testSubmitTweet_invalidRequest_statusIsNull() throws IOException, TweeterRemoteException {
        SubmitTweetResponse response = mockServerFacade.submitTweet(invalidRequest3, "/submittweet");
        Assertions.assertEquals(failureResponse, response);
    }

    private Status getRecognizedStatus(User user) {
        DateFormat dateFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");
        String mOne = "@JacobWest @RickyMartin";
        Date tempDate = createDate(2020, 0, 11, 0, 13);
        String d = dateFormat.format(tempDate);
        Status s = new Status(user, "Recognized status", "multiply.com", d, mOne);
        return s;
    }

    private Status getUnRecognizedStatus(User user) {
        DateFormat dateFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");
        String uOne = "multiply.com";
        String mOne = "@MartinShort @JamesBond";
        Date tempDate = createDate(2007, 0, 11, 0, 13);
        String d = dateFormat.format(tempDate);
        Status s = new Status(user, "UnRecognized status", uOne, d, mOne);
        return s;
    }

    private Date createDate(int year, int month, int day, int hour, int minute) {
        Date d = new Date(year - 1900, month, day);
        d.setHours(hour);
        d.setMinutes(minute);
        return d;
    }
}
