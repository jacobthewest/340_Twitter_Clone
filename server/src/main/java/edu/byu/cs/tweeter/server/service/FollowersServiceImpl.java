package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.server.util.RequestAndResponseChecker;
import edu.byu.cs.tweeter.shared.service.FollowersService;
import edu.byu.cs.tweeter.shared.service.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.service.response.FollowersResponse;

public class FollowersServiceImpl implements FollowersService {

    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        RequestAndResponseChecker checker = new RequestAndResponseChecker();

        // User, limit, lastFollower(type User)
        checker.checkUserRequest(request.getUser());
        checker.checkLimitRequest(request.getLimit());
        checker.checkUserLastFollowRequest(request.getUser(), request.getLastFollower());

        FollowersResponse followersResponse = getFollowersDAO().getFollowers(request);

        if(!followersResponse.getSuccess()) {
            throw new RuntimeException("[InternalServerError] " + followersResponse.getMessage());
        }

        // Response:: List<User> followers
        checker.checkUserListResponse(followersResponse.getFollowers(), request.getLimit());

        return followersResponse;
    }

    FollowersDAO getFollowersDAO() {return new FollowersDAO();}
}
