package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutUser;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public class RetrieveUserDaoTest {

    public RetrieveUserDAO retrieveUserDAO;
    public RetrieveUserRequest validRequest;
    public RetrieveUserRequest invalidRequest;
    public User permanentTestUser;
    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";

    @BeforeEach
    public void setup() {
        permanentTestUser = new User("Permanent Test", "User", "ImageUrl", "password");
        permanentTestUser.setAlias(PERMANENT_TEST_USER);

        retrieveUserDAO = new RetrieveUserDAO();
        validRequest = new RetrieveUserRequest(permanentTestUser.getAlias());
        invalidRequest = new RetrieveUserRequest(UUID.randomUUID().toString());
    }

    @Test
    public void testValidRequest() {
        Object o = PutUser.putUser(permanentTestUser);
        Assertions.assertTrue(!o.toString().toUpperCase().contains("ERROR"));

        RetrieveUserResponse validResponse = retrieveUserDAO.retrieveUser(validRequest);
        Assertions.assertTrue(validResponse.getSuccess());
        Assertions.assertEquals(validResponse.getUser().getAlias(), permanentTestUser.getAlias());
        Assertions.assertEquals(validResponse.getUser().getFirstName(), permanentTestUser.getFirstName());
        Assertions.assertEquals(validResponse.getUser().getLastName(), permanentTestUser.getLastName());
        Assertions.assertEquals(validResponse.getUser().getPassword(), permanentTestUser.getPassword());
    }

    @Test
    public void testInvalidRequest() {
        RetrieveUserResponse invalidResponse = retrieveUserDAO.retrieveUser(invalidRequest);
        Assertions.assertNull(invalidResponse.getUser());
        Assertions.assertFalse(invalidResponse.getSuccess());
    }
}
