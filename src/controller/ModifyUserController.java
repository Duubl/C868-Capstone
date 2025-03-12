package controller;

import DAO.UserDAO;
import helper.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyUserController extends AddUserController implements Initializable {

    private static User selected;

    @FXML
    private Label modify_user_label;

    /**
     * Modifies the selected user.
     * @param actionEvent
     */

    // TODO: Add password requirements to meet industry appropriate security features

    @Override
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
                    UserDAO.updateUser(selected.getUserID(), username, password, admin);
                    onClose(actionEvent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selected = GUIController.getUserToModify();

        username_box.setText(selected.getUsername());
        password_box.setText(selected.getPassword());
        if (selected.getAdmin()) {
            admin_check.setSelected(true);
        }
        if (selected.getUserID() == UserDAO.getCurrentUser().getUserID()) {
            admin_check.setDisable(true);
        }
    }
}
