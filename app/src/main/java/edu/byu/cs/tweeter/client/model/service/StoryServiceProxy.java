package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.StoryService;
import edu.byu.cs.tweeter.shared.service.request.StoryRequest;
import edu.byu.cs.tweeter.shared.service.response.StoryResponse;

/**
 * Contains the business logic for getting the statuses of the logged in user.
 */
public class StoryServiceProxy implements StoryService {

    private static final String URL_PATH = "/story";

    /**
     * Returns the statuses of the specified user in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the statuses from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the statuses.
     */
    @Override
    public StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException {
        StoryResponse response = getServerFacade().getStory(request, URL_PATH);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    /**
     * Loads the profile image of the user for each status in the StoryResponse.
     *
     * @param response the response from the story request.
     */
    private void loadImages(StoryResponse response) throws IOException {
        for(Status status : response.getStory()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(status.getUser().getImageUrl());
            status.getUser().setImageBytes(bytes);
        }
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
