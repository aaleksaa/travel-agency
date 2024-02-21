package models.enums;

public enum RoomType {
    SINGLE_ROOM("Single-room"),
    DOUBLE_ROOM("Double-room"),
    TRIPLE_ROOM("Triple-room"),
    APARTMENT("Apartment");

    private String type;

    private RoomType(String type) {
        this.type = type;
    }

    public static RoomType fromString(String type) {
        return switch (type) {
            case "Single-room" -> SINGLE_ROOM;
            case "Double-room" -> DOUBLE_ROOM;
            case "Triple-room" -> TRIPLE_ROOM;
            case "Apartment" -> APARTMENT;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return type;
    }
}
