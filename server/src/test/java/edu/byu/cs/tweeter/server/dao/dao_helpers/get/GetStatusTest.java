package edu.byu.cs.tweeter.server.dao.dao_helpers.get;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutStatus;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;

public class GetStatusTest {
    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";

    @BeforeEach
    public void setup() {

    }

    @Test
    public void testGetStatusValid() {
        Status valid  = getExistingStatus();
        Object o = PutStatus.putStatus(valid);
        Assertions.assertTrue(!o.toString().toUpperCase().contains("ERROR"));

        Status retrievedStatus = GetStatus.getStatus(valid.getUser().getAlias(), valid.getTimePosted(), false);
        Assertions.assertEquals(retrievedStatus, valid);
    }

    @Test
    /**
     * Get a status that doesn't exist by the username
     */
    public void testGetStatusInvalidUsername() {
        Status invalid = getNonExistingStatus();
        Status invalidStatus = GetStatus.getStatus(invalid.getUser().getAlias(), invalid.getTimePosted(), false);
        Assertions.assertNull(invalidStatus);
    }

    @Test
    /**
     * Get a status that doesn't exist by the date time
     */
    public void testGetStatusInvalidDate() {
        Status valid  = getExistingStatus();
        Object o = PutStatus.putStatus(valid);
        Assertions.assertTrue(!o.toString().toUpperCase().contains("ERROR"));

        Status s = getExistingStatusNonExistingDate();
        Status invalidStatus = GetStatus.getStatus(s.getUser().getAlias(), s.getTimePosted(), false);
        Assertions.assertNull(invalidStatus);
    }

    private Status getExistingStatus() {
        User u = new User("Permanent Test", "User", "imageUrl", "password");
        u.setAlias(PERMANENT_TEST_USER);

        String tweetText = "This is a valid tweet www.google.com @Jacob";
        String urls = "www.google.com";
        String mentions = "@Jacob";
        String timePosted = "Feb 11 2020 12:13 AM";

        Status status = new Status(u, tweetText, urls, timePosted, mentions);

        return status;
    }

    private Status getExistingStatusNonExistingDate() {
        User u = new User("Permanent Test", "User", "imageUrl", "password");
        u.setAlias(PERMANENT_TEST_USER);

        String tweetText = "This is a valid tweet www.google.com @Jacob";
        String urls = "www.google.com";
        String mentions = "@Jacob";
        String timePosted = "Feb 11 2050 12:13 AM";

        Status status = new Status(u, tweetText, urls, timePosted, mentions);

        return status;
    }

    private Status getNonExistingStatus() {
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
