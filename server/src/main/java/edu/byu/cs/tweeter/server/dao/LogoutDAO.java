package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.service.response.LogoutResponse;

public class LogoutDAO {

    public LogoutResponse logout(LogoutRequest request) {
        // TODO: logs in a hard-coded user. Replace with a real implementation.

        //  START
        //  Code to log the user out
        //  END

        User user = request.getUser();
        user.setImageBytes(null);

        return new LogoutResponse(user, request.getAuthToken());
    }
}
