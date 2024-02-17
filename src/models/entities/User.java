package models.entities;

public class User {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;

    public User(int id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isUsernameMatching(String inputUsername) {
        return username.equals(inputUsername);
    }

    public boolean isPasswordMatching(String inputPassword) {
        return password.equals(inputPassword);
    }

    public boolean areCredentialsMatching(String inputUsername, String inputPassword) {
        return isUsernameMatching(inputUsername) && isPasswordMatching(inputPassword);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " Username: " + username;
    }
}
