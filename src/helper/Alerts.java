package helper;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class Alerts implements Initializable {

    /**
     * Gets an error and pushes it to the screen. Uses either English or French.
     * @param error an integer value associated with a given error.
     */

    public static void getError(int error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch(error) {

            // Incorrect login credentials
            case 1:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("IncorrectLogin"));
                alert.showAndWait();
                break;

            // Username is blank
            case 2:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("BlankUser"));
                alert.showAndWait();
                break;

            // Password is blank
            case 3:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("BlankPassword"));
                alert.showAndWait();
                break;

            // Fail loading customers
            case 4:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("LoadCustomerFail"));
                alert.showAndWait();
                break;

            // No customer selected
            case 5:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("NoCustomerSelected"));
                alert.showAndWait();
                break;

            // One or more empty fields
            case 6:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("EmptyFields"));
                alert.showAndWait();
                break;
        }
    }

    // Change later
    public static void getConfirm(int c) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch (c) {
            case 1:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("IncorrectLogin"));
                alert.showAndWait();
                break;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
