package edu.byu.cs.tweeter.server.dao.dao_helpers.get;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.DB;
import edu.byu.cs.tweeter.server.dao.dao_helpers.utils.Converter;
import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;

public class GetStatus {

    public static final String TABLE_NAME = "status";
    public static final String USER_PARTITION_KEY = "username"; // AKA the primary key.
    public static final String SORT_KEY = "timePosted";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String IMAGE_URL = "imageUrl";
    public static final String PASSWORD = "password";

    public static Status getStatus(String alias, String timePosted, boolean convertTimePostedForDb) {

        Table table = DB.getDatabase(TABLE_NAME);

        if (convertTimePostedForDb) {
            timePosted = Converter.convertTimePostedToSortableTime(timePosted);
        }

        GetItemSpec spec = new GetItemSpec().withPrimaryKey(USER_PARTITION_KEY, alias, SORT_KEY, timePosted);

        try {
            Item outcome = table.getItem(spec);
            User returnUser = GetUser.getUser(alias);
            Status returnStatus = Converter.convertItemToStatus(outcome, returnUser);
            return returnStatus;
        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + alias);
            System.err.println(e.getMessage());
            return null;
        }
    }
}
