package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import edu.byu.cs.tweeter.server.dao.dao_helpers.delete.DeleteFollows;
import edu.byu.cs.tweeter.server.dao.dao_helpers.get.GetFollow;
import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutFollows;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.UpdateFollowRequest;
import edu.byu.cs.tweeter.shared.service.response.UpdateFollowResponse;

public class UpdateFollowDaoTest {

    public UpdateFollowDAO updateFollowDAO;
    public UpdateFollowRequest request;
    public UpdateFollowResponse response;
    public User user;
    public User followUser;
    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";

    @BeforeEach
    public void setup() {
        updateFollowDAO = new UpdateFollowDAO();

        user = new User();
        user.setAlias(PERMANENT_TEST_USER);
        user.setFirstName("Permanent Test");
        user.setLastName("User");

        followUser = new User();
        followUser.setAlias("@Dad");
        followUser.setFirstName("Brett");
        followUser.setLastName("West");
    }

    @Test
    public void testValidFollow() {
        // PermanentTestUser follows @Dad. First make them unfollow @Dad
        String result = DeleteFollows.deleteFollows(PERMANENT_TEST_USER, "@Dad");
        Assertions.assertTrue(!result.toUpperCase().contains("ERROR"));

        request = new UpdateFollowRequest(user, followUser, true);
        response = updateFollowDAO.updateFollow(request);

        Assertions.assertTrue(response.getSuccess());
        boolean worked = GetFollow.doesFollowExist(user.getAlias(), followUser.getAlias());
        Assertions.assertTrue(worked);
    }

    @Test
    // Follow a user who doesn't exist
    public void testInvalidFollow() {
        followUser.setAlias(UUID.randomUUID().toString());

        request = new UpdateFollowRequest(user, followUser, true);
        response = updateFollowDAO.updateFollow(request);

        Assertions.assertFalse(response.getSuccess());
    }

    @Test
    public void testValidUnfollow() {
        request = new UpdateFollowRequest(user, followUser, false);
        response = updateFollowDAO.updateFollow(request);

        Assertions.assertTrue(response.getSuccess());
        boolean exists = GetFollow.doesFollowExist(user.getAlias(), followUser.getAlias());
        Assertions.assertFalse(exists);

        // @PermanentTestUser followed @Dad before the test. Restore that.
        Object o = PutFollows.putFollows(user, followUser);
        Assertions.assertTrue(!o.toString().toUpperCase().contains("ERROR"));
    }

    @Test
    // Both users exist but they already don't follow each other.
    public void testInvalidUnfollow() {
        String result = DeleteFollows.deleteFollows(PERMANENT_TEST_USER, "@Dad");
        Assertions.assertTrue(!result.toUpperCase().contains("ERROR"));

        request = new UpdateFollowRequest(user, followUser, false);
        response = updateFollowDAO.updateFollow(request);

        Assertions.assertTrue(response.getSuccess());
        boolean exists = GetFollow.doesFollowExist(user.getAlias(), followUser.getAlias());
        Assertions.assertFalse(exists);

        // @PermanentTestUser followed @Dad before the test. Restore that.
        Object o = PutFollows.putFollows(user, followUser);
        Assertions.assertTrue(!o.toString().toUpperCase().contains("ERROR"));
    }
}
