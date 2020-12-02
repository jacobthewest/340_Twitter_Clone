package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import edu.byu.cs.tweeter.server.dao.dao_helpers.aws.S3;
import edu.byu.cs.tweeter.server.dao.dao_helpers.delete.DeleteAuthToken;
import edu.byu.cs.tweeter.server.dao.dao_helpers.delete.DeleteUser;
import edu.byu.cs.tweeter.server.dao.dao_helpers.put.PutUser;
import edu.byu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.shared.service.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.service.response.RegisterResponse;

public class RegisterDaoTest {

    public RegisterDAO registerDAO;

    // This is "password" hashed.
    public static final String HASHED_PASSWORD = "$2a$10$4GlITVTMxdTDHMHdnP93V.FYEvFocSsdq8VbdE83Ya5FqISCTTZCy";
    public static final String PASSWORD = "password";
    public static final String EXISTS_IMAGE_URL = "https://340tweeter.s3-us-west-2.amazonaws.com/%40doNotDeleteTestImage";
    public static final String filePath = "C:\\Users\\jacob\\Documents\\Fall2020\\340\\Milestones\\M4\\Project\\server\\catch.png";
    public static final String PERMANENT_IMAGE_ALIAS = "@doNotDeleteTest";

    @BeforeEach
    public void setup() {
        registerDAO = new RegisterDAO();
    }

    @Test
    public void testRegisterPreExistingUser() {

        // Add a user to the database so we know it exists
        User userWhoExists = new User("Existing", "User", EXISTS_IMAGE_URL, HASHED_PASSWORD);
        PutUser.putUser(userWhoExists);

        // Attempt to register the pre-existing user
        RegisterRequest existsRequest = new RegisterRequest(userWhoExists);
        RegisterResponse existsRegisterResponse = registerDAO.register(existsRequest);

        Assertions.assertFalse(registerDAO.register(existsRequest).getSuccess());
}

    @Test
    public void testRegisterNonExistingUser() {

        User userWhoDoesNotExist = new User("Non_Existing", "User", null, PASSWORD);
        byte[] imageBytes = getImageBytes();
        if(imageBytes == null) {
            Assertions.assertTrue(false);
        }
        userWhoDoesNotExist.setImageBytes(imageBytes);
        RegisterRequest doesNotExistRequest = new RegisterRequest(userWhoDoesNotExist);
        RegisterResponse registerResponse = registerDAO.register(doesNotExistRequest);

        // Make sure it worked.
        Assertions.assertTrue(registerResponse.getSuccess());

        // Delete the user and the image so future tests pass
        String deleteUserResult = DeleteUser.deleteUser(userWhoDoesNotExist.getAlias());
        Assertions.assertTrue(!deleteUserResult.toUpperCase().contains("ERROR"));
        String deleteAuthTokenResult = DeleteAuthToken.deleteAuthToken(userWhoDoesNotExist.getAlias());
        Assertions.assertTrue(!deleteAuthTokenResult.toUpperCase().contains("ERROR"));
        S3.deleteImage(userWhoDoesNotExist.getAlias());
    }

    private byte[] getImageBytes() {
        try {
            // file to byte[], File -> Path
            File file = new File(filePath);
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
