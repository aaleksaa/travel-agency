package implementation.client;

import database.Database;
import models.reservation.Reservation;

import java.util.List;

public class ArrangementReservation {
    public final static String ARRANGEMENT_NOT_SELECTED = "Arrangement is not selected!";
    public final static String SUCCESSFUL_RESERVATION = "Arrangement is successfully reserved!";

    public static void addReservation(List<Reservation> reservations, Reservation reservation) {
        reservations.add(reservation);
    }


}
