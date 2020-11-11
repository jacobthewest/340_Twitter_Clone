package edu.byu.cs.tweeter.shared.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a user in the system.
 */
public class User implements Comparable<User>, Serializable {

    private String firstName;
    private String lastName;
    private String alias;
    private String imageUrl;
    private String password;
    private String imageBytesAsString;
    private byte[] imageBytes;

    public User() {}

    // Most basic constructor
    public User(String firstName, String lastName, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = "@" + firstName + lastName;
        this.imageUrl = imageUrl;
    }

    // Constructor with a password
    public User(String firstName, String lastName, String imageUrl, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = "@" + firstName + lastName;
        this.imageUrl = imageUrl;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getAlias() {
        return alias;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public String getImageBytesAsString() { return imageBytesAsString; }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImageBytesAsString(String imageBytesAsString) { this.imageBytesAsString = imageBytesAsString; }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    @Override
    public int compareTo(User user) {
        return this.getAlias().compareTo(user.getAlias());
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", alias='" + alias + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", password='" + password +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(alias, user.alias) &&
                Objects.equals(imageUrl, user.imageUrl) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(firstName, lastName, alias, imageUrl, password);
        result = 31 * result;
        return result;
    }
}
