package view;

import controllers.ClientController;
import implementation.client.ArrangementViewer;
import implementation.client.ReservationManager;
import implementation.general.LogAlert;
import implementation.general.MessageDisplay;
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
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.entities.Arrangement;
import models.entities.BankAccount;
import models.entities.Client;
import models.entities.Reservation;
import models.enums.ReservationType;
import models.enums.RoomType;
import models.enums.Transport;

import java.time.LocalDate;

public class ClientPage extends Application {
    private final Client client;
    private final BankAccount bankAccount;
    private ClientController controller;

    public ClientPage(Client client, BankAccount bankAccount) {
        this.client = client;
        this.bankAccount = bankAccount;
    }

    @Override
    public void start(Stage stage) throws Exception {
        controller = new ClientController();

        ReservationManager.returnMoneyCanceledReservations(
                controller.getAgency().getReservations(),
                client,
                bankAccount,
                controller.getAgency().getAgencyBankAccount(),
                LocalDate.parse(LogAlert.updateLogs(client)),
                LocalDate.now()
        );
        ReservationManager.markReservations(controller.getAgency().getReservations(), client);

        HBox root = new HBox(20);
        root.setId("root");
        setupScene(root, stage, 2);

        Scene scene = new Scene(root, 1100, 750);
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Travelsphere - client");
        stage.show();

        if (ReservationManager.alertClientToCompletePayment(controller.getAgency().getReservations(), client))
            MessageDisplay.showAlert(ReservationManager.ALERT_CLIENT_TO_PAY, Alert.AlertType.INFORMATION);

        if (LogAlert.clientAlert(client))
            MessageDisplay.showAlert("Admin canceled arrangement!\nBalance: " + bankAccount.getBalance(), Alert.AlertType.INFORMATION);
    }

    private void setupScene(HBox root, Stage stage, int scene) {
        switch (scene) {
            case 1:
                profilePopup(stage);
                break;
            case 2:
                root.getChildren().clear();
                sidebarGUI(root, stage);
                arrangementsGUI(root, stage);
                break;
            case 3:
                root.getChildren().clear();
                sidebarGUI(root, stage);
                reservationsGUI(root, stage);
                break;
        }
    }

    private void sidebarGUI(HBox root, Stage stage) {
        VBox vbSidebar = new VBox(50);
        VBox vbMenu = new VBox(25);

        ImageView img = new ImageView(new Image("file:img/logo.png"));
        Button btnProfile = new Button("View profile");
        Button btnArrangements = new Button("View arrangements");
        Button btnReservations = new Button("Your reservations");
        Button btnLogout = new Button("Logout");

        vbMenu.getChildren().addAll(btnProfile, btnArrangements, btnReservations, btnLogout);
        vbSidebar.getChildren().addAll(img, vbMenu);
        root.getChildren().add(vbSidebar);

        img.setFitWidth(150);
        img.setFitHeight(150);
        vbSidebar.getStyleClass().add("sidebar");
        btnProfile.getStyleClass().add("btn1");
        btnArrangements.getStyleClass().add("btn1");
        btnReservations.getStyleClass().add("btn1");
        btnLogout.getStyleClass().add("btn2");

        btnLogout.setOnAction(e -> Navigation.toLoginPage(stage));
        btnProfile.setOnAction(e -> setupScene(root, stage, 1));
        btnArrangements.setOnAction(e -> setupScene(root, stage, 2));
        btnReservations.setOnAction(e -> setupScene(root, stage, 3));
    }

    private void arrangementsGUI(HBox root, Stage stage) {
        VBox vbArrangements = new VBox(20);
        HBox hbSort = new HBox(20);
        VBox vbFilter = new VBox(15);
        HBox hb1 = new HBox(15);
        HBox hb2 = new HBox(30);
        HBox hbReserve = new HBox(10);

        RadioButton rb1 = new RadioButton("Trip date");
        RadioButton rb2 = new RadioButton("Price");
        ChoiceBox<String> cbSort = new ChoiceBox<>();
        Button btnSort = new Button("Sort");
        ToggleGroup tg = new ToggleGroup();

        TextField tfPrice = new TextField();
        TextField tfDestination = new TextField();
        TextField tfStarReview = new TextField();
        DatePicker dpTrip = new DatePicker();
        DatePicker dpArrival = new DatePicker();

        ChoiceBox<String> cbTransport = new ChoiceBox<>();
        ChoiceBox<String> cbRoomType = new ChoiceBox<>();
        Button btnFilter = new Button("Filter");
        Button btnReset = new Button("Reset");

        ListView<Arrangement> lv = new ListView<>();

        Button btnReserve = new Button("Reserve");
        Label lblMessage = new Label();

        cbSort.getItems().addAll("Ascending", "Descending");
        cbTransport.getItems().addAll("Select transport", "Bus", "Plane", "Self-transport");
        cbRoomType.getItems().addAll("Select room type", "Single-room", "Double-room", "Triple-room", "Apartment");
        lv.getItems().addAll(ArrangementViewer.arrangementsOnOffer(controller.getAgency().getArrangements()));
        tg.getToggles().addAll(rb1, rb2);
        hbSort.getChildren().addAll(rb1, rb2, cbSort, btnSort);
        hb1.getChildren().addAll(tfPrice, tfDestination, tfStarReview, dpTrip, dpArrival);
        hb2.getChildren().addAll(cbRoomType, cbTransport, btnFilter, btnReset);
        vbFilter.getChildren().addAll(hb1, hb2);
        hbReserve.getChildren().addAll(btnReserve, lblMessage);
        vbArrangements.getChildren().addAll(hbSort, vbFilter, lv, hbReserve);
        root.getChildren().add(vbArrangements);

        hbReserve.setAlignment(Pos.CENTER_LEFT);
        hb1.setAlignment(Pos.CENTER_LEFT);
        hb2.setAlignment(Pos.CENTER_LEFT);
        hbSort.setAlignment(Pos.CENTER_LEFT);
        btnSort.setPrefWidth(140);
        btnFilter.setPrefWidth(140);
        btnReserve.setPrefWidth(140);
        btnReset.setPrefWidth(140);
        rb1.setSelected(true);
        cbSort.getSelectionModel().selectFirst();
        cbTransport.getSelectionModel().selectFirst();
        cbRoomType.getSelectionModel().selectFirst();
        vbArrangements.getStyleClass().add("rightSide");
        btnReserve.getStyleClass().add("btn2");
        btnSort.getStyleClass().add("btn2");
        btnFilter.getStyleClass().add("btn2");
        btnReset.getStyleClass().add("btn2");
        tfPrice.setPromptText("Price");
        tfDestination.setPromptText("Destination");
        tfStarReview.setPromptText("Star review");

        btnSort.setOnAction(e -> {
            if (rb1.isSelected())
                ArrangementViewer.sortListView(lv, 1, cbSort.getValue());
            else
                ArrangementViewer.sortListView(lv, 2, cbSort.getValue());
        });

        btnFilter.setOnAction(e -> lv.getItems().setAll(ArrangementViewer.filterArrangements(
                controller.getAgency().getArrangements(),
                tfPrice.getText(),
                tfDestination.getText(),
                tfStarReview.getText(),
                RoomType.fromString(cbRoomType.getValue()),
                Transport.fromString(cbTransport.getValue()),
                dpTrip.getValue(),
                dpArrival.getValue()
        )));

        btnReserve.setOnAction(e -> controller.reservationBtnEvent(
                stage,
                lblMessage,
                lv.getSelectionModel().getSelectedItem(),
                client,
                bankAccount
        ));

        TextInputControl[] inputs = {tfPrice, tfDestination, tfStarReview};
        btnReset.setOnAction(e -> ArrangementViewer.reset(
                controller.getAgency().getArrangements(),
                lv,
                inputs,
                dpTrip,
                dpArrival,
                cbTransport,
                cbRoomType
        ));
    }

    private void reservationsGUI(HBox root, Stage stage) {
        VBox vbReservations = new VBox(20);
        HBox hbFilter = new HBox(15);
        HBox hbInfo = new HBox(300);
        HBox hbBtns = new HBox(5);

        RadioButton rb1 = new RadioButton("All");
        RadioButton rb2 = new RadioButton("Active");
        RadioButton rb3 = new RadioButton("Past");
        RadioButton rb4 = new RadioButton("Canceled");
        ToggleGroup tg = new ToggleGroup();

        Text txtMoney = new Text(controller.printTotalSpent(client));
        Text txtMoney2 = new Text(controller.printTotalRemaining(client));

        ListView<Reservation> lvReservations = new ListView<>();
        Label lblInfo = new Label();

        Button btnPay = new Button("Pay");
        Button btnCancel = new Button("Cancel");
        Label lblMessage = new Label();

        lvReservations.getItems().addAll(
                ReservationManager.getAllReservations(controller.getAgency().getReservations(), client)
        );
        tg.getToggles().addAll(rb1, rb2, rb3, rb4);
        hbFilter.getChildren().addAll(rb1, rb2, rb3, rb4);
        hbInfo.getChildren().addAll(txtMoney, txtMoney2);
        hbBtns.getChildren().addAll(btnPay, btnCancel, lblMessage);
        vbReservations.getChildren().addAll(hbFilter, hbInfo, lvReservations, hbBtns, lblInfo);
        root.getChildren().add(vbReservations);

        hbBtns.setAlignment(Pos.CENTER_LEFT);
        rb1.setSelected(true);
        vbReservations.getStyleClass().add("rightSide");
        txtMoney.getStyleClass().add("title");
        txtMoney2.getStyleClass().add("title");
        lblInfo.getStyleClass().add("title2");
        btnPay.getStyleClass().add("btn2");
        btnCancel.getStyleClass().add("btn2");
        btnPay.setStyle("-fx-pref-width: 180px");
        btnCancel.setStyle("-fx-pref-width: 180px");

        rb1.setOnAction(e -> {
            if (rb1.isSelected()) {
                lvReservations.getItems().setAll(
                        ReservationManager.getAllReservations(controller.getAgency().getReservations(), client)
                );
                lblInfo.setText("");
            }
        });

        rb2.setOnAction(e -> {
            if (rb2.isSelected()) {
                lvReservations.getItems().setAll(
                        ReservationManager.getFilteredReservations(
                                controller.getAgency().getReservations(), client, ReservationType.ACTIVE
                        )
                );
                lblInfo.setText("");
            }
        });

        rb3.setOnAction(e -> {
            if (rb3.isSelected()) {
                lvReservations.getItems().setAll(
                        ReservationManager.getFilteredReservations(
                                controller.getAgency().getReservations(), client, ReservationType.PAST
                        )
                );
                lblInfo.setText("");
            }
        });

        rb4.setOnAction(e -> {
            if (rb4.isSelected()) {
                lvReservations.getItems().setAll(
                        ReservationManager.getFilteredReservations(
                                controller.getAgency().getReservations(), client, ReservationType.CANCELED
                        )
                );
                lblInfo.setText("");
            }
        });

        lvReservations.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && newSelection.getReservationType() == ReservationType.ACTIVE)
                lblInfo.setText("Remaining for this reservation: " + newSelection.unpaidAmount());
        });


        btnCancel.setOnAction(e -> controller.cancelBtnEvent(
                lvReservations.getSelectionModel().getSelectedItem(),
                bankAccount,
                lblMessage)
        );
        btnPay.setOnAction(e -> controller.payBtnEvent(
                stage,
                lvReservations.getSelectionModel().getSelectedItem(),
                client,
                bankAccount,
                lblMessage,
                lblInfo,
                txtMoney,
                txtMoney2)
        );
    }

    private void profilePopup(Stage stage) {
        Popup popup = new Popup();
        VBox vb = new VBox(10);
        Label lbl = new Label("Your profile");
        Text txt = new Text(client.printInfo());

        Button btn = new Button("Change password");

        vb.getChildren().addAll(lbl, txt, btn);
        popup.getContent().add(vb);
        popup.show(stage);

        vb.setId("popup");
        vb.setAlignment(Pos.CENTER);
        btn.getStyleClass().add("btn2");
        lbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0C254B");

        btn.setOnAction(e -> controller.changePasswordEvent(stage, popup, client));
    }
}
