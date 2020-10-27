package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.RetrieveUserDAO;
import edu.byu.cs.tweeter.shared.service.RetrieveUserService;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public class RetrieveUserServiceImpl implements RetrieveUserService {

    @Override
    public RetrieveUserResponse retrieveUser(RetrieveUserRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        return getRetrieveUserDAO().retrieveUser(request);
    }

    RetrieveUserDAO getRetrieveUserDAO() {return new RetrieveUserDAO();}
}
