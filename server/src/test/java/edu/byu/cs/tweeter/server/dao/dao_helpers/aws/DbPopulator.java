package edu.byu.cs.tweeter.server.dao.dao_helpers.aws;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutStatus;
import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutUser;
import edu.byu.cs.tweeter.server.util.ListOfStatuses;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;

public class DbPopulator {

    public static final String FOLLOWER_NAME = "follower_name";
    public static final String FOLLOWEE_NAME = "followee_name";
    public static final String PERMANENT_TEST_USER = "@PermanentTestUser";

    /**
     * Parent function
     */
    public static void putUserDataForTest() {
        List<User> family = getFamilyForPutUsersData();
        for(User person: family) {
            person.setPassword(ManagePassword.hashPassword(person.getPassword()));
            PutUser.putUser(person);
        }
    }

//    @Test
//    public void testPutUserData() {
//        putUserDataForTest();
//    }

    /**
     * Parent function.
     * @throws Exception
     */
    public static void putFollowsDataForTest() throws Exception {
        List<List<String>> family = getFamilyForPutFollowsData();

        for (List<String> outerList: family) {
            String follower_handle = outerList.get(0);
            String follower_name = outerList.get(1);

            for (List<String> innerList: family) {
                String followee_handle = innerList.get(0);
                String followee_name = innerList.get(1);

                if(!followee_handle.equals(follower_handle)) {

                    final Map<String, Object> infoMap = new HashMap<String, Object>();
                    infoMap.put(FOLLOWER_NAME, follower_name);
                    infoMap.put(FOLLOWEE_NAME, followee_name);

                    put(followee_handle, followee_name, follower_handle, follower_name);
                }
            }
        }
    }

    /**
     * Parent function.
     * @throws Exception
     */
    public static void putStoryDataForTest() {
        List<User> family = getFamilyForPutUsersData();
        ListOfStatuses listOfStatuses = new ListOfStatuses();

        // Put Stories
        for(User familyMember: family) {
            List<Status> story = listOfStatuses.get21StatusesForDaoTests(familyMember);
            for(Status singlePost: story) {
                PutStatus.putStatus(singlePost);
            }
        }
    }

    /**
     * Parent function.
     * @throws Exception
     */
    public void putImageDataForTest() {
        // TODO: Implement with imageUrl. See the S3 class.
        List<User> family = getFamilyForPutUsersData();
        for(User member: family) {
        }
    }

    public static Object put(String followeeHandle, String followeeName, String followerHandle, String followerName) throws Exception {

        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put(FOLLOWER_NAME, followerName);
        infoMap.put(FOLLOWEE_NAME, followeeName);

        PutItemOutcome outcome = (PutItemOutcome) putToDatabase(followeeHandle, followerHandle, infoMap);
        return outcome.getPutItemResult();
    }

    public static Object putToDatabase(String followeeHandle, String followerHandle, Map<String, Object> infoMap) {
        try {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                    .withRegion("us-west-2")
                    .build();

            DynamoDB dynamoDB = new DynamoDB(client);

            Table table = dynamoDB.getTable("follows");

            /** Adds json information to the object through "info" **/
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("followee_handle", followeeHandle, "follower_handle", followerHandle)
                            .withMap("info", infoMap));
            return outcome;
        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + followeeHandle + " " + followerHandle);
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static List<List<String>> getFamilyForPutFollowsData() {

        List<List<String>> family = new ArrayList<>();

        family.add(new ArrayList<>() {{add("@Dad"); add("Brett West"); }});
        family.add(new ArrayList<>() {{add("@Mom"); add("Holly West"); }});
        family.add(new ArrayList<>() {{add(PERMANENT_TEST_USER); add("Permanent Test User"); }});
        family.add(new ArrayList<>() {{add("@Jenny"); add("Jenny West"); }});
        family.add(new ArrayList<>() {{add("@Allison"); add("Allison Monney"); }});
        family.add(new ArrayList<>() {{add("@Trevor"); add("Trevor Monney"); }});
        family.add(new ArrayList<>() {{add("@Kevin"); add("Kevin West"); }});
        family.add(new ArrayList<>() {{add("@Rachel"); add("Rachel West"); }});
        family.add(new ArrayList<>() {{add("@Melissa"); add("Melissa West"); }});
        family.add(new ArrayList<>() {{add("@Kyle"); add("Kyle West"); }});
        family.add(new ArrayList<>() {{add("@Katelyn"); add("Katelyn West"); }});
        family.add(new ArrayList<>() {{add("@Rebecca"); add("Rebecca West"); }});
        family.add(new ArrayList<>() {{add("@Sarah"); add("Sarah West"); }});
        family.add(new ArrayList<>() {{add("@Tyler"); add("Tyler West"); }});

        return family;
    }

    public static List<User> getFamilyForPutUsersData() {
        List<User> returnMe = new ArrayList<>();
        String baseUrl = "https://340tweeter.s3-us-west-2.amazonaws.com/%40";

        User dad = new User("Brett", "West", baseUrl + "DadImage", "password");
        dad.setAlias("@Dad");
        returnMe.add(dad);

        User mom = new User("Holly", "West", baseUrl + "MomImage", "password");
        mom.setAlias("@Mom");
        returnMe.add(mom);

        User jacob = new User("Jacob", "West", baseUrl + "JacobImage", "password");
        jacob.setAlias("@Jacob");
        returnMe.add(jacob);

        User jenny = new User("Jenny", "West", baseUrl + "JennyImage", "password");
        jenny.setAlias("@Jenny");
        returnMe.add(jenny);

        User allison = new User("Allison", "Monney", baseUrl + "AllisonImage", "password");
        allison.setAlias("@Allison");
        returnMe.add(allison);

        User trevor = new User("Trevor", "Monney", baseUrl + "TrevorImage", "password");
        trevor.setAlias("@Trevor");
        returnMe.add(trevor);

        User kevin = new User("Kevin", "West", baseUrl + "KevinImage", "password");
        kevin.setAlias("@Kevin");
        returnMe.add(kevin);

        User Rachel = new User("Rachel", "West", baseUrl + "RachelImage", "password");
        Rachel.setAlias("@Rachel");
        returnMe.add(Rachel);

        User Melissa = new User("Melissa", "West", baseUrl + "MelissaImage", "password");
        Melissa.setAlias("@Melissa");
        returnMe.add(Melissa);

        User Kyle = new User("Kyle", "West", baseUrl + "KyleImage", "password");
        Kyle.setAlias("@Kyle");
        returnMe.add(Kyle);

        User Katelyn = new User("Katelyn", "West", baseUrl + "KatelynImage", "password");
        Katelyn.setAlias("@Katelyn");
        returnMe.add(Katelyn);

        User Rebecca = new User("Rebecca", "West", baseUrl + "RebeccaImage", "password");
        Rebecca.setAlias("@Rebecca");
        returnMe.add(Rebecca);

        User Sarah = new User("Sarah", "West", baseUrl + "SarahImage", "password");
        Sarah.setAlias("@Sarah");
        returnMe.add(Sarah);

        User Tyler = new User("Tyler", "West", baseUrl + "TylerImage", "password");
        Tyler.setAlias("@Tyler");
        returnMe.add(Tyler);

        User permanentTestUser = new User("Permanent Test", "User",baseUrl + "PermanentTestUserImage", "password");
        permanentTestUser.setAlias("@PermanentTestUser");
        returnMe.add(permanentTestUser);

        return returnMe;
    }
}
