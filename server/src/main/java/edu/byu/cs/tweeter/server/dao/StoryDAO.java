package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.server.dao.dao_helpers.query.QueryStatus;
import edu.byu.cs.tweeter.server.util.ListOfStatuses;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.service.request.StoryRequest;
import edu.byu.cs.tweeter.shared.service.response.StoryResponse;

public class StoryDAO {

    public StoryResponse getStory(StoryRequest request) {
        List<Status> story = QueryStatus.queryStorySorted(request);

        if (story == null) {
            return new StoryResponse("Error retrieving the story.");
        }

//        // Load the pages for the users in the feed.
//        for(Status s: story) {
//            User u = s.getUser();
//            byte[] imageBytes = S3.getImage(u.getAlias());
//            u.setImageBytes(imageBytes);
//        }

        // How do we tell if we need more pages?
        boolean hasMorePages = false;
        if(story.size() == request.getLimit()) {
            hasMorePages = true;
        }

        return new StoryResponse(story, hasMorePages);
    }

    public StoryResponse oldGetStory(StoryRequest request) {

        ListOfStatuses tool = new ListOfStatuses();
        Map<String, List<Status>> storyStatusesByUser = tool.initializeStory();

        List<Status> allStatuses = storyStatusesByUser.get(request.getUser().getAlias());
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (allStatuses == null) {
            return new StoryResponse(responseStatuses, hasMorePages);
        }

        if(request.getLimit() > 0) {
            if (responseStatuses != null) {
                int statusesIndex = getStoryStartingIndex(request.getLastStatus(), allStatuses);

                for(int limitCounter = 0; statusesIndex < allStatuses.size() && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(statusesIndex));
                }

                hasMorePages = statusesIndex < allStatuses.size();
            }
        }

        return new StoryResponse(responseStatuses, hasMorePages);
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
    private int getStoryStartingIndex(Status lastStatus, List<Status> allStatuses) {

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
