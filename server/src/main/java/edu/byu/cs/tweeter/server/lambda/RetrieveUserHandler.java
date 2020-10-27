package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.service.RetrieveUserServiceImpl;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public class RetrieveUserHandler implements RequestHandler<RetrieveUserRequest, RetrieveUserResponse> {
    @Override
    public RetrieveUserResponse handleRequest(RetrieveUserRequest retrieveUserRequest, Context context) {
        RetrieveUserServiceImpl service = new RetrieveUserServiceImpl();
        return service.retrieveUser(retrieveUserRequest);
    }
}
