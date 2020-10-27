package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.service.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.service.response.LogoutResponse;

public class LogoutDAO {

    public LogoutResponse logout(LogoutRequest request) {
        // TODO: logs in a hard-coded user. Replace with a real implementation.
        assert request.getUser() != null;
        assert request.getAuthToken() != null;

        //  START
        //  Code to log the user out
        //  END

        return new LogoutResponse(request.getUser(), request.getAuthToken());
    }
}
