package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.shared.service.FollowersService;
import edu.byu.cs.tweeter.shared.service.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.service.response.FollowersResponse;

public class FollowersServiceImpl implements FollowersService {

    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        return getFollowersDAO().getFollowers(request);
    }

    FollowersDAO getFollowersDAO() {return new FollowersDAO();}
}
