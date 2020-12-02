package edu.byu.cs.tweeter.client.model.service;

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

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.FeedRequest;
import edu.byu.cs.tweeter.shared.service.response.FeedResponse;

public class FeedServiceProxyTest {

    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private FeedRequest validRequest;
    private FeedRequest invalidRequestOne;
    private FeedRequest invalidRequestTwo;
    private FeedResponse successResponse;
    private FeedResponse failureResponse;
    private ServerFacade mockServerFacade;
    private FeedServiceProxy feedServiceProxy;
    private String imageUrl = "https://i.imgur.com/VZQQiQ1.jpg";
    private final User JacobWest = new User("Jacob", "West", MIKE, "password");
    private final User RickyMartin = new User("Ricky", "Martin",  MIKE, "password");
    private final User theMedia = new User("the", "Media", MIKE, "password");
    private final User BillBelichick = new User("Bill", "Belichick", MIKE, "password");
    private final User Rudy = new User("Rudy", "Gobert", MIKE, "password");

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("Test", "User", null, "password");
        List<Status> feed = getFeed();

        // Setup request objects to use in the tests
        validRequest = new FeedRequest(currentUser, 5, null);
        invalidRequestOne = new FeedRequest(null, 5, null);
        invalidRequestTwo = new FeedRequest(currentUser, -1, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FeedResponse(feed, false);
        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFeed(validRequest, "/feed")).thenReturn(successResponse);

        failureResponse = new FeedResponse("An exception occured");
        Mockito.when(mockServerFacade.getFeed(invalidRequestOne, "/feed")).thenReturn(failureResponse);
        Mockito.when(mockServerFacade.getFeed(invalidRequestTwo, "/feed")).thenReturn(failureResponse);

        // Create a CountServiceProxy instance and wrap it with a spy that will use the mock edu.byu.cs.tweeter.server.service
        feedServiceProxy = Mockito.spy(new FeedServiceProxy());
        Mockito.when(feedServiceProxy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFeed_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceProxy.getFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFeed_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceProxy.getFeed(validRequest);

        for(Status status : response.getStatuses()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(status.getUser().getImageUrl());
            status.getUser().setImageBytes(bytes);
            Assertions.assertNotNull(status.getUser().getImageBytes());
        }
    }

    @Test
    public void testGetFeed_invalidRequest_nullUser() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceProxy.getFeed(invalidRequestOne);
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testGetFeed_invalidRequest_negativeLimit() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceProxy.getFeed(invalidRequestTwo);
        Assertions.assertEquals(failureResponse, response);
    }

    private List<Status> getFeed() {
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

        // --------------------- 3 --------------------- //
        String mThree = "@JacobWest";
        d = createDate(2019, 3, 16, 3, 34);
        String c = dateFormat.format(d);
        s = new Status(theMedia, "Dolphins @JacobWest have Tua", "", c, mThree);
        feed.add(s);

        // --------------------- 4 --------------------- //
        d = createDate(2014, 7, 30, 17, 01);
        String de = dateFormat.format(d);
        s = new Status(JacobWest, "Jacksonville will draft third", "", de, "");
        feed.add(s);

        // --------------------- 5 --------------------- //
        String uFive = "dell.com";
        d = createDate(2012, 3, 3, 18, 21);
        String e = dateFormat.format(d);
        s = new Status(RickyMartin, "I endorse dell.com", uFive, e, "");
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
