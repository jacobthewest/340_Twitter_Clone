package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import edu.byu.cs.tweeter.server.dao.dao_helpers.delete.DeleteAuthToken;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.LoginRequest;
import edu.byu.cs.tweeter.shared.service.response.LoginResponse;

public class LoginDaoTest {

    public LoginDAO loginDAO;
    public LoginRequest request;
    public LoginResponse response;
    public User validUser;
    public AuthToken validAuthToken;
    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";
    public static final String FIXED_ID = "abc123";

    @BeforeEach
    public void setup() {
        loginDAO = new LoginDAO();
        validUser = new User("Permanent Test", "User", "imageUrl", "password");
        validUser.setAlias(PERMANENT_TEST_USER);
    }

    @Test
    public void testValidLogin() {
        // Username and Password
        request = new LoginRequest(PERMANENT_TEST_USER, "password");
        response = loginDAO.login(request); // TODO: For some reason the user in the db had its hashed password overwritten
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getUser().getAlias(), request.getUsername());
        Assertions.assertTrue(response.getAuthToken().getIsActive());

        String result = DeleteAuthToken.deleteAuthToken(request.getUsername());
        Assertions.assertTrue(!result.toUpperCase().contains("ERROR"));
    }

    @Test
    public void testInvalidLogin() {
        // Incorrect password
        request = new LoginRequest(PERMANENT_TEST_USER, UUID.randomUUID().toString());
        response = loginDAO.login(request);
        Assertions.assertFalse(response.getSuccess());
    }
}
