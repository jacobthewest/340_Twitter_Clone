package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.UpdateFollowServiceProxy;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.UpdateFollowService;
import edu.byu.cs.tweeter.shared.service.request.UpdateFollowRequest;
import edu.byu.cs.tweeter.shared.service.response.UpdateFollowResponse;

public class UpdateFollowPresenter {

    private final UpdateFollowPresenter.View view;

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
    public UpdateFollowPresenter(UpdateFollowPresenter.View view) {
        this.view = view;
    }

    /**
     * Makes a updateFollow request.
     *
     * @param updateFollowRequest the request.
     */
    public UpdateFollowResponse getUpdateFollow(UpdateFollowRequest updateFollowRequest) throws IOException, TweeterRemoteException {
        UpdateFollowService updateFollowService = getUpdateFollowService();
        return updateFollowService.updateFollow(updateFollowRequest);
    }

    public UpdateFollowService getUpdateFollowService() {
        return new UpdateFollowServiceProxy();
    }
}
