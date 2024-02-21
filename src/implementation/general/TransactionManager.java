package implementation.general;

import database.Database;
import models.entities.BankAccount;

import java.sql.SQLException;
import java.util.List;

/**
 * The TransactionManager class provides methods for managing transactions between bank accounts.
 */
public class TransactionManager {
    /**
     * Updates the balance of the client and agency bank accounts in the database.
     * @param clientBank  The bank account of the client.
     * @param agencyBank  The bank account of the agency.
     * @throws SQLException if a SQL exception occurs while updating the balances in the database.
     */
    private static void updateBalanceInDatabase(BankAccount clientBank, BankAccount agencyBank) throws SQLException {
        Database.updateBalance(clientBank.getId(), clientBank.getBalance());
        Database.updateBalance(agencyBank.getId(), agencyBank.getBalance());
    }

    /**
     * Performs a transaction between two bank accounts.
     * @param clientBank  The bank account of the client.
     * @param agencyBank  The bank account of the agency.
     * @param amount      The amount to be transacted.
     * @param toClient    Specifies whether the transaction is to the client (true) or from the client (false).
     * @throws SQLException if a SQL exception occurs while updating the balances in the database.
     */
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

    /**
     * Retrieves the bank account corresponding to the given account number from the provided list of bank accounts.
     * @param bankAccounts  The list of bank accounts.
     * @param accountNumber The account number to retrieve the bank account.
     * @return The BankAccount object if found, or null if not found.
     */
    public static BankAccount getBankAccount(List<BankAccount> bankAccounts, String accountNumber) {
        for (BankAccount bankAccount : bankAccounts)
            if (bankAccount.isAccountNumberMatching(accountNumber))
                return bankAccount;
        return null;
    }
}
