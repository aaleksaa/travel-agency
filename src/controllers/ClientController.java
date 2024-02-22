package controllers;

import exceptions.UnsuccessfulReservationException;
import implementation.admin.ArrangementManager;
import implementation.client.ReservationManager;
import implementation.general.MessageDisplay;
import implementation.general.Validator;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.entities.*;

import java.sql.SQLException;

public class ClientController {
    private Agency agency;

    public ClientController() throws SQLException {
        agency = new Agency();
    }

    public Agency getAgency() {
        return agency;
    }

    public void reservationBtnEvent(Stage stage, Label lbl, Arrangement arr, Client client, BankAccount bankAccount) {
        if (arr == null)
            MessageDisplay.showMessageLabel(lbl, ArrangementManager.ARRANGEMENT_NOT_SELECTED, false);
        else {
            try {
                Validator.isReserved(agency.getReservations(), client, arr);
                Validator.checkBalanceForTransaction(bankAccount, arr.getAmountForPayment());
            } catch (UnsuccessfulReservationException e) {
                MessageDisplay.showMessageLabel(lbl, e.getMessage(), false);
            }
        }
    }

    public void cancelBtnEvent(Reservation res, BankAccount bankAccount, Label lbl) {
        if (res == null)
            MessageDisplay.showMessageLabel(lbl, ReservationManager.RESERVATION_NOT_SELECTED, false);
        else {
            if (!res.isCancellationAvailable())
                MessageDisplay.showMessageLabel(lbl, ReservationManager.CANCEL_UNAVAILABLE, false);
            else {
                try {
                    ReservationManager.clientReservationCancel(res, bankAccount, agency.getAgencyBankAccount());
                    MessageDisplay.showMessageLabel(lbl, ReservationManager.SUCCESSFUL_RESERVATION_CANCEL + bankAccount.getBalance(), true);
                } catch (SQLException e) {
                    MessageDisplay.showAlert(Agency.DATABASE_ERROR, Alert.AlertType.INFORMATION);
                }
            }
        }
    }
}
