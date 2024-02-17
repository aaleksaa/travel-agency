package implementation.general;

import javafx.stage.Stage;
import models.entities.Admin;
import models.entities.BankAccount;
import models.entities.Client;
import view.AdminPage;
import view.ClientPage;
import view.LoginPage;
import view.RegistrationPage;

public class Navigation {
    public static void toLoginPage(Stage stage) {
        stage.close();
        LoginPage loginPage = new LoginPage();

        try {
            loginPage.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void toRegistrationPage(Stage stage) {
        stage.close();
        RegistrationPage registrationPage = new RegistrationPage();

        try {
            registrationPage.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void toClientPage(Stage stage, Client client, BankAccount bankAccount) {
        stage.close();
        ClientPage clientPage = new ClientPage(client, bankAccount);

        try {
            clientPage.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void toAdminPage(Stage stage, Admin admin) {
        stage.close();

        AdminPage adminPage = new AdminPage(admin);

        try {
            adminPage.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
