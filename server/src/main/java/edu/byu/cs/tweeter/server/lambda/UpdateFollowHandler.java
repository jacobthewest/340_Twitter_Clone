package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.UpdateFollowServiceImpl;
import edu.byu.cs.tweeter.shared.service.request.UpdateFollowRequest;
import edu.byu.cs.tweeter.shared.service.response.UpdateFollowResponse;

public class UpdateFollowHandler implements RequestHandler<UpdateFollowRequest, UpdateFollowResponse> {
    @Override
    public UpdateFollowResponse handleRequest(UpdateFollowRequest updateFollowRequest, Context context) {
        UpdateFollowServiceImpl service = new UpdateFollowServiceImpl();
        return service.updateFollow(updateFollowRequest);
    }
}
