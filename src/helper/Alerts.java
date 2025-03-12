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

            // No appointment selected
            case 7:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("NoAppointmentSelected"));
                alert.showAndWait();
                break;

            // Scheduling outside of business hours
            case 8:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("OutsideBusinessHours"));
                alert.showAndWait();
                break;

            // Start date after end date
            case 9:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("InvalidDate"));
                alert.showAndWait();
                break;

            // Start date after end date
            case 10:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("InvalidTime"));
                alert.showAndWait();
                break;

            // Start date after end date
            case 11:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("AppointmentOverlap"));
                alert.showAndWait();
                break;

            // Start date after end date
            case 12:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("ExistingAppointments"));
                alert.showAndWait();
                break;

            // No appointments found within 15 minutes
            case 13:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("NoAppointments"));
                alert.showAndWait();
                break;

            // Search found nothing
            case 14:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("NoFound"));
                alert.showAndWait();
                break;

            // User is signed on
            case 15:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("UserSignedOn"));
                alert.showAndWait();
                break;

            // No user selected
            case 16:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("NoUserSelected"));
                alert.showAndWait();
                break;

            // Passwords don't match
            case 17:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("NoMatch"));
                alert.showAndWait();
                break;

            // No contact selected
            case 18:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("NoContactSelected"));
                alert.showAndWait();
                break;

            // Incorrect email formatting
            case 19:
                alert.setTitle(Main.lang_bundle.getString("Error"));
                alert.setContentText(Main.lang_bundle.getString("EmailFormatting"));
                alert.showAndWait();
                break;
        }
    }

    public static void customMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Main.lang_bundle.getString("Error"));
        alert.setContentText(Main.lang_bundle.getString("AppointmentSoon") + message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
