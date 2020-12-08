package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.util.RequestAndResponseChecker;
import edu.byu.cs.tweeter.shared.service.StoryService;
import edu.byu.cs.tweeter.shared.service.request.StoryRequest;
import edu.byu.cs.tweeter.shared.service.response.StoryResponse;

public class StoryServiceImpl implements StoryService {

    @Override
    public StoryResponse getStory(StoryRequest request) {
        RequestAndResponseChecker checker = new RequestAndResponseChecker();

        // Request:: User, limit, lastStatus
        checker.checkUserRequest(request.getUser());
        checker.checkLimitRequest(request.getLimit());

        StoryResponse storyResponse = getStoryDAO().getStory(request);

        if(!storyResponse.getSuccess()) {
            throw new RuntimeException("[InternalServerError] " + storyResponse.getMessage());
        }

        // Response:: List<Status>
        checker.checkStatusListResponse(storyResponse.getStatuses(), request.getLimit());

        return storyResponse;
    }

    StoryDAO getStoryDAO() {return new StoryDAO();}
}
