package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        int limit = 10;

        ListOfStatuses tool = new ListOfStatuses();
        List<Status> listOfStatuses = tool.get21StatusesForStoryDaoTest(user); // TODO: implement get21StatusesForFeedDaoTest();
        // List<Status> firstTenStatusesSorted = getFirst10StatusesSorted(listOfStatuses);

        FeedRequest validRequest = new FeedRequest(user, limit, null);
        FeedDAO feedDAO = new FeedDAO();
        FeedResponse response = feedDAO.getFeed(validRequest);
        Assertions.assertTrue(response.getSuccess());

        List<Status> responseStatuses = response.getStatuses();
//        for(int i = 0; i < limit; i++) {
//            Assertions.assertEquals(responseStatuses.get(i), firstTenStatusesSorted.get(i));
//        }
    }
}
