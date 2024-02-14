package models.arrangement;

public enum Transport {
    BUS("Bus"),
    PLANE("Plane"),
    SELF_TRANSPORT("Self-transport");

    private String transport;

    private Transport(String transport) {
        this.transport = transport;
    }

    public static Transport fromString(String transport) {
        return switch (transport) {
            case "Bus" -> BUS;
            case "Plane" -> PLANE;
            case "Self-transport" -> SELF_TRANSPORT;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return transport;
    }
}
