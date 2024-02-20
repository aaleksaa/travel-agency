package controllers;

import exceptions.EmptyInputException;
import exceptions.InvalidInputException;
import implementation.admin.AdminViewer;
import implementation.admin.ArrangementManager;
import implementation.admin.RevenueViewer;
import implementation.general.LogAlert;
import implementation.general.MessageDisplay;
import implementation.general.Validator;
import javafx.scene.control.*;
import models.entities.*;

import java.sql.SQLException;

public class AdminController {
    private Agency agency;

    public AdminController() throws SQLException {
        this.agency = new Agency();
    }

    public Agency getAgency() {
        return agency;
    }

    public void addAdminEvent(TextField tfFirstName, TextField tfLastName, TextField tfUsername, Label lbl, ListView<Admin> lv) {
        TextInputControl[] inputs = {tfFirstName, tfLastName, tfUsername};
        try {
            Validator.areInputsEmpty(inputs);
            Validator.isUsernameAvailable(agency.getUsers(), tfUsername.getText());

            Admin admin = new Admin(
                    AdminViewer.nextAdminID(agency.getUsers()),
                    tfFirstName.getText(),
                    tfLastName.getText(),
                    tfUsername.getText(),
                    "12345678"
            );

            AdminViewer.addAdmin(agency.getUsers(), admin);

            lbl.setText(printAdminCounter());
            lv.getItems().add(admin);
            MessageDisplay.showAlert(AdminViewer.SUCCESSFUL_ADMIN_REGISTRATION, Alert.AlertType.INFORMATION);
            Validator.resetInputs(inputs);
        } catch (EmptyInputException e) {
            MessageDisplay.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
        } catch (InvalidInputException e) {
            MessageDisplay.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
            tfUsername.clear();
        } catch (SQLException e) {
            MessageDisplay.showAlert(Agency.DATABASE_ERROR, Alert.AlertType.INFORMATION);
        }
    }

    public void cancelBtnEvent(Arrangement arr, Label lbl, ListView<Arrangement> lv) throws SQLException {
        if (arr != null) {
            if (ArrangementManager.agencyMoneyLost(agency.getReservations(), arr) == 0)
                MessageDisplay.showMessageLabel(lbl, ArrangementManager.NO_RESERVATIONS, true);
            else {
                MessageDisplay.showMessageLabel(
                        lbl,
                        "Agency lost: " + ArrangementManager.agencyMoneyLost(agency.getReservations(), arr),
                        false
                );

                ArrangementManager.returnMoneyToClients(
                        agency.getBankAccounts(),
                        agency.getReservations(),
                        arr,
                        agency.getAgencyBankAccount()
                );

                LogAlert.updateAlertFile(agency.getReservations(), arr);
            }

            ArrangementManager.removeArrangement(
                    agency.getReservations(),
                    agency.getArrangements(),
                    agency.getAccommodations(),
                    arr
            );
            lv.getItems().remove(arr);
            MessageDisplay.showAlert(ArrangementManager.SUCCESSFUL_ARRANGEMENT_REMOVAL, Alert.AlertType.INFORMATION);
        } else
            MessageDisplay.showMessageLabel(lbl, ArrangementManager.ARRANGEMENT_NOT_SELECTED, false);
    }

    public void reservationItemEvent(Reservation res, Label lbl, ListView<Client> lv) {
        lbl.setText(RevenueViewer.reservationInfo(res));
        lv.getItems().clear();
        lv.getItems().addAll(RevenueViewer.findClients(agency.getReservations(), res.getArrangement()));
    }

    public String printAdminCounter() {
        return "Admin counter: " + AdminViewer.adminCounter(agency.getUsers());
    }

    public String printRevenue() {
        return "Agency revenue: " + agency.getAgencyBankAccount().getBalance();
    }

    public String printTotalRemaining() {
        return "Total remaining: " + RevenueViewer.totalRemaining(agency.getReservations());
    }
}
