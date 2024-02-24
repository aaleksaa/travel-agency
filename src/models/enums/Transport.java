package models.enums;

/**
 * Enum representing different modes of transport.
 */
public enum Transport {
    BUS("Bus"),
    PLANE("Plane"),
    SELF_TRANSPORT("Self-transport");

    private String transport;

    /**
     * Constructor to initialize Transport enum with a string representation.
     *
     * @param transport the string representation of the transport mode.
     */
    private Transport(String transport) {
        this.transport = transport;
    }

    /**
     * Returns the Transport enum corresponding to the given string representation.
     *
     * @param transport the string representation of the transport mode.
     * @return the Transport enum corresponding to the given string, or null if not found.
     */
    public static Transport fromString(String transport) {
        return switch (transport) {
            case "Bus" -> BUS;
            case "Plane" -> PLANE;
            case "Self-transport" -> SELF_TRANSPORT;
            default -> null;
        };
    }

    /**
     * Returns the string representation of the Transport enum.
     *
     * @return the string representation of the transport mode.
     */
    @Override
    public String toString() {
        return transport;
    }
}
