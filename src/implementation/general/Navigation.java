package implementation.general;

import javafx.stage.Stage;
import models.entities.Admin;
import models.entities.BankAccount;
import models.entities.Client;
import view.AdminPage;
import view.ClientPage;
import view.LoginPage;
import view.RegistrationPage;

/**
 * The Navigation class provides methods for navigating between different pages of the application.
 */
public class Navigation {
    /**
     * Closes the current stage and navigates to the login page.
     *
     * @param stage The stage to be closed.
     */
    public static void toLoginPage(Stage stage) {
        stage.close();
        LoginPage loginPage = new LoginPage();

        try {
            loginPage.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the current stage and navigates to the registration page.
     *
     * @param stage The stage to be closed.
     */
    public static void toRegistrationPage(Stage stage) {
        stage.close();
        RegistrationPage registrationPage = new RegistrationPage();

        try {
            registrationPage.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the current stage and navigates to the client page.
     *
     * @param stage       The stage to be closed.
     * @param client      The client for whom the page is being displayed.
     * @param bankAccount The bank account associated with the client.
     */
    public static void toClientPage(Stage stage, Client client, BankAccount bankAccount) {
        stage.close();
        ClientPage clientPage = new ClientPage(client, bankAccount);

        try {
            clientPage.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the current stage and navigates to the admin page.
     *
     * @param stage The stage to be closed.
     * @param admin The admin for whom the page is being displayed.
     */
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
