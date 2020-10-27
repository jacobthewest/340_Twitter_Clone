package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.SubmitTweetServiceImpl;
import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

public class SubmitTweetHandler implements RequestHandler<SubmitTweetRequest, SubmitTweetResponse> {
    @Override
    public SubmitTweetResponse handleRequest(SubmitTweetRequest submitTweetRequest, Context context) {
        SubmitTweetServiceImpl service = new SubmitTweetServiceImpl();
        return service.submitTweet(submitTweetRequest);
    }
}
