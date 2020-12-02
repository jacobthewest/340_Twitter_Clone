package edu.byu.cs.tweeter.server.dao.dao_helpers.delete;

import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;

import edu.byu.cs.tweeter.server.dao.dao_helpers.database.DB;

public class DeleteUser {

    public static final String TABLE_NAME = "user";
    public static final String USER_PARTITION_KEY = "alias"; // AKA the primary key.

    public static String deleteUser(String alias) {

        Table table = DB.getDatabase(TABLE_NAME);

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(USER_PARTITION_KEY, alias));

        try {
            table.deleteItem(deleteItemSpec);
            String result = table.deleteItem(deleteItemSpec).getDeleteItemResult().toString();
            return result;
        }
        catch (Exception e) {
            System.err.println("Unable to delete item: " + alias);
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }
}
