package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.CountDAO;
import edu.byu.cs.tweeter.server.util.RequestAndResponseChecker;
import edu.byu.cs.tweeter.shared.service.CountService;
import edu.byu.cs.tweeter.shared.service.request.CountRequest;
import edu.byu.cs.tweeter.shared.service.response.CountResponse;

public class CountServiceImpl implements CountService {

    @Override
    public CountResponse getCount(CountRequest request) {
        RequestAndResponseChecker checker = new RequestAndResponseChecker();

        // Request:: User
        checker.checkUserRequest(request.getUser());

        CountResponse countResponse = getCountDAO().getCount(request);

        // Response:: User, followingCount, followersCount
        checker.checkUserResponse(countResponse.getUser());
        checker.checkFollowingFollowersResponse(countResponse.getFollowingCount(), countResponse.getFollowersCount());

        return countResponse;
    }

    CountDAO getCountDAO() {return new CountDAO();}
}
