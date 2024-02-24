package implementation.admin;

import models.entities.Arrangement;
import models.entities.Client;
import models.entities.Reservation;

import java.util.List;

/**
 * The RevenueViewer class provides utility methods for viewing revenue-related information
 * such as reservation details, finding clients, and calculating total remaining revenue.
 */
public class RevenueViewer {
    /**
     * Returns a string containing information about the given reservation.
     * Information includes payment status, cancellation status, and client details.
     *
     * @param res The reservation to retrieve information for.
     * @return A string containing reservation information.
     */
    public static String reservationInfo(Reservation res) {
        if (res.isTotallyPaid())
            return "Client " + res.getClient().getUsername() + " paid arrangement in total!";
        else if (res.isCanceledByClient())
            return "Client " + res.getClient().getUsername() + " canceled reservation!";
        else if (res.isCanceled() && !res.isCanceledByClient())
            return "Reservation canceled! Client " + res.getClient().getUsername() + " did not make the payment on time!";
        else if (res.isWithinThreeDayDeadline())
            return "Paid: " + res.getPaidAmount() + " Unpaid: " + res.unpaidAmount() + " Phone number: " + res.getClient().getPhoneNumber();
        else
            return "Paid: " + res.getPaidAmount() + " Unpaid: " + res.unpaidAmount();
    }

    /**
     * Finds and returns a list of clients who have reservations matching the given arrangement.
     *
     * @param reservations The list of reservations to search through.
     * @param arr The arrangement to match against.
     * @return A list of clients with reservations matching the given arrangement.
     */
    public static List<Client> findClients(List<Reservation> reservations, Arrangement arr) {
        return reservations
                .stream()
                .filter(res -> res.isArrangementMatching(arr))
                .map(Reservation::getClient)
                .toList();
    }

    /**
     * Calculates and returns the total remaining amount from unpaid reservations.
     *
     * @param reservations The list of reservations to calculate remaining amount from.
     * @return The total remaining amount from unpaid reservations.
     */
    public static double totalRemaining(List<Reservation> reservations) {
        return reservations
                .stream()
                .filter(res -> !res.isCanceled())
                .mapToDouble(Reservation::unpaidAmount)
                .sum();
    }
}
