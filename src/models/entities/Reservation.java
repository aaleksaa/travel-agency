package models.entities;

import models.enums.ReservationType;

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

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setReservationType(ReservationType reservationType) {
        this.reservationType = reservationType;
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

    public boolean isClientMatching(Client c) {
        return client.getId() == c.getId();
    }

    public boolean isArrangementMatching(Arrangement a) {
        return arrangement.getId() == a.getId();
    }

    public boolean isReservedByClient(Client c, Arrangement a) {
        return isClientMatching(c) && isArrangementMatching(a);
    }

    public boolean areClientAndTypeMatching(Client c, ReservationType rt) {
        return isClientMatching(c) && reservationType == rt;
    }

    public boolean isWithinThreeDayDeadline() {
        return !isTotallyPaid() && arrangement.isInDeadline();
    }

    public boolean isRefundable(Arrangement arr) {
        return isArrangementMatching(arr) && !isCanceled();
    }

    @Override
    public String toString() {
        return client.getUsername() + " " + arrangement.getName() + " Ukupna cijena: " + totalPrice + "\n";
    }
}
