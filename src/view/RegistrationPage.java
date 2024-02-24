package view;

import controllers.RegistrationController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistrationPage extends Application {
    private RegistrationController controller;

    @Override
    public void start(Stage stage) throws Exception {
        controller = new RegistrationController();

        VBox root = new VBox(10);
        root.setId("root-auth");
        registrationGUI(stage, root);

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Travelsphere - SignIn");
        stage.show();
    }

    public void registrationGUI(Stage stage, VBox root) {
        VBox vbRegistration = new VBox(20);
        HBox hbForm = new HBox(30);
        VBox vbLeft = new VBox(15);
        VBox vbRight = new VBox(15);

        ImageView logo = new ImageView(new Image("file:img/logo.png"));

        TextField tfFirstName = new TextField();
        TextField tfLastName = new TextField();
        TextField tfPhoneNumber = new TextField();
        TextField tfJmbg = new TextField();

        TextField tfBankNumber = new TextField();
        TextField tfUsername = new TextField();
        PasswordField pfPassword = new PasswordField();
        PasswordField pfConfirm = new PasswordField();

        Button btnSignIn = new Button("Sign In");

        hbForm.getChildren().addAll(vbLeft, vbRight);
        vbLeft.getChildren().addAll(tfFirstName, tfLastName, tfPhoneNumber, tfJmbg);
        vbRight.getChildren().addAll(tfBankNumber, tfUsername, pfPassword, pfConfirm);
        vbRegistration.getChildren().addAll(logo, hbForm, btnSignIn);
        root.getChildren().add(vbRegistration);

        vbRegistration.setAlignment(Pos.TOP_CENTER);
        hbForm.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        logo.setFitHeight(150);
        logo.setFitWidth(150);
        vbRegistration.getStyleClass().add("registration");
        btnSignIn.getStyleClass().add("btn");
        tfFirstName.setPromptText("First name");
        tfLastName.setPromptText("Last name");
        tfPhoneNumber.setPromptText("Phone number");
        tfJmbg.setPromptText("JMBG");
        tfBankNumber.setPromptText("Bank account");
        tfUsername.setPromptText("Username");
        pfPassword.setPromptText("Password");
        pfConfirm.setPromptText("Confirm password");

        btnSignIn.setOnAction(e -> controller.signInBtnEvent(
                stage,
                tfFirstName,
                tfLastName,
                tfPhoneNumber,
                tfJmbg,
                tfBankNumber,
                tfUsername,
                pfPassword,
                pfConfirm
        ));
    }
}
