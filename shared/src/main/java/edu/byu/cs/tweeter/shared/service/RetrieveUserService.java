package edu.byu.cs.tweeter.shared.service;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public interface RetrieveUserService {
    RetrieveUserResponse retrieveUser(RetrieveUserRequest request)
            throws IOException, TweeterRemoteException;
}
