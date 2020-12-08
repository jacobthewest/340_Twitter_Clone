package edu.byu.cs.tweeter.server.dao.dao_helpers.delete;

import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;
import edu.byu.cs.tweeter.server.dao.dao_helpers.utils.Converter;

public class DeleteStatus {
    public static final String TABLE_NAME = "status";
    public static final String USER_PARTITION_KEY = "username"; // AKA the primary key.
    public static final String USER_SORT_KEY = "timePosted"; // AKA the range key.

    public static String deleteStatus(String username, String timePosted) {

        timePosted = Converter.convertTimePostedToSortableTime(timePosted);

        Table table = DB.getDatabase(TABLE_NAME);

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(USER_PARTITION_KEY, username, USER_SORT_KEY, timePosted));

        try {
            table.deleteItem(deleteItemSpec);
            String result = table.deleteItem(deleteItemSpec).getDeleteItemResult().toString();
            return result;
        }
        catch (Exception e) {
            System.err.println("Unable to delete item: " + username + " " + timePosted);
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }
}
