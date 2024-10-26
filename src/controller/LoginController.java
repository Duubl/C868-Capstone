package controller;

import DAO.UserDAO;
import helper.Alerts;
import helper.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Customer;
import model.User;

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
        int user_id = UserDAO.validateUser(username_text_box.getText(), password_text_box.getText());
        if (user_id != -1) {
            ObservableList<User> user_list = UserDAO.getUserList();
            for (User user : user_list) {
                if (user.getUserID() == user_id) {
                    Main.setCurrentUser(user);
                }
            }
            System.out.println(Main.lang_bundle.getString("SignInSuccess"));
            Parent root = FXMLLoader.load(getClass().getResource("/view/main-view.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle(Main.lang_bundle.getString("Scheduler"));
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
        username_label.setText(Main.lang_bundle.getString("Username") + ":");
        password_label.setText(Main.lang_bundle.getString("Password") + ":");
        sign_in_button.setText(Main.lang_bundle.getString("Login"));
        location_label.setText(Main.lang_bundle.getString("Location") + ":");
        }
    }
