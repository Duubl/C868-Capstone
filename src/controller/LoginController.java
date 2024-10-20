package controller;

import helper.Alerts;
import helper.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
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

    public boolean checkUser() throws IOException {
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

    public boolean onSignIn(ActionEvent actionEvent) throws IOException {
        if (checkUser()) {
            if (!password_text_box.getText().equals(password)) {

                // Incorrect password
                Alerts.getError(2);
                Logger.logLogin(username_text_box.getText(), false);
                return false;
            } else {

                // Successful sign on
                Logger.logLogin(username_text_box.getText(), true);
                System.out.println(Main.langBundle.getString("SignInSuccess"));
                Parent root = FXMLLoader.load(getClass().getResource("/view/main-view.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle(Main.langBundle.getString("Scheduler"));
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        location_code.setText(Main.getZoneID().toString());

        // Uses the language bundle to get the language to display.
        username_label.setText(Main.langBundle.getString("Username") + ":");
        password_label.setText(Main.langBundle.getString("Password") + ":");
        sign_in_button.setText(Main.langBundle.getString("Login"));
        location_label.setText(Main.langBundle.getString("Location") + ":");
        }
    }
