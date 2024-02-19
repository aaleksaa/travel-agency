package implementation.admin;

import models.entities.Arrangement;
import models.entities.Client;
import models.entities.Reservation;

import java.util.List;

public class RevenueViewer {
    public static String reservationInfo(Reservation res) {
        if (res.isTotallyPaid())
            return "Client " + res.getClient().getUsername() + " paid arrangement in total!";
        else if (res.isCanceledByClient())
            return "Client" + res.getClient().getUsername() + " canceled reservation!";
        else if (res.isCanceled() && !res.isCanceledByClient())
            return "Reservation canceled! Client " + res.getClient().getUsername() + " did not make the payment on time!";
        else if (res.isWithinThreeDayDeadline())
            return "Paid: " + res.getPaidAmount() + " Unpaid: " + res.unpaidAmount() + " Phone number: " + res.getClient().getPhoneNumber();
        else
            return "Paid: " + res.getPaidAmount() + " Unpaid: " + res.unpaidAmount();
    }

    public static List<Client> findClients(List<Reservation> reservations, Arrangement arr) {
        return reservations
                .stream()
                .filter(res -> res.isArrangementMatching(arr))
                .map(Reservation::getClient)
                .toList();
    }

    public static double totalRemaining(List<Reservation> reservations) {
        return reservations
                .stream()
                .filter(res -> !res.isCanceled())
                .mapToDouble(Reservation::unpaidAmount)
                .sum();
    }
}
