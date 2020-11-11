package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.service.response.RegisterResponse;

public class RegisterDAO {

    public RegisterResponse register(RegisterRequest request) {
        // START
        // Code to register the user
        // END

        User user = request.getUser();

        return new RegisterResponse(user, new AuthToken(user.getAlias()));
    }

}
