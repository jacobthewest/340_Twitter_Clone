package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.byu.cs.tweeter.server.util.ListOfStatuses;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.StoryRequest;
import edu.byu.cs.tweeter.shared.service.response.StoryResponse;

public class StoryDaoTest {
    public static final String baseUrl = "https://340tweeter.s3-us-west-2.amazonaws.com/%40";

    @Test
    public void testQueryStorySortedValidNoLastStatus() {
        User user = new User("Permanent Test", "User", baseUrl + "PermanentTestUser", "password");
        user.setAlias("@PermanentTestUser");
        int limit = 10;

        ListOfStatuses tool = new ListOfStatuses();
        List<Status> listOfStatuses = tool.get21StatusesForDaoTests(user);
        List<Status> firstTenStatusesSorted = getFirst10StatusesSorted(listOfStatuses);

        
        StoryRequest validRequest = new StoryRequest(user, limit, null);
        StoryDAO storyDAO = new StoryDAO();
        StoryResponse response = storyDAO.getStory(validRequest);
        Assertions.assertTrue(response.getSuccess());

        List<Status> responseStatuses = response.getStatuses();
        for(int i = 0; i < limit; i++) {
            Assertions.assertEquals(responseStatuses.get(i), firstTenStatusesSorted.get(i));
        }
    }

    @Test
    public void testQueryStorySortedInvalidNoLastStatus() {
        // The user does not exist in the database
        User user = new User("Permanent Test", "User", baseUrl + "PermanentTestUser", "password");
        user.setAlias(UUID.randomUUID().toString());
        int limit = 10;

        StoryRequest invalidRequest = new StoryRequest(user, limit, null);
        StoryDAO storyDAO = new StoryDAO();
        StoryResponse response = storyDAO.getStory(invalidRequest);
        Assertions.assertEquals(response.getStatuses().size(), 0);
    }

    @Test
    public void testQueryStorySortedValidHasLastStatusMiddleTen() {
        User user = new User("Permanent Test", "User", baseUrl + "PermanentTestUser", "password");
        user.setAlias("@PermanentTestUser");
        int limit = 10;

        ListOfStatuses tool = new ListOfStatuses();
        List<Status> listOfStatuses = tool.get21StatusesForDaoTests(user);
        List<Status> middleTenStatusesSorted = getMiddle10StatusesSorted(listOfStatuses);

        Status lastStatus = listOfStatuses.get(13); // Trust me, it's right.

        StoryRequest validRequest = new StoryRequest(user, limit, lastStatus);
        StoryDAO storyDAO = new StoryDAO();
        StoryResponse response = storyDAO.getStory(validRequest);
        Assertions.assertTrue(response.getSuccess());

        List<Status> responseStatuses = response.getStatuses();
        for(int i = 0; i < limit; i++) {
            Assertions.assertEquals(responseStatuses.get(i), middleTenStatusesSorted.get(i));
        }
    }

    @Test
    public void testQueryStorySortedInvalidHasLastStatusMiddleTen() {
        User invalidUser = new User("Permanent Test", "User", baseUrl + "PermanentTestUser", "password");
        invalidUser.setAlias(UUID.randomUUID().toString());
        User validUser = new User("Permanent Test", "User", baseUrl + "PermanentTestUser", "password");
        validUser.setAlias("@PermanentTestUser");
        int limit = 10;

        ListOfStatuses tool = new ListOfStatuses();
        List<Status> listOfStatuses = tool.get21StatusesForDaoTests(validUser); // Need valid to know we have a valid lastStatus
        Status lastStatus = listOfStatuses.get(13); // Trust me, it's right.

        StoryRequest invalidRequest = new StoryRequest(invalidUser, limit, lastStatus);
        StoryDAO storyDAO = new StoryDAO();
        StoryResponse response = storyDAO.getStory(invalidRequest);

        List<Status> following = response.getStatuses();
        Assertions.assertTrue(following.size() == 0);
    }

    @Test
    public void testQueryStorySortedValidHasLastStatusLastOne() {
        User user = new User("Permanent Test", "User", baseUrl + "PermanentTestUser", "password");
        user.setAlias("@PermanentTestUser");
        int limit = 10;

        ListOfStatuses tool = new ListOfStatuses();
        List<Status> listOfStatuses = tool.get21StatusesForDaoTests(user);
        List<Status> lastStatusesSorted = getLastStatusSorted(listOfStatuses);

        Status lastStatus = listOfStatuses.get(8); // Trust me, it's right.

        StoryRequest validRequest = new StoryRequest(user, limit, lastStatus);
        StoryDAO storyDAO = new StoryDAO();
        StoryResponse response = storyDAO.getStory(validRequest);
        Assertions.assertTrue(response.getSuccess());

        List<Status> responseStatuses = response.getStatuses();
        for(int i = 0; i < responseStatuses.size(); i++) {
            Assertions.assertEquals(responseStatuses.get(i), lastStatusesSorted.get(i));
        }
    }

    @Test
    public void testQueryStorySortedInvalidHasLastStatusLastOne() {
        User invalidUser = new User("Permanent Test", "User", baseUrl + "PermanentTestUser", "password");
        invalidUser.setAlias(UUID.randomUUID().toString());
        User validUser = new User("Permanent Test", "User", baseUrl + "PermanentTestUser", "password");
        validUser.setAlias("@PermanentTestUser");
        int limit = 10;

        ListOfStatuses tool = new ListOfStatuses();
        List<Status> listOfStatuses = tool.get21StatusesForDaoTests(validUser); // Need valid to know we have a valid lastStatus
        Status lastStatus = listOfStatuses.get(8); // Trust me, it's right.

        StoryRequest invalidRequest = new StoryRequest(invalidUser, limit, lastStatus);
        StoryDAO storyDAO = new StoryDAO();
        StoryResponse response = storyDAO.getStory(invalidRequest);

        List<Status> following = response.getStatuses();
        Assertions.assertTrue(following.size() == 0);
    }


    private List<Status> getFirst10StatusesSorted(List<Status> listOfStatuses) {
        List<Status> sorted = new ArrayList<>();
        sorted.add(listOfStatuses.get(18));
        sorted.add(listOfStatuses.get(20));
        sorted.add(listOfStatuses.get(19));
        sorted.add(listOfStatuses.get(1));
        sorted.add(listOfStatuses.get(0));
        sorted.add(listOfStatuses.get(9));
        sorted.add(listOfStatuses.get(2));
        sorted.add(listOfStatuses.get(3));
        sorted.add(listOfStatuses.get(12));
        sorted.add(listOfStatuses.get(13));

        return sorted;
    }

    private List<Status> getMiddle10StatusesSorted(List<Status> listOfStatuses) {
        List<Status> sorted = new ArrayList<>();
        sorted.add(listOfStatuses.get(14));
        sorted.add(listOfStatuses.get(15));
        sorted.add(listOfStatuses.get(16));
        sorted.add(listOfStatuses.get(4));
        sorted.add(listOfStatuses.get(17));
        sorted.add(listOfStatuses.get(11));
        sorted.add(listOfStatuses.get(10));
        sorted.add(listOfStatuses.get(7));
        sorted.add(listOfStatuses.get(5));
        sorted.add(listOfStatuses.get(8));

        return sorted;
    }

    private List<Status> getLastStatusSorted (List<Status> listOfStatuses) {
        List<Status> returnMe = new ArrayList<>();
        returnMe.add(listOfStatuses.get(6));
        return returnMe;
    }

//    @Test
//    /**
//     * This one takes like 4 minutes to run so only do it to populate the database.
//     */
//    public void initDb() {
//        DbPopulator.putStoryDataForTest();
//    }
}
