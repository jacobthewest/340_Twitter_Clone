package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.LoginDAO;
import edu.byu.cs.tweeter.server.util.RequestAndResponseChecker;
import edu.byu.cs.tweeter.shared.service.LoginService;
import edu.byu.cs.tweeter.shared.service.request.LoginRequest;
import edu.byu.cs.tweeter.shared.service.response.LoginResponse;

public class LoginServiceImpl implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) {
        RequestAndResponseChecker checker = new RequestAndResponseChecker();

        // Request:: Alias, password.
        checker.checkEmptyNullStringRequest(request.getUsername(), "Alias");
        checker.checkEmptyNullStringRequest(request.getPassword(), "Password");

        LoginResponse loginResponse = getLoginDAO().login(request);

        if(!loginResponse.getSuccess()) {
            throw new RuntimeException("[InternalServerError] " + loginResponse.getMessage());
        }

        // Response:: User, AuthToken
        checker.checkUserResponse(loginResponse.getUser());
        checker.checkAuthTokenResponse(loginResponse.getUser(), loginResponse.getAuthToken());

        return loginResponse;
    }

    LoginDAO getLoginDAO() {return new LoginDAO();}
}
