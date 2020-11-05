package edu.byu.cs.tweeter.shared.service.request;

/**
 * Contains all the information needed to make a register request.
 */
public class RegisterRequest {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private byte[] imageBytes;

    public RegisterRequest() {}

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be registered.
     * @param password the password of the user to be registered.
     * @param firstName the password of the user to be registered.
     * @param lastName the password of the user to be registered.
     */
    public RegisterRequest(String username, String password, String firstName, String lastName, String imageUrl, byte[] imageBytes) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
        this.imageBytes = imageBytes;
    }

    /**
     * Returns the username of the user to be registered by this request.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user to be registered by this request.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the firstName of the user to be registered by this request.
     *
     * @return the firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the lastName of the user to be registered by this request.
     *
     * @return the lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the imageUrl of the user to be registered by this request.
     *
     * @return the imageUrl.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Returns the imageBytes of the user to be registered by this request.
     *
     * @return the imageUrl.
     */
    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}

