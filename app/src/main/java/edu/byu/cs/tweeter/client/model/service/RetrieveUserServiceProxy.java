package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.RetrieveUserService;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;

/**
 * Contains the business logic to support the retrieveUser operation.
 */
public class RetrieveUserServiceProxy implements RetrieveUserService {

    private static final String URL_PATH = "/retrieveuser";

    @Override
    public RetrieveUserResponse retrieveUser(RetrieveUserRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        RetrieveUserResponse retrieveUserResponse = serverFacade.retrieveUser(request, URL_PATH);

        if(retrieveUserResponse.isSuccess()) {
            // loadImage(retrieveUserResponse.getUser());
        }

        return retrieveUserResponse;
    }

    /**
     * Loads the profile image data for the user.
     *
     * @param user the user whose profile image data is to be loaded.
     */
    private void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
