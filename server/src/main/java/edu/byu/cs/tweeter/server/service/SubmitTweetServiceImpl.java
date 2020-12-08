package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.SubmitTweetDAO;
import edu.byu.cs.tweeter.server.util.RequestAndResponseChecker;
import edu.byu.cs.tweeter.shared.service.SubmitTweetService;
import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

public class SubmitTweetServiceImpl implements SubmitTweetService {

    @Override
    public SubmitTweetResponse submitTweet(SubmitTweetRequest request) {
        RequestAndResponseChecker checker = new RequestAndResponseChecker();

        // Request:: User
        checker.checkUserRequest(request.getUser());

        SubmitTweetResponse submitTweetResponse = getSubmitTweetDAO().submitTweet(request);

        if(!submitTweetResponse.getSuccess()) {
            throw new RuntimeException("[InternalServerError] " + submitTweetResponse.getMessage());
        }

        // Response:: User, Status
        checker.checkUserResponse(submitTweetResponse.getUser());
        checker.checkStatusResponse(request.getUser(), submitTweetResponse.getStatus());

        return submitTweetResponse;
    }

    SubmitTweetDAO getSubmitTweetDAO() {return new SubmitTweetDAO();}
}
