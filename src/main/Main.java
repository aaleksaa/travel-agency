package main;

import implementation.general.Navigation;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Navigation.toLoginPage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
