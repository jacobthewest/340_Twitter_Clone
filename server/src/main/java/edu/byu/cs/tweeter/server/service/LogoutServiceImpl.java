package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.LogoutDAO;
import edu.byu.cs.tweeter.server.util.RequestAndResponseChecker;
import edu.byu.cs.tweeter.shared.service.LogoutService;
import edu.byu.cs.tweeter.shared.service.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.service.response.LogoutResponse;

public class LogoutServiceImpl implements LogoutService {

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        RequestAndResponseChecker checker = new RequestAndResponseChecker();

        // Request:: User, AuthToken
        checker.checkUserRequest(request.getUser());
        checker.checkAuthTokenRequest(request.getUser(), request.getAuthToken());

        LogoutResponse logoutResponse = getLogoutDAO().logout(request);

        // Response:: User, AuthToken
        checker.checkUserResponse(logoutResponse.getUser());
        checker.checkAuthTokenResponse(logoutResponse.getUser(), logoutResponse.getAuthToken());

        return logoutResponse;
    }

    LogoutDAO getLogoutDAO() {return new LogoutDAO();}
}
