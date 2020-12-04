package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.service.response.FollowingResponse;

public class FollowingDaoTest {

    @Test
    public void testQueryFollowingSortedValidNoLastFollowee() {
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias("@PermanentTestUser");
        int limit = 10;

        FollowingRequest validRequest = new FollowingRequest(user, limit, null);
        FollowingDAO followingDAO = new FollowingDAO();
        FollowingResponse response = followingDAO.getFollowees(validRequest);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void testQueryFollowingSortedInvalidNoLastFollowee() {
        // The user does not exist in the database
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias(UUID.randomUUID().toString());
        int limit = 10;

        FollowingRequest invalidRequest = new FollowingRequest(user, limit, null);
        FollowingDAO followingDAO = new FollowingDAO();
        FollowingResponse response = followingDAO.getFollowees(invalidRequest);
        Assertions.assertEquals(response.getFollowees().size(), 0);
    }

    @Test
    public void testQueryFollowingSortedValidHasLastFollowee() {
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias("@PermanentTestUser");
        int limit = 10;
        User lastFollowee = new User("Rachel", "West", "imageUrl", "password");
        lastFollowee.setAlias("@Rachel");

        FollowingRequest validRequest = new FollowingRequest(user, limit, lastFollowee);
        FollowingDAO followingDAO = new FollowingDAO();
        FollowingResponse response = followingDAO.getFollowees(validRequest);
        Assertions.assertTrue(response.getSuccess());

        List<User> following = response.getFollowees();
        Assertions.assertTrue(following.size() == 4);
    }

    @Test
    public void testQueryFollowingSortedInvalidHasLastFollowee() {
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias(UUID.randomUUID().toString());
        int limit = 10;
        User lastFollowee = new User("Rachel", "West", "imageUrl", "password");
        lastFollowee.setAlias("@Rachel");

        FollowingRequest invalidRequest = new FollowingRequest(user, limit, lastFollowee);
        FollowingDAO followingDAO = new FollowingDAO();
        FollowingResponse response = followingDAO.getFollowees(invalidRequest);

        List<User> following = response.getFollowees();
        Assertions.assertTrue(following.size() == 0);
    }



//    @Test
//    public void initFamilyInAws() {
//        PutUser.putFamilyAndTestUser();
//    }
}
