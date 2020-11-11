package edu.byu.cs.tweeter.shared.service.request;

import edu.byu.cs.tweeter.shared.domain.User;

/**
 * Contains all the information needed to make a register request.
 */
public class RegisterRequest {

    private User user;

    public RegisterRequest() {}

    public RegisterRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

