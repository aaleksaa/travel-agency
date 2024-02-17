package view;

import implementation.general.Navigation;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.entities.BankAccount;
import models.entities.Client;

public class ClientPage extends Application {
    private Client client;
    private BankAccount bankAccount;

    public ClientPage(Client client, BankAccount bankAccount) {
        this.client = client;
        this.bankAccount = bankAccount;
    }
    @Override
    public void start(Stage stage) throws Exception {
        HBox root = new HBox(20);

        setupScene(root, stage, 3);

        Scene scene = new Scene(root, 1100, 650);
        scene.getStylesheets().add(getClass().getResource("css/client.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Travelsphere - client");
        stage.show();
    }

    private void setupScene(HBox root, Stage stage, int scene) {
        root.getChildren().clear();
        sidebarGUI(root, stage);

        switch (scene) {
            case 3: reservationsGUI(root, stage); break;
        }
    }

    private void sidebarGUI(HBox root, Stage stage) {
        VBox vbSidebar = new VBox(50);
        ImageView img = new ImageView(new Image("file:img/logo.png"));
        VBox vbMenu = new VBox(25);
        vbSidebar.getChildren().addAll(img, vbMenu);

        Button btnInfo = new Button("Vaš nalog");
        Button btnArrangements = new Button("Lista putovanja/izleta");
        Button btnReservations = new Button("Vaše rezervacije");
        Button btnLogout = new Button("Odjavi se");
        vbMenu.getChildren().addAll(btnInfo, btnArrangements, btnReservations, btnLogout);

        vbSidebar.getStyleClass().add("sidebar");
        img.setFitWidth(150);
        img.setFitHeight(150);
        btnInfo.getStyleClass().add("btn1");
        btnArrangements.getStyleClass().add("btn1");
        btnReservations.getStyleClass().add("btn1");
        btnLogout.getStyleClass().add("btn2");

        btnLogout.setOnAction(e -> Navigation.toLoginPage(stage));
//        btnInfo.setOnAction(e -> setupScene(root, stage, 1));
//        btnArrangements.setOnAction(e -> setupScene(root, stage, 2));
//        btnReservations.setOnAction(e -> setupScene(root, stage, 3));

        root.getChildren().add(vbSidebar);
    }

    private void reservationsGUI(HBox root, Stage stage) {
        VBox vbReservations = new VBox(20);

        HBox hbFilter = new HBox(15);
        RadioButton rb1 = new RadioButton("Sve");
        RadioButton rb2 = new RadioButton("Aktivne");
        RadioButton rb3 = new RadioButton("Protekle");
        RadioButton rb4 = new RadioButton("Otkazane");
        ToggleGroup tg = new ToggleGroup();
        tg.getToggles().addAll(rb1, rb2, rb3, rb4);
        hbFilter.getChildren().addAll(rb1, rb2, rb3, rb4);

        HBox hbInfo = new HBox(230);
        Text txtMoney = new Text("Ukupno potroseno:");
        Text txtMoney2 = new Text("Ukupno za uplatu:");
        hbInfo.getChildren().addAll(txtMoney, txtMoney2);

        ListView<String> lvReservations = new ListView<>();
//        lvReservations.getItems().addAll(
//                ReservationViewer.getAllReservations(controller.getAgency().getReservations(), client)
//        );

        HBox hbBtns = new HBox(5);
        Button btnPay = new Button("Uplati");
        Button btnCancel = new Button("Otkaži");
        Label lblError = new Label();
        hbBtns.getChildren().addAll(btnPay, btnCancel, lblError);

        Label lblInfo = new Label();

        rb1.setSelected(true);
        hbBtns.setAlignment(Pos.CENTER_LEFT);
        txtMoney.getStyleClass().add("title");
        txtMoney2.getStyleClass().add("title");
        vbReservations.getStyleClass().add("rightSide");
        btnPay.getStyleClass().add("btn2");
        btnCancel.getStyleClass().add("btn2");
        btnPay.setStyle("-fx-pref-width: 180px");
        btnCancel.setStyle("-fx-pref-width: 180px");
        lblInfo.setStyle("-fx-text-fill: #cc5803; -fx-font-size: 15px");

//        rb1.setOnAction(e -> {
//            if (rb1.isSelected()) {
//                lvReservations.getItems().setAll(
//                        ReservationViewer.getAllReservations(controller.getAgency().getReservations(), client)
//                );
//                lblInfo.setText("");
//            }
//        });
//
//        rb2.setOnAction(e -> {
//            if (rb2.isSelected()) {
//                lvReservations.getItems().setAll(
//                        ReservationViewer.getFilteredReservations(
//                                controller.getAgency().getReservations(), client.getId(), ReservationType.ACTIVE
//                        )
//                );
//                lblInfo.setText("");
//            }
//        });
//
//
//        rb3.setOnAction(e -> {
//            if (rb3.isSelected()) {
//                lvReservations.getItems().setAll(
//                        ReservationViewer.getFilteredReservations(
//                                controller.getAgency().getReservations(), client.getId(), ReservationType.PAST
//                        )
//                );
//                lblInfo.setText("");
//            }
//        });
//
//        rb4.setOnAction(e -> {
//            if (rb4.isSelected()) {
//                lvReservations.getItems().setAll(
//                        ReservationViewer.getFilteredReservations(
//                                controller.getAgency().getReservations(), client.getId(), ReservationType.CANCELED
//                        )
//                );
//                lblInfo.setText("");
//            }
//        });

        vbReservations.getChildren().addAll(hbFilter, hbInfo, lvReservations, hbBtns, lblInfo);
        root.getChildren().add(vbReservations);

//        lvReservations.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null && newSelection.isActiveReservation())
//                lblInfo.setText("Za uplatu ove rezervacije je ostalo: " + newSelection.leftToPay());
//        });



        //btnCancel.setOnAction(e -> controller.cancelBtnEvent(lvReservations.getSelectionModel().getSelectedItem(), lblError, bankAccount));
       // btnPay.setOnAction(e -> controller.payBtnEvent(stage,  lvReservations.getSelectionModel().getSelectedItem(), lblError, bankAccount, client, txtMoney, txtMoney2, lblInfo));
    }
}
