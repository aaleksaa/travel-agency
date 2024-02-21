package implementation.admin;

import database.Database;
import implementation.general.TransactionManager;
import interfaces.Identifiable;
import models.entities.Accommodation;
import models.entities.Arrangement;
import models.entities.Reservation;
import models.entities.BankAccount;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * The ArrangementManager class provides utility methods for managing arrangements,
 * accommodations, and associated reservations within the system.
 */
public class ArrangementManager {
    public final static String SUCCESSFUL_ARRANGEMENT_ADD = "Arrangement successfully added!";
    public final static String SUCCESSFUL_ARRANGEMENT_REMOVAL = "Arrangement successfully removed!";
    public final static String NO_RESERVATIONS = "There aren't reservations for this arrangement! Agency didn't lose money!";
    public final static String ARRANGEMENT_NOT_SELECTED = "Arrangement is not selected!";

    /**
     * Finds the next available ID for an entity in the given list based on the existing IDs.
     *
     * @param list The list of entities to search for the next available ID.
     * @param <T>  The type of the entity, must implement Identifiable.
     * @return The next available ID.
     */
    private static <T extends Identifiable> int findID(List<T> list) {
        list.sort((a, b) -> Integer.compare(a.getId(), b.getId()));

        if (list.isEmpty() || list.get(0).getId() != 1)
            return 1;

        for (int i = 0; i < list.size() - 1; i++)
            if (list.get(i + 1).getId() - list.get(i).getId() > 1)
                return list.get(i).getId() + 1;

        return list.size() + 1;
    }

    /**
     * Finds the next available ID for accommodation in the given list.
     *
     * @param accommodations The list of accommodations to search through.
     * @return The next available ID for accommodation.
     */
    public static int nextAccommodationID(List<Accommodation> accommodations) {
        return findID(accommodations);
    }

    /**
     * Finds the next available ID for an arrangement in the given list.
     *
     * @param arrangements The list of arrangements to search through.
     * @return The next available ID for an arrangement.
     */
    public static int nextArrangementID(List<Arrangement> arrangements) {
        return findID(arrangements);
    }

    /**
     * Adds accommodation to the list of accommodations and the database.
     *
     * @param accommodations The list of accommodations to add to.
     * @param accomm The accommodation to add.
     * @throws SQLException if an SQL exception occurs.
     */
    public static void addAccommodation(List<Accommodation> accommodations, Accommodation accomm) throws SQLException {
        accommodations.add(accomm);
        Database.addAccommodation(
                accomm.getId(),
                accomm.getName(),
                accomm.getStarReview(),
                accomm.getRoomType().toString(),
                accomm.getPricePerNight()
        );
    }

    /**
     * Adds an arrangement to the list of arrangements and the database.
     *
     * @param arrangements The list of arrangements to add to.
     * @param arr The arrangement to add.
     * @throws SQLException if an SQL exception occurs.
     */
    public static void addArrangement(List<Arrangement> arrangements, Arrangement arr) throws SQLException {
        arrangements.add(arr);
        Database.addArrangement(
                arr.getId(),
                arr.getName(),
                arr.getDestination(),
                arr.getTransport().toString(),
                arr.getTripDate(),
                arr.getArrivalDate(),
                arr.getPrice(),
                arr.getAccommodation() != null ? arr.getAccommodation().getId() : null
        );
    }

    /**
     * Calculates the total amount of money lost by the agency due to admin canceling reservations for a specific arrangement.
     *
     * @param reservations The list of reservations to calculate from.
     * @param arr The arrangement which is canceled.
     * @return The total amount of money lost by the agency.
     */
    public static double agencyMoneyLost(List<Reservation> reservations, Arrangement arr) {
        return reservations
                .stream()
                .filter(res -> res.isRefundable(arr))
                .mapToDouble(Reservation::getPaidAmount)
                .sum();
    }

    /**
     * Returns money to clients for refundable reservations related to a specific arrangement.
     *
     * @param accounts The list of bank accounts.
     * @param reservations The list of reservations.
     * @param arr The arrangement which is canceled.
     * @param agencyBank The agency's bank account.
     * @throws SQLException if an SQL exception occurs.
     */
    public static void returnMoneyToClients(List<BankAccount> accounts, List<Reservation> reservations, Arrangement arr, BankAccount agencyBank) throws SQLException {
        for (Reservation res : reservations)
            if (res.isRefundable(arr))
                TransactionManager.performTransaction(
                        TransactionManager.getBankAccount(accounts, res.getClient().getBankAccountNumber()),
                        agencyBank,
                        res.getPaidAmount(),
                        true
                );
    }

    /**
     * Removes an arrangement and related data from the system.
     *
     * @param reservations The list of reservations.
     * @param arrangements The list of arrangements.
     * @param accommodations The list of accommodations.
     * @param arr The arrangement to remove.
     * @throws SQLException if an SQL exception occurs.
     */
    public static void removeArrangement(List<Reservation> reservations, List<Arrangement> arrangements, List<Accommodation> accommodations, Arrangement arr) throws SQLException {
        Iterator<Reservation> iter = reservations.iterator();
        boolean removed = false;

        while (iter.hasNext()) {
            Reservation res = iter.next();
            if (res.isArrangementMatching(arr)) {
                iter.remove();
                removed = true;
            }
        }

        if (removed)
            Database.deleteObject(arr.getId(), "rezervacija", "Aranzman_id");

        arrangements.remove(arr);
        Database.deleteObject(arr.getId(), "aranzman", "id");

        if (arr.getAccommodation() != null) {
            accommodations.remove(arr.getAccommodation());
            Database.deleteObject(arr.getAccommodation().getId(), "smjestaj", "id");
        }
    }
}
