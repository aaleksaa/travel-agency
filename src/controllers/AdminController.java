package controllers;

import implementation.admin.RevenueViewer;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.entities.Agency;
import models.entities.Client;
import models.entities.Reservation;

import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private Agency agency;

    public AdminController() throws SQLException {
        this.agency = new Agency();
    }

    public Agency getAgency() {
        return agency;
    }

    public void reservationItemEvent(Reservation res, Label lbl, ListView<Client> lv) {
        lbl.setText(RevenueViewer.reservationInfo(res));
        lv.getItems().clear();
        lv.getItems().addAll(RevenueViewer.findClients(agency.getReservations(), res.getArrangement()));
    }

    public String showRevenue() {
        return "Agency revenue: " + agency.getAgencyBankAccount().getBalance();
    }

    public String showTotalRemaining() {
        return "Total remaining: " + RevenueViewer.totalRemaining(agency.getReservations());
    }
}
