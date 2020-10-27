package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.client.presenter.FeedPresenter;
import edu.byu.cs.tweeter.shared.service.request.FeedRequest;
import edu.byu.cs.tweeter.shared.service.response.FeedResponse;

/**
 * An {@link AsyncTask} for retrieving a user's feed.
 */
public class GetFeedTask extends AsyncTask<FeedRequest, Void, FeedResponse> {

    private final FeedPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void statusesRetrieved(FeedResponse FeedResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followees.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetFeedTask(FeedPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve feed statuses. This method is
     * invoked indirectly by calling {@link #execute(FeedRequest...)}.
     *
     * @param feedRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected FeedResponse doInBackground(FeedRequest... feedRequests) {

        FeedResponse response = null;

        try {
            response = presenter.getFeed(feedRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param feedResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(FeedResponse feedResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.statusesRetrieved(feedResponse);
        }
    }
}
