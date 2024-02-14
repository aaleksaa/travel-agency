package models.user;

public class BankAccount {
    private final int id;
    private final String jmbg;
    private final String accountNumber;
    private double balance;

    public BankAccount(int id, String jmbg, String accountNumber, double balance) {
        this.id = id;
        this.jmbg = jmbg;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getJmbg() {
        return jmbg;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isAgencyBankAccount() {
        return jmbg.length() == 10;
    }
}
