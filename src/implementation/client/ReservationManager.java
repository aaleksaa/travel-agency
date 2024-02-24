package implementation.client;

import database.Database;
import exceptions.UnsuccessfulReservationException;
import implementation.general.TransactionManager;
import implementation.general.Validator;
import models.entities.Arrangement;
import models.entities.BankAccount;
import models.entities.Reservation;
import models.enums.ReservationType;
import models.entities.Client;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * The ReservationManager class manages reservations for clients.
 */
public class ReservationManager {
    public final static String ALERT_CLIENT_TO_PAY = "Reservations are close to deadline! Complete your payment!";
    public final static String RESERVATION_NOT_SELECTED = "Reservation is not selected!";
    public final static String CANCEL_UNAVAILABLE = "This reservation can't be canceled!";
    public final static String SUCCESSFUL_RESERVATION_CANCEL = "Reservation is canceled!\nBalance: ";
    public final static String SUCCESSFUL_RESERVATION = "Arrangement is reserved!\nBalance: ";
    public final static String NOT_ACTIVE_RESERVATION = "This reservation is not active!";
    public final static String RESERVATION_TOTALLY_PAID = "This reservation is totally paid!";
    public final static String SUCCESSFUL_PAYMENT = "Payment successful!\nBalance: ";

    /**
     * Adds a new reservation.
     *
     * @param reservations the list of reservations.
     * @param res          the reservation to add.
     * @throws SQLException if a database error occurs.
     */
    public static void addReservation(List<Reservation> reservations, Reservation res) throws SQLException {
        reservations.add(res);
        Database.addReservation(
                res.getClient().getId(),
                res.getArrangement().getId(),
                res.getTotalPrice(),
                res.getPaidAmount()
        );
    }

    /**
     * Retrieves all reservations for a client.
     *
     * @param reservations the list of reservations.
     * @param client       the client.
     * @return a list of reservations for the client.
     */
    public static List<Reservation> getAllReservations(List<Reservation> reservations, Client client) {
        return reservations
                .stream()
                .filter(reservation -> reservation.isClientMatching(client))
                .toList();
    }

    /**
     * Retrieves filtered reservations for a client.
     *
     * @param reservations the list of reservations.
     * @param client       the client.
     * @param rt           the reservation type.
     * @return a list of filtered reservations for the client.
     */
    public static List<Reservation> getFilteredReservations(List<Reservation> reservations, Client client, ReservationType rt) {
        return reservations
                .stream()
                .filter(reservation -> reservation.areClientAndTypeMatching(client, rt))
                .toList();
    }

    /**
     * Marks reservations for a client based on their status.
     *
     * @param reservations the list of reservations.
     * @param client       the client.
     */
    public static void markReservations(List<Reservation> reservations, Client client) {
        for (Reservation reservation : reservations)
            if (reservation.isClientMatching(client)) {
                if (reservation.isPast())
                    reservation.setReservationType(ReservationType.PAST);
                else if (reservation.isCanceled())
                    reservation.setReservationType(ReservationType.CANCELED);
                else
                    reservation.setReservationType(ReservationType.ACTIVE);
            }
    }

    /**
     * Calculates the total amount spent by a client on reservations.
     *
     * @param reservations the list of reservations.
     * @param client       the client.
     * @return the total amount spent by the client.
     */
    public static double clientMoneySpent(List<Reservation> reservations, Client client) {
        double sum = 0;

        for (Reservation res : reservations)
            if (res.isClientMatching(client)) {
                if (res.getReservationType() == ReservationType.CANCELED && res.getPaidAmount() != 0)
                    sum += res.getArrangement().getHalfPrice();
                else
                    sum += res.getPaidAmount();
            }

        return sum;
    }

    /**
     * Calculates the remaining amount to be paid by a client for active reservations.
     *
     * @param reservations the list of reservations.
     * @param client       the client.
     * @return the remaining amount to be paid by the client.
     */
    public static double remainingAmountToPay(List<Reservation> reservations, Client client) {
        return reservations
                .stream()
                .filter(reservation -> reservation.areClientAndTypeMatching(client, ReservationType.ACTIVE))
                .mapToDouble(Reservation::unpaidAmount)
                .sum();
    }

    /**
     * Returns money for canceled reservations that are past the payment deadline.
     *
     * @param reservations the list of reservations.
     * @param client       the client.
     * @param clientBank   the client's bank account.
     * @param agencyBank   the agency's bank account.
     * @param date1        the start date of the payment deadline period.
     * @param date2        the end date of the payment deadline period.
     * @throws SQLException if a database error occurs.
     */
    public static void returnMoneyCanceledReservations(List<Reservation> reservations, Client client, BankAccount clientBank, BankAccount agencyBank, LocalDate date1, LocalDate date2) throws SQLException {
        for (Reservation res : reservations)
            if (res.isClientMatching(client) && res.isCanceled() && !res.isCanceledByClient()) {
                double half = res.getArrangement().getHalfPrice();

                if (res.getPaidAmount() != half)
                    res.setPaidAmount(res.getPaidAmount() - half);

                if (checkLogs(res, date1, date2))
                    TransactionManager.performTransaction(clientBank, agencyBank, res.getPaidAmount(), true);
            }
    }

    private static boolean checkLogs(Reservation res, LocalDate date1, LocalDate date2) {
        int diff1 = Arrangement.dateDifference(date1, res.getArrangement().getTripDate());
        int diff2 = Arrangement.dateDifference(date2, res.getArrangement().getTripDate());

        return (diff1 >= Arrangement.PAYMENT_DEADLINE_END && diff2 < Arrangement.PAYMENT_DEADLINE_END) && (res.getPaidAmount() != res.getArrangement().getHalfPrice());
    }

    /**
     * Checks if there are any pending payments for a client and alerts them.
     *
     * @param reservations the list of reservations.
     * @param client       the client.
     * @return true if there are pending payments, false otherwise.
     */
    public static boolean alertClientToCompletePayment(List<Reservation> reservations, Client client) {
        for (Reservation res : reservations)
            if (res.alertClient(client))
                return true;
        return false;
    }

    /**
     * Cancels a client's reservation.
     *
     * @param res         the reservation to cancel.
     * @param clientBank  the client's bank account.
     * @param agencyBank  the agency's bank account.
     * @throws SQLException if a database error occurs.
     */
    public static void clientReservationCancel(Reservation res, BankAccount clientBank, BankAccount agencyBank) throws SQLException {
        TransactionManager.performTransaction(clientBank, agencyBank, res.getPaidAmount(), true);
        res.setPaidAmount(0);
        res.setReservationType(ReservationType.CANCELED);
        Database.updateReservationPaidAmount(res.getClient().getId(), res.getArrangement().getId(), 0);
    }

    /**
     * Processes a payment for a reservation.
     *
     * @param res         the reservation to pay for.
     * @param clientBank  the client's bank account.
     * @param agencyBank  the agency's bank account.
     * @param amount      the amount to pay.
     * @throws UnsuccessfulReservationException if the reservation payment is unsuccessful.
     * @throws SQLException                   if a database error occurs.
     */
    public static void payReservation(Reservation res, BankAccount clientBank, BankAccount agencyBank, double amount) throws UnsuccessfulReservationException, SQLException {
        double val = res.unpaidAmount();
        if (amount > val)
            amount = val;

        Validator.checkBalanceForTransaction(clientBank, amount);
        res.setPaidAmount(res.getPaidAmount() + amount);
        TransactionManager.performTransaction(clientBank, agencyBank, amount, false);
        Database.updateReservationPaidAmount(res.getClient().getId(), res.getArrangement().getId(), res.getPaidAmount());
    }
}
