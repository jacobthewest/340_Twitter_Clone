package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.RetrieveUserRequest;
import edu.byu.cs.tweeter.shared.service.response.RetrieveUserResponse;

public class RetrieveUserDAO {

    // This is the hard coded followee data returned by the 'getFollowees()' method
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
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL, "password");
    private final User JacobWest = new User("Jacob", "West", "@JacobWest", MIKE, "password");
    private final User RickyMartin = new User("Ricky", "Martin", "@RickyMartin", MIKE, "password");
    private final User RobertGardner = new User("Robert", "Gardner", "@RobertGardner", MIKE, "password");
    private final User Snowden = new User("The", "Snowden", "@Snowden", MIKE, "password");
    private final User TristanThompson = new User("Tristan", "Thompson", "@TristanThompson", MIKE, "password");
    private final User KCP = new User("Kontavius", "Caldwell Pope", "@KCP", MIKE, "password");
    private final User theMedia = new User("the", "Media", "@theMedia", MIKE, "password");
    private final User Rudy = new User("Rudy", "Gobert", "@Rudy", MIKE, "password");
    private final User BillBelichick = new User("Bill", "Belichick", "@BillBelichick", MIKE, "password");
    private final User TestUser = new User("Test", "User", "@TestUser", MALE_IMAGE_URL, "password");
    private final User userBarney = new User("Barney", "Rubble", "", "password");
    private final User DaffyDuck = new User("Daffy", "Duck", "", "password");
    private final User Zoe = new User("Zoe", "Zabriski", "", "password");

    public RetrieveUserResponse retrieveUser(RetrieveUserRequest request) {
        assert request.getUsername().equals("") || request.getUsername() == null;

        // START
        // Code to get the user from the database by their username
        // STOP

        if (request.getUsername().equals(user1.getAlias())) {
            return new RetrieveUserResponse(user1);
        } else if (request.getUsername().equals(user2.getAlias())) {
            return new RetrieveUserResponse(user2);
        } else if (request.getUsername().equals(user3.getAlias())) {
            return new RetrieveUserResponse(user3);
        } else if (request.getUsername().equals(user4.getAlias())) {
            return new RetrieveUserResponse(user4);
        } else if (request.getUsername().equals(user5.getAlias())) {
            return new RetrieveUserResponse(user5);
        } else if (request.getUsername().equals(user6.getAlias())) {
            return new RetrieveUserResponse(user6);
        } else if (request.getUsername().equals(user7.getAlias())) {
            return new RetrieveUserResponse(user7);
        } else if (request.getUsername().equals(user8.getAlias())) {
            return new RetrieveUserResponse(user8);
        } else if (request.getUsername().equals(user9.getAlias())) {
            return new RetrieveUserResponse(user9);
        } else if (request.getUsername().equals(user10.getAlias())) {
            return new RetrieveUserResponse(user10);
        } else if (request.getUsername().equals(user11.getAlias())) {
            return new RetrieveUserResponse(user11);
        } else if (request.getUsername().equals(user12.getAlias())) {
            return new RetrieveUserResponse(user12);
        } else if (request.getUsername().equals(user13.getAlias())) {
            return new RetrieveUserResponse(user13);
        } else if (request.getUsername().equals(user14.getAlias())) {
            return new RetrieveUserResponse(user14);
        } else if (request.getUsername().equals(user15.getAlias())) {
            return new RetrieveUserResponse(user15);
        } else if (request.getUsername().equals(user16.getAlias())) {
            return new RetrieveUserResponse(user16);
        } else if (request.getUsername().equals(user17.getAlias())) {
            return new RetrieveUserResponse(user17);
        } else if (request.getUsername().equals(user18.getAlias())) {
            return new RetrieveUserResponse(user18);
        } else if (request.getUsername().equals(user19.getAlias())) {
            return new RetrieveUserResponse(user19);
        } else if (request.getUsername().equals(user20.getAlias())) {
            return new RetrieveUserResponse(user20);
        } else if (request.getUsername().equals(JacobWest.getAlias())) {
            return new RetrieveUserResponse(JacobWest);
        } else if (request.getUsername().equals(RickyMartin.getAlias())) {
            return new RetrieveUserResponse(RickyMartin);
        } else if (request.getUsername().equals(RobertGardner.getAlias())) {
            return new RetrieveUserResponse(RobertGardner);
        } else if (request.getUsername().equals(Snowden.getAlias())) {
            return new RetrieveUserResponse(Snowden);
        } else if (request.getUsername().equals(TristanThompson.getAlias())) {
            return new RetrieveUserResponse(TristanThompson);
        } else if (request.getUsername().equals(KCP.getAlias())) {
            return new RetrieveUserResponse(KCP);
        } else if (request.getUsername().equals(theMedia.getAlias())) {
            return new RetrieveUserResponse(theMedia);
        } else if (request.getUsername().equals(Rudy.getAlias())) {
            return new RetrieveUserResponse(Rudy);
        } else if (request.getUsername().equals(BillBelichick.getAlias())) {
            return new RetrieveUserResponse(BillBelichick);
        } else if (request.getUsername().equals(TestUser.getAlias())) {
            return new RetrieveUserResponse(TestUser);
        } else {
            throw new AssertionError(); // Username is not recognized for us.
        }
    }
}
