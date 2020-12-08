package edu.byu.cs.tweeter.server.dao.dao_helpers.get;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;

public class GetFollow {
    public static final String TABLE_NAME = "follows";
    public static final String USER_PARTITION_KEY = "follower_handle"; // AKA the primary key.
    public static final String USER_SORT_KEY = "followee_handle"; // AKA the range key.

    public static boolean doesFollowExist(String followerHandle, String followeeHandle) {

        Table table = DB.getDatabase(TABLE_NAME);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey(USER_PARTITION_KEY, followerHandle, USER_SORT_KEY, followeeHandle);

        try {
            Item outcome = table.getItem(spec);
            if(outcome == null) {
                return false;
            }
            return true;
        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + followerHandle + " " + followeeHandle);
            System.err.println(e.getMessage());
            return false;
        }
    }
}
