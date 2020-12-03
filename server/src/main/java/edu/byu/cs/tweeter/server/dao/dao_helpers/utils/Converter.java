package edu.byu.cs.tweeter.server.dao.dao_helpers.utils;

import com.amazonaws.services.dynamodbv2.document.Item;

import java.util.Map;

import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.User;

public class Converter {
    public static AuthToken convertItemToAuthToken(Item outcome) {
        Map<String, Object> info = outcome.getMap("info");

        boolean isActive = (boolean) info.get("isActive");
        String username = (String) outcome.get("username");
        String id = (String) info.get("id");

        AuthToken returnMe = new AuthToken();

        returnMe.setId(id);
        returnMe.setUsername(username);
        returnMe.setIsActive(isActive);

        return returnMe;
    }

    public static User convertItemToUser(Item outcome) {
        Map<String, Object> info = outcome.getMap("info");

        String firstName = (String) info.get("firstName");
        String lastName = (String) info.get("lastName");
        String imageUrl = (String) info.get("imageUrl");
        String password = (String) info.get("password");
        String alias = (String) outcome.getString("alias");

        User returnMe = new User(firstName,lastName,imageUrl,password);
        returnMe.setAlias(alias);

        return returnMe;
    }
}
