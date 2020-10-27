package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.shared.service.FeedService;
import edu.byu.cs.tweeter.shared.service.request.FeedRequest;
import edu.byu.cs.tweeter.shared.service.response.FeedResponse;

public class FeedServiceImpl implements FeedService {

    @Override
    public FeedResponse getFeed(FeedRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        return getFeedDAO().getFeed(request);
    }

    FeedDAO getFeedDAO() {return new FeedDAO();}
}
