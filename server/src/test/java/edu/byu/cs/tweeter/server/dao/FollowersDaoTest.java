package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.service.response.FollowersResponse;

public class FollowersDaoTest {

    @Test
    public void testQueryFollowersSortedValidNoLastFollower() {
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias("@PermanentTestUser");
        int limit = 10;

        FollowersRequest validRequest = new FollowersRequest(user, limit, null);
        FollowersDAO followingDAO = new FollowersDAO();
        FollowersResponse response = followingDAO.getFollowers(validRequest);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void testQueryFollowersSortedInvalidNoLastFollower() {
        // The user does not exist in the database
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias(UUID.randomUUID().toString());
        int limit = 10;

        FollowersRequest invalidRequest = new FollowersRequest(user, limit, null);
        FollowersDAO followingDAO = new FollowersDAO();
        FollowersResponse response = followingDAO.getFollowers(invalidRequest);
        Assertions.assertEquals(response.getFollowers().size(), 0);
    }

    @Test
    public void testQueryFollowersSortedValidHasLastFollower() {
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias("@PermanentTestUser");
        int limit = 10;
        User lastFollowee = new User("Rachel", "West", "imageUrl", "password");
        lastFollowee.setAlias("@Rachel");

        FollowersRequest validRequest = new FollowersRequest(user, limit, lastFollowee);
        FollowersDAO followingDAO = new FollowersDAO();
        FollowersResponse response = followingDAO.getFollowers(validRequest);
        Assertions.assertTrue(response.getSuccess());

        List<User> followers = response.getFollowers();
        Assertions.assertTrue(followers.size() == 4);
    }

    @Test
    public void testQueryFollowersSortedInvalidHasLastFollower() {
        User user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias(UUID.randomUUID().toString());
        int limit = 10;
        User lastFollowee = new User("Rachel", "West", "imageUrl", "password");
        lastFollowee.setAlias("@Rachel");

        FollowersRequest invalidRequest = new FollowersRequest(user, limit, lastFollowee);
        FollowersDAO followingDAO = new FollowersDAO();
        FollowersResponse response = followingDAO.getFollowers(invalidRequest);

        List<User> followers = response.getFollowers();
        Assertions.assertTrue(followers.size() == 0);
    }
}
