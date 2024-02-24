package models.entities;

/**
 * The BankAccount class represents a bank account entity.
 * It contains information about the account holder, account number, and balance.
 */
public class BankAccount {
    private final int id;
    private final String jmbg;
    private final String accountNumber;
    private double balance;

    /**
     * Constructs a BankAccount object with the specified attributes.
     *
     * @param id            the unique identifier of the bank account.
     * @param jmbg          the JMBG (Unique Master Citizen Number) associated with the account.
     * @param accountNumber the account number.
     * @param balance       the balance of the account.
     */
    public BankAccount(int id, String jmbg, String accountNumber, double balance) {
        this.id = id;
        this.jmbg = jmbg;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    /**
     * Retrieves the unique identifier of the bank account.
     *
     * @return the id of the bank account.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the JMBG (Unique Master Citizen Number) associated with the account.
     *
     * @return the JMBG associated with the account.
     */
    public String getJmbg() {
        return jmbg;
    }

    /**
     * Retrieves the account number.
     *
     * @return the account number.
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Retrieves the balance of the account.
     *
     * @return the balance of the account.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the account.
     *
     * @param balance the new balance to set.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Checks if the bank account belongs to an agency (based on the length of the JMBG).
     *
     * @return true if the bank account belongs to an agency, false otherwise.
     */
    public boolean isAgencyBankAccount() {
        return jmbg.length() == 10;
    }

    /**
     * Checks if the provided JMBG matches the JMBG associated with the account.
     *
     * @param inputJMBG the JMBG to check against.
     * @return true if the provided JMBG matches the JMBG associated with the account, false otherwise.
     */
    public boolean isJmbgMatching(String inputJMBG) {
        return jmbg.equals(inputJMBG);
    }

    /**
     * Checks if the provided account number matches the account number of the account.
     *
     * @param inputAccountNumber the account number to check against.
     * @return true if the provided account number matches the account number of the account, false otherwise.
     */
    public boolean isAccountNumberMatching(String inputAccountNumber) {
        return accountNumber.equals(inputAccountNumber);
    }
}
