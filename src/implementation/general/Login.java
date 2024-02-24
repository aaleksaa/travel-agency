package implementation.general;

import exceptions.InvalidInputException;
import models.entities.User;

import java.util.List;

/**
 * The Login class provides methods for user authentication.
 */
public class Login {
    /**
     * Checks if a user with the given username and password exists in the provided list of users.
     *
     * @param users    The list of users.
     * @param username The username to be checked.
     * @param password The password to be checked.
     * @throws InvalidInputException if the username or password is invalid.
     */
    public static void isUserInDatabase(List<User> users, String username, String password) throws InvalidInputException {
        if (users.stream().noneMatch(user -> user.areCredentialsMatching(username, password)))
            throw new InvalidInputException("Invalid username or password!");
    }

    /**
     * Retrieves the user corresponding to the given username from the provided list of users.
     *
     * @param users    The list of users.
     * @param username The username to retrieve the user object.
     * @return The User object if found, or null if not found.
     */
    public static User getUserByUsername(List<User> users, String username) {
        for (User user : users)
            if (user.isUsernameMatching(username))
                return user;
        return null;
    }
}
