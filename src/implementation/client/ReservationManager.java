package implementation.client;

import models.entities.Reservation;
import models.enums.ReservationType;
import models.entities.Client;

import java.util.List;

public class ReservationManager {
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


}
