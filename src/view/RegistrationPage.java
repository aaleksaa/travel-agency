package view;

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
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        registrationGUI(stage, root);

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Travelsphere - SignIn");
        stage.show();
    }

    public void registrationGUI(Stage stage, VBox root) {
        VBox vbRegistration = new VBox(20);
        HBox hbForm = new HBox(20);
        VBox vbLeft = new VBox(10);
        VBox vbRight = new VBox(10);
        hbForm.getChildren().addAll(vbLeft, vbRight);
        ImageView logo = new ImageView(new Image("file:img/logo.png"));


        TextField tfFirstName = new TextField();
        TextField tfLastName = new TextField();
        TextField tfPhoneNumber = new TextField();
        TextField tfJmbg = new TextField();
        vbLeft.getChildren().addAll(tfFirstName, tfLastName, tfPhoneNumber, tfJmbg);

        TextField tfBankNumber = new TextField();
        TextField tfUsername = new TextField();
        PasswordField pfPassword = new PasswordField();
        PasswordField pfConfirm = new PasswordField();
        vbRight.getChildren().addAll(tfBankNumber, tfUsername, pfPassword, pfConfirm);

        Button btnSignIn = new Button("Sign In");
        vbRegistration.getChildren().addAll(logo, hbForm, btnSignIn);

        vbRegistration.getStyleClass().add("registration");
        vbRegistration.setAlignment(Pos.TOP_CENTER);
        logo.setFitHeight(150);
        logo.setFitWidth(150);
        btnSignIn.getStyleClass().add("btn");
        tfFirstName.setPromptText("First name");
        tfLastName.setPromptText("Last name");
        tfPhoneNumber.setPromptText("Phone number");
        tfJmbg.setPromptText("JMBG");
        tfBankNumber.setPromptText("Bank account");
        tfUsername.setPromptText("Username");
        pfPassword.setPromptText("Password");
        pfConfirm.setPromptText("Confirm password");

        root.getChildren().add(vbRegistration);
    }
}
