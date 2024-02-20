package controllers;

import database.Database;
import exceptions.EmptyInputException;
import exceptions.InvalidBankAccountException;
import exceptions.InvalidInputException;
import exceptions.PasswordMismatchException;
import implementation.general.*;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import models.entities.Agency;

import java.sql.SQLException;

public class RegistrationController {
    private Agency agency;

    public RegistrationController() throws SQLException {
        this.agency = new Agency();
    }

    public void signInBtnEvent(Stage stage, TextField tfFirstName, TextField tfLastName, TextField tfPhoneNumber, TextField tfJMBG, TextField tfAccountNumber, TextField tfUsername, PasswordField pfPassword, PasswordField pfConfirmPassword) {
        TextInputControl[] inputs = {
                tfFirstName,
                tfLastName,
                tfPhoneNumber,
                tfJMBG,
                tfAccountNumber,
                tfUsername,
                pfPassword,
                pfConfirmPassword
        };

        try {
            Validator.areInputsEmpty(inputs);
            Validator.isUsernameAvailable(agency.getUsers(), tfUsername.getText());
            Registration.isBankAccountInDatabase(agency.getBankAccounts(), tfAccountNumber.getText());
            Validator.passwordMatch(pfPassword.getText(), pfConfirmPassword.getText());

            Database.registerClient(
                    Registration.nextClientID(agency.getUsers()),
                    tfFirstName.getText(),
                    tfLastName.getText(),
                    tfPhoneNumber.getText(),
                    tfJMBG.getText(),
                    tfAccountNumber.getText(),
                    tfUsername.getText(),
                    pfPassword.getText()
            );

            LogAlert.logNewClient(tfUsername.getText());
            MessageDisplay.showAlert("Registration is successful!", Alert.AlertType.INFORMATION);
            Navigation.toLoginPage(stage);
        } catch (EmptyInputException e) {
            MessageDisplay.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
        } catch (InvalidInputException e) {
            MessageDisplay.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
            tfUsername.clear();
        } catch (InvalidBankAccountException e) {
            MessageDisplay.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
            tfAccountNumber.clear();
        } catch (PasswordMismatchException e) {
            MessageDisplay.showAlert(e.getMessage(), Alert.AlertType.INFORMATION);
            pfConfirmPassword.clear();
        } catch (SQLException e) {
            MessageDisplay.showAlert(Agency.DATABASE_ERROR, Alert.AlertType.INFORMATION);
        }
    }
}
