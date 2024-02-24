package models.enums;

/**
 * Enum representing different types of rooms.
 */
public enum RoomType {
    SINGLE_ROOM("Single-room"),
    DOUBLE_ROOM("Double-room"),
    TRIPLE_ROOM("Triple-room"),
    APARTMENT("Apartment");

    private String type;

    /**
     * Constructor to initialize RoomType enum with a string representation.
     *
     * @param type the string representation of the room type.
     */
    private RoomType(String type) {
        this.type = type;
    }

    /**
     * Returns the RoomType enum corresponding to the given string representation.
     *
     * @param type the string representation of the room type.
     * @return the RoomType enum corresponding to the given string, or null if not found.
     */
    public static RoomType fromString(String type) {
        return switch (type) {
            case "Single-room" -> SINGLE_ROOM;
            case "Double-room" -> DOUBLE_ROOM;
            case "Triple-room" -> TRIPLE_ROOM;
            case "Apartment" -> APARTMENT;
            default -> null;
        };
    }

    /**
     * Returns the string representation of the RoomType enum.
     *
     * @return the string representation of the room type.
     */
    @Override
    public String toString() {
        return type;
    }
}
