package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.CountRequest;
import edu.byu.cs.tweeter.shared.service.response.CountResponse;

public class CountServiceProxyTest {

    private CountRequest validRequest;
    private CountRequest invalidRequest;
    private CountResponse successResponse;
    private CountResponse failureResponse;
    private ServerFacade mockServerFacade;
    private CountServiceProxy countServiceProxy;
    private int followingCount = 29;
    private int followersCount = 29;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("Test", "User", null, "password");

        // Setup request objects to use in the tests
        validRequest = new CountRequest(currentUser);
        invalidRequest = new CountRequest(null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new CountResponse(currentUser, followingCount, followersCount );
        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getCount(validRequest, "/count")).thenReturn(successResponse);

        failureResponse = new CountResponse("An exception occured");
        Mockito.when(mockServerFacade.getCount(invalidRequest, "/count")).thenReturn(failureResponse);

        // Create a CountServiceProxy instance and wrap it with a spy that will use the mock service
        countServiceProxy = Mockito.spy(new CountServiceProxy());
        Mockito.when(countServiceProxy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetCount_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        CountResponse response = countServiceProxy.getCount(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetCount_invalidRequest_returnsNoCount() throws IOException, TweeterRemoteException {
        CountResponse response = countServiceProxy.getCount(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

}
