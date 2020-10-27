package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.LogoutDAO;
import edu.byu.cs.tweeter.shared.service.LogoutService;
import edu.byu.cs.tweeter.shared.service.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.service.response.LogoutResponse;

public class LogoutServiceImpl implements LogoutService {

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        return getLogoutDAO().logout(request);
    }

    LogoutDAO getLogoutDAO() {return new LogoutDAO();}
}
