package edu.byu.cs.tweeter.client.view.util;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.presenter.RetrieveUserPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.RetrieveUserTask;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public class AliasClickableSpan extends ClickableSpan implements RetrieveUserPresenter.View, RetrieveUserTask.Observer {
    private static final String LOG_TAG = "RegisterFragment";

    private Activity activity;
    private User user;
    private User followUser;
    private String mention;
    private AuthToken authToken;
    private RetrieveUserPresenter presenter;
    private Toast toast;

    public AliasClickableSpan(Activity activity, User user, String mention, AuthToken authToken) {
        this.activity = activity;
        this.user = user;
        this.mention = mention;
        this.authToken = authToken;
        presenter = new RetrieveUserPresenter(this);
    }

    private RetrieveUserTask.Observer getObserver() {
        return this;
    }

    @Override
    public void onClick(@NonNull View widget) {
        RetrieveUserRequest retrieveUserRequest = new RetrieveUserRequest(mention);
        RetrieveUserTask retrieveUserTask = new RetrieveUserTask(presenter, getObserver());
        retrieveUserTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, retrieveUserRequest);
    }

    @Override
    public void retrieveUserSuccessful(RetrieveUserResponse retrieveUserResponse) {
        Intent intent = new Intent(this.activity, MainActivity.class);

        this.user.setImageBytes(null);
        User followUser = retrieveUserResponse.getUser();
        followUser.setImageBytes(null);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, user);
        intent.putExtra(MainActivity.CURRENT_FOLLOW_KEY, followUser);
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, this.authToken);

        this.activity.startActivity(intent);
        activity.finish(); // This prevents us from going back, but it makes the app work. See what you can
        // do with this later on. // TODO: Make intent data stay on the memory stack.
    }

    @Override
    public void retrieveUserUnsuccessful(RetrieveUserResponse retrieveUserResponse) {
        Toast.makeText(this.activity, "Failed to retrieve clicked user. " + retrieveUserResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception ex) {
        Log.e(LOG_TAG, ex.getMessage(), ex);
        toast.setText("Failed to retrieve clicked user because of exception: " + ex.getMessage());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
