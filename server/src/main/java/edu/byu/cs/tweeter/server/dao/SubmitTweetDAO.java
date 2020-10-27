package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

public class SubmitTweetDAO {

    public SubmitTweetResponse submitTweet(SubmitTweetRequest request) {
        String userAlias = request.getUser().getAlias();
        String statusAlias = request.getStatus().getUser().getAlias();
        assert request.getStatus() == null;
        assert request.getUser() == null;
        assert userAlias.equals(statusAlias);

        // START
        // Code that actually submits the tweet to the database
        // END

        return new SubmitTweetResponse(request.getUser(), request.getStatus());
    }
}
