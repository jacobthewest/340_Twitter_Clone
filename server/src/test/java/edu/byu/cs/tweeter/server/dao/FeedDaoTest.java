package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DbPopulator;
import edu.byu.cs.tweeter.server.util.ListOfStatuses;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.FeedRequest;
import edu.byu.cs.tweeter.shared.service.response.FeedResponse;

public class FeedDaoTest {

    @Test
    public void testQueryFeedSortedValidNoLastStatus() {
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias("@PermanentTestUser");
        int limit = 3;

        List<Status> feed = getFeed(user);

        List<Status> firstThreeStatusesSorted = getFirstThreeStatusesSorted(feed);

        FeedRequest validRequest = new FeedRequest(user, limit, null);
        FeedDAO feedDAO = new FeedDAO();
        FeedResponse response = feedDAO.getFeed(validRequest);
        Assertions.assertTrue(response.getSuccess());

        List<Status> responseStatuses = response.getStatuses();
        for(int i = 0; i < limit; i++) {
            Assertions.assertEquals(responseStatuses.get(i), firstThreeStatusesSorted.get(i));
        }
    }

    @Test
    public void testQueryFeedSortedInvalidNoLastStatus() {
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias(UUID.randomUUID().toString());
        int limit = 3;

        FeedRequest validRequest = new FeedRequest(user, limit, null);
        FeedDAO feedDAO = new FeedDAO();
        FeedResponse response = feedDAO.getFeed(validRequest);
        Assertions.assertTrue(response.getSuccess());

        Assertions.assertEquals(response.getStatuses().size(), 0);
    }

    @Test
    public void testQueryFeedSortedValidLastStatus() {
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias("@PermanentTestUser");
        int limit = 3;

        List<Status> feed = getFeed(user);

        List<Status> correct3 = getThreeFromFeed(feed);

        FeedRequest validRequest = new FeedRequest(user, limit, feed.get(142)); // Just trust me for this number.
        FeedDAO feedDAO = new FeedDAO();
        FeedResponse response = feedDAO.getFeed(validRequest);
        Assertions.assertTrue(response.getSuccess());

        List<Status> responseStatuses = response.getStatuses();
        for(int i = 0; i < limit; i++) {
            Assertions.assertEquals(responseStatuses.get(i), correct3.get(i));
        }
    }

    @Test
    public void testQueryFeedSortedInvalidLastStatus() {
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias(UUID.randomUUID().toString());
        int limit = 3;

        List<Status> feed = getFeed(user);

        FeedRequest validRequest = new FeedRequest(user, limit, feed.get(142)); // Just trust me for this number.
        FeedDAO feedDAO = new FeedDAO();
        FeedResponse response = feedDAO.getFeed(validRequest);
        Assertions.assertTrue(response.getSuccess());

        Assertions.assertEquals(response.getStatuses().size(), 0);
    }

    private List<Status> getFeed(User user) {
        List<Status> feed = new ArrayList<>();
        List<User> family = DbPopulator.getFamilyForPutUsersData();
        ListOfStatuses tool = new ListOfStatuses();

        for(User person: family) {
            if(!person.getAlias().equals(user.getAlias())) {
                List<Status> statusesForPerson = tool.get21StatusesForDaoTests(person);
                for(Status s: statusesForPerson) {
                    feed.add(s);
                }
            }
        }
        return feed;
    }

    private List<Status> getFirstThreeStatusesSorted(List<Status> feed) {
        List<Status> returnMe = new ArrayList<>();
        returnMe.add(feed.get(39));
        returnMe.add(feed.get(228));
        returnMe.add(feed.get(270));
        return returnMe;
    }

    private List<Status> getThreeFromFeed(List<Status> feed) {
        List<Status> returnMe = new ArrayList<>();
        returnMe.add(feed.get(16));
        returnMe.add(feed.get(121));
        returnMe.add(feed.get(163));
        return returnMe;
    }
}
