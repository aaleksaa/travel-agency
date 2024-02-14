package models.user;

public class Client extends User {
    private final String phoneNumber;
    private final String jmbg;
    private final String bankAccountNumber;

    public Client(int id, String firstName, String lastName, String username, String password, String phoneNumber, String jmbg, String bankAccountNumber) {
        super(id, firstName, lastName, username, password);
        this.phoneNumber = phoneNumber;
        this.jmbg = jmbg;
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getJmbg() {
        return jmbg;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
}
