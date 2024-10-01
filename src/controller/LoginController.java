package controller;

import helper.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private Label username_label;
    @FXML private Label password_label;
    @FXML private Label location_label;
    @FXML private PasswordField password_text_box;
    @FXML private TextField username_text_box;
    @FXML private Label location_code;
    @FXML private Button sign_in_button;


    // Username and password for testing
    private String username = "test";
    private String password = "test";

    /**
     * Checks the currently entered username.
     * @return true when a proper username has been entered, false if the username text box is empty or invalid.
     */

    public boolean checkUser() {
        if (username_text_box.getText().isEmpty()) {

            // Blank username error
            Alerts.getError(3);
            return false;
        }
        if (!username_text_box.getText().equals(username) && !username_text_box.getText().isEmpty()) {

            // Incorrect username error
            Alerts.getError(1);
            return false;
        }
        return true;
    }

    /**
     * Checks the username and password to see if the user can sign on.
     * @param actionEvent on pressing the sign-in button on the login page.
     */

    public boolean on_sign_in(ActionEvent actionEvent) {
        if (checkUser()) {
            if (!password_text_box.getText().equals(password)) {

                // Incorrect password
                Alerts.getError(2);
                return false;
            } else {

                // Successful sign on
                System.out.println("Sign in for user " + username + " successful.");
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        location_code.setText(Main.getZoneID().toString());

        // Uses the language bundle to get the language to display.
        username_label.setText(Main.langBundle.getString("Username"));
        password_label.setText(Main.langBundle.getString("Password"));
        sign_in_button.setText(Main.langBundle.getString("Login"));
        location_label.setText(Main.langBundle.getString("Location"));
        }
    }
