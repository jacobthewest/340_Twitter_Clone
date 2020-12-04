package edu.byu.cs.tweeter.server.dao.dao_helpers.put;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;
import edu.byu.cs.tweeter.shared.domain.User;

public class PutUser {
    public static final String USER_PARTITION_KEY = "alias"; // AKA the primary key.
    public static final String TABLE_NAME = "user";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String IMAGE_URL = "imageUrl";
    public static final String PASSWORD = "password";
    public static final String IMAGE_BYTES = "imageBytes";

    public static Object putUser(User user) {
        try {
            Table table = DB.getDatabase(TABLE_NAME);
            Map<String, Object> userInfoMap = getUserInfoMap(user);

            /** Adds json information to the object through "info" **/
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey(USER_PARTITION_KEY, user.getAlias()).withMap("info", userInfoMap));
            return outcome;
        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + user.getAlias());
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static Map<String, Object> getUserInfoMap(User user) {
        final Map<String, Object> userInfoMap = new HashMap<String, Object>();
        userInfoMap.put(FIRST_NAME, user.getFirstName());
        userInfoMap.put(LAST_NAME, user.getLastName());
        userInfoMap.put(IMAGE_URL, user.getImageUrl());
        userInfoMap.put(PASSWORD, user.getPassword());
        // userInfoMap.put(IMAGE_BYTES, user.getImageBytes());
        
        return userInfoMap;
    }

    private static List<User> getFamily() {
        List<User> returnMe = new ArrayList<>();

        User dad = new User("Brett", "West", "imageUrl", "password");
        dad.setAlias("@Dad");
        returnMe.add(dad);

        User mom = new User("Holly", "West", "imageUrl", "password");
        mom.setAlias("@Mom");
        returnMe.add(mom);

        User jacob = new User("Jacob", "West", "imageUrl", "password");
        jacob.setAlias("@Jacob");
        returnMe.add(jacob);

        User jenny = new User("Jenny", "West", "imageUrl", "password");
        jenny.setAlias("@Jenny");
        returnMe.add(jenny);

        User allison = new User("Allison", "Monney", "imageUrl", "password");
        allison.setAlias("@Allison");
        returnMe.add(allison);

        User trevor = new User("Trevor", "Monney", "imageUrl", "password");
        trevor.setAlias("@Trevor");
        returnMe.add(trevor);

        User kevin = new User("Kevin", "West", "imageUrl", "password");
        kevin.setAlias("@Kevin");
        returnMe.add(kevin);

        User Rachel = new User("Rachel", "West", "imageUrl", "password");
        Rachel.setAlias("@Rachel");
        returnMe.add(Rachel);

        User Melissa = new User("Melissa", "West", "imageUrl", "password");
        Melissa.setAlias("@Melissa");
        returnMe.add(Melissa);

        User Kyle = new User("Kyle", "West", "imageUrl", "password");
        Kyle.setAlias("@Kyle");
        returnMe.add(Kyle);

        User Katelyn = new User("Katelyn", "West", "imageUrl", "password");
        Katelyn.setAlias("@Katelyn");
        returnMe.add(Katelyn);

        User Rebecca = new User("Rebecca", "West", "imageUrl", "password");
        Rebecca.setAlias("@Rebecca");
        returnMe.add(Rebecca);

        User Sarah = new User("Sarah", "West", "imageUrl", "password");
        Sarah.setAlias("@Sarah");
        returnMe.add(Sarah);

        User Tyler = new User("Tyler", "West", "imageUrl", "password");
        Tyler.setAlias("@Tyler");
        returnMe.add(Tyler);

        User permanentTestUser = new User("Permanent Test", "User", "imageUrl", "password");
        permanentTestUser.setAlias("@PermanentTestUser");
        returnMe.add(permanentTestUser);

        return returnMe;
    }

    public static void putFamilyAndTestUser() {
        List<User> family = getFamily();
        for(User person: family) {
            putUser(person);
        }
    }
}
