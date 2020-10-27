package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.shared.service.StoryService;
import edu.byu.cs.tweeter.shared.service.request.StoryRequest;
import edu.byu.cs.tweeter.shared.service.response.StoryResponse;

public class StoryServiceImpl implements StoryService {

    @Override
    public StoryResponse getStory(StoryRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        return getStoryDAO().getStory(request);
    }

    StoryDAO getStoryDAO() {return new StoryDAO();}
}
