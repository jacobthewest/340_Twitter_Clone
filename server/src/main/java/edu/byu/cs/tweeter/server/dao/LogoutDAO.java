package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.server.dao.dao_helpers.delete.DeleteAuthToken;
import edu.byu.cs.tweeter.server.dao.dao_helpers.get.GetAuthToken;
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

        // Delete the authToken
        String alias = request.getUser().getAlias();
        String authTokenId = request.getAuthToken().getId();

        String o = DeleteAuthToken.deleteAuthToken(request.getUser().getAlias());
        if(o.toUpperCase().contains("ERROR")) {
            return new LogoutResponse("There was an error deleting the AuthToken");
        }

        AuthToken updatedAuthToken = GetAuthToken.getAuthTokenByUsernameAndId(alias, authTokenId);

        User user = request.getUser();
        user.setImageBytes(null); // Currently at M4 and I have no idk why this is here.

        return new LogoutResponse(user, updatedAuthToken);
    }
}
