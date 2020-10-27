package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.client.presenter.CountPresenter;
import edu.byu.cs.tweeter.shared.service.request.CountRequest;
import edu.byu.cs.tweeter.shared.service.response.CountResponse;

public class CountTask extends AsyncTask<CountRequest, Void, CountResponse> {

    private final CountPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void countSuccessful(CountResponse countResponse);
        void countUnsuccessful(CountResponse countResponse);
        void handleException(Exception ex);
    }
    
    public CountTask(CountPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }
    
    @Override
    protected CountResponse doInBackground(CountRequest... countRequests) {
        CountResponse countResponse = null;

        try {
            countResponse = presenter.getCount(countRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return countResponse;
    }

    @Override
    protected void onPostExecute(CountResponse countResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(countResponse.isSuccess()) {
            observer.countSuccessful(countResponse);
        } else {
            observer.countUnsuccessful(countResponse);
        }
    }
}
