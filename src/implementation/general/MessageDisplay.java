package implementation.general;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class MessageDisplay {
    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Travelsphere - alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showMessageLabel(Label lbl, String message, boolean success) {
        lbl.setText(message);

        if (success) {
            lbl.getStyleClass().remove("error");
            lbl.getStyleClass().add("success");
        } else {
            lbl.getStyleClass().remove("success");
            lbl.getStyleClass().add("error");
        }
    }
}
