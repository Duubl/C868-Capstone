package controller;

import DAO.CustomerDAO;
import DAO.UserDAO;
import helper.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FirstLevelDivision;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController {
    @FXML private Label add_user_label;
    @FXML private Label username_label;
    @FXML private Label password_label;
    @FXML private CheckBox admin_check;
    @FXML private TextField username_box;
    @FXML private TextField password_box;
    @FXML private Button close_button;
    @FXML private Button save_button;

    /**
     * Checks if any of the fields are empty.
     * @return true when all fields have values, false otherwise.
     */

    public boolean checkEmpty() {
        TextField[] fields = {username_box, password_box};
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                Alerts.getError(6);
                return false;
            }
        }
        return true;
    }

    public void onUserSave(ActionEvent actionEvent) {
        String username = username_box.getText();
        String password = password_box.getText();
        boolean admin = admin_check.isSelected();
        if (checkEmpty()) {
            try {
                UserDAO.createUser(UserDAO.getUniqueUserID(), username, password, admin);
                onClose(actionEvent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Closes the add appointment stage without saving.
     * @param actionEvent on pressing the close button.
     */

    public void onClose(ActionEvent actionEvent) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
}
