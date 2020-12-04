package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.FollowingRequest;
import edu.byu.cs.tweeter.shared.service.response.FollowingResponse;

public class FollowingDaoTest {

    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";
    public FollowingDAO followingDao;
    public FollowingRequest validRequest;

    @BeforeEach
    public void setup() {
        followingDao = new FollowingDAO();
    }


    @Test
    public void testValidFollowing() {
        // User, limit, lastFollowee
        User permanentTestUser = new User("Permanent Test", "User", "imageUrl", "password");
        permanentTestUser.setAlias(PERMANENT_TEST_USER);

        int limit = 10;

        User lastFollowee = new User("Trevor", "Monney", "imageUrl", "password");
        lastFollowee.setAlias("@Trevor");

        validRequest = new FollowingRequest(permanentTestUser, limit, lastFollowee);
        FollowingResponse validResponse = followingDao.getFollowees(validRequest);
        Assertions.assertTrue(validResponse.getSuccess());
    }


//    @Test
//    public void initFamilyInAws() {
//        PutUser.putFamilyAndTestUser();
//    }
}
