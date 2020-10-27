package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.LoginDAO;
import edu.byu.cs.tweeter.shared.service.LoginService;
import edu.byu.cs.tweeter.shared.service.request.LoginRequest;
import edu.byu.cs.tweeter.shared.service.response.LoginResponse;

public class LoginServiceImpl implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) {

        // TODO: Generates dummy data. Replace with a real implementation.
        return getLoginDAO().login(request);
    }

    LoginDAO getLoginDAO() {return new LoginDAO();}
}
