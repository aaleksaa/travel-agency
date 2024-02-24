package models.entities;

/**
 * The Client class represents a client user in the system.
 * It extends the User class and provides additional attributes and functionality specific to clients.
 */
public class Client extends User {
    private final String phoneNumber;
    private final String jmbg;
    private final String bankAccountNumber;

    /**
     * Constructs a Client object with the specified attributes.
     *
     * @param id                the unique identifier of the client.
     * @param firstName         the first name of the client.
     * @param lastName          the last name of the client.
     * @param username          the username of the client.
     * @param password          the password of the client.
     * @param phoneNumber       the phone number of the client.
     * @param jmbg              the JMBG (Unique Master Citizen Number) of the client.
     * @param bankAccountNumber the bank account number of the client.
     */
    public Client(int id, String firstName, String lastName, String username, String password, String phoneNumber, String jmbg, String bankAccountNumber) {
        super(id, firstName, lastName, username, password);
        this.phoneNumber = phoneNumber;
        this.jmbg = jmbg;
        this.bankAccountNumber = bankAccountNumber;
    }

    /**
     * Retrieves the phone number of the client.
     *
     * @return the phone number of the client.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Retrieves the JMBG (Unique Master Citizen Number) of the client.
     *
     * @return the JMBG of the client.
     */
    public String getJmbg() {
        return jmbg;
    }

    /**
     * Retrieves the bank account number of the client.
     *
     * @return the bank account number of the client.
     */
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    /**
     * Generates a string representation of the client's information.
     *
     * @return a string containing the client's first name, last name, username, phone number, JMBG, and bank account number.
     */
    @Override
    public String printInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("First name | ").append(firstName).append("\n");
        sb.append("Last name | ").append(lastName).append("\n");
        sb.append("Username | ").append(username).append("\n");
        sb.append("Phone number | ").append(phoneNumber).append("\n");
        sb.append("JMBG | ").append(jmbg).append("\n");
        sb.append("Bank account number | ").append(bankAccountNumber).append("\n");

        return sb.toString();
    }
}
