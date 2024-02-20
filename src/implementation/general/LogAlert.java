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

public class LogAlert {
    private final static String alertFilename = "alert.txt";
    private final static String logsFilename = "logs.txt";


    /**
     * Dohvaća korisnička imena korisnika koji su rezervisali određeni aranžman.
     * @param reservations Lista rezervacija
     * @param arr Aranžman za koji se traže korisnička imena
     * @return Lista korisničkih imena
     */
    private static List<String> getUsernames(List<Reservation> reservations, Arrangement arr) {
        return reservations
                .stream()
                .filter(r -> r.isArrangementMatching(arr))
                .map(r -> r.getClient().getUsername())
                .toList();
    }


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
