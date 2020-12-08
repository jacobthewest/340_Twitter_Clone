package edu.byu.cs.tweeter.server.dao.dao_helpers.put;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;
import edu.byu.cs.tweeter.shared.domain.User;

public class PutFollows {
    public static final String USER_PARTITION_KEY = "follower_handle"; // AKA the primary key.
    public static final String USER_SORT_KEY = "followee_handle"; // AKA the primary key.
    public static final String TABLE_NAME = "follows";
    public static final String FOLLOWER_NAME = "follower_name";
    public static final String FOLLOWEE_NAME = "followee_name";

    public static Object putFollows(User follower, User followee) {
        try {
            Table table = DB.getDatabase(TABLE_NAME);

            final Map<String, Object> infoMap = new HashMap<String, Object>();
            infoMap.put(FOLLOWER_NAME, follower.getFirstName() + " " + follower.getLastName());
            infoMap.put(FOLLOWEE_NAME, followee.getFirstName() + " " + followee.getLastName());

            /** Adds json information to the object through "info" **/
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey(USER_PARTITION_KEY, follower.getAlias(), USER_SORT_KEY, followee.getAlias())
                            .withMap("info", infoMap));
            return outcome;
        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + follower.getAlias() + " " + followee.getAlias());
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }
}
