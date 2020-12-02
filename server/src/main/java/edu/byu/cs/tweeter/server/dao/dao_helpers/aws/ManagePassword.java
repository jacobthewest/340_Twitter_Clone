package edu.byu.cs.tweeter.server.dao.dao_helpers.aws;

import org.mindrot.jbcrypt.BCrypt;

public class ManagePassword {

    public static String hashPassword(String normalPassword) {
        String hashedPassword = BCrypt.hashpw(normalPassword, BCrypt.gensalt());
        return hashedPassword;
    }

    public static boolean checkPasswordsMatch(String normalPassword, String hashedPassword) {
        return BCrypt.checkpw(normalPassword, hashedPassword);
    }
}
