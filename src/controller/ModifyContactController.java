package controller;

import DAO.ContactDAO;
import helper.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Contact;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyContactController extends AddContactController implements Initializable {

    private static Contact selected;

    @FXML private Label modify_contact_label;

    /**
     * Modifies the selected contact
     * @param actionEvent
     */

    public void onContactSave(ActionEvent actionEvent) {
        String name = contact_name_box.getText();
        String email = contact_email_box.getText();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            if (checkEmpty()) {
                try {
                    ContactDAO.updateContact(selected.getContactID(), name, email);
                    onClose(actionEvent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            // Incorrect email formatting!
            Alerts.getError(19);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selected = GUIController.getContactToModify();
        contact_name_box.setText(selected.getContactName());
        contact_email_box.setText(selected.getContactEmail());
    }
}
