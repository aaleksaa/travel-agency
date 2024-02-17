package controllers;

import exceptions.EmptyInputException;
import exceptions.InvalidInputException;
import implementation.general.Login;
import implementation.general.Navigation;
import implementation.general.TransactionManager;
import implementation.general.Validator;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import models.entities.Admin;
import models.entities.Agency;
import models.entities.Client;
import models.entities.User;

import java.sql.SQLException;

public class LoginController {
    private Agency agency;

    public LoginController() throws SQLException {
        this.agency = new Agency();
    }

    public void loginBtnEvent(Stage stage, TextField tfUsername, PasswordField pfPassword) {
        TextInputControl[] inputs = {tfUsername, pfPassword};

        try {
            Validator.areInputsEmpty(inputs);
            Login.isUserInDatabase(agency.getUsers(), tfUsername.getText(), pfPassword.getText());
            User user = Login.getUserByUsername(agency.getUsers(), tfUsername.getText());

            if (user instanceof Client)
                Navigation.toClientPage(
                        stage,
                        (Client) user,
                        TransactionManager.getBankAccount(agency.getBankAccounts(), ((Client) user).getBankAccountNumber())
                );
            else
                Navigation.toAdminPage(stage, (Admin) user);

        } catch (EmptyInputException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
    }
}
