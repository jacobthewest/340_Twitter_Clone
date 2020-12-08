package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.RegisterDAO;
import edu.byu.cs.tweeter.server.util.RequestAndResponseChecker;
import edu.byu.cs.tweeter.shared.service.RegisterService;
import edu.byu.cs.tweeter.shared.service.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.service.response.RegisterResponse;

public class RegisterServiceImpl implements RegisterService {

    @Override
    public RegisterResponse register(RegisterRequest request) {
        RequestAndResponseChecker checker = new RequestAndResponseChecker();

        // Request:: User
        checker.checkUserRequest(request.getUser());
        checker.checkEmptyNullStringRequest(request.getUser().getAlias(), "Alias");
        checker.checkEmptyNullStringRequest(request.getUser().getFirstName(), "FirstName");
        checker.checkEmptyNullStringRequest(request.getUser().getLastName(), "LastName");
        checker.checkEmptyNullStringRequest(request.getUser().getLastName(), "Password");

        RegisterResponse registerResponse = getRegisterDAO().register(request);

        // Response:: User, AuthToken
        if(!registerResponse.getSuccess()) {
            throw new RuntimeException("[InternalServerError] " + registerResponse.getMessage());
        }

        checker.checkUserResponse(registerResponse.getUser());
        checker.checkAuthTokenResponse(registerResponse.getUser(), registerResponse.getAuthToken());
        checker.checkEmptyNullStringResponse(request.getUser().getImageUrl(), "ImageUrl");

        return registerResponse;
    }

    RegisterDAO getRegisterDAO() {return new RegisterDAO();}
}
