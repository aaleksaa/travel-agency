package models.entities;

import models.enums.Transport;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class Arrangement {
    private final int id;
    private final String name;
    private final String destination;
    private final Transport transport;
    private final LocalDate tripDate;
    private final LocalDate arrivalDate;
    private final double price;
    private final Accommodation accommodation;

    public final static int PAYMENT_DEADLINE_START = 16;
    public final static int PAYMENT_DEADLINE_END = 14;

    public final static Comparator<Arrangement> priceAscending = (o1, o2) -> Double.compare(o1.calculateTotalPrice(), o2.calculateTotalPrice());
    public final static Comparator<Arrangement> priceDescending = (o1, o2) -> Double.compare(o2.calculateTotalPrice(), o1.calculateTotalPrice());
    public final static Comparator<Arrangement> tripDateAscending = (o1, o2) -> o1.tripDate.compareTo(o2.tripDate);
    public final static Comparator<Arrangement> tripDateDescending = (o1, o2) -> o2.tripDate.compareTo(o1.tripDate);

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public Transport getTransport() {
        return transport;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public double getPrice() {
        return price;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public static int dateDifference(LocalDate date1, LocalDate date2) {
        return (int) ChronoUnit.DAYS.between(date1, date2);
    }

    public boolean isOnOffer() {
        return tripDate.isAfter(LocalDate.now());
    }

    private int getNumberOfNights() {
        return dateDifference(tripDate, arrivalDate) - 1;
    }

    public double calculateTotalPrice() {
        return accommodation == null ? price : price + (getNumberOfNights() * accommodation.getPricePerNight());
    }

    public double getHalfPrice() {
        return calculateTotalPrice() / 2;
    }

    public int daysUntilTrip() {
        return dateDifference(LocalDate.now(), tripDate);
    }

    public boolean isPastDeadline() {
        return daysUntilTrip() < PAYMENT_DEADLINE_END;
    }

    public double getAmountForPayment() {
        return isPastDeadline() ? calculateTotalPrice() : getHalfPrice();
    }

    public boolean isInDeadline() {
        return daysUntilTrip() >= PAYMENT_DEADLINE_END && daysUntilTrip() <= PAYMENT_DEADLINE_START;
    }

    public boolean isDestinationMatching(String inputDestination) {
        return destination.equals(inputDestination);
    }

    public boolean isPriceLower(double amount) {
        return calculateTotalPrice() <= amount;
    }

    private String oneDayTripToString() {
        return name + " | " + destination + " | " + calculateTotalPrice()  + " | " + transport + " | " + arrivalDate + " | " + tripDate;
    }

    private String tripToString() {
        return name + " | " + destination + " | " + calculateTotalPrice()  + " | " + transport + " | " + arrivalDate + " | " + tripDate + " | " + accommodation;
    }

    @Override
    public String toString() {
        return accommodation == null ? oneDayTripToString() : tripToString();
    }
}
