package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.RetrieveUserServiceProxy;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.RetrieveUserService;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public class RetrieveUserPresenter {

    private final RetrieveUserPresenter.View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public RetrieveUserPresenter(RetrieveUserPresenter.View view) {
        this.view = view;
    }

    /**
     * Makes a retrieveUser request.
     *
     * @param retrieveUserRequest the request.
     */
    public RetrieveUserResponse retrieveUser(RetrieveUserRequest retrieveUserRequest) throws IOException, TweeterRemoteException {
        RetrieveUserService retrieveUserService = getRetrieveUserService();
        return retrieveUserService.retrieveUser(retrieveUserRequest);
    }

    public RetrieveUserService getRetrieveUserService() {
        return new RetrieveUserServiceProxy();
    }
}
