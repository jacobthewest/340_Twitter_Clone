package edu.byu.cs.tweeter.server.util;

import java.util.List;

import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;

public class RequestAndResponseChecker {
    public RequestAndResponseChecker() {}

    public void checkUserRequest(User user) {
        if(user == null) {
            throw new RuntimeException("^\\[BadRequest\\].* User object cannot be null.");
        }
        if(user.getAlias().equals("") || user.getAlias().equals(null)) {
            throw new RuntimeException("^\\[BadRequest\\].* User alias cannot be an empty string or null.");
        }
    }

    public void checkUserResponse(User user) {
        if(user == null) {
            throw new RuntimeException("^\\[InternalServerError\\].* User object cannot be null.");
        }
        if(user.getAlias().equals("") || user.getAlias().equals(null)) {
            throw new RuntimeException("^\\[InternalServerError\\].* User alias cannot be an empty string or null.");
        }
    }

    public void checkUserLastFollowRequest(User user, User lastFollow) {
        if(user.equals(lastFollow)) {
            throw new RuntimeException("^\\[BadRequest\\].* User cannot equal lastFollowUser.");
        }
    }

    public void checkStatus(Status status) {
        if(status == null) {
            throw new RuntimeException("^\\[BadRequest\\].* LastStatus cannot be null.");
        }
        if(status == null) {
            throw new RuntimeException("^\\[BadRequest\\].* LastStatus user cannot be null.");
        }
        if(status.getUser().getAlias().equals(null) || status.getUser().getAlias().equals("")) {
            throw new RuntimeException("^\\[BadRequest\\].* LastStatus user's alias cannot be null or empty.");
        }
    }

    public void checkLimitRequest(int limit) {
        if(limit <= 0) {
            throw new RuntimeException("^\\[BadRequest\\].* Request limit must be greater than zero.");
        }
    }

    public void checkStatusListResponse(List<Status> statuses, int limit) {
        if(statuses.size() > limit) {
            throw new RuntimeException("^\\[InternalServerError\\].* Returned more statuses than asked for.");
        }
    }

    public void checkUserListResponse(List<User> users, int limit) {
        if(users.size() > limit) {
            throw new RuntimeException("^\\[InternalServerError\\].* Returned more users than asked for.");
        }
    }

    public void checkFollowingFollowersResponse(int followingCount, int followersCount) {
        if(followingCount < 0 || followersCount < 0) {
            throw new RuntimeException("^\\[InternalServerError\\].* User cannot have negative followers or following.");
        }
    }

    public void checkEmptyNullStringRequest(String str, String strName) {
        if(str.equals("") || str.equals(null)) {
            throw new RuntimeException("^\\[BadRequest\\].* " + strName + " cannot be null or empty.");
        }
    }

    public void checkEmptyNullStringResponse(String str, String strName) {
        if(str.equals("") || str.equals(null)) {
            throw new RuntimeException("^\\[InternalServerError\\].* " + strName + " cannot be null or empty.");
        }
    }

    public void checkAuthTokenResponse(User user, AuthToken authToken) {
        if(!user.getAlias().equals(authToken.getUsername())) {
            throw new RuntimeException("^\\[InternalServerError\\].* Response user and AuthToken usernames don't match.");
        }
        checkEmptyNullStringResponse(authToken.getId(), "Id");
        checkEmptyNullStringResponse(authToken.getUsername(), "Alias");
    }

    public void checkAuthTokenRequest(User user, AuthToken authToken) {
        if(!user.getAlias().equals(authToken.getUsername())) {
            throw new RuntimeException("^\\[BadRequest\\].* Response user and AuthToken usernames don't match.");
        }
        checkEmptyNullStringRequest(authToken.getId(), "Id");
        checkEmptyNullStringRequest(authToken.getUsername(), "Alias");
    }

    public void checkStatusResponse(User user, Status status) {
        if(status.equals(null)) {
            throw new RuntimeException("^\\[InternalServerError\\].* Posted status cannot be null.");
        }
        if(!status.getUser().equals(user)) {
            throw new RuntimeException("^\\[InternalServerError\\].* Posted status does not equal user who posted it.");
        }
    }
}
