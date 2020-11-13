package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StoryServiceProxy;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.StoryRequest;
import edu.byu.cs.tweeter.shared.service.response.StoryResponse;

public class StoryIntegrationTest {

    private StoryRequest validRequest;
    private StoryRequest invalidRequest;
    private StoryResponse correctResponse;
    private StoryServiceProxy storyServiceProxy;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private User user;

    @BeforeEach
    public void setup() {
        user = new User("Test", "User", MIKE, "password");
        int limit = 2;
        Status lastStatus = null;
        List<Status> statuses = getStatuses();

        // Setup a request object to use in the tests
        validRequest = new StoryRequest(user, limit, lastStatus);
        invalidRequest = new StoryRequest(null, limit, lastStatus);

        // Setup a mock StoryDAO that will return known responses
        correctResponse = new StoryResponse(statuses, true);

        storyServiceProxy = new StoryServiceProxy();
    }

    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceProxy.getStory(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetStory_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        try {
            storyServiceProxy.getStory(invalidRequest);
        } catch(TweeterRemoteException e) {
            String error = e.getMessage().toString();
            Assertions.assertTrue(error.contains("[BadRequest]"));
        }
    }

    private List<Status> getStatuses() {
        List<Status> story = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        // --------------------- 1--------------------- //
        String uOne = "multiply.com";
        String mOne = "@JacobWest @RickyMartin";
        Date d = createDate(2020, 0, 11, 0, 13);
        String a = dateFormat.format(d);
        Status s = new Status(user, "This is a text @JacobWest @RickyMartin multiply.com", uOne, a, mOne);
        story.add(s); // # 1

        // --------------------- 2 --------------------- //
        String uTwo = "tinyurl.com";
        d = createDate(2020, 0, 11, 0, 14);
        String b = dateFormat.format(d);
        s = new Status(user, "You should visit tinyurl.com", uTwo, b, "");
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

