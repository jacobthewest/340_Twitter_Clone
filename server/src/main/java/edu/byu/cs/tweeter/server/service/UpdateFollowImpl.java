package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.UpdateFollowDAO;
import edu.byu.cs.tweeter.shared.service.UpdateFollowService;
import edu.byu.cs.tweeter.shared.service.request.UpdateFollowRequest;
import edu.byu.cs.tweeter.shared.service.response.UpdateFollowResponse;

public class UpdateFollowImpl implements UpdateFollowService {

    @Override
    public UpdateFollowResponse updateFollow(UpdateFollowRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        return getUpdateFollowDAO().updateFollow(request);
    }

    UpdateFollowDAO getUpdateFollowDAO() {return new UpdateFollowDAO();}
}
