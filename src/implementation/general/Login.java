package implementation.general;

import exceptions.InvalidInputException;
import models.entities.User;

import java.util.List;

public class Login {

    public static void isUserInDatabase(List<User> users, String username, String password) throws InvalidInputException {
        if (users.stream().noneMatch(user -> user.areCredentialsMatching(username, password)))
            throw new InvalidInputException("Invalid username or password!");
    }


    public static User getUserByUsername(List<User> users, String username) {
        for (User user : users)
            if (user.isUsernameMatching(username))
                return user;
        return null;
    }
}
