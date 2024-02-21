package implementation.admin;

import database.Database;
import models.entities.Admin;
import models.entities.User;

import java.sql.SQLException;
import java.util.List;

/**
 * The AdminViewer class provides functionality for managing administrators within a system.
 */
public class AdminViewer {
    public final static String SUCCESSFUL_ADMIN_REGISTRATION = "Admin successfully added!";

    /**
     * Counts the number of administrators in the given list of users.
     *
     * @param users The list of users to count administrators from.
     * @return The number of administrators in the list of users.
     */
    public static int adminCounter(List<User> users) {
        return (int) users
                .stream()
                .filter(user -> user instanceof Admin)
                .count();
    }

    /**
     * Generates the next available ID for an admin based on the number of admins currently in the system.
     *
     * @param users The list of users to generate the next admin ID from.
     * @return The next available ID for an admin.
     */
    public static int nextAdminID(List<User> users) {
        return adminCounter(users) + 1;
    }

    /**
     * Adds a new admin to the list of users and registers the admin in the database.
     *
     * @param users The list of users to which the admin will be added.
     * @param admin The admin to add to the list of users and register in the database.
     * @throws SQLException If an error occurs while registering the admin in the database.
     */
    public static void addAdmin(List<User> users, Admin admin) throws SQLException {
        users.add(admin);
        Database.registerAdmin(
                admin.getId(),
                admin.getFirstName(),
                admin.getLastName(),
                admin.getUsername(),
                admin.getPassword()
        );
    }

    /**
     * Retrieves a list of admins from the list of users.
     *
     * @param users The list of users to retrieve admins from.
     * @return A list of admins extracted from the given list of users.
     */
    public static List<Admin> getAdmins(List<User> users) {
        return users
                .stream()
                .filter(user -> user instanceof Admin)
                .map(user -> (Admin) user)
                .toList();
    }
}
