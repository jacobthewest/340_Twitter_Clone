package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.CountService;
import edu.byu.cs.tweeter.shared.service.request.CountRequest;
import edu.byu.cs.tweeter.shared.service.response.CountResponse;

public class CountServiceProxy implements CountService {

    static final String URL_PATH = "/count";

    @Override
    public CountResponse getCount(CountRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        CountResponse countResponse = serverFacade.getCount(request, URL_PATH);

        return countResponse;
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
