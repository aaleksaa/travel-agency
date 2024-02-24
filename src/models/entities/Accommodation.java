package models.entities;

import interfaces.Identifiable;
import models.enums.RoomType;

/**
 * The Accommodation class represents accommodation facilities.
 * It contains information such as ID, star review, name, room type, and price per night.
 */
public class Accommodation implements Identifiable {
    private final int id;
    private final int starReview;
    private final String name;
    private final RoomType roomType;
    private final double pricePerNight;

    /**
     * Constructs an Accommodation object with the specified attributes.
     *
     * @param id            the unique identifier of the accommodation.
     * @param starReview    the star review rating of the accommodation.
     * @param name          the name of the accommodation.
     * @param roomType      the type of room available in the accommodation.
     * @param pricePerNight the price per night of the accommodation.
     */
    public Accommodation(int id, int starReview, String name, RoomType roomType, double pricePerNight) {
        this.id = id;
        this.starReview = starReview;
        this.name = name;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }

    /**
     * Retrieves the unique identifier of the accommodation.
     *
     * @return the id of the accommodation.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Retrieves the star review rating of the accommodation.
     *
     * @return the star review rating.
     */
    public int getStarReview() {
        return starReview;
    }

    /**
     * Retrieves the name of the accommodation.
     *
     * @return the name of the accommodation.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the type of room available in the accommodation.
     *
     * @return the room type.
     */
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * Retrieves the price per night of the accommodation.
     *
     * @return the price per night.
     */
    public double getPricePerNight() {
        return pricePerNight;
    }

    /**
     * Returns a string representation of the accommodation.
     *
     * @return a string containing the name, star review rating, and room type of the accommodation.
     */
    @Override
    public String toString() {
        return "[Accommodation] " + name + " | " + starReview + " | " + roomType;
    }
}
