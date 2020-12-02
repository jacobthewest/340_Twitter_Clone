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
import edu.byu.cs.tweeter.shared.service.request.StoryRequest;
import edu.byu.cs.tweeter.shared.service.response.StoryResponse;

public class StoryServiceProxyTest {
    private StoryRequest validRequest;
    private StoryRequest invalidRequestOne;
    private StoryRequest invalidRequestTwo;
    private StoryResponse successResponse;
    private StoryResponse failureResponse;
    private ServerFacade mockServerFacade;
    private StoryServiceProxy storyServiceProxy;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        mockServerFacade = Mockito.mock(ServerFacade.class);
        User currentUser = new User("Test", "User", MALE_IMAGE_URL, "password");
        List<Status> story = getStory(currentUser);

        // Setup request objects to use in the tests
        validRequest = new StoryRequest(currentUser, 5, null);
        invalidRequestOne = new StoryRequest(null, 5, null);
        invalidRequestTwo = new StoryRequest(currentUser, -1, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StoryResponse(story, false);
        Mockito.when(mockServerFacade.getStory(validRequest, "/story")).thenReturn(successResponse);

        failureResponse = new StoryResponse("An exception occured");
        Mockito.when(mockServerFacade.getStory(invalidRequestOne, "/story")).thenReturn(failureResponse);
        Mockito.when(mockServerFacade.getStory(invalidRequestTwo, "/story")).thenReturn(failureResponse);

        // Create a CountServiceProxy instance and wrap it with a spy that will use the mock edu.byu.cs.tweeter.server.service
        storyServiceProxy = Mockito.spy(new StoryServiceProxy());
        Mockito.when(storyServiceProxy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceProxy.getStory(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetStory_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceProxy.getStory(validRequest);

        for(Status status : response.getStatuses()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(status.getUser().getImageUrl());
            status.getUser().setImageBytes(bytes);
            Assertions.assertNotNull(status.getUser().getImageBytes());
        }
    }

    @Test
    public void testGetStory_invalidRequest_nullUser() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceProxy.getStory(invalidRequestOne);
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testGetStory_invalidRequest_negativeLimit() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceProxy.getStory(invalidRequestTwo);
        Assertions.assertEquals(failureResponse, response);
    }

    private List<Status> getStory(User definedUser) {
        List<Status> story = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");
        
        // --------------------- 1--------------------- //
        String uOne = "multiply.com";
        String mOne = "@JacobWest @RickyMartin";
        Date d = createDate(2020, 0, 11, 0, 13);
        String a = dateFormat.format(d);
        Status s = new Status(definedUser, "This is a text @JacobWest @RickyMartin multiply.com", uOne, a, mOne);
        story.add(s); // # 1

        // --------------------- 2 --------------------- //
        String uTwo = "tinyurl.com";
        d = createDate(2020, 0, 11, 0, 14);
        String b = dateFormat.format(d);
        s = new Status(definedUser, "You should visit tinyurl.com", uTwo, b, "");
        story.add(s);

        // --------------------- 3 --------------------- //
        String mThree = "@JacobWest";
        d = createDate(2019, 3, 16, 3, 34);
        String c = dateFormat.format(d);
        s = new Status(definedUser, "Dolphins @JacobWest have Tua", "", c, mThree);
        story.add(s);

        // --------------------- 4 --------------------- //
        d = createDate(2014, 7, 30, 17, 01);
        String de = dateFormat.format(d);
        s = new Status(definedUser, "Jacksonville will draft third", "", de, "");
        story.add(s);

        // --------------------- 5 --------------------- //
        String uFive = "dell.com";
        d = createDate(2012, 3, 3, 18, 21);
        String e = dateFormat.format(d);
        s = new Status(definedUser, "I endorse dell.com", uFive, e, "");
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
