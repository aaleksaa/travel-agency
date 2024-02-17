package models.entities;

import database.Database;

import java.sql.SQLException;
import java.util.List;

public class Agency {
    private List<User> users;
    private List<Arrangement> arrangements;
    private List<Accommodation> accommodations;
    private List<BankAccount> bankAccounts;
    private List<Reservation> reservations;
    private BankAccount agencyBankAccount;

    public final static String DATABASE_ERROR = "Error with database!";

    public Agency() throws SQLException {
        users = Database.getUsers();
        arrangements = Database.getArrangements();
        accommodations = Database.getAccommodations();
        bankAccounts = Database.getBankAccounts();
        reservations = Database.getReservations();
        agencyBankAccount = Database.getAgencyBankAccount();
    }

    public List<Arrangement> getArrangements() {
        return arrangements;
    }


    public List<User> getUsers() {
        return users;
    }


    public List<Reservation> getReservations() {
        return reservations;
    }


    public List<Accommodation> getAccommodations() {
        return accommodations;
    }


    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }


    public BankAccount getAgencyBankAccount() {
        return agencyBankAccount;
    }
}
