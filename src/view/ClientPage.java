package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.arrangement.Arrangement;
import models.arrangement.Transport;

public class ClientPage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(20);

        exploreScene(stage, root);

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("css/client.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Travelsphere - client");
        stage.show();
    }

    public void exploreScene(Stage stage, VBox root) {
        root.getChildren().clear();
        menuGUI(root);
        exploreGUI(stage, root);
    }

    public void menuGUI(VBox root) {
        HBox hbMenu = new HBox(400);
        HBox hbNav = new HBox(10);
        HBox hbAcc = new HBox(10);
        hbMenu.getChildren().addAll(hbNav, hbAcc);

        Label lblTitle = new Label("TRAVELSPHERE");
        Button btnArrangements = new Button("Explore");
        Button btnReservations = new Button("View Reservations");
        hbNav.getChildren().addAll(lblTitle, btnArrangements, btnReservations);

        Label lblUser = new Label("marko_markovic");
        ImageView accIcon = new ImageView(new Image("file:img/user.png"));
        hbAcc.getChildren().addAll(lblUser, accIcon);

        lblTitle.getStyleClass().add("title");
        hbMenu.getStyleClass().add("menu");
        accIcon.setFitWidth(50);
        accIcon.setFitHeight(50);
        hbMenu.setAlignment(Pos.CENTER_LEFT);
        hbNav.setAlignment(Pos.CENTER);
        hbAcc.setAlignment(Pos.CENTER);

        root.getChildren().add(hbMenu);

        btnArrangements.setOnAction(e -> {
            btnArrangements.getStyleClass().add("clicked");
            btnReservations.getStyleClass().remove("clicked");
        });

        btnReservations.setOnAction(e -> {
            btnReservations.getStyleClass().add("clicked");
            btnArrangements.getStyleClass().remove("clicked");
        });
    }

    public void exploreGUI(Stage stage, VBox root) {
        HBox hbExplore = new HBox(20);
        VBox vbSidebar = new VBox(20);
        VBox vbList = new VBox();
        hbExplore.getChildren().addAll(vbSidebar, vbList);

        VBox vbSort = new VBox();
        HBox hbSort1 = new HBox();
        RadioButton rb1 = new RadioButton("Price");
        RadioButton rb2 = new RadioButton("Trip date");
        hbSort1.getChildren().addAll(rb1, rb2);
        HBox hbSort2 = new HBox();
        RadioButton rb3 = new RadioButton("Ascending");
        RadioButton rb4 = new RadioButton("Descending");
        hbSort2.getChildren().addAll(rb3, rb4);
        Button btnSort = new Button("Sort");
        vbSort.getChildren().addAll(hbSort1, hbSort2, btnSort);

        ToggleGroup tg1 = new ToggleGroup();
        ToggleGroup tg2 = new ToggleGroup();
        tg1.getToggles().addAll(rb1, rb2);
        tg2.getToggles().addAll(rb3, rb4);

        TextField tfPrice = new TextField();
        TextField tfDestination = new TextField();
        Spinner<Integer> spStarReview = new Spinner<>();
        DatePicker dpDate1 = new DatePicker();
        DatePicker dpDate2 = new DatePicker();
        ChoiceBox<String> cbTransport = new ChoiceBox<>();
        cbTransport.getItems().addAll("Select transport", "Self-transport", "Bus", "Plane");
        ChoiceBox<String> cbRoomType = new ChoiceBox<>();
        cbRoomType.getItems().addAll("Select room type", "Single-room", "Double-room", "Triple-room", "Apartment");
        HBox hbButtons = new HBox();
        Button btnFilter = new Button("Filter");
        Button btnReset = new Button("Reset");
        hbButtons.getChildren().addAll(btnFilter, btnReset);

        vbSidebar.getChildren().addAll(vbSort, tfPrice, tfDestination, spStarReview, dpDate2, dpDate1, cbTransport, cbRoomType, hbButtons);


        Label lblTitle = new Label("Reserve arrangement");
        ListView<Arrangement> lvArrangements = new ListView<>();
        Button btnReserve = new Button("Reserve");
        Label lblInfo = new Label();
        vbList.getChildren().addAll(lblTitle, lvArrangements, btnReserve, lblInfo);


        cbTransport.getSelectionModel().selectFirst();
        cbRoomType.getSelectionModel().selectFirst();
        spStarReview.setPromptText("Star review");
        dpDate1.setPromptText("Trip date");
        dpDate2.setPromptText("Arrival date");
        vbList.setStyle("-fx-padding: 20px; -fx-background-color: white");
        hbExplore.setStyle("-fx-padding: 20px;");
        vbSidebar.getStyleClass().add("sidebar");

        root.getChildren().add(hbExplore);
    }
}
