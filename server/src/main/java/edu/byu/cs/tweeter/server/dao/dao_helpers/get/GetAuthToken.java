package edu.byu.cs.tweeter.server.dao.dao_helpers.get;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.util.Map;

import edu.byu.cs.tweeter.server.dao.dao_helpers.database.DB;
import edu.byu.cs.tweeter.shared.domain.AuthToken;

public class GetAuthToken {

    public static final String TABLE_NAME = "authToken";
    public static final String AUTH_PARTITION_KEY = "username"; // AKA the primary key.
    public static final String AUTH_SORT_KEY = "id";
    public static final String IS_ACTIVE = "isActive";

    public static AuthToken getAuthToken(String username) {

        Table table = DB.getDatabase(TABLE_NAME);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey(AUTH_PARTITION_KEY, username);

        try {
            Item outcome = table.getItem(spec);
            AuthToken returnAuthToken = convertItemToUser(outcome);
            return returnAuthToken;
        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + username);
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static AuthToken convertItemToUser(Item outcome) {
        Map<String, Object> info = outcome.getMap("info");

        boolean isActive = (boolean) info.get(IS_ACTIVE);
        String username = (String) outcome.get(AUTH_PARTITION_KEY);
        String id = (String) outcome.get(AUTH_SORT_KEY);

        AuthToken returnMe = new AuthToken();

        returnMe.setId(id);
        returnMe.setUsername(username);
        returnMe.setIsActive(isActive);

        return returnMe;
    }
}
