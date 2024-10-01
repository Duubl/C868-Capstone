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

            // Incorrect username error
            case 1:
                alert.setTitle(Main.langBundle.getString("Error"));
                alert.setContentText(Main.langBundle.getString("WrongUser"));
                alert.showAndWait();
                break;

            // Incorrect password for user
            case 2:
                alert.setTitle(Main.langBundle.getString("Error"));
                alert.setContentText(Main.langBundle.getString("WrongPass"));
                alert.showAndWait();
                break;

            // Username is blank
            case 3:
                alert.setTitle(Main.langBundle.getString("Error"));
                alert.setContentText(Main.langBundle.getString("BlankUser"));
                alert.showAndWait();
                break;

            // Password is blank
            case 4:
                alert.setTitle(Main.langBundle.getString("Error"));
                alert.setContentText(Main.langBundle.getString("BlankPassword"));
                alert.showAndWait();
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
