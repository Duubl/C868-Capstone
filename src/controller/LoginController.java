package controller;

import DAO.UserDAO;
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

    /**
     * Checks the username and password to see if the user can sign on.
     * @param actionEvent on pressing the sign-in button on the login page.
     */

    public boolean onSignIn(ActionEvent actionEvent) throws IOException {
        if (UserDAO.validateUser(username_text_box.getText(), password_text_box.getText()) != -1) {

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
