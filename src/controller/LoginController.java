package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;

import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private PasswordField password_text_box;
    @FXML private TextField username_text_box;
    @FXML private Label location_code;
    @FXML private Button sign_in_button;

    private String username = "User";
    private String password = "passw0rd!";

    /**
     * Checks the currently entered username.
     * @return true when a proper username has been entered, false if the username text box is empty or invalid.
     */

    public boolean checkUser() {
        if (username_text_box.getText().isEmpty()) {
            Alert none = new Alert(Alert.AlertType.ERROR);
            none.setTitle("Hol' Up!");
            none.setContentText("No user found with the provided username!");
            none.showAndWait();
            return false;
        }
        if (!username_text_box.getText().equals(username) && !username_text_box.getText().isEmpty()) {
            Alert none = new Alert(Alert.AlertType.ERROR);
            none.setTitle("Hol' Up!");
            none.setContentText("User with the username provided not found!");
            none.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Checks the username and password to see if the user can sign on.
     * @param actionEvent on pressing the sign-in button on the login page.
     */

    public void on_sign_in(ActionEvent actionEvent) {
        if (checkUser()) {
            if (!password_text_box.getText().equals(password)) {
                Alert none = new Alert(Alert.AlertType.ERROR);
                none.setTitle("Hol' Up!");
                none.setContentText("Incorrect password!");
                none.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        location_code.setText(Main.getZoneID().toString());
        }
    }
