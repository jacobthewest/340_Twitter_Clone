package edu.byu.cs.tweeter.server.dao.dao_helpers.put;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;
import edu.byu.cs.tweeter.server.dao.dao_helpers.get.GetUser;
import edu.byu.cs.tweeter.server.dao.dao_helpers.utils.Converter;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;

public class PutStatus {
    public static final String STATUS_PARTITION_KEY = "username"; // AKA the primary key.
    public static final String STATUS_SORT_KEY = "timePosted"; // AKA the range key.
    public static final String TABLE_NAME = "status";
    public static final String USER = "user";
    public static final String TWEET_TEXT = "tweetText";
    public static final String URLS = "urls";
    public static final String MENTIONS = "mentions";

    public static Object putStatus(Status status) {
        try {
            Table table = DB.getDatabase(TABLE_NAME);
            Map<String, Object> statusInfoMap = getStatusInfoMap(status);

            // Make sure a user with that alias exists in the databse
            User doIExist = GetUser.getUser(status.getUser().getAlias());
            if(doIExist == null) {
                return null; // The status should not be allowed to be posted because the User doesn't exist
                // by that alias.
            }

            String reformattedTimeForDynamoDbSorting = Converter.convertTimePostedToSortableTime(status.getTimePosted());

            /** Adds json information to the object through "info" **/
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey(STATUS_PARTITION_KEY, status.getUser().getAlias(),
                            STATUS_SORT_KEY, reformattedTimeForDynamoDbSorting).withMap("info", statusInfoMap));
            return outcome;
        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + status.getUser().getAlias() + " " + status.getTweetText());
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static Map<String, Object> getStatusInfoMap(Status status) {
        final Map<String, Object> statusInfoMap = new HashMap<String, Object>();
        // statusInfoMap.put(USER, status.getUser()); It throws an error when we try to put a user object.
        statusInfoMap.put(TWEET_TEXT, status.getTweetText());
        statusInfoMap.put(URLS, status.getUrls());
        statusInfoMap.put(MENTIONS, status.getMentions());

        return statusInfoMap;
    }
}
