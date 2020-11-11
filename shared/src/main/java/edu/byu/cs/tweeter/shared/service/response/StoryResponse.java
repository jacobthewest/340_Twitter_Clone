package edu.byu.cs.tweeter.shared.service.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.shared.domain.Status;

public class StoryResponse extends PagedResponse {

    private List<Status> statuses;

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
    public StoryResponse(List<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.statuses = statuses;
    }

    /**
     * Returns the statuses for the corresponding request.
     *
     * @return the story.
     */
    public List<Status> getStatuses() {
        return statuses;
    }

    public boolean getSuccess() {
        return super.isSuccess();
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryResponse that = (StoryResponse) o;
        return Objects.equals(statuses, that.statuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuses);
    }
}
