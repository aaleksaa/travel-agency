package controllers;

import database.Database;
import exceptions.EmptyInputException;
import exceptions.InvalidDateException;
import exceptions.InvalidInputException;
import exceptions.PasswordMismatchException;
import implementation.admin.AdminViewer;
import implementation.admin.ArrangementManager;
import implementation.admin.RevenueViewer;
import implementation.general.LogAlert;
import implementation.general.MessageDisplay;
import implementation.general.Validator;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.entities.*;
import models.enums.RoomType;
import models.enums.Transport;

import java.sql.SQLException;
import java.time.LocalDate;

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

    public void addTripEvent(TextField tfTripName, TextField tfDestination, ChoiceBox<String> cbTransport, DatePicker dpTripDate, DatePicker dpArrivalDate, TextField tfArrangementPrice, TextField tfAccommodationName, TextField tfStarReview, ChoiceBox<String> cbRoomType, TextField tfPricePerNight) {
        TextInputControl[] inputs = {
                tfTripName,
                tfDestination,
                tfArrangementPrice,
                tfAccommodationName,
                tfStarReview,
                tfPricePerNight
        };

        try {
            Validator.areInputsEmpty(inputs);
            Validator.areChoiceBoxesSelected(cbTransport, cbRoomType);
            Validator.isDateSelected(dpTripDate.getValue());
            Validator.isDateSelected(dpArrivalDate.getValue());
            Validator.isTripDateAfterArrivalDate(dpTripDate.getValue(), dpArrivalDate.getValue());
            Validator.isPriceValid(tfArrangementPrice.getText());
            Validator.isPriceValid(tfPricePerNight.getText());

            Accommodation accomm = new Accommodation(
                    ArrangementManager.nextAccommodationID(agency.getAccommodations()),
                    Integer.parseInt(tfStarReview.getText()),
                    tfAccommodationName.getText(),
                    RoomType.fromString(cbRoomType.getValue()),
                    Double.parseDouble(tfPricePerNight.getText())
            );

            ArrangementManager.addAccommodation(agency.getAccommodations(), accomm);

            ArrangementManager.addArrangement(
                    agency.getArrangements(),
                    new Arrangement(
                        ArrangementManager.nextArrangementID(agency.getArrangements()),
                            tfTripName.getText(),
                            tfDestination.getText(),
                            Transport.fromString(cbTransport.getValue()),
                            dpTripDate.getValue(),
                            dpArrivalDate.getValue(),
                            Double.parseDouble(tfArrangementPrice.getText()),
                            accomm
                    )
            );

            MessageDisplay.showAlert(ArrangementManager.SUCCESSFUL_ARRANGEMENT_ADD, Alert.AlertType.INFORMATION);
            Validator.resetInputs(inputs);
            cbRoomType.getSelectionModel().selectFirst();
            cbTransport.getSelectionModel().selectFirst();
            dpArrivalDate.setValue(null);
            dpTripDate.setValue(null);
        } catch (EmptyInputException | InvalidInputException e) {
            MessageDisplay.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
        } catch (InvalidDateException e) {
            MessageDisplay.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
            dpArrivalDate.setValue(null);
        } catch (SQLException e) {
            MessageDisplay.showAlert(Agency.DATABASE_ERROR, Alert.AlertType.INFORMATION);
        }
    }

    public void addOneDayTripEvent(TextField tfName, TextField tfDestination, DatePicker dp, TextField tfPrice) {
        TextInputControl[] inputs = {tfName, tfDestination, tfPrice};
        try {
            Validator.areInputsEmpty(inputs);
            Validator.isDateSelected(dp.getValue());
            Validator.isPriceValid(tfPrice.getText());

            ArrangementManager.addArrangement(
                    agency.getArrangements(),
                    new Arrangement(
                            ArrangementManager.nextArrangementID(agency.getArrangements()),
                            tfName.getText(),
                            tfDestination.getText(),
                            Transport.fromString("Bus"),
                            dp.getValue(),
                            dp.getValue(),
                            Double.parseDouble(tfPrice.getText()),
                            null
                    )
            );

            MessageDisplay.showAlert(ArrangementManager.SUCCESSFUL_ARRANGEMENT_ADD, Alert.AlertType.INFORMATION);
            Validator.resetInputs(inputs);
            dp.setValue(null);
        } catch (EmptyInputException e) {
            MessageDisplay.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
        } catch (InvalidInputException e) {
            MessageDisplay.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
            tfPrice.clear();
        } catch (SQLException e) {
            MessageDisplay.showAlert(Agency.DATABASE_ERROR, Alert.AlertType.INFORMATION);
        }
    }

    public void cancelBtnEvent(Arrangement arr, Label lbl, ListView<Arrangement> lv) {
        if (arr != null) {
            if (ArrangementManager.agencyMoneyLost(agency.getReservations(), arr) == 0)
                MessageDisplay.showMessageLabel(lbl, ArrangementManager.NO_RESERVATIONS, true);
            else {
                MessageDisplay.showMessageLabel(
                        lbl,
                        "Agency lost: " + ArrangementManager.agencyMoneyLost(agency.getReservations(), arr),
                        false
                );

                try {
                    ArrangementManager.returnMoneyToClients(
                            agency.getBankAccounts(),
                            agency.getReservations(),
                            arr,
                            agency.getAgencyBankAccount()
                    );
                } catch (SQLException e) {
                    MessageDisplay.showAlert(Agency.DATABASE_ERROR, Alert.AlertType.INFORMATION);
                }

                LogAlert.updateAlertFile(agency.getReservations(), arr);
            }

            try {
                ArrangementManager.removeArrangement(
                        agency.getReservations(),
                        agency.getArrangements(),
                        agency.getAccommodations(),
                        arr
                );
            } catch (SQLException e) {
                MessageDisplay.showAlert(Agency.DATABASE_ERROR, Alert.AlertType.INFORMATION);
            }

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

    public void changePasswordEvent(Stage stage, Popup info, Admin admin) {
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

        btn.setOnAction(e -> changePassword(popup, admin, pfOld, pfNew, lblMessage));
    }

    private void changePassword(Popup popup, Admin admin, PasswordField pfOld, PasswordField pfNew, Label lbl) {
        TextInputControl[] inputs = {pfOld, pfNew};
        try {
            Validator.areInputsEmpty(inputs);
            Validator.passwordMatch(admin.getPassword(), pfOld.getText());
            if (pfNew.getText().equals(pfOld.getText())) {
                lbl.setText("Set new password!");
                pfNew.clear();
            } else {
                admin.setPassword(pfNew.getText());
                Database.changePassword(admin.getId(), admin.getPassword(), "admin");
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
