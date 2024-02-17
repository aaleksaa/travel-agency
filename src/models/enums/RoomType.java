package models.enums;

public enum RoomType {
    SINGLE_ROOM("Singleroom"),
    DOUBLE_ROOM("Doubleroom"),
    TRIPLE_ROOM("Tripleroom"),
    APARTMENT("Apartment");

    private String type;

    private RoomType(String type) {
        this.type = type;
    }

    public static RoomType fromString(String type) {
        return switch (type) {
            case "Singleroom" -> SINGLE_ROOM;
            case "Doubleroom" -> DOUBLE_ROOM;
            case "Tripleroom" -> TRIPLE_ROOM;
            case "Apartment" -> APARTMENT;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return type;
    }
}
