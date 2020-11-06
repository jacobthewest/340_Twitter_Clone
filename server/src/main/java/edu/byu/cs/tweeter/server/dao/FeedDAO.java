package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.server.util.ListOfStatuses;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.service.request.FeedRequest;
import edu.byu.cs.tweeter.shared.service.response.FeedResponse;

public class FeedDAO {

    /**
     * Returns the statuses of the user specified in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose statuses are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FeedResponse getFeed(FeedRequest request) {
        assert request.getLimit() >= 0;
        assert request.getUser() != null;

        ListOfStatuses tool = new ListOfStatuses();
        Map<String, List<Status>> feedStatusesByUser = tool.initializeFeed();

        List<Status> allStatuses = feedStatusesByUser.get(request.getUser().getAlias());
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());
        Status[] arrResponseStatuses = (Status[]) responseStatuses.toArray();

        boolean hasMorePages = false;

        if (allStatuses == null) {
            return new FeedResponse(arrResponseStatuses, hasMorePages);
        }

        if(request.getLimit() > 0) {
            if (responseStatuses != null) {
                int statusesIndex = getFeedStartingIndex(request.getLastStatus(), allStatuses);

                for(int limitCounter = 0; statusesIndex < allStatuses.size() && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(statusesIndex));
                }

                hasMorePages = statusesIndex < allStatuses.size();
            }
        }

        arrResponseStatuses = (Status[]) responseStatuses.toArray();
        return new FeedResponse(arrResponseStatuses, hasMorePages);
    }

    /**
     * Determines the index for the first status in the specified 'allStatuses' list that should
     * be returned in the current request. This will be the index of the next status after the
     * specified 'lastStatus'.
     *
     * @param lastStatus the last follower that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allStatuses the generated list of followers from which we are returning paged results.
     * @return the index of the first follower to be returned.
     */
    private int getFeedStartingIndex(Status lastStatus, List<Status> allStatuses) {

        int statusesIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusesIndex = i + 1;
                }
            }
        }

        return statusesIndex;
    }
}
