package view;

import controllers.AdminController;
import implementation.client.ArrangementViewer;
import implementation.general.Navigation;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.entities.Admin;
import models.entities.Arrangement;
import models.entities.Client;
import models.entities.Reservation;

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

        setupScene(root, stage, 4);
        Scene scene = new Scene(root, 1100, 750);
        scene.getStylesheets().add(getClass().getResource("css/admin.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Travelsphere - Admin");
        stage.show();
    }

    private void sidebarGUI(HBox root, Stage stage) {
        VBox vbSidebar = new VBox(50);
        ImageView img = new ImageView(new Image("file:img/logo.png"));
        VBox vbMenu = new VBox(25);
        vbSidebar.getChildren().addAll(img, vbMenu);

        Button btnAdminPanel = new Button("Admin Panel");
        Button btnReservations = new Button("Reservations List");
        Button btnAddArrangement = new Button("Add Arrangement");
        Button btnCancelArrangement = new Button("Cancel Arrangement");
        Button btnLogout = new Button("Logout");
        vbMenu.getChildren().addAll(btnAdminPanel, btnReservations, btnAddArrangement, btnCancelArrangement, btnLogout);

        vbSidebar.getStyleClass().add("sidebar");
        img.setFitWidth(150);
        img.setFitHeight(150);
        btnAdminPanel.getStyleClass().add("btn1");
        btnAddArrangement.getStyleClass().add("btn1");
        btnCancelArrangement.getStyleClass().add("btn1");
        btnReservations.getStyleClass().add("btn1");
        btnLogout.getStyleClass().add("btn2");

//        btnAdminPanel.setOnAction(e -> setupScene(root, stage, 1));
        btnReservations.setOnAction(e -> setupScene(root, stage, 2));
//        btnAddArrangement.setOnAction(e -> setupScene(root, stage, 3));
        btnCancelArrangement.setOnAction(e -> setupScene(root, stage, 4));
        btnLogout.setOnAction(e -> Navigation.toLoginPage(stage));

        root.getChildren().add(vbSidebar);
    }

    private void setupScene(HBox root, Stage stage, int scene) {
        root.getChildren().clear();
        sidebarGUI(root, stage);

        switch (scene) {
//            case 1: adminsGUI(root); break;
            case 2: reservationsGUI(root); break;
//            case 3: addArrangementGUI(root); break;
            case 4: cancelArrangementGUI(root); break;
        }
    }

    private void cancelArrangementGUI(HBox root) {
        VBox vbCancelArrangement = new VBox(20);

        Text txtTitle = new Text("Cancel arrangement");
        ListView<Arrangement> lvArrangements = new ListView<>();
        Button btnCancel = new Button("Cancel");
        Label lblMessage = new Label();

        vbCancelArrangement.getChildren().addAll(txtTitle, lvArrangements, btnCancel, lblMessage);

        vbCancelArrangement.getStyleClass().add("rightSide");
        txtTitle.getStyleClass().add("title");
        lvArrangements.setPrefWidth(700);
        lvArrangements.setPrefHeight(350);
        btnCancel.getStyleClass().add("btn2");
        lvArrangements.getItems().addAll(ArrangementViewer.arrangementsOnOffer(controller.getAgency().getArrangements()));

//        btnCancel.setOnAction(e -> {
//            try {
//                controller.cancelBtnClick(lvArrangements.getSelectionModel().getSelectedItem(), lblMessage, lvArrangements);
//            } catch (SQLException ex) {
//                MessageDisplayer.showInformationAlert(Agency.DATABASE_ERROR_MESSAGE);
//            }
//        });

        root.getChildren().add(vbCancelArrangement);
    }

    private void reservationsGUI(HBox root) {
        VBox vbReservations = new VBox(15);

        HBox hbRevenue = new HBox(250);
        Label lblRevenue = new Label(controller.showRevenue());
        Label lblToPay = new Label(controller.showTotalRemaining());
        hbRevenue.getChildren().addAll(lblRevenue, lblToPay);

        ListView<Reservation> lvReservations = new ListView<>();
        lvReservations.getItems().addAll(controller.getAgency().getReservations());
        Label lblInfo = new Label();

        VBox vbClients = new VBox(10);
        Label lblClients = new Label("This arrangement is reserved by:");
        ListView<Client> lvClients = new ListView<>();
        vbClients.getChildren().addAll(lblClients, lvClients);

        vbReservations.getChildren().addAll(hbRevenue, lvReservations, lblInfo, vbClients);

        lblRevenue.getStyleClass().add("title");
        lblToPay.getStyleClass().add("title");
        vbReservations.getStyleClass().add("rightSide");
        lvReservations.setPrefWidth(700);
        vbClients.setVisible(false);
        lblClients.getStyleClass().add("title2");
        lblInfo.getStyleClass().add("title2");


        lvReservations.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.reservationItemEvent(newSelection, lblInfo, lvClients);
            vbClients.setVisible(true);
        });

        root.getChildren().add(vbReservations);
    }
}
