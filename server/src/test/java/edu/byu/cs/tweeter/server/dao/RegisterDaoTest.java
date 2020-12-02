package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.dao.dao_helpers.delete.DeleteUser;
import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutUser;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.service.response.RegisterResponse;

public class RegisterDaoTest {

    public RegisterDAO registerDAO;

    @BeforeEach
    public void setup() {
        registerDAO = new RegisterDAO();
    }

    @Test
    public void testRegisterPreExistingUser() {

        User userWhoExists = new User("I", "Really", "Exist", "password");

        PutUser.putUser(userWhoExists);

        RegisterRequest existsRequest = new RegisterRequest(userWhoExists);

        RegisterResponse existsRegisterResponse = registerDAO.register(existsRequest);

        Assertions.assertFalse(registerDAO.register(existsRequest).getSuccess());

    }

    @Test
    public void testRegisterNonExistingUser() {

        User userWhoDoesNotExist = new User("I", "Dont", "Exist", "ok?");

        RegisterRequest doesNotExistRequest = new RegisterRequest(userWhoDoesNotExist);

        RegisterResponse doesNotExistRegisterResponse = registerDAO.register(doesNotExistRequest);

        Assertions.assertTrue(doesNotExistRegisterResponse.getSuccess());

        String deletionResult = DeleteUser.deleteUser(userWhoDoesNotExist.getAlias());
        Assertions.assertTrue(!deletionResult.toUpperCase().contains("ERROR"), "Go delete user: " +
                userWhoDoesNotExist.getAlias() + " from the user table");
    }
}
