package models.entities;

import interfaces.Identifiable;
import models.enums.Transport;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

/**
 * The Arrangement class represents a travel arrangement.
 * It contains information such as ID, name, destination, transport, trip date, arrival date, price, and accommodation.
 */
public class Arrangement implements Identifiable {
    private final int id;
    private final String name;
    private final String destination;
    private final Transport transport;
    private final LocalDate tripDate;
    private final LocalDate arrivalDate;
    private final double price;
    private final Accommodation accommodation;

    /** Constants for payment deadline calculation */
    public final static int PAYMENT_DEADLINE_START = 16;
    public final static int PAYMENT_DEADLINE_END = 14;

    /** Comparators for sorting arrangements */
    public final static Comparator<Arrangement> priceAscending = (o1, o2) -> Double.compare(o1.calculateTotalPrice(), o2.calculateTotalPrice());
    public final static Comparator<Arrangement> priceDescending = (o1, o2) -> Double.compare(o2.calculateTotalPrice(), o1.calculateTotalPrice());
    public final static Comparator<Arrangement> tripDateAscending = (o1, o2) -> o1.tripDate.compareTo(o2.tripDate);
    public final static Comparator<Arrangement> tripDateDescending = (o1, o2) -> o2.tripDate.compareTo(o1.tripDate);

    /**
     * Constructs an Arrangement object with the specified attributes.
     *
     * @param id            the unique identifier of the arrangement.
     * @param name          the name of the arrangement.
     * @param destination   the destination of the arrangement.
     * @param transport     the mode of transport for the arrangement.
     * @param tripDate      the date of the trip.
     * @param arrivalDate   the arrival date.
     * @param price         the price of the arrangement.
     * @param accommodation the accommodation for the arrangement.
     */
    public Arrangement(int id, String name, String destination, Transport transport, LocalDate tripDate, LocalDate arrivalDate, double price, Accommodation accommodation) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.transport = transport;
        this.tripDate = tripDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
        this.accommodation = accommodation;
    }

    /**
     * Retrieves the unique identifier of the arrangement.
     *
     * @return the id of the arrangement.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of the arrangement.
     *
     * @return the name of the arrangement.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the destination of the arrangement.
     *
     * @return the destination of the arrangement.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Retrieves the mode of transport for the arrangement.
     *
     * @return the mode of transport.
     */
    public Transport getTransport() {
        return transport;
    }

    /**
     * Retrieves the trip date of the arrangement.
     *
     * @return the trip date.
     */
    public LocalDate getTripDate() {
        return tripDate;
    }

    /**
     * Retrieves the arrival date of the arrangement.
     *
     * @return the arrival date.
     */
    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Retrieves the price of the arrangement.
     *
     * @return the price of the arrangement.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieves the accommodation for the arrangement.
     *
     * @return the accommodation.
     */
    public Accommodation getAccommodation() {
        return accommodation;
    }

    /**
     * Calculates the difference in days between two dates.
     *
     * @param date1 the first date.
     * @param date2 the second date.
     * @return the difference in days between the two dates.
     */
    public static int dateDifference(LocalDate date1, LocalDate date2) {
        return (int) ChronoUnit.DAYS.between(date1, date2);
    }

    /**
     * Checks if the trip is scheduled in the future.
     *
     * @return true if the trip is scheduled in the future, false otherwise.
     */
    public boolean isOnOffer() {
        return tripDate.isAfter(LocalDate.now());
    }

    /**
     * Calculates the total price of the arrangement, including accommodation if available.
     *
     * @return the total price of the arrangement.
     */
    public double calculateTotalPrice() {
        return accommodation == null ? price : price + (getNumberOfNights() * accommodation.getPricePerNight());
    }

    /**
     * Calculates the number of nights for the arrangement based on the trip and arrival dates.
     *
     * @return the number of nights for the arrangement.
     */
    private int getNumberOfNights() {
        return dateDifference(tripDate, arrivalDate) - 1;
    }

    /**
     * Calculates the half price of the arrangement.
     *
     * @return the half price of the arrangement.
     */
    public double getHalfPrice() {
        return calculateTotalPrice() / 2;
    }

    /**
     * Calculates the number of days until the trip date.
     *
     * @return the number of days until the trip date.
     */
    public int daysUntilTrip() {
        return dateDifference(LocalDate.now(), tripDate);
    }

    /**
     * Checks if the arrangement is past the payment deadline.
     *
     * @return true if the arrangement is past the payment deadline, false otherwise.
     */
    public boolean isPastDeadline() {
        return daysUntilTrip() < PAYMENT_DEADLINE_END;
    }

    /**
     * Retrieves the amount to be paid based on whether the arrangement is past the payment deadline or not.
     *
     * @return the amount to be paid.
     */
    public double getAmountForPayment() {
        return isPastDeadline() ? calculateTotalPrice() : getHalfPrice();
    }

    /**
     * Checks if the arrangement is within the payment deadline range.
     *
     * @return true if the arrangement is within the payment deadline range, false otherwise.
     */
    public boolean isInDeadline() {
        return daysUntilTrip() >= PAYMENT_DEADLINE_END && daysUntilTrip() <= PAYMENT_DEADLINE_START;
    }

    /**
     * Checks if the destination of the arrangement matches the given input destination.
     *
     * @param inputDestination the destination to compare with.
     * @return true if the destination matches, false otherwise.
     */
    public boolean isDestinationMatching(String inputDestination) {
        return destination.equals(inputDestination);
    }

    /**
     * Checks if the total price of the arrangement is lower than or equal to the specified amount.
     *
     * @param amount the amount to compare with.
     * @return true if the total price is lower than or equal to the specified amount, false otherwise.
     */
    public boolean isPriceLower(double amount) {
        return calculateTotalPrice() <= amount;
    }

    /**
     * Checks if the trip date of the arrangement is scheduled after the given date.
     *
     * @param date the date to compare with.
     * @return true if the trip date is after the given date, false otherwise.
     */
    public boolean isTripScheduledAfter(LocalDate date) {
        return !tripDate.isBefore(date);
    }

    /**
     * Checks if the arrival date of the arrangement is planned before the given date.
     *
     * @param date the date to compare with.
     * @return true if the arrival date is before the given date, false otherwise.
     */
    public boolean isArrivalPlannedBefore(LocalDate date) {
        return !arrivalDate.isAfter(date);
    }

    /**
     * Returns a string representation of the arrangement for a one-day trip.
     *
     * @return a string containing the name, destination, total price, transport, trip date, and arrival date of the arrangement.
     */
    private String oneDayTripToString() {
        return name + " | " + destination + " | " + calculateTotalPrice()  + " | " + transport + " | " + tripDate + " | " + arrivalDate;
    }

    /**
     * Returns a string representation of the arrangement for multi-day trips.
     *
     * @return a string containing the name, destination, total price, transport, trip date, arrival date, and accommodation of the arrangement.
     */
    private String tripToString() {
        return name + " | " + destination + " | " + calculateTotalPrice()  + " | " + transport + " | " + tripDate + " | " + arrivalDate + " | " + accommodation;
    }

    /**
     * Returns a string representation of the arrangement.
     *
     * @return a string containing the name, destination, total price, transport, trip date, arrival date, and accommodation (if available) of the arrangement.
     */
    @Override
    public String toString() {
        return accommodation == null ? oneDayTripToString() : tripToString();
    }
}
