package implementation.general;

import models.entities.Arrangement;
import models.entities.Reservation;
import models.entities.Client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * The LogAlert class provides methods for logging and managing alerts for clients.
 */
public class LogAlert {
    private final static String alertFilename = "alert.txt";
    private final static String logsFilename = "logs.txt";

    /**
     * Retrieves usernames of clients with reservations for a specific arrangement.
     *
     * @param reservations the list of reservations.
     * @param arr          the arrangement.
     * @return a list of usernames of clients with reservations for the arrangement.
     */
    private static List<String> getUsernames(List<Reservation> reservations, Arrangement arr) {
        return reservations
                .stream()
                .filter(res -> res.isArrangementMatching(arr))
                .map(res -> res.getClient().getUsername())
                .toList();
    }

    /**
     * Logs a new client's registration.
     *
     * @param username the username of the new client.
     */
    public static void logNewClient(String username) {
        Path path = Paths.get(logsFilename);
        String newLog = username + " " + LocalDate.now();
        try {
            List<String> info = Files.readAllLines(path);
            info.add(newLog);
            Files.write(path, info);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the alert file based on reservations for an arrangement.
     *
     * @param reservations the list of reservations.
     * @param arr          the arrangement.
     */
    public static void updateAlertFile(List<Reservation> reservations, Arrangement arr) {
        List<String> usernames = getUsernames(reservations, arr);

        Path path = Paths.get(alertFilename);
        try {
            List<String> lines = Files.readAllLines(path);

            for (String username : usernames)
                if (!lines.contains(username))
                    lines.add(username);

            Files.write(path, usernames);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if there is an alert for a specific client.
     *
     * @param client the client.
     * @return true if there is an alert for the client, false otherwise.
     */
    public static boolean clientAlert(Client client) {
        Path path = Paths.get(alertFilename);
        boolean alert = false;

        try {
            List<String> lines = Files.readAllLines(path);

            if (lines.contains(client.getUsername())) {
                lines.remove(client.getUsername());
                alert = true;
            }

            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return alert;
    }

    /**
     * Updates client logs and returns the previous log entry.
     *
     * @param client the client.
     * @return the previous log entry.
     */
    public static String updateLogs(Client client) {
        Path path = Paths.get(logsFilename);
        String log = null;
        String remove = null;
        String add = null;

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                String[] info = line.split(" ");
                if (client.getUsername().equals(info[0])) {
                    log = info[1].trim();
                    remove = line;
                    add = info[0] + " " + LocalDate.now();
                }
            }

            lines.remove(remove);
            lines.add(add);
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return log;
    }
}
