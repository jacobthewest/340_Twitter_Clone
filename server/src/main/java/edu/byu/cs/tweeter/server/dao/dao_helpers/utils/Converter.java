package edu.byu.cs.tweeter.server.dao.dao_helpers.utils;

import com.amazonaws.services.dynamodbv2.document.Item;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import edu.byu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.shared.domain.Status;
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

    public static Status convertItemToStatus(Item outcome, User user) {
        Map<String, Object> info = outcome.getMap("info");

        String tweetText = (String) info.get("tweetText");
        String urls = (String) info.get("urls");
        String mentions = (String) info.get("mentions");

        String timePosted = (String) outcome.getString("timePosted");
        timePosted = convertSortableTimeToTimePosted(timePosted);

        Status returnMe = new Status(user, tweetText, urls, timePosted, mentions);
        return returnMe;
    }

    public static String convertTimePostedToSortableTime(String timePosted) {
        // Current form is "MMM d yyyy h:mm aaa"
        // We need the form to be "yyyyMMddHHmm"

        // Convert timePosted to a date object
        try {
            Date date = new SimpleDateFormat("MMM d yyyy h:mm aaa").parse(timePosted);
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
            String formattedDate = dateFormat.format(date);
            return formattedDate;
        } catch(ParseException e) {
            return e.getMessage();
        }
    }

    public static String convertSortableTimeToTimePosted(String timePosted) {
        // Current form is "yyyyMMddHHmm"
        // We need the form to be "MMM d yyyy h:mm aaa"

        // Convert timePosted to a date object
        try {
            Date date = new SimpleDateFormat("yyyyMMddHHmm").parse(timePosted);
            DateFormat dateFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");
            String formattedDate = dateFormat.format(date);
            return formattedDate;
        } catch(ParseException e) {
            return e.getMessage();
        }
    }
}
