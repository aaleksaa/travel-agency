package models.entities;

import interfaces.Identifiable;
import models.enums.RoomType;

public class Accommodation implements Identifiable {
    private final int id;
    private final int starReview;
    private final String name;
    private final RoomType roomType;
    private final double pricePerNight;

    public Accommodation(int id, int starReview, String name, RoomType roomType, double pricePerNight) {
        this.id = id;
        this.starReview = starReview;
        this.name = name;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getStarReview() {
        return starReview;
    }

    public String getName() {
        return name;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }


    @Override
    public String toString() {
        return "[Accommodation] " + name + " | " + starReview + " | " + roomType;
    }
}
