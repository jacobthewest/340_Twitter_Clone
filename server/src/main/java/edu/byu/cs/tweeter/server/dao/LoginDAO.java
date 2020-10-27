package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.LoginRequest;
import edu.byu.cs.tweeter.shared.service.response.LoginResponse;

public class LoginDAO {

    public LoginResponse login(LoginRequest request) {
        // TODO: logs in a hard-coded user. Replace with a real implementation.
        assert request.getUsername() != null;
        assert request.getPassword() != null;

        //  START
        //  Code to log the user in
        //  END

        User loggedInUser = new User("Test", "User", "https://i.imgur.com/VZQQiQ1.jpg", "password");
        AuthToken authToken = new AuthToken(request.getUsername());

        return new LoginResponse(loggedInUser, authToken);
    }

}
