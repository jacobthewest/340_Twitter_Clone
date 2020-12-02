package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.service.response.RegisterResponse;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;

public class RegisterServiceProxyTest {

    private RegisterRequest validRequest;
    private RegisterRequest invalidRequestOne;
    private RegisterRequest invalidRequestTwo;
    private RegisterResponse successResponse;
    private RegisterResponse failureResponse;
    private ServerFacade mockServerFacade;
    private RegisterServiceProxy registerServiceProxy;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {

        String username = "@TestUser";
        String password = "password";
        String firstName = "Test";
        String lastName = "User";
        String imageUrl = MALE_IMAGE_URL;
        byte[] imageBytes = null;
        AuthToken authToken = new AuthToken(username);
        User user = new User(firstName, lastName, imageUrl, password);
        user.setAlias(username);
        user.setImageBytes(imageBytes);

        try {
            InputStream iStream = new ByteArrayInputStream(imageUrl.getBytes());
            imageBytes = ImageUtils.byteArrayFromUri(iStream);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Setup request objects to use in the tests
        User validUser = new User(firstName, lastName, imageUrl, password);
        validUser.setImageBytes(imageBytes);
        validRequest = new RegisterRequest(validUser);

        User inValidUser1 = new User(firstName, lastName, imageUrl, password);
        inValidUser1.setImageBytes(null);
        invalidRequestOne = new RegisterRequest(inValidUser1);
        invalidRequestOne = new RegisterRequest(inValidUser1);
            // Screw up the imageBytes
        User inValidUser2 = new User(firstName, lastName, imageUrl, password);
        inValidUser2.setImageBytes(imageBytes);
        inValidUser2.setAlias("BadUsername");
        invalidRequestTwo = new RegisterRequest(inValidUser2);
            // Screw up the username

        // Setup a mock ServerFacade that will return known responses
        successResponse = new RegisterResponse(user, authToken);
        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.register(validRequest, "/register")).thenReturn(successResponse);

        failureResponse = new RegisterResponse("An exception occured");
        Mockito.when(mockServerFacade.register(invalidRequestOne, "/register")).thenReturn(failureResponse);
        Mockito.when(mockServerFacade.register(invalidRequestTwo, "/register")).thenReturn(failureResponse);

        // Create a CountServiceProxy instance and wrap it with a spy that will use the mock edu.byu.cs.tweeter.server.service
        registerServiceProxy = Mockito.spy(new RegisterServiceProxy());
        Mockito.when(registerServiceProxy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testRegister_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceProxy.register(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testRegister_invalidRequest_emptyImageBytes() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceProxy.register(invalidRequestOne);
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testRegister_invalidRequest_usernameDoesNotMatchWithAuthToken() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceProxy.register(invalidRequestTwo);
        Assertions.assertEquals(failureResponse, response);
    }
}
