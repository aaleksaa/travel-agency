package implementation.admin;

import database.Database;
import models.user.Admin;
import models.user.User;

import java.sql.SQLException;
import java.util.List;

public class AdminViewer {
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
}
