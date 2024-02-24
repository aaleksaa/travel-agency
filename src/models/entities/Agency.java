package models.entities;

import database.Database;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents an agency that manages users, arrangements, accommodations, bank accounts, and reservations.
 */
public class Agency {
    private List<User> users;
    private List<Arrangement> arrangements;
    private List<Accommodation> accommodations;
    private List<BankAccount> bankAccounts;
    private List<Reservation> reservations;
    private BankAccount agencyBankAccount;

    /** Error message for database issues. */
    public final static String DATABASE_ERROR = "Error with database!";

    /**
     * Initializes the agency with data retrieved from the database.
     *
     * @throws SQLException if there is an error accessing the database.
     */
    public Agency() throws SQLException {
        users = Database.getUsers();
        arrangements = Database.getArrangements();
        accommodations = Database.getAccommodations();
        bankAccounts = Database.getBankAccounts();
        reservations = Database.getReservations();
        agencyBankAccount = Database.getAgencyBankAccount();
    }

    /**
     * Retrieves the list of arrangements managed by the agency.
     *
     * @return the list of arrangements.
     */
    public List<Arrangement> getArrangements() {
        return arrangements;
    }

    /**
     * Retrieves the list of users managed by the agency.
     *
     * @return the list of users.
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Retrieves the list of reservations managed by the agency.
     *
     * @return the list of reservations.
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Retrieves the list of accommodations managed by the agency.
     *
     * @return the list of accommodations.
     */
    public List<Accommodation> getAccommodations() {
        return accommodations;
    }

    /**
     * Retrieves the list of bank accounts managed by the agency.
     *
     * @return the list of bank accounts.
     */
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    /**
     * Retrieves the bank account of the agency.
     *
     * @return the agency bank account.
     */
    public BankAccount getAgencyBankAccount() {
        return agencyBankAccount;
    }
}
