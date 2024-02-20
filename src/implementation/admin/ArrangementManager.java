package implementation.admin;

import database.Database;
import implementation.general.TransactionManager;
import models.entities.Accommodation;
import models.entities.Arrangement;
import models.entities.Reservation;
import models.entities.BankAccount;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class ArrangementManager {
    public final static String SUCCESSFUL_ARRANGEMENT_ADD = "Arrangement successfully added!";
    public final static String SUCCESSFUL_ARRANGEMENT_REMOVAL = "Arrangement successfully removed!";
    public final static String NO_RESERVATIONS = "There aren't reservations for this arrangement! Agency didn't lose money!";
    public final static String ARRANGEMENT_NOT_SELECTED = "Arrangement is not selected!";

    public static double agencyMoneyLost(List<Reservation> reservations, Arrangement arr) {
        return reservations
                .stream()
                .filter(res -> res.isRefundable(arr))
                .mapToDouble(Reservation::getPaidAmount)
                .sum();
    }

    public static void returnMoneyToClients(List<BankAccount> accounts, List<Reservation> reservations, Arrangement arr, BankAccount agencyBank) throws SQLException {
        for (Reservation res : reservations)
            if (res.isRefundable(arr))
                TransactionManager.performTransaction(
                        TransactionManager.getBankAccount(accounts, res.getClient().getBankAccountNumber()),
                        agencyBank,
                        res.getPaidAmount(),
                        true
                );
    }

    public static void removeArrangement(List<Reservation> reservations, List<Arrangement> arrangements, List<Accommodation> accommodations, Arrangement arr) throws SQLException {
        Iterator<Reservation> iter = reservations.iterator();
        boolean removed = false;

        // Provjerava svaku rezervaciju i uklanja one koje se odnose na zadani aranžman
        while (iter.hasNext()) {
            Reservation res = iter.next();
            if (res.isArrangementMatching(arr)) {
                iter.remove();
                removed = true;
            }
        }

        // Ako su postojale rezervacije za taj aranžman, uklanjaju se iz baze podataka
        if (removed)
            Database.deleteObject(arr.getId(), "rezervacija", "Aranzman_id");

        // Uklanja aranžman iz liste aranžmana i iz baze podataka
        arrangements.remove(arr);
        Database.deleteObject(arr.getId(), "aranzman", "id");

        // Ako aranžman ima pridruženi smještaj, uklanja se smještaj iz liste i iz baze podataka
        if (arr.getAccommodation() != null) {
            accommodations.remove(arr.getAccommodation());
            Database.deleteObject(arr.getAccommodation().getId(), "smjestaj", "id");
        }
    }
}
