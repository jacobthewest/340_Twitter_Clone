package edu.byu.cs.tweeter.shared.service.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.shared.domain.User;

public class UpdateFollowResponse extends Response {

    private User user;
    private User followUser;
    private List<User> following;

    public UpdateFollowResponse() {
        super(true, null);
    }

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public UpdateFollowResponse(String message) {
        super(false, message);
    }

    public UpdateFollowResponse(User user, User followUser, List<User> following) {
        super(true, null);
        this.user = user;
        this.followUser = followUser;
        this.following = following;
    }

    public User getUser() {
        return this.user;
    }

    public User getFollowUser() {
        return this.followUser;
    }

    public List<User> getFollowing() {
        return this.following;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFollowUser(User followUser) {
        this.followUser = followUser;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateFollowResponse that = (UpdateFollowResponse) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(followUser, that.followUser) &&
                Objects.equals(following, that.following);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, followUser, following);
    }

    @Override
    public String toString() {
        return "UpdateFollowResponse{" +
                "user=" + user +
                ", followUser=" + followUser +
                ", following=" + following +
                '}';
    }
}
