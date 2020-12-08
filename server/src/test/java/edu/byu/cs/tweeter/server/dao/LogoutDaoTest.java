package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.ManagePassword;
import edu.byu.cs.tweeter.server.dao.dao_helpers.delete.DeleteAuthToken;
import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutAuth;
import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutUser;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.service.response.LogoutResponse;

public class LogoutDaoTest {

    public LogoutDAO logoutDAO;
    public LogoutRequest validRequest;
    public LogoutRequest invalidRequest;
    public User validUser;
    public AuthToken validAuthToken;
    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";
    public static final String FIXED_ID = "abc123";
    public static final String baseUrl = "https://340tweeter.s3-us-west-2.amazonaws.com/%40";

    @BeforeEach
    public void setup() {
        logoutDAO = new LogoutDAO();
    }

    @Test
    public void testValidLogout() {
        validUser = new User("Permanent Test", "User", baseUrl + "PermanentTestUser", "password");
        validUser.setAlias(PERMANENT_TEST_USER);
        validUser.setPassword(ManagePassword.hashPassword(validUser.getPassword()));
        validAuthToken = new AuthToken(PERMANENT_TEST_USER);
        validAuthToken.setId(FIXED_ID);
        validAuthToken.setIsActive(true);
        validRequest = new LogoutRequest(validUser, validAuthToken);

        Object o = PutAuth.putAuthWithFixedId(PERMANENT_TEST_USER, FIXED_ID);
        Assertions.assertTrue(!o.toString().toUpperCase().contains("ERROR"));

        o = PutUser.putUser(validUser);
        Assertions.assertTrue(!o.toString().toUpperCase().contains("ERROR"));


        LogoutResponse validResponse = logoutDAO.logout(validRequest);
        Assertions.assertTrue(validResponse.getSuccess());
        Assertions.assertFalse(validResponse.getAuthToken().getIsActive());
        Assertions.assertEquals(validAuthToken.getId(), validResponse.getAuthToken().getId());
        Assertions.assertEquals(validAuthToken.getUsername(), validResponse.getAuthToken().getUsername());

        String result = DeleteAuthToken.deleteAuthToken(PERMANENT_TEST_USER);
        Assertions.assertTrue(!result.toUpperCase().contains("ERROR"));
    }

    @Test
    /**
     * Tests for trying to logout a User with a bad alias and a matching bad AuthToken that
     * both don't exist in the database.
     */
    public void testInvalidLogout() {
        String randomAlias = UUID.randomUUID().toString();

        User user = new User("Bad", "User", "imageUrl", "password");
        user.setAlias("@" + randomAlias);
        AuthToken badAuthToken = new AuthToken("@" + randomAlias);

        invalidRequest = new LogoutRequest(user, badAuthToken);

        LogoutResponse invalidResponse = logoutDAO.logout(invalidRequest);
        Assertions.assertFalse(invalidResponse.getSuccess());
    }
}
