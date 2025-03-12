package controller;

import DAO.ContactDAO;
import helper.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FirstLevelDivision;

public class AddContactController {

    @FXML private Label add_contact_label;
    @FXML protected Label contact_name_label;
    @FXML protected Label contact_email_label;
    @FXML protected TextField contact_name_box;
    @FXML protected TextField contact_email_box;
    @FXML protected Button close_button;
    @FXML protected Button save_button;

    /**
     * Creates a new contact based on the information provided.
     * @param actionEvent
     */

    public void onContactSave(ActionEvent actionEvent) {
        String name = contact_name_box.getText();
        String email = contact_email_box.getText();
        if (checkEmpty()) {
            try {
                ContactDAO.createContact(ContactDAO.getUniqueContactID(), name, email);
                onClose(actionEvent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Checks if any of the fields are empty.
     * @return true when all fields have values, false otherwise.
     */

    public boolean checkEmpty() {
        TextField[] fields = {contact_name_box, contact_email_box};
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                Alerts.getError(6);
                return false;
            }
        }
        return true;
    }

    /**
     * Closes the add contact stage without saving.
     * @param actionEvent on pressing the close button.
     */

    public void onClose(ActionEvent actionEvent) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
}
