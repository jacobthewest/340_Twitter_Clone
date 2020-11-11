package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.util.RequestAndResponseChecker;
import edu.byu.cs.tweeter.shared.service.FeedService;
import edu.byu.cs.tweeter.shared.service.request.FeedRequest;
import edu.byu.cs.tweeter.shared.service.response.FeedResponse;

public class FeedServiceImpl implements FeedService {

    @Override
    public FeedResponse getFeed(FeedRequest request) {
        RequestAndResponseChecker checker = new RequestAndResponseChecker();

        // Request:: User, limit, lastStatus
        checker.checkUserRequest(request.getUser());
        checker.checkLimitRequest(request.getLimit());

        FeedResponse feedResponse = getFeedDAO().getFeed(request);

        // Response:: list of statuses
        checker.checkStatusListResponse(feedResponse.getFeed(), request.getLimit());

        return feedResponse;
    }

    FeedDAO getFeedDAO() {return new FeedDAO();}
}
