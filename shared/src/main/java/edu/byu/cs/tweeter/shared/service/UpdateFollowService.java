package edu.byu.cs.tweeter.shared.service;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.UpdateFollowRequest;
import edu.byu.cs.tweeter.shared.service.response.UpdateFollowResponse;

public interface UpdateFollowService {
    UpdateFollowResponse updateFollow(UpdateFollowRequest request)
            throws IOException, TweeterRemoteException;
}
