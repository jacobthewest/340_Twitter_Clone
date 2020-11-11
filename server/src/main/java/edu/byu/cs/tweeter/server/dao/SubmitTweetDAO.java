package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

public class SubmitTweetDAO {

    public SubmitTweetResponse submitTweet(SubmitTweetRequest request) {
        String userAlias = request.getUser().getAlias();
        String statusAlias = request.getStatus().getUser().getAlias();

        // START
        // Code that actually submits the tweet to the database
        // END

        User user = request.getUser();
        user.setImageBytes(null);
        User statusUser = request.getStatus().getUser();
        statusUser.setImageBytes(null);
        request.getStatus().setUser(statusUser);

        return new SubmitTweetResponse(user, request.getStatus());
    }
}
