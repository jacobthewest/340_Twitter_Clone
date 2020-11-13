package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.LoginRequest;
import edu.byu.cs.tweeter.shared.service.response.LoginResponse;

public class LoginDAO {

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

    public LoginResponse login(LoginRequest request) {
        // TODO: logs in a hard-coded user. Replace with a real implementation.

        if(!isRecognizedUser(request.getUsername())) {
            return new LoginResponse("User does not exist in the database.");
        }

        //  START
        //  Code to log the user in
        //  END

        User loggedInUser = new User("Test", "User", "https://i.imgur.com/VZQQiQ1.jpg", "password");
        AuthToken authToken = new AuthToken(request.getUsername());

        return new LoginResponse(loggedInUser, authToken);
    }

    private boolean isRecognizedUser(String alias) {
        if (alias.equals(user1.getAlias()) || alias.equals(user2.getAlias()) || alias.equals(user3.getAlias()) ||  alias.equals(user4.getAlias()) ||
                alias.equals(user5.getAlias()) || alias.equals(user6.getAlias()) || alias.equals(user7.getAlias()) || alias.equals(user8.getAlias()) ||
                alias.equals(user9.getAlias()) || alias.equals(user10.getAlias()) || alias.equals(user11.getAlias()) || alias.equals(user12.getAlias()) ||
                alias.equals(user13.getAlias()) || alias.equals(user14.getAlias()) || alias.equals(user15.getAlias()) || alias.equals(user16.getAlias()) ||
                alias.equals(user17.getAlias()) || alias.equals(user18.getAlias()) || alias.equals(user19.getAlias()) || alias.equals(user20.getAlias()) ||
                alias.equals(JacobWest.getAlias()) || alias.equals(RickyMartin.getAlias()) || alias.equals(RobertGardner.getAlias()) || alias.equals(Snowden.getAlias()) ||
                alias.equals(TristanThompson.getAlias()) || alias.equals(KCP.getAlias()) || alias.equals(theMedia.getAlias()) || alias.equals(Rudy.getAlias()) ||
                alias.equals(BillBelichick.getAlias()) || alias.equals(TestUser.getAlias()) || alias.equals(userBarney.getAlias()) ||
                alias.equals(DaffyDuck.getAlias()) || alias.equals(Zoe.getAlias())) { return true;}
        return false;
    }
}
