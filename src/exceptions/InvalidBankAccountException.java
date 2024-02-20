package exceptions;

public class InvalidBankAccountException extends Exception {
    public InvalidBankAccountException(String accountNumber) {
        super("Account number " + accountNumber + " doesn't exist!");
    }
}
