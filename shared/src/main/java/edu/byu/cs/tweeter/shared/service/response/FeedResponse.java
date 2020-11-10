package edu.byu.cs.tweeter.shared.service.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.shared.domain.Status;

public class FeedResponse extends PagedResponse {

    private List<Status> statuses;

    public FeedResponse() {
        super();
    }

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FeedResponse(String message) {
        super(false, message, false);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param statuses the statuses to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public FeedResponse(List<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.statuses = statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    /**
     * Returns the statuses for the corresponding request.
     *
     * @return the feed.
     */
    public List<Status> getFeed() {
        return statuses;
    }

    public boolean getSuccess() {
        return super.isSuccess();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedResponse that = (FeedResponse) o;
        return Objects.equals(statuses, that.statuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuses);
    }
}
