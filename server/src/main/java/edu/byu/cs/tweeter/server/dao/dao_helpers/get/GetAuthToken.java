package edu.byu.cs.tweeter.server.dao.dao_helpers.get;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;
import edu.byu.cs.tweeter.server.dao.dao_helpers.utils.Converter;
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
            AuthToken returnAuthToken = Converter.convertItemToAuthToken(outcome);
            return returnAuthToken;
        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + username);
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static AuthToken getAuthTokenByUsernameAndId(String username, String id) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(AUTH_PARTITION_KEY, username);

        ItemCollection<QueryOutcome > items = null;
        Iterator<Item> iterator = null;
        List<Item> responseItems = new ArrayList<>();

        try {
            Table table = DB.getDatabase(TABLE_NAME);
            Item outcome = table.getItem(spec);
            AuthToken returnAuthToken = Converter.convertItemToAuthToken(outcome);
            return returnAuthToken;
        } catch (Exception e) {
            System.err.println("Unable to perform the query.");
            System.err.println(e.getMessage());
            return null;
        }
    }


}
