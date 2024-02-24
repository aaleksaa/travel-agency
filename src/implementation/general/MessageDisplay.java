package implementation.general;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * The MessageDisplay class provides methods for displaying messages and updating labels.
 */
public class MessageDisplay {
    /**
     * Displays an alert with the given message and type.
     *
     * @param message the message to be displayed.
     * @param type    the type of the alert.
     */
    public static void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Travelsphere - alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Updates a label with the given message and style.
     *
     * @param lbl     the label to be updated.
     * @param message the message to be set on the label.
     * @param success a boolean indicating whether it's a success message or not.
     */
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
