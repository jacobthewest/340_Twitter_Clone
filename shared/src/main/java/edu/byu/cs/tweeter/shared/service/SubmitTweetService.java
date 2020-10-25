package edu.byu.cs.tweeter.shared.service;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

public interface SubmitTweetService {
    SubmitTweetResponse submitTweet(SubmitTweetRequest request)
            throws IOException, TweeterRemoteException;
}
