package edu.byu.cs.tweeter.shared.service.request;

import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;

public class LogoutRequest {

    private User user;
    private AuthToken authToken;

    public LogoutRequest() {}

    /**
     * Creates an instance.
     *
     * @param user the username of the user to be logged out.
     */
    public LogoutRequest(User user, AuthToken authToken) {
        this.user = user;
        this.authToken = authToken;
    }

    /**
     * Returns the username of the user to be logged out by this request.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the authToken of the user to be logged out by this request.
     *
     * @return the authToken.
     */
    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
