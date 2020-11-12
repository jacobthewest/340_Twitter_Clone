package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.service.request.UpdateFollowRequest;
import edu.byu.cs.tweeter.shared.service.response.UpdateFollowResponse;

public class UpdateFollowServiceProxyTest {

    private UpdateFollowRequest validRequestFollow;
    private UpdateFollowRequest validRequestUnFollow;
    private UpdateFollowResponse followSuccessResponse;
    private UpdateFollowResponse unFollowSuccessResponse;
    private ServerFacade mockServerFacade;
    private UpdateFollowServiceProxy updateFollowServiceProxy;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
    private static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL, "password");
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL, "password");
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL, "password");
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL, "password");
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL, "password");
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL, "password");
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL, "password");
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL, "password");
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL, "password");
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL, "password");
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL, "password");
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL, "password");
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL, "password");
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL, "password");
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL, "password");
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL, "password");
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL, "password");
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL, "password");
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL, "password");
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL,"password");
    private final User JacobWest = new User("Jacob", "West", MIKE, "password");
    private final User RickyMartin = new User("Ricky", "Martin",  MIKE, "password");
    private final User RobertGardner = new User("Robert", "Gardner",  MIKE, "password");
    private final User Snowden = new User("The", "Snowden", MIKE, "password");
    private final User TristanThompson = new User("Tristan", "Thompson", MIKE, "password");
    private final User KCP = new User("Kontavius", "Caldwell Pope", MIKE,"password");
    private final User theMedia = new User("the", "Media", MIKE, "password");
    private final User Rudy = new User("Rudy", "Gobert", MIKE, "password");
    private final User BillBelichick = new User("Bill", "Belichick", MIKE, "password");
    private final User TestUser = new User("Test", "User", MALE_IMAGE_URL, "password");
    private final User userBarney = new User("Barney", "Rubble", MALE_IMAGE_URL, "password");
    private final User DaffyDuck = new User("Daffy", "Duck", FEMALE_IMAGE_URL, "password");
    private final User Zoe = new User("Zoe", "Zabriski", FEMALE_IMAGE_URL, "password");
    final User user = new User("Test", "User", MALE_IMAGE_URL, "password");
    private List<User> follow_following;
    private List<User> unFollow_following;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User unRecognizedUser = new User("Not", "Recognized", MIKE, "password");
        User unFollowThisUser = Rudy;
        User followThisUser = JacobWest;

        follow_following = getFollowing();
        follow_following.add(followThisUser);

        unFollow_following = getFollowing();
        unFollow_following.remove(unFollowThisUser);

        // Setup request objects to use in the tests
        validRequestFollow = new UpdateFollowRequest(user, followThisUser, true);
        validRequestUnFollow = new UpdateFollowRequest(user, unFollowThisUser, false);

        // Setup a mock ServerFacade that will return known responses
        followSuccessResponse = new UpdateFollowResponse(user, followThisUser, follow_following);
        unFollowSuccessResponse = new UpdateFollowResponse(user, unFollowThisUser, unFollow_following);
        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.updateFollow(validRequestFollow, "/updatefollow")).thenReturn(followSuccessResponse);
        Mockito.when(mockServerFacade.updateFollow(validRequestUnFollow, "/updatefollow")).thenReturn(unFollowSuccessResponse);

        // Create a CountServiceProxy instance and wrap it with a spy that will use the mock service
        updateFollowServiceProxy = Mockito.spy(new UpdateFollowServiceProxy());
        Mockito.when(updateFollowServiceProxy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testUpdateFollow_validRequest_validRequestFollow() throws IOException, TweeterRemoteException {
        UpdateFollowResponse response = updateFollowServiceProxy.updateFollow(validRequestFollow);
        Assertions.assertEquals(followSuccessResponse, response);
    }

    @Test
    public void testUpdateFollow_validRequest_validRequestUnFollow() throws IOException, TweeterRemoteException {
        UpdateFollowResponse response = updateFollowServiceProxy.updateFollow(validRequestUnFollow);
        Assertions.assertEquals(unFollowSuccessResponse, response);
    }

    private List<User> getFollowing() {
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(theMedia);
        list.add(user4);
        list.add(user5);
        list.add(user6);
        list.add(user7);
        list.add(RickyMartin);
        list.add(RobertGardner);
        list.add(TristanThompson);
        list.add(user8);
        list.add(user9);
        list.add(user10);
        list.add(Snowden);
        list.add(user12);
        list.add(user13);
        list.add(user14);
        list.add(user15);
        list.add(user16);
        list.add(user17);
        list.add(user18);
        list.add(TestUser);
        list.add(user19);
        list.add(user20);
        list.add(BillBelichick);
        list.add(KCP);
        list.add(Rudy);
        list.add(TestUser);
//        return Arrays.asList(user1, user2, theMedia, user4, user5, user6, user7, RickyMartin, RobertGardner, TristanThompson,
//                user8, user9, user10, user11, Snowden, user12, user13, user14, user15, user16, user17, user18, TestUser,
//                user19, user20, BillBelichick, KCP, Rudy, TestUser);
        return list;
    }

}
