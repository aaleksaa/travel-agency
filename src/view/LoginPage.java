package view;

import controllers.LoginController;
import implementation.general.Navigation;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPage extends Application {
    private LoginController controller;
    @Override
    public void start(Stage stage) throws Exception {
        controller = new LoginController();

        VBox root = new VBox(10);
        root.setId("root-auth");
        loginGUI(stage, root);

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Travelsphere - Login");
        stage.show();
    }

    private void loginGUI(Stage stage, VBox root) {
        VBox vbLogin = new VBox(30);
        HBox hbSignIn = new HBox();

        ImageView logo = new ImageView(new Image("file:img/logo.png"));
        TextField tfUsername = new TextField();
        PasswordField pfPassword = new PasswordField();
        Button btnLogin = new Button("Login");

        Label lblRegister = new Label("Create new account!");
        Button btnSignIn = new Button("Sign In!");

        hbSignIn.getChildren().addAll(lblRegister, btnSignIn);
        vbLogin.getChildren().addAll(logo, tfUsername, pfPassword, btnLogin, hbSignIn);
        root.getChildren().add(vbLogin);

        vbLogin.setAlignment(Pos.TOP_CENTER);
        root.setAlignment(Pos.CENTER);
        hbSignIn.setAlignment(Pos.CENTER);
        logo.setFitHeight(150);
        logo.setFitWidth(150);
        vbLogin.getStyleClass().add("login");
        btnLogin.getStyleClass().add("btn");
        btnSignIn.getStyleClass().add("register-btn");
        tfUsername.setPromptText("Username");
        pfPassword.setPromptText("Password");

        btnSignIn.setOnAction(e -> Navigation.toRegistrationPage(stage));
        btnLogin.setOnAction(e -> controller.loginBtnEvent(stage, tfUsername, pfPassword));
    }
}
