package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public class RetrieveUserServiceProxyTest {

    String username = "@TestUser";
    final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    User TestUser = new User("Test", "User", MALE_IMAGE_URL, "password");
    private RetrieveUserRequest validRequest;
    private RetrieveUserRequest invalidRequestOne;
    private RetrieveUserRequest invalidRequestTwo;
    private RetrieveUserRequest invalidRequestThree;
    private RetrieveUserResponse successResponse;
    private RetrieveUserResponse failureResponse;
    private ServerFacade mockServerFacade;
    private RetrieveUserServiceProxy retrieveUserServiceProxy;


    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        mockServerFacade = Mockito.mock(ServerFacade.class);
        TestUser.setAlias("@TestUser");
        User currentUser = new User("Test", "User", MALE_IMAGE_URL, "password");

        // Setup request objects to use in the tests
        validRequest = new RetrieveUserRequest(username);
        invalidRequestOne = new RetrieveUserRequest("@NotRegistered"); // Username not registered
        invalidRequestTwo = new RetrieveUserRequest(null); // Username is null
        invalidRequestThree = new RetrieveUserRequest(""); // Username is empty

        // Setup a mock ServerFacade that will return known responses
        successResponse = new RetrieveUserResponse(TestUser);
        Mockito.when(mockServerFacade.retrieveUser(validRequest, "/retrieveuser")).thenReturn(successResponse);

        failureResponse = new RetrieveUserResponse("An exception occured");
        Mockito.when(mockServerFacade.retrieveUser(invalidRequestOne, "/retrieveuser")).thenReturn(failureResponse);
        Mockito.when(mockServerFacade.retrieveUser(invalidRequestTwo, "/retrieveuser")).thenReturn(failureResponse);
        Mockito.when(mockServerFacade.retrieveUser(invalidRequestThree, "/retrieveuser")).thenReturn(failureResponse);

        // Create a CountServiceProxy instance and wrap it with a spy that will use the mock service
        retrieveUserServiceProxy = Mockito.spy(new RetrieveUserServiceProxy());
        Mockito.when(retrieveUserServiceProxy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testRetrieveUser_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        RetrieveUserResponse response = retrieveUserServiceProxy.retrieveUser(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testRetrieveUser_invalidRequest_usernameNotRegistered() throws IOException, TweeterRemoteException {
        RetrieveUserResponse response = retrieveUserServiceProxy.retrieveUser(invalidRequestOne);
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testRetrieveUser_invalidRequest_usernameIsNull() throws IOException, TweeterRemoteException {
        RetrieveUserResponse response = retrieveUserServiceProxy.retrieveUser(invalidRequestTwo);
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testRetrieveUser_invalidRequest_usernameIsEmpty() throws IOException, TweeterRemoteException {
        RetrieveUserResponse response = retrieveUserServiceProxy.retrieveUser(invalidRequestThree);
        Assertions.assertEquals(failureResponse, response);
    }

}
