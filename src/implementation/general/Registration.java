package implementation.general;

import exceptions.InvalidBankAccountException;
import exceptions.InvalidJmbgException;
import exceptions.PasswordMismatchException;
import models.entities.BankAccount;
import models.entities.Client;
import models.entities.User;

import java.util.List;

/**
 * The Registration class provides methods for registration-related operations.
 */
public class Registration {
    /**
     * Checks if a bank account exists in the database.
     *
     * @param bankAccounts  the list of bank accounts.
     * @param accountNumber the account number to be checked.
     * @throws InvalidBankAccountException if the bank account is not found in the database.
     */
    public static void isBankAccountInDatabase(List<BankAccount> bankAccounts, String accountNumber) throws InvalidBankAccountException {
        if (bankAccounts.stream().noneMatch(acc -> acc.isAccountNumberMatching(accountNumber)))
            throw new InvalidBankAccountException(accountNumber);
    }

    /**
     * Validates the JMBG (Unique Citizen Number).
     *
     * @param bankAccounts  the list of bank accounts.
     * @param accountNumber the account number associated with the JMBG.
     * @param jmbg          the JMBG to be validated.
     * @throws InvalidJmbgException if the JMBG is not valid.
     */
    public static void isJmbgValid(List<BankAccount> bankAccounts, String accountNumber, String jmbg) throws InvalidJmbgException {
        if (bankAccounts.stream().noneMatch(acc -> acc.isAccountNumberMatching(accountNumber) && acc.isJmbgMatching(jmbg)))
            throw new InvalidJmbgException();
    }

    /**
     * Generates the ID for the next client.
     *
     * @param users the list of users.
     * @return the ID for the next client.
     */
    public static int nextClientID(List<User> users) {
        return (int) users
                .stream()
                .filter(user -> user instanceof Client)
                .count() + 1;
    }
}
