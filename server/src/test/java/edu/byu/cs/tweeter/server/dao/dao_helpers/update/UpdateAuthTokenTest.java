package edu.byu.cs.tweeter.server.dao.dao_helpers.update;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.dao.dao_helpers.delete.DeleteAuthToken;
import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutAuth;

public class UpdateAuthTokenTest {
    public static final String USERNAME = "username"; // AKA the primary key.
    public static final String IS_ACTIVE = "isActive";
    public static final String TABLE_NAME = "authToken";
    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testDeactivateAuthToken() {
        // Put an active auth token in the database.
        Object o = PutAuth.putAuth(PERMANENT_TEST_USER);
        Assertions.assertTrue(!o.toString().toUpperCase().contains("ERROR"));

        // Deactivate the authToken
        Object result = UpdateAuthToken.deactivateAuthToken(PERMANENT_TEST_USER);
        String resultAsString = result.toString();
        Assertions.assertTrue(resultAsString.contains("isActive={BOOL: false}"));

        String deleteResult = DeleteAuthToken.deleteAuthToken(PERMANENT_TEST_USER);
        Assertions.assertTrue(!deleteResult.toUpperCase().contains("ERROR"));
    }

    @Test
    public void testDeactivateAuthTokenNoRecognizedAlias() {
        // Deactivate the authToken
        Object result = UpdateAuthToken.deactivateAuthToken("@NoRecognizedAlias");
        String resultAsString = result.toString();
        Assertions.assertTrue(resultAsString.toUpperCase().contains("ERROR"));
    }

}
