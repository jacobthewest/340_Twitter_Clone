package edu.byu.cs.tweeter.shared.service.response;

import java.util.Arrays;

import edu.byu.cs.tweeter.shared.domain.Status;

public class StoryResponse extends PagedResponse {

    private Status[] statuses;

    public StoryResponse() {
        super();
    }

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public StoryResponse(String message) {
        super(false, message, false);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param statuses the statuses to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public StoryResponse(Status[] statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.statuses = statuses;
    }

    /**
     * Returns the statuses for the corresponding request.
     *
     * @return the story.
     */
    public Status[] getStory() {
        return statuses;
    }

    public void setStatuses(Status[] statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryResponse that = (StoryResponse) o;
        return Arrays.equals(statuses, that.statuses);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(statuses);
    }
}
