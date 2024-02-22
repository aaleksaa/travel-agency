package implementation.client;

import database.Database;
import implementation.general.TransactionManager;
import models.entities.BankAccount;
import models.entities.Reservation;
import models.enums.ReservationType;
import models.entities.Client;

import java.sql.SQLException;
import java.util.List;

public class ReservationManager {
    public final static String RESERVATION_NOT_SELECTED = "Reservation is not selected!";
    public final static String CANCEL_UNAVAILABLE = "This reservation can't be canceled!";
    public final static String SUCCESSFUL_RESERVATION_CANCEL = "Reservation is canceled!\nBalance: ";



    public static void addReservation(List<Reservation> reservations, Reservation reservation) {
        reservations.add(reservation);

    }

    public static List<Reservation> getAllReservations(List<Reservation> reservations, Client client) {
        return reservations
                .stream()
                .filter(reservation -> reservation.isClientMatching(client))
                .toList();
    }

    public static List<Reservation> getFilteredReservations(List<Reservation> reservations, Client client, ReservationType rt) {
        return reservations
                .stream()
                .filter(reservation -> reservation.areClientAndTypeMatching(client, rt))
                .toList();
    }

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

    public static double remainingAmountToPay(List<Reservation> reservations, Client client) {
        return reservations
                .stream()
                .filter(reservation -> reservation.areClientAndTypeMatching(client, ReservationType.ACTIVE))
                .mapToDouble(Reservation::unpaidAmount)
                .sum();
    }

    public static void clientReservationCancel(Reservation res, BankAccount clientBank, BankAccount agencyBank) throws SQLException {
        TransactionManager.performTransaction(clientBank, agencyBank, res.getPaidAmount(), true);
        res.setPaidAmount(0);
        res.setReservationType(ReservationType.CANCELED);
        Database.updateReservationPaidAmount(res.getClient().getId(), res.getArrangement().getId(), 0);
    }

}
