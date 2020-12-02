package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.ManagePassword;
import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.S3;
import edu.byu.cs.tweeter.server.dao.dao_helpers.get.GetAuthToken;
import edu.byu.cs.tweeter.server.dao.dao_helpers.get.GetUser;
import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutAuth;
import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutUser;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.service.response.RegisterResponse;

public class RegisterDAO {

    public static final String USER_PARTITION_KEY = "alias"; // AKA the primary key.

    public RegisterResponse register(RegisterRequest request) {

        // Check if user already exists in the database
        if(isUserAlreadyPresentInDatabase(request.getUser().getAlias())) {
            return new RegisterResponse("User already exists in the database");
        }

        // Check if AuthToken associated with proposed username already exists in the database
        if(isAuthTokenAlreadyPresentInDatabase(request.getUser().getAlias())) {
            return new RegisterResponse("AuthToken by that alias already exists in the database");
        }

        // Attempt to create User in the database
        User createMe = request.getUser();

        // Attempt to upload user image to S3 bucket
        String imageUrl = S3.uploadImage(createMe.getImageBytes(), createMe.getAlias());
        if(imageUrl.toUpperCase().contains("ERROR")) {
            return new RegisterResponse("Failed to upload user image to AWS S3.");
        }
        createMe.setImageUrl(imageUrl);
//        byte[] returnedBytes = S3.getImage(createMe.getAlias());
//        if(returnedBytes == null) {
//            return new RegisterResponse("Failed to get Image bytes from S3.");
//        }
//        createMe.setImageBytes(returnedBytes);

        // Hash the password.
        createMe.setPassword(ManagePassword.hashPassword(createMe.getPassword()));

        // Attempt to create the new user.
        PutItemOutcome putUserOutcome = (PutItemOutcome) PutUser.putUser(request.getUser());

        // If we failed to create a new User
        if(putUserOutcome.toString().toUpperCase().contains("ERROR")) {
            return new RegisterResponse(putUserOutcome.toString());
        }

        // Attempt to create a new AuthToken in the database
        PutItemOutcome putAuthOutcome = (PutItemOutcome) PutAuth.putAuth(request.getUser());

        // If we failed to create a new authToken
        if(putUserOutcome.toString().toUpperCase().contains("ERROR")) {
            return new RegisterResponse(putUserOutcome.toString());
        }

        // No errors => Successfully registered a new user
        String alias = request.getUser().getAlias();

        AuthToken createdAuthToken = GetAuthToken.getAuthToken(alias);
        User createdUser = GetUser.getUser(alias);

        return new RegisterResponse(createdUser, createdAuthToken);
    }

    public boolean isUserAlreadyPresentInDatabase(String alias) {
        if(GetUser.getUser(alias) == null) {
            return false;
        }
        return true;
    }

    public boolean isAuthTokenAlreadyPresentInDatabase(String alias) {
        if(GetAuthToken.getAuthToken(alias) == null) {
            return false;
        }
        return true;
    }
}
