package edu.byu.cs.tweeter.server.dao.dao_helpers.get;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;
import edu.byu.cs.tweeter.server.dao.dao_helpers.utils.Converter;
import edu.byu.cs.tweeter.shared.domain.User;

public class GetUser {

    public static final String TABLE_NAME = "user";
    public static final String USER_PARTITION_KEY = "alias"; // AKA the primary key.
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String IMAGE_URL = "imageUrl";
    public static final String PASSWORD = "password";

    public static User getUser(String alias) {

        Table table = DB.getDatabase(TABLE_NAME);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey(USER_PARTITION_KEY, alias);

        try {
            Item outcome = table.getItem(spec);
            User returnUser = Converter.convertItemToUser(outcome);
            return returnUser;
        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + alias);
            System.err.println(e.getMessage());
            return null;
        }
    }
}
