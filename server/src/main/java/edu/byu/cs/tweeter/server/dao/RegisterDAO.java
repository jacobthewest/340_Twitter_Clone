package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.service.response.RegisterResponse;

public class RegisterDAO {

    public RegisterResponse register(RegisterRequest request) {
        assert request.getUsername().equals("") || request.getUsername() == null;
        assert request.getPassword().equals("") || request.getPassword() == null;
        assert request.getFirstName().equals("") || request.getFirstName() == null;
        assert request.getLastName().equals("") || request.getLastName() == null;
        assert request.getImageUrl().equals("") || request.getImageUrl() == null;
        assert request.getImageBytes().equals("") || request.getImageBytes() == null;

        // START
        // Code to register the user
        // END

        User user = new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getImageUrl(), request.getImageBytes(), request.getPassword());
        return new RegisterResponse(user, new AuthToken(user.getAlias()));
    }

}
