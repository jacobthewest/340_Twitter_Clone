package edu.byu.cs.tweeter.shared.service.response;

import java.util.Arrays;

import edu.byu.cs.tweeter.shared.domain.Status;

public class FeedResponse extends PagedResponse {

    private Status[] statuses;

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
    public FeedResponse(Status[] statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.statuses = statuses;
    }

    public void setStatuses(Status[] statuses) {
        this.statuses = statuses;
    }

    /**
     * Returns the statuses for the corresponding request.
     *
     * @return the feed.
     */
    public Status[] getFeed() {
        return statuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedResponse that = (FeedResponse) o;
        return Arrays.equals(statuses, that.statuses);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(statuses);
    }
}
