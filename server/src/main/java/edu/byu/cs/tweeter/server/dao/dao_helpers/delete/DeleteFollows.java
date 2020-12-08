package edu.byu.cs.tweeter.server.dao.dao_helpers.delete;

import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;

public class DeleteFollows {
    public static final String TABLE_NAME = "follows";
    public static final String USER_PARTITION_KEY = "follower_handle"; // AKA the primary key.
    public static final String USER_SORT_KEY = "followee_handle"; // AKA the range key.

    public static String deleteFollows(String followerHandle, String followeeHandle) {

        Table table = DB.getDatabase(TABLE_NAME);

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(USER_PARTITION_KEY, followerHandle, USER_SORT_KEY, followeeHandle));

        try {
            table.deleteItem(deleteItemSpec);
            String result = table.deleteItem(deleteItemSpec).getDeleteItemResult().toString();
            return result;
        }
        catch (Exception e) {
            System.err.println("Unable to delete item: " + followerHandle + " " + followeeHandle);
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }
}
