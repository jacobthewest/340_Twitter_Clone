package edu.byu.cs.tweeter.server.dao.dao_helpers.put;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;
import edu.byu.cs.tweeter.shared.domain.User;

public class PutUser {
    public static final String USER_PARTITION_KEY = "alias"; // AKA the primary key.
    public static final String TABLE_NAME = "user";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lasttName";
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
}
