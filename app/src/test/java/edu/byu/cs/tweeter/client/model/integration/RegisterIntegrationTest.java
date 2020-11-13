package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.RegisterServiceProxy;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.service.response.RegisterResponse;

public class RegisterIntegrationTest {

    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;
    private RegisterResponse correctResponse;
    private RegisterServiceProxy registerServiceProxy;
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";
    private final String id = "37971095-e717-41ff-b249-1af46eb3c849";

    @BeforeEach
    public void setup() throws Exception {
        User user = new User("New", "User", MIKE, "password");
        user.setImageBytesAsString("A legit image byte string... hehe.");
        AuthToken authToken = new AuthToken("@NewUser");
        authToken.setId(id);
        User invalidUser = new User(null, "User", MIKE, "password");
        invalidUser.setImageBytes(ImageUtils.byteArrayFromUrl(MIKE));

        // Setup a request object to use in the tests
        validRequest = new RegisterRequest(user);
        invalidRequest = new RegisterRequest(invalidUser);

        // Setup a mock RegisterDAO that will return known responses
        correctResponse = new RegisterResponse(user, authToken);

        registerServiceProxy = new RegisterServiceProxy();
    }

    @Test
    public void testGetRegister_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceProxy.register(validRequest);
        response.getAuthToken().setId(id);
        response.getUser().setImageBytes(null);
        Assertions.assertEquals(correctResponse, response);
    }

    @Test
    public void testGetRegister_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        try {
            registerServiceProxy.register(invalidRequest);
        } catch(TweeterRemoteException e) {
            String error = e.getMessage().toString();
            Assertions.assertTrue(error.contains("[BadRequest]"));
        }
    }
}