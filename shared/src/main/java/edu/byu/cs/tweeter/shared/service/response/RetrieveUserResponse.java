package edu.byu.cs.tweeter.shared.service.response;

import java.util.Objects;

import edu.byu.cs.tweeter.shared.domain.User;

/**
 * A response for a {@link edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest}.
 */
public class RetrieveUserResponse extends Response {

    private User user;

    public RetrieveUserResponse() {
        super(true, null);
    }

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public RetrieveUserResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param user the now retrieved user.
     */
    public RetrieveUserResponse(User user) {
        super(true, null);
        this.user = user;
    }

    /**
     * Returns the retrieved user.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    public boolean getSuccess() {
        return super.isSuccess();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetrieveUserResponse that = (RetrieveUserResponse) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "RetrieveUserResponse{" +
                "user=" + user +
                '}';
    }
}
