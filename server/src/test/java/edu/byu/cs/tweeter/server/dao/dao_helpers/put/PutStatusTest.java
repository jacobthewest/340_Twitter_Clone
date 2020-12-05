package edu.byu.cs.tweeter.server.dao.dao_helpers.put;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import edu.byu.cs.tweeter.server.dao.dao_helpers.get.GetStatus;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;

public class PutStatusTest {

    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";

    @BeforeEach
    public void setup() {

    }

    @Test
    public void testPutStatusValid() {
        Status valid  = getValidStatus();
        Object o = PutStatus.putStatus(valid);
        Assertions.assertTrue(!o.toString().toUpperCase().contains("ERROR"));

        Status retrievedStatus = GetStatus.getStatus(valid.getUser().getAlias(), valid.getTimePosted(), false);
        Assertions.assertEquals(retrievedStatus, valid);
    }

    @Test
    /**
     * User for the status does not exist in the database.
     */
    public void testPutStatusInvalid() {
        Status invalid = getInvalidStatus();
        Object o = PutStatus.putStatus(invalid);
        Assertions.assertNull(o);
    }

    private Status getValidStatus() {
        User u = new User("Permanent Test", "User", "imageUrl", "password");
        u.setAlias(PERMANENT_TEST_USER);

        String tweetText = "This is a valid tweet www.google.com @Jacob";
        String urls = "www.google.com";
        String mentions = "@Jacob";
        String timePosted = "Feb 11 2020 12:13 AM";

        Status status = new Status(u, tweetText, urls, timePosted, mentions);

        return status;
    }

    private Status getInvalidStatus() {
        User u = new User("Permanent Test", "User", "imageUrl", "password");
        u.setAlias(UUID.randomUUID().toString()); // Make sure we never get a real user.

        String tweetText = "This is a valid tweet www.google.com @Jacob";
        String urls = "www.google.com";
        String mentions = "@Jacob";
        String timePosted = "Jan 11 2020 12:13 AM";

        Status status = new Status(u, tweetText, urls, timePosted, mentions);

        return status;
    }
}
