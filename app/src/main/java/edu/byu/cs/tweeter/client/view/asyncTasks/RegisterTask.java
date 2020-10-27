package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.client.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.service.response.RegisterResponse;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResponse> {

    private final RegisterPresenter presenter;
    private final RegisterTask.Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void registerSuccessful(RegisterResponse registerResponse);
        void registerUnsuccessful(RegisterResponse registerResponse);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to register.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public RegisterTask(RegisterPresenter presenter, RegisterTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on a background thread to log the user in. This method is
     * invoked indirectly by calling {@link #execute(RegisterRequest...)}.
     *
     * @param registerRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected RegisterResponse doInBackground(RegisterRequest... registerRequests) {
        RegisterResponse registerResponse = null;

        try {
            registerResponse = presenter.getRegister(registerRequests[0]);

            if(registerResponse.isSuccess()) {
                loadImage(registerResponse.getUser());
            }
        } catch (Exception ex) {
            exception = ex;
        }

        return registerResponse;
    }

    /**
     * Loads the profile image for the user.
     *
     * @param user the user whose profile image is to be loaded.
     */
    private void loadImage(User user) {
        // TODO: I changed this to get the user's set image bytes that we DID NOT get from the imageURL from the server.
        // we will probably have to load that manually from the server, but for Milestone #2, I'm just using the image
        // locally.
//        try {
        byte [] bytes = user.getImageBytes();
        user.setImageBytes(bytes);
//                ***Old code below*** Includes the try catch
//                byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
//                user.setImageBytes(bytes);
//        } catch (IOException e) {
//            Log.e(this.getClass().getName(), e.toString(), e);
//        }
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(RegisterRequest...)} method) when the task completes.
     *
     * @param registerResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(RegisterResponse registerResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(registerResponse.isSuccess()) {
            observer.registerSuccessful(registerResponse);
        } else {
            observer.registerUnsuccessful(registerResponse);
        }
    }
}
