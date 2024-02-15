package implementation.general;

import javafx.stage.Stage;
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

    public static void toClientPage(Stage stage) {
        stage.close();
        ClientPage clientPage = new ClientPage();

        try {
            clientPage.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void toAdminPage(Stage stage) {
        stage.close();

    }
}
