package edu.byu.cs.tweeter.server.dao.dao_helpers.put;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.tweeter.server.dao.dao_helpers.database.DB;
import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;

public class PutAuth {

    public static final String AUTH_PARTITION_KEY = "username"; // AKA the primary key.
    public static final String AUTH_SORT_KEY = "id"; // AKA the primary key.
    public static final String IS_ACTIVE = "isActive";
    public static final String TABLE_NAME = "authToken";

    public static Object putAuth(User user) {
        try {
            Table table = DB.getDatabase(TABLE_NAME);
            AuthToken newAuthToken = new AuthToken(user.getAlias());
            Map<String, Object> authInfoMap = getAuthInfoMap(newAuthToken);

            /** Adds json information to the object through "info" **/
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey(AUTH_PARTITION_KEY, newAuthToken.getUsername(),
                            AUTH_SORT_KEY, newAuthToken.getId()).withMap("info", authInfoMap));
            return outcome;
        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + user.getAlias());
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static Map<String, Object> getAuthInfoMap(AuthToken authToken) {
        final Map<String, Object> userInfoMap = new HashMap<String, Object>();
        userInfoMap.put(IS_ACTIVE, authToken.getIsActive());

        return userInfoMap;
    }

    public static Table getDatabase() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-west-2")
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        return dynamoDB.getTable(TABLE_NAME);
    }
}
