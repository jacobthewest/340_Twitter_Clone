package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.server.dao.dao_helpers.query.QueryFollows;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.FollowersRequest;
import edu.byu.cs.tweeter.shared.service.response.FollowersResponse;

public class FollowersDAO {

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


    public FollowersResponse getFollowers(FollowersRequest request) {

        List<User> responseFollowers = QueryFollows.queryFollowersSorted(request);

        if (responseFollowers == null) {
            return new FollowersResponse("Error retrieving the followers.");
        }

//        // Load the pages for the users in the responseFollowees.
//        for(User u: responseFollowers) {
//            byte[] imageBytes = S3.getImage(u.getAlias());
//            u.setImageBytes(imageBytes);
//        }

        // How do we tell if we need more pages?
        boolean hasMorePages = false;
        if(responseFollowers.size() == request.getLimit()) {
            hasMorePages = true;
        }

        return new FollowersResponse(responseFollowers, hasMorePages);
    }

    /**
     * Returns the users who follow the user specified in the request. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowersResponse oldGetFollowers(FollowersRequest request) {

        if(!isRecognizedUser(request.getUser().getAlias())) {
            List<User> returnMe = new ArrayList<>();
            return new FollowersResponse(returnMe, false);
        }

        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            // TODO the followers index will now be the last followerHandle and the followeeHandle pair
            // TODO: The followees index won't implement the index.
            int followersIndex = getFollowersStartingIndex(request.getLastFollower(), allFollowers);

            for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followersIndex));
            }

            hasMorePages = followersIndex < allFollowers.size();
        }

        return new FollowersResponse(responseFollowers, hasMorePages);
    }

    /**
     * Determines the index for the first follower in the specified 'allFollowers' list that should
     * be returned in the current request. This will be the index of the next follower after the
     * specified 'lastFollower'.
     *
     * @param lastFollower the last follower that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowers the generated list of followers from which we are returning paged results.
     * @return the index of the first follower to be returned.
     */
    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers) {

        int followersIndex = 0;

        if(lastFollower != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollower.equals(allFollowers.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                }
            }
        }

        return followersIndex;
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

    List<User> getDummyFollowers() {
        return Arrays.asList(user3, JacobWest, user1, RickyMartin, user4, RobertGardner, user2, Snowden, user5, user6, user7,
                user17, user9, user13, user11, user12, user10, user14, user15, user8, user19, TristanThompson, KCP, theMedia, Rudy,
                user18, user20, BillBelichick, TestUser);
    }
}
