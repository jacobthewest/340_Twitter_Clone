package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.server.dao.dao_helpers.get.GetAuthToken;
import edu.byu.cs.tweeter.server.dao.dao_helpers.update.UpdateAuthToken;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.service.response.LogoutResponse;

public class LogoutDAO {

    public static final String AUTH_TOKEN_TABLE_NAME = "authToken";

    public LogoutResponse logout(LogoutRequest request) {

        if(!request.getUser().getAlias().equals(request.getAuthToken().getUsername())) {
            return new LogoutResponse("AuthToken and User's alias do not match");
        }

        // Deactivate the authToken
        String alias = request.getUser().getAlias();
        String authTokenId = request.getAuthToken().getId();

        Object o = UpdateAuthToken.deactivateAuthToken(request.getUser().getAlias());
        if(o.toString().toUpperCase().contains("ERROR")) {
            return new LogoutResponse("There was an error deactivating the AuthToken");
        }

        AuthToken updatedAuthToken = GetAuthToken.getAuthTokenByUsernameAndId(alias, authTokenId);

        User user = request.getUser();
        user.setImageBytes(null); // Currently at M4 and I have no idk why this is here.

        return new LogoutResponse(user, updatedAuthToken);
    }
}
