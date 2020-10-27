package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.SubmitTweetService;
import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

/**
 * Contains the business logic to support the submitTweet operation.
 */
public class SubmitTweetServiceProxy implements SubmitTweetService {

    private static final String URL_PATH = "/submittweet";

    @Override
    public SubmitTweetResponse submitTweet(SubmitTweetRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        SubmitTweetResponse submitTweetResponse = serverFacade.submitTweet(request, URL_PATH);

        return submitTweetResponse;
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
