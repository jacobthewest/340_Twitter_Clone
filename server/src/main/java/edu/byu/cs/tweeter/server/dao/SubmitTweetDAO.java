package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.server.dao.dao_helpers.get.GetStatus;
import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutStatus;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.service.request.SubmitTweetRequest;
import edu.byu.cs.tweeter.shared.service.response.SubmitTweetResponse;

public class SubmitTweetDAO {

    public SubmitTweetResponse submitTweet(SubmitTweetRequest request) {
        String userAlias = request.getUser().getAlias();
        String statusAlias = request.getStatus().getUser().getAlias();

        if(!userAlias.equals(statusAlias)) {
            return new SubmitTweetResponse("User alias and status alias don't match.");
        }

        Object o = PutStatus.putStatus(request.getStatus());
        if(o == null) {
            return new SubmitTweetResponse("Error submitting the tweet");

        }

        Status status = GetStatus.getStatus(userAlias, request.getStatus().getTimePosted(), true);

        return new SubmitTweetResponse(request.getUser(), status);
    }
}
