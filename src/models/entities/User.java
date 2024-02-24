package models.entities;

/**
 * The User class represents a user entity with basic information.
 */
public abstract class User {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;

    /**
     * Constructs a User object with the specified attributes.
     *
     * @param id         the user ID.
     * @param firstName  the user's first name.
     * @param lastName   the user's last name.
     * @param username   the user's username.
     * @param password   the user's password.
     */
    public User(int id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the user's ID.
     *
     * @return the user ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the user's first name.
     *
     * @return the user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the user's last name.
     *
     * @return the user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieves the user's username.
     *
     * @return the user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the user's password.
     *
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the provided username matches the user's username.
     *
     * @param inputUsername the username to check.
     * @return true if the provided username matches the user's username, otherwise false.
     */
    public boolean isUsernameMatching(String inputUsername) {
        return username.equals(inputUsername);
    }

    /**
     * Checks if the provided password matches the user's password.
     *
     * @param inputPassword the password to check.
     * @return true if the provided password matches the user's password, otherwise false.
     */
    public boolean isPasswordMatching(String inputPassword) {
        return password.equals(inputPassword);
    }

    /**
     * Checks if the provided credentials match the user's credentials.
     *
     * @param inputUsername the username to check.
     * @param inputPassword the password to check.
     * @return true if the provided credentials match the user's credentials, otherwise false.
     */
    public boolean areCredentialsMatching(String inputUsername, String inputPassword) {
        return isUsernameMatching(inputUsername) && isPasswordMatching(inputPassword);
    }

    /**
     * Abstract method to print user information.
     *
     * @return a string representing user information.
     */
    public abstract String printInfo();

    /**
     * Retrieves a string representation of the user.
     *
     * @return a string representation of the user.
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " Username: " + username;
    }
}
