package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.RegisterDAO;
import edu.byu.cs.tweeter.shared.service.RegisterService;
import edu.byu.cs.tweeter.shared.service.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.service.response.RegisterResponse;

public class RegisterServiceImpl implements RegisterService {

    @Override
    public RegisterResponse register(RegisterRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        return getRegisterDAO().register(request);
    }

    RegisterDAO getRegisterDAO() {return new RegisterDAO();}
}
