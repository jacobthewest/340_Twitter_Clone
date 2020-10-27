package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.SubmitTweetDAO;
import edu.byu.cs.tweeter.shared.service.SubmitTweetService;
import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

public class SubmitTweetServiceImpl implements SubmitTweetService {

    @Override
    public SubmitTweetResponse submitTweet(SubmitTweetRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        return getSubmitTweetDAO().submitTweet(request);
    }

    SubmitTweetDAO getSubmitTweetDAO() {return new SubmitTweetDAO();}
}
