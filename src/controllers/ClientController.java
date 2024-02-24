package controllers;

import database.Database;
import exceptions.EmptyInputException;
import exceptions.InvalidInputException;
import exceptions.PasswordMismatchException;
import exceptions.UnsuccessfulReservationException;
import implementation.admin.ArrangementManager;
import implementation.client.ReservationManager;
import implementation.general.MessageDisplay;
import implementation.general.TransactionManager;
import implementation.general.Validator;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.entities.*;
import models.enums.ReservationType;

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
                confirmReservationPopup(stage, arr, client, bankAccount, lbl);
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

    public void payBtnEvent(Stage stage, Reservation res, Client client, BankAccount bankAccount, Label lbl1, Label lbl2, Text txt1, Text txt2) {
        if (res == null)
            MessageDisplay.showMessageLabel(lbl1, ReservationManager.RESERVATION_NOT_SELECTED, false);
        else {
            if (res.getReservationType() != ReservationType.ACTIVE)
                MessageDisplay.showMessageLabel(lbl1, ReservationManager.NOT_ACTIVE_RESERVATION, false);
            else {
                if (res.isTotallyPaid())
                    MessageDisplay.showMessageLabel(lbl1, ReservationManager.RESERVATION_TOTALLY_PAID, false);
                else
                    payReservationPopup(stage, res, client, bankAccount, lbl1, lbl2, txt1, txt2);
            }
        }
    }

    private void payReservationPopup(Stage stage, Reservation res, Client client, BankAccount bankAccount, Label lbl1, Label lbl2, Text txt1, Text txt2) {
        Popup popup = new Popup();
        VBox vb = new VBox(15);
        Text txtConfirm = new Text("Confirm password");
        PasswordField pf = new PasswordField();
        Text txtAmount = new Text("Input amount");
        TextField tf = new TextField();
        Button btn = new Button("Pay");
        Label lblMessage = new Label();
        vb.getChildren().addAll(txtConfirm, pf, txtAmount, tf, btn, lblMessage);

        popup.getContent().add(vb);
        popup.show(stage);

        vb.setId("popup");
        btn.getStyleClass().add("btn2");
        vb.setAlignment(Pos.CENTER);

        btn.setOnAction(e -> popupPayBtnEvent(popup, res, client, bankAccount, lbl1, lbl2, lblMessage, txt1, txt2, tf, pf));
    }

    private void popupPayBtnEvent(Popup popup, Reservation res, Client client, BankAccount bankAccount, Label lbl1, Label lbl2, Label lbl3, Text txt1, Text txt2, TextField tf, PasswordField pf) {
        TextInputControl[] inputs = {tf, pf};
        try {
            Validator.areInputsEmpty(inputs);
            Validator.passwordMatch(client.getPassword(), pf.getText());
            Validator.isPriceValid(tf.getText());

            ReservationManager.payReservation(res, bankAccount, agency.getAgencyBankAccount(), Double.parseDouble(tf.getText()));
            popup.hide();
            txt1.setText(printTotalSpent(client));
            txt2.setText(printTotalRemaining(client));
            lbl2.setText("Remaining amount for this reservation: " + res.unpaidAmount());
            MessageDisplay.showMessageLabel(lbl1, ReservationManager.SUCCESSFUL_PAYMENT + bankAccount.getBalance(), true);
        } catch (EmptyInputException | UnsuccessfulReservationException e) {
            lbl3.setText(e.getMessage());
        } catch (PasswordMismatchException e) {
            lbl3.setText(e.getMessage());
            pf.clear();
        } catch (InvalidInputException e) {
            lbl3.setText(e.getMessage());
            tf.clear();
        } catch (SQLException e) {
            lbl3.setText(Agency.DATABASE_ERROR);
        }
    }

    private void confirmReservationPopup(Stage stage, Arrangement arr, Client client, BankAccount bankAccount, Label lbl) {
        Popup popup = new Popup();
        VBox vb = new VBox(10);
        Text txt = new Text("Confirm password");
        PasswordField pfConfirm = new PasswordField();
        Button btnConfirm = new Button("Confirm");
        Label lblMessage = new Label();
        vb.getChildren().addAll(txt, pfConfirm, btnConfirm, lblMessage);

        popup.getContent().add(vb);
        popup.show(stage);

        vb.setAlignment(Pos.CENTER);
        btnConfirm.getStyleClass().add("btn2");
        vb.setId("popup");

        btnConfirm.setOnAction(e -> confirmBtnEvent(popup, arr, client, bankAccount, lbl, lblMessage, pfConfirm));
    }



    private void confirmBtnEvent(Popup popup, Arrangement arr, Client client, BankAccount bankAccount, Label lbl1, Label lbl2, PasswordField pf) {
        try {
            Validator.passwordMatch(client.getPassword(), pf.getText());
            ReservationManager.addReservation(
                    agency.getReservations(),
                    new Reservation(
                            client,
                            arr,
                            ReservationType.ACTIVE,
                            arr.calculateTotalPrice(),
                            arr.getAmountForPayment()
                    )
            );
            TransactionManager.performTransaction(bankAccount, agency.getAgencyBankAccount(), arr.getAmountForPayment(), false);
            popup.hide();
            MessageDisplay.showMessageLabel(lbl1, ReservationManager.SUCCESSFUL_RESERVATION + bankAccount.getBalance(), true);
        } catch (PasswordMismatchException e) {
            lbl2.setText(e.getMessage());
            pf.clear();
        } catch (SQLException e) {
            lbl2.setText(Agency.DATABASE_ERROR);
        }
    }

    public void changePasswordEvent(Stage stage, Popup info, Client client) {
        info.hide();
        Popup popup = new Popup();
        VBox vb = new VBox(15);
        Text txt = new Text("Change password");
        Label lblMessage = new Label();

        PasswordField pfOld = new PasswordField();
        PasswordField pfNew = new PasswordField();

        Button btn = new Button("Change");
        vb.getChildren().addAll(txt, pfOld, pfNew, btn, lblMessage);
        popup.getContent().add(vb);
        popup.show(stage);

        pfOld.setPromptText("Old password");
        pfNew.setPromptText("New password");
        vb.setId("popup");
        vb.setAlignment(Pos.CENTER);
        btn.getStyleClass().add("btn2");

        btn.setOnAction(e -> changePassword(popup, client, pfOld, pfNew, lblMessage));
    }

    private void changePassword(Popup popup, Client client, PasswordField pfOld, PasswordField pfNew, Label lbl) {
        TextInputControl[] inputs = {pfOld, pfNew};
        try {
            Validator.areInputsEmpty(inputs);
            Validator.passwordMatch(client.getPassword(), pfOld.getText());
            if (pfNew.getText().equals(pfOld.getText())) {
                lbl.setText("Set new password!");
                pfNew.clear();
            } else {
                client.setPassword(pfNew.getText());
                Database.changePassword(client.getId(), client.getPassword(), "klijent");
                popup.hide();
            }
        } catch (EmptyInputException e) {
            lbl.setText(e.getMessage());
        } catch (PasswordMismatchException e) {
            lbl.setText(e.getMessage());
            pfOld.clear();
        } catch (SQLException e) {
            lbl.setText(Agency.DATABASE_ERROR);
        }
    }

    public String printTotalSpent(Client client) {
        return "Total spent: " + ReservationManager.clientMoneySpent(agency.getReservations(), client);
    }

    public String printTotalRemaining(Client client) {
        return "Total remaining: " + ReservationManager.remainingAmountToPay(agency.getReservations(), client);
    }
}
