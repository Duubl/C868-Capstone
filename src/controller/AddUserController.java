package controller;

import DAO.CustomerDAO;
import DAO.UserDAO;
import helper.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.FirstLevelDivision;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddUserController {
    @FXML private Label add_user_label;
    @FXML protected Label username_label;
    @FXML protected Label password_label;
    @FXML protected Label reenter_pass_label;
    @FXML protected CheckBox admin_check;
    @FXML protected TextField username_box;
    @FXML protected TextField password_box;
    @FXML protected TextField reenter_pass_box;
    @FXML protected Button close_button;
    @FXML protected Button save_button;

    /**
     * Checks if any of the fields are empty.
     * @return true when all fields have values, false otherwise.
     */

    public boolean checkEmpty() {
        TextField[] fields = {username_box, password_box, reenter_pass_box};
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                Alerts.getError(6);
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a new user based on the input provided.
     * @param actionEvent
     */

    public void onUserSave(ActionEvent actionEvent) {
        String username = username_box.getText();
        String password = password_box.getText();
        String sec_password = reenter_pass_box.getText();
        boolean admin = admin_check.isSelected();
        if (checkEmpty()) {
            if (!password.contentEquals(sec_password)) {
                // Passwords don't match error
                Alerts.getError(17);
            } else {
                try {
                    UserDAO.createUser(UserDAO.getUniqueUserID(), username, password, admin);
                    onClose(actionEvent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
