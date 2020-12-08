package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.byu.cs.tweeter.server.dao.dao_helpers.delete.DeleteStatus;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

public class SubmitTweetDaoTest {

    public SubmitTweetResponse response;
    public SubmitTweetDAO submitTweetDAO;
    public SubmitTweetRequest request;
    public User user;
    public Status status;
    public Date date;
    String timePosted;

    @BeforeEach
    public void setup() {
        user = new User("Permanent Test", "User", "imageUrl", "password");
        user.setAlias("@PermanentTestUser");
        timePosted = getTime();
        status = new Status(user, "This is a test tweet @Mom", "www.google.com", timePosted, "@Mom");
        submitTweetDAO = new SubmitTweetDAO();
    }

    @Test
    public void testSubmitTweetValid() {
        request = new SubmitTweetRequest(user, status);
        response = submitTweetDAO.submitTweet(request);
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(user, response.getUser());
        Assertions.assertEquals(status, response.getStatus());

        String result = DeleteStatus.deleteStatus(user.getAlias(), status.getTimePosted());
        Assertions.assertTrue(!result.toUpperCase().contains("ERROR"));
    }

    @Test
    /**
     * User alias and status's alias don't match
     */
    public void testSubmitTweetInvalid() {
        User u = new User("Permanent Test", "User", "imageUrl", "password");
        Status s = new Status(user, "This is a test tweet @Mom", "www.google.com", timePosted, "@Mom");
        request = new SubmitTweetRequest(u, s);
        response = submitTweetDAO.submitTweet(request);
        Assertions.assertFalse(response.getSuccess());
    }

    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");
        Date d = createDate(2020, 11, 7, 9, 51);
        return dateFormat.format(d);
    }

    private Date createDate(int year, int month, int day, int hour, int minute) {
        Date d = new Date(year - 1900, month, day);
        d.setHours(hour);
        d.setMinutes(minute);
        return d;
    }
}
