package implementation.general;

import exceptions.InvalidBankAccountException;
import exceptions.PasswordMismatchException;
import models.entities.BankAccount;
import models.entities.Client;
import models.entities.User;

import java.util.List;

public class Registration {
    public static void isBankAccountInDatabase(List<BankAccount> bankAccounts, String accountNumber) throws InvalidBankAccountException {
        if (bankAccounts.stream().noneMatch(acc -> acc.isAccountNumberMatching(accountNumber)))
            throw new InvalidBankAccountException(accountNumber);
    }

    public static int nextClientID(List<User> users) {
        return (int) users
                .stream()
                .filter(user -> user instanceof Client)
                .count() + 1;
    }
}
