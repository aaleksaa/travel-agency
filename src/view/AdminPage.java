package view;

import controllers.AdminController;
import implementation.admin.AdminViewer;
import implementation.client.ArrangementViewer;
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
import models.entities.Admin;
import models.entities.Arrangement;
import models.entities.Client;
import models.entities.Reservation;

import java.time.LocalDate;

public class AdminPage extends Application {
    private final Admin admin;
    private AdminController controller;

    public AdminPage(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void start(Stage stage) throws Exception {
        controller = new AdminController();

        HBox root = new HBox(20);
        root.setId("root");

        setupScene(root, stage, 1);
        Scene scene = new Scene(root, 1100, 750);
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Travelsphere - Admin");
        stage.show();
    }

    private void sidebarGUI(HBox root, Stage stage) {
        VBox vbSidebar = new VBox(50);
        VBox vbMenu = new VBox(25);

        ImageView img = new ImageView(new Image("file:img/logo.png"));
        Button btnProfile = new Button("View profile");
        Button btnAdmins = new Button("Admins");
        Button btnReservations = new Button("View reservations");
        Button btnAddArrangement = new Button("Add arrangement");
        Button btnCancelArrangement = new Button("Cancel arrangement");
        Button btnLogout = new Button("Logout");

        vbSidebar.getChildren().addAll(img, vbMenu);
        vbMenu.getChildren().addAll(btnProfile, btnAdmins, btnReservations, btnAddArrangement, btnCancelArrangement, btnLogout);
        root.getChildren().add(vbSidebar);

        img.setFitWidth(150);
        img.setFitHeight(150);
        vbSidebar.getStyleClass().add("sidebar");
        btnAdmins.getStyleClass().add("btn1");
        btnProfile.getStyleClass().add("btn1");
        btnAddArrangement.getStyleClass().add("btn1");
        btnCancelArrangement.getStyleClass().add("btn1");
        btnReservations.getStyleClass().add("btn1");
        btnLogout.getStyleClass().add("btn2");

        btnAdmins.setOnAction(e -> setupScene(root, stage, 1));
        btnReservations.setOnAction(e -> setupScene(root, stage, 2));
        btnAddArrangement.setOnAction(e -> setupScene(root, stage, 3));
        btnCancelArrangement.setOnAction(e -> setupScene(root, stage, 4));
        btnProfile.setOnAction(e -> setupScene(root, stage, 5));
        btnLogout.setOnAction(e -> Navigation.toLoginPage(stage));
    }

    private void setupScene(HBox root, Stage stage, int scene) {
        switch (scene) {
            case 1:
                root.getChildren().clear();
                sidebarGUI(root, stage);
                adminsGUI(root);
                break;
            case 2:
                root.getChildren().clear();
                sidebarGUI(root, stage);
                reservationsGUI(root);
                break;
            case 3:
                root.getChildren().clear();
                sidebarGUI(root, stage);
                addArrangementGUI(root);
                break;
            case 4:
                root.getChildren().clear();
                sidebarGUI(root, stage);
                cancelArrangementGUI(root);
                break;
            case 5:
                profilePopup(stage);
                break;
        }
    }

    private void cancelArrangementGUI(HBox root) {
        VBox vbCancelArrangement = new VBox(20);

        Text txtTitle = new Text("Cancel arrangement");
        ListView<Arrangement> lvArrangements = new ListView<>();
        Button btnCancel = new Button("Cancel");
        Label lblMessage = new Label();

        lvArrangements.getItems().addAll(ArrangementViewer.arrangementsOnOffer(controller.getAgency().getArrangements()));
        vbCancelArrangement.getChildren().addAll(txtTitle, lvArrangements, btnCancel, lblMessage);
        root.getChildren().add(vbCancelArrangement);

        lvArrangements.setPrefWidth(700);
        vbCancelArrangement.getStyleClass().add("rightSide");
        txtTitle.getStyleClass().add("title");
        btnCancel.getStyleClass().add("btn2");

        btnCancel.setOnAction(e -> controller.cancelBtnEvent(
                lvArrangements.getSelectionModel().getSelectedItem(),
                lblMessage,
                lvArrangements
        ));
    }

    private void addArrangementGUI(HBox root) {
        HBox hbAddArrangement = new HBox(300);
        VBox vbTrip = new VBox(20);
        VBox vbTrip2 = new VBox(20);

        Text txtTrip = new Text("Add trip");
        TextField tfTripName = new TextField();
        TextField tfDestination = new TextField();
        TextField tfArrangementPrice = new TextField();
        TextField tfAccommodationName = new TextField();
        TextField tfStarReview = new TextField();
        TextField tfPricePerNight = new TextField();
        DatePicker dpTripDate = new DatePicker();
        DatePicker dpArrivalDate = new DatePicker();
        ChoiceBox<String> cbTransport = new ChoiceBox<>();
        ChoiceBox<String> cbRoomType = new ChoiceBox<>();
        Button btnAddTrip = new Button("Add");

        Text txtTrip2 = new Text("Add one day trip");
        TextField tfTripName2 = new TextField();
        TextField tfDestination2 = new TextField();
        TextField tfPrice = new TextField();
        DatePicker dpTripDate2 = new DatePicker();
        Button btnAddOneDayTrip = new Button("Add");

        cbTransport.getItems().addAll("Select transport", "Bus", "Plane", "Self-transport");
        cbRoomType.getItems().addAll("Select room type", "Single-room", "Double-room", "Triple-room", "Apartment");
        hbAddArrangement.getChildren().addAll(vbTrip, vbTrip2);
        vbTrip.getChildren().addAll(
                txtTrip,
                tfTripName,
                tfDestination,
                cbTransport,
                dpTripDate,
                dpArrivalDate,
                tfArrangementPrice,
                tfAccommodationName,
                tfStarReview,
                cbRoomType,
                tfPricePerNight,
                btnAddTrip
        );
        vbTrip2.getChildren().addAll(
                txtTrip2,
                tfTripName2,
                tfDestination2,
                dpTripDate2,
                tfPrice,
                btnAddOneDayTrip
        );
        root.getChildren().add(hbAddArrangement);

        cbTransport.getSelectionModel().selectFirst();
        cbRoomType.getSelectionModel().selectFirst();
        cbTransport.getSelectionModel().selectFirst();
        cbRoomType.getSelectionModel().selectFirst();
        hbAddArrangement.getStyleClass().add("rightSide");
        txtTrip.getStyleClass().add("title");
        txtTrip2.getStyleClass().add("title");
        btnAddTrip.getStyleClass().add("btn2");
        btnAddOneDayTrip.getStyleClass().add("btn2");
        dpTripDate2.setPromptText("Trip date");
        dpTripDate.setPromptText("Trip date");
        dpArrivalDate.setPromptText("Arrival date");
        tfTripName.setPromptText("Trip name");
        tfTripName2.setPromptText("Trip name");
        tfDestination.setPromptText("Destination");
        tfDestination2.setPromptText("Destination");
        tfArrangementPrice.setPromptText("Arrangement price");
        tfPrice.setPromptText("Arrangement price");
        tfAccommodationName.setPromptText("Accommodation name");
        tfStarReview.setPromptText("Accommodation star review");
        tfPricePerNight.setPromptText("Price per night");

        dpTripDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || !date.isAfter(LocalDate.now()));
            }
        });

        dpTripDate2.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || !date.isAfter(LocalDate.now()));
            }
        });

        btnAddOneDayTrip.setOnAction(e -> controller.addOneDayTripEvent(
                tfTripName2,
                tfDestination2,
                dpTripDate2,
                tfPrice
        ));

        btnAddTrip.setOnAction(e -> controller.addTripEvent(
                tfTripName,
                tfDestination,
                cbTransport,
                dpTripDate,
                dpArrivalDate,
                tfArrangementPrice,
                tfAccommodationName,
                tfStarReview,
                cbRoomType,
                tfPricePerNight
        ));
    }

    private void adminsGUI(HBox root) {
        VBox vbAdmins = new VBox(10);
        HBox hbInfo = new HBox(420);
        VBox vbAdd = new VBox(15);

        Label lblAdmins = new Label("Admins");
        Label lblCounter = new Label(controller.printAdminCounter());

        ListView<Admin> lv = new ListView<>();

        Label lblAdd = new Label("Add new admin");
        TextField tfFirstName = new TextField();
        TextField tfLastName = new TextField();
        TextField tfUsername = new TextField();
        Button btnAdd = new Button("Add");

        lv.getItems().addAll(AdminViewer.getAdmins(controller.getAgency().getUsers()));
        hbInfo.getChildren().addAll(lblAdmins, lblCounter);
        vbAdd.getChildren().addAll(lblAdd, tfFirstName, tfLastName, tfUsername, btnAdd);
        vbAdmins.getChildren().addAll(hbInfo, lv, vbAdd);
        root.getChildren().add(vbAdmins);

        lv.setPrefWidth(700);
        lv.setPrefHeight(300);
        vbAdmins.getStyleClass().add("rightSide");
        lblAdd.getStyleClass().add("title");
        lblAdmins.getStyleClass().add("title");
        lblCounter.getStyleClass().add("title");
        btnAdd.getStyleClass().add("btn2");
        tfFirstName.setPromptText("First name");
        tfLastName.setPromptText("Last name");
        tfUsername.setPromptText("Username");

        btnAdd.setOnAction(e -> controller.addAdminEvent(tfFirstName, tfLastName, tfUsername, lblCounter, lv));
    }

    private void reservationsGUI(HBox root) {
        VBox vbReservations = new VBox(15);
        HBox hbRevenue = new HBox(250);
        VBox vbClients = new VBox(10);

        Label lblRevenue = new Label(controller.printRevenue());
        Label lblToPay = new Label(controller.printTotalRemaining());

        ListView<Reservation> lvReservations = new ListView<>();
        Label lblInfo = new Label();

        Label lblClients = new Label("This arrangement is reserved by:");
        ListView<Client> lvClients = new ListView<>();

        lvReservations.getItems().addAll(controller.getAgency().getReservations());
        hbRevenue.getChildren().addAll(lblRevenue, lblToPay);
        vbClients.getChildren().addAll(lblClients, lvClients);
        vbReservations.getChildren().addAll(hbRevenue, lvReservations, lblInfo, vbClients);
        root.getChildren().add(vbReservations);

        lvReservations.setPrefWidth(700);
        vbReservations.getStyleClass().add("rightSide");
        lblRevenue.getStyleClass().add("title");
        lblToPay.getStyleClass().add("title");
        lblClients.getStyleClass().add("title2");
        lblInfo.getStyleClass().add("title2");
        vbClients.setVisible(false);

        lvReservations.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.reservationItemEvent(newSelection, lblInfo, lvClients);
            vbClients.setVisible(true);
        });
    }

    private void profilePopup(Stage stage) {
        Popup popup = new Popup();
        VBox vb = new VBox(10);

        Label lbl = new Label("Your profile");
        Text txt = new Text(admin.printInfo());
        Button btn = new Button("Change password");

        vb.getChildren().addAll(lbl, txt, btn);
        popup.getContent().add(vb);
        popup.show(stage);

        vb.setAlignment(Pos.CENTER);
        vb.setId("popup");
        btn.getStyleClass().add("btn2");
        lbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0C254B");

        btn.setOnAction(e -> controller.changePasswordEvent(stage, popup, admin));
    }
}
