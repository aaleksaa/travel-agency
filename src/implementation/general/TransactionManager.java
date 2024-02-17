package implementation.general;

import database.Database;
import models.entities.BankAccount;

import java.sql.SQLException;
import java.util.List;

public class TransactionManager {
    private static void updateBalanceInDatabase(BankAccount clientBank, BankAccount agencyBank) throws SQLException {
        Database.updateBalance(clientBank.getId(), clientBank.getBalance());
        Database.updateBalance(agencyBank.getId(), agencyBank.getBalance());
    }

    public static void performTransaction(BankAccount clientBank, BankAccount agencyBank, double amount, boolean toClient) throws SQLException {
        if (toClient) {
            clientBank.setBalance(clientBank.getBalance() + amount);
            agencyBank.setBalance(agencyBank.getBalance() - amount);
        } else {
            clientBank.setBalance(clientBank.getBalance() - amount);
            agencyBank.setBalance(agencyBank.getBalance() + amount);
        }

        updateBalanceInDatabase(clientBank, agencyBank);
    }

    public static BankAccount getBankAccount(List<BankAccount> bankAccounts, String accountNumber) {
        for (BankAccount bankAccount : bankAccounts)
            if (bankAccount.isAccountNumberMatching(accountNumber))
                return bankAccount;
        return null;
    }


}
