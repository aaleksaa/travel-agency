package models.reservation;

import models.arrangement.Arrangement;
import models.user.Client;

public class Reservation {
    private final Client client;
    private final Arrangement arrangement;
    private ReservationType reservationType;
    private final double totalPrice;
    private double paidAmount;

    public Reservation(Client client, Arrangement arrangement, ReservationType reservationType, double totalPrice, double paidAmount) {
        this.client = client;
        this.arrangement = arrangement;
        this.reservationType = reservationType;
        this.totalPrice = totalPrice;
        this.paidAmount = paidAmount;
    }

    public Client getClient() {
        return client;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }

    public ReservationType getReservationType() {
        return reservationType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public boolean isTotallyPaid() {
        return paidAmount == totalPrice;
    }

    public boolean isPast() {
        return isTotallyPaid() && !arrangement.isOnOffer();
    }

    public boolean isCanceledByClient() {
        return paidAmount == 0;
    }

    public boolean isCanceled() {
        return isCanceledByClient() || (!isTotallyPaid() && arrangement.isPastDeadline());
    }

    public double unpaidAmount() {
        return totalPrice - paidAmount;
    }

    @Override
    public String toString() {
        return client.getUsername() + " " + arrangement.getName() + " Ukupna cijena: " + totalPrice + "\n";
    }
}
