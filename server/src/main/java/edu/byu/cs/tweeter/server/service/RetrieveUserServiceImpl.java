package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.RetrieveUserDAO;
import edu.byu.cs.tweeter.server.util.RequestAndResponseChecker;
import edu.byu.cs.tweeter.shared.service.RetrieveUserService;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public class RetrieveUserServiceImpl implements RetrieveUserService {

    @Override
    public RetrieveUserResponse retrieveUser(RetrieveUserRequest request) {
        RequestAndResponseChecker checker = new RequestAndResponseChecker();

        // Request:: username
        checker.checkEmptyNullStringRequest(request.getUsername(), "Alias");

        RetrieveUserResponse retrieveUserResponse = getRetrieveUserDAO().retrieveUser(request);

        if(!retrieveUserResponse.getSuccess()) {
            throw new RuntimeException("[InternalServerError] " + retrieveUserResponse.getMessage());
        }

        // Response:: User
        checker.checkUserResponse(retrieveUserResponse.getUser());

        return retrieveUserResponse;
    }

    RetrieveUserDAO getRetrieveUserDAO() {return new RetrieveUserDAO();}
}
