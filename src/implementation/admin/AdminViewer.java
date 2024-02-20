package implementation.admin;

import database.Database;
import models.entities.Admin;
import models.entities.User;

import java.sql.SQLException;
import java.util.List;

public class AdminViewer {
    public final static String SUCCESSFUL_ADMIN_REGISTRATION = "Admin successfully added!";

    public static int adminCounter(List<User> users) {
        return (int) users
                .stream()
                .filter(user -> user instanceof Admin)
                .count();
    }

    public static int nextAdminID(List<User> users) {
        return adminCounter(users) + 1;
    }

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

    public static List<Admin> getAdmins(List<User> users) {
        return users
                .stream()
                .filter(user -> user instanceof Admin)
                .map(user -> (Admin) user)
                .toList();
    }
}
