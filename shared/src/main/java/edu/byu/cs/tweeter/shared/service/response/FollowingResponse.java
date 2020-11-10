package edu.byu.cs.tweeter.shared.service.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.shared.domain.User;

/**
 * A paged response for a {@link edu.byu.cs.tweeter.shared.service.request.FollowingRequest}.
 */
public class FollowingResponse extends PagedResponse {

    private List<User> followees;

    public FollowingResponse() {
        super();
    }

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FollowingResponse(String message) {
        super(false, message, false);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param followees the followees to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public FollowingResponse(List<User> followees, boolean hasMorePages) {
        super(true, hasMorePages);
        this.followees = followees;
    }

    /**
     * Returns the followees for the corresponding request.
     *
     * @return the followees.
     */
    public List<User> getFollowees() {
        return followees;
    }

    public void setFollowees(List<User> followees) {
        this.followees = followees;
    }

    public boolean getSuccess() {
        return super.isSuccess();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowingResponse that = (FollowingResponse) o;
        return Objects.equals(followees, that.followees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followees);
    }
}
