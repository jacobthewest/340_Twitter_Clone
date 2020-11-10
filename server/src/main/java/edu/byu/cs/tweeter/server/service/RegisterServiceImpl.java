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

        // Request:: username, password, firstName, lastName, imageUrl, imageBytes (MUST BE DEFINED as a string btw)
        checker.checkEmptyNullStringRequest(request.getUsername(), "Alias");
        checker.checkEmptyNullStringRequest(request.getPassword(), "Password");
        checker.checkEmptyNullStringRequest(request.getFirstName(), "First name");
        checker.checkEmptyNullStringRequest(request.getLastName(), "Last name");
        checker.checkEmptyNullStringRequest(request.getImageUrl(), "ImageUrl");
        checker.checkEmptyNullStringRequest(request.getImageBytesAsString(), "ImageBytesAsString");

        RegisterResponse registerResponse = getRegisterDAO().register(request);

        // Response:: User, AuthToken
        checker.checkUserResponse(registerResponse.getUser());
        checker.checkAuthTokenResponse(registerResponse.getUser(), registerResponse.getAuthToken());

        return registerResponse;
    }

    RegisterDAO getRegisterDAO() {return new RegisterDAO();}
}
