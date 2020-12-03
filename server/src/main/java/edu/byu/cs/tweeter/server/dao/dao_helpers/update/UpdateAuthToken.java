package edu.byu.cs.tweeter.server.dao.dao_helpers.update;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

public class UpdateAuthToken {
    public static final String USERNAME = "username"; // AKA the primary key.
    public static final String IS_ACTIVE = "isActive";
    public static final String TABLE_NAME = "authToken";

    public static Object deactivateAuthToken(String username) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(USERNAME, username)
        .withUpdateExpression("set info." + IS_ACTIVE + " = :isActive")
        .withValueMap(new ValueMap().withBoolean(":isActive", false))
        .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                    .withRegion("us-west-2")
                    .build();

            DynamoDB dynamoDB = new DynamoDB(client);

            Table table = dynamoDB.getTable(TABLE_NAME);

            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            return outcome;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }
}
