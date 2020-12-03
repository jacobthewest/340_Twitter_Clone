package edu.byu.cs.tweeter.server.dao.dao_helpers.get;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutAuth;
import edu.byu.cs.tweeter.shared.domain.AuthToken;

public class GetAuthTokenTest {

    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";
    public static final String FIXED_ID = "abc123";

    @Test
    public void testGetAuthTokenByUsernameAndId() {
        Object o = PutAuth.putAuthWithFixedId(PERMANENT_TEST_USER, FIXED_ID);
        Assertions.assertTrue(!o.toString().toUpperCase().contains("ERROR"));

        AuthToken authTokenResponse = GetAuthToken.getAuthTokenByUsernameAndId(PERMANENT_TEST_USER, FIXED_ID);
        Assertions.assertEquals(authTokenResponse.getId(), FIXED_ID);
        Assertions.assertEquals(authTokenResponse.getUsername(), PERMANENT_TEST_USER);
    }
}
