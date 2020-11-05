package edu.byu.cs.tweeter.shared.service.request;

import edu.byu.cs.tweeter.shared.domain.User;

public class UpdateFollowRequest {

    private User user;
    private User followUser;
    private boolean followTheFollowUser;

    public UpdateFollowRequest() {}

    public UpdateFollowRequest(User user, User followUser, boolean followTheFollowUser) {
        this.user = user;
        this.followUser = followUser;
        this.followTheFollowUser = followTheFollowUser;
    }

    public User getUser() {
        return user;
    }
    public User getFollowUser() {
        return followUser;
    }
    public boolean followTheFollowUser() { return followTheFollowUser; }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFollowUser(User followUser) {
        this.followUser = followUser;
    }

    public void setFollowTheFollowUser(boolean followTheFollowUser) {
        this.followTheFollowUser = followTheFollowUser;
    }
}
