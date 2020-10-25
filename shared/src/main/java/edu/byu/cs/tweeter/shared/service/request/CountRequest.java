package edu.byu.cs.tweeter.shared.service.request;

import edu.byu.cs.tweeter.shared.domain.User;

public class CountRequest {

    private final User user;

    public CountRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
