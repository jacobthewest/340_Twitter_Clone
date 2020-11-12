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

import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.StoryRequest;
import edu.byu.cs.tweeter.shared.service.response.StoryResponse;

public class StoryServiceImplTest {

    private StoryRequest validRequest;
    private StoryRequest invalidRequest;
    private StoryResponse correctResponse;
    private StoryDAO mockStoryDAO;
    private StoryServiceImpl storyServiceImplSpy;
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
        validRequest = new StoryRequest(user, limit, lastStatus);
        invalidRequest = new StoryRequest(null, limit, lastStatus);

        // Setup a mock StoryDAO that will return known responses
        correctResponse = new StoryResponse(statuses, true);
        mockStoryDAO = Mockito.mock(StoryDAO.class);
        Mockito.when(mockStoryDAO.getStory(validRequest)).thenReturn(correctResponse);

        storyServiceImplSpy = Mockito.spy(StoryServiceImpl.class);
        Mockito.when(storyServiceImplSpy.getStoryDAO()).thenReturn(mockStoryDAO);
    }

    /**
     * Verify that the {@link StoryServiceImpl#getStory(StoryRequest)}
     * method returns the same result as the {@link StoryDAO} class.
     */
    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceImplSpy.getStory(validRequest);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetStory_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            storyServiceImplSpy.getStory(invalidRequest);
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

