package models.entities;

/**
 * The Admin class represents an administrative user in the system.
 * It extends the User class and provides additional functionality specific to administrators.
 */
public class Admin extends User {
    /**
     * Constructs an Admin object with the specified attributes.
     *
     * @param id         the unique identifier of the admin.
     * @param firstName  the first name of the admin.
     * @param lastName   the last name of the admin.
     * @param username   the username of the admin.
     * @param password   the password of the admin.
     */
    public Admin(int id, String firstName, String lastName, String username, String password) {
        super(id, firstName, lastName, username, password);
    }

    /**
     * Generates a string representation of the admin's information.
     *
     * @return a string containing the admin's first name, last name, and username.
     */
    @Override
    public String printInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("First name | ").append(firstName).append("\n");
        sb.append("Last name | ").append(lastName).append("\n");
        sb.append("Username | ").append(username).append("\n");

        return sb.toString();
    }
}
