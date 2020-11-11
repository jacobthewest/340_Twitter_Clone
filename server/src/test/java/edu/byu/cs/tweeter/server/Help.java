package edu.byu.cs.tweeter.server;

import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.service.FeedServiceImpl;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.FeedRequest;
import edu.byu.cs.tweeter.shared.service.response.FeedResponse;

public class Help {

    @Test
    public void help() {
        FeedServiceImpl feedServiceImpl = new FeedServiceImpl();
        User user =  new User("Test", "User", "https://i.imgur.com/VZQQiQ1.jpg", "password");
        user.setImageBytesAsString(null);
        int limit = 10;
        Status lastStatus = null;
        FeedRequest feedRequest = new FeedRequest(user, limit, lastStatus);

        FeedResponse feedResponse = feedServiceImpl.getFeed(feedRequest);
        String s = "stopHere";
    }
}
