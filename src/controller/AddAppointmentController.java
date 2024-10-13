package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    // Labels
    @FXML protected Label add_appt_label;
    @FXML protected Label title_label;
    @FXML protected Label desc_label;
    @FXML protected Label location_label;
    @FXML protected Label type_label;
    @FXML protected Label contact_label;
    @FXML protected Label start_label;
    @FXML protected Label end_label;
    @FXML protected Label user_id_label;
    @FXML protected Label customer_id_label;

    // Data entry
    @FXML protected TextField appt_title_box;
    @FXML protected TextField appt_desc_box;
    @FXML protected TextField appt_loc_box;
    @FXML protected TextField appt_type_box;
    @FXML protected ComboBox contact_combo;
    @FXML protected DatePicker start_date_combo;
    @FXML protected DatePicker end_date_combo;
    @FXML protected ComboBox start_time_combo;
    @FXML protected ComboBox end_time_combo;
    @FXML protected ComboBox user_id_combo;
    @FXML protected ComboBox cust_id_combo;

    // Bottom buttons
    @FXML protected Button close_button;
    @FXML protected Button save_button;

    public void onApptSave(ActionEvent actionEvent) {

    }

    /**
     * Closes the add appointment stage without saving.
     * @param actionEvent on pressing the close button.
     */

    public void onClose(ActionEvent actionEvent) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_appt_label.setText(Main.langBundle.getString("AddAppointment"));
        title_label.setText(Main.langBundle.getString("Title") + ":");
        desc_label.setText(Main.langBundle.getString("Desc") + ":");
        location_label.setText(Main.langBundle.getString("Location") + ":");
        type_label.setText(Main.langBundle.getString("Type") + ":");
        contact_label.setText(Main.langBundle.getString("Contact") + ":");
        start_label.setText(Main.langBundle.getString("Start") + ":");
        end_label.setText(Main.langBundle.getString("End") + ":");
        user_id_label.setText(Main.langBundle.getString("UserID") + ":");
        customer_id_label.setText(Main.langBundle.getString("CustID") + ":");
        close_button.setText(Main.langBundle.getString("Close"));
        save_button.setText(Main.langBundle.getString("Save"));
    }
}
