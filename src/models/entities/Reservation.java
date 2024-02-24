package models.entities;

import models.enums.ReservationType;

/**
 * Represents a reservation made by a client for a specific arrangement.
 */
public class Reservation {
    private final Client client;
    private final Arrangement arrangement;
    private ReservationType reservationType;
    private final double totalPrice;
    private double paidAmount;

    /**
     * Constructs a new Reservation object.
     *
     * @param client          the client making the reservation.
     * @param arrangement     the arrangement being reserved.
     * @param reservationType the type of reservation.
     * @param totalPrice      the total price of the reservation.
     * @param paidAmount      the amount already paid for the reservation.
     */
    public Reservation(Client client, Arrangement arrangement, ReservationType reservationType, double totalPrice, double paidAmount) {
        this.client = client;
        this.arrangement = arrangement;
        this.reservationType = reservationType;
        this.totalPrice = totalPrice;
        this.paidAmount = paidAmount;
    }

    /**
     * Retrieves the client making the reservation.
     *
     * @return the client making the reservation.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Retrieves the arrangement being reserved.
     *
     * @return the arrangement being reserved.
     */
    public Arrangement getArrangement() {
        return arrangement;
    }

    /**
     * Retrieves the type of reservation.
     *
     * @return the type of reservation.
     */
    public ReservationType getReservationType() {
        return reservationType;
    }

    /**
     * Retrieves the total price of the reservation.
     *
     * @return the total price of the reservation.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Retrieves the amount already paid for the reservation.
     *
     * @return the amount already paid for the reservation.
     */
    public double getPaidAmount() {
        return paidAmount;
    }

    /**
     * Sets the amount already paid for the reservation.
     *
     * @param paidAmount the amount already paid for the reservation.
     */
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * Updates reservation type
     * @param reservationType new reservation type
     */
    public void setReservationType(ReservationType reservationType) {
        this.reservationType = reservationType;
    }

    /**
     * Checks if the reservation is totally paid.
     *
     * @return true if the reservation is totally paid, false otherwise.
     */
    public boolean isTotallyPaid() {
        return paidAmount == totalPrice;
    }

    /**
     * Checks if the reservation is past its trip date and fully paid.
     *
     * @return true if the reservation is past its trip date and fully paid, false otherwise.
     */
    public boolean isPast() {
        return isTotallyPaid() && !arrangement.isOnOffer();
    }

    /**
     * Checks if the reservation is canceled by the client.
     *
     * @return true if the reservation is canceled by the client, false otherwise.
     */
    public boolean isCanceledByClient() {
        return paidAmount == 0;
    }

    /**
     * Checks if the reservation is canceled.
     *
     * @return true if the reservation is canceled, false otherwise.
     */
    public boolean isCanceled() {
        return isCanceledByClient() || (!isTotallyPaid() && arrangement.isPastDeadline());
    }

    /**
     * Calculates the unpaid amount for the reservation.
     *
     * @return the unpaid amount for the reservation.
     */
    public double unpaidAmount() {
        return totalPrice - paidAmount;
    }

    /**
     * Checks if the client of the reservation matches the given client.
     *
     * @param c the client to compare.
     * @return true if the client matches, false otherwise.
     */
    public boolean isClientMatching(Client c) {
        return client.getId() == c.getId();
    }

    /**
     * Checks if the arrangement of the reservation matches the given arrangement.
     *
     * @param a the arrangement to compare.
     * @return true if the arrangement matches, false otherwise.
     */
    public boolean isArrangementMatching(Arrangement a) {
        return arrangement.getId() == a.getId();
    }

    /**
     * Checks if the reservation is made by the given client for the given arrangement.
     *
     * @param c the client to compare.
     * @param a the arrangement to compare.
     * @return true if the reservation is made by the client for the arrangement, false otherwise.
     */
    public boolean isReservedByClient(Client c, Arrangement a) {
        return isClientMatching(c) && isArrangementMatching(a);
    }

    /**
     * Checks if the client and reservation type of the reservation match the given client and reservation type.
     *
     * @param c  the client to compare.
     * @param rt the reservation type to compare.
     * @return true if the client and reservation type match, false otherwise.
     */
    public boolean areClientAndTypeMatching(Client c, ReservationType rt) {
        return isClientMatching(c) && reservationType == rt;
    }

    /**
     * Checks if the cancellation is available for the reservation.
     *
     * @return true if the cancellation is available, false otherwise.
     */
    public boolean isCancellationAvailable() {
        return arrangement.daysUntilTrip() >= Arrangement.PAYMENT_DEADLINE_END && reservationType == ReservationType.ACTIVE;
    }

    /**
     * Checks if the reservation is within the three-day deadline for payment.
     *
     * @return true if the reservation is within the three-day deadline, false otherwise.
     */
    public boolean isWithinThreeDayDeadline() {
        return !isTotallyPaid() && arrangement.isInDeadline();
    }

    /**
     * Checks if the reservation is refundable for the given arrangement.
     *
     * @param arr the arrangement to check for refundability.
     * @return true if the reservation is refundable, false otherwise.
     */
    public boolean isRefundable(Arrangement arr) {
        return isArrangementMatching(arr) && !isCanceled();
    }

    /**
     * Alerts the client if the reservation is active and within the three-day payment deadline.
     *
     * @param client the client to alert.
     * @return true if the client is alerted, false otherwise.
     */
    public boolean alertClient(Client client) {
        return isClientMatching(client) && reservationType == ReservationType.ACTIVE && isWithinThreeDayDeadline();
    }

    /**
     * Generates a string representation of the reservation.
     *
     * @return a string representation of the reservation.
     */
    @Override
    public String toString() {
        return client.getUsername() + " " + arrangement.getName();
    }

}
