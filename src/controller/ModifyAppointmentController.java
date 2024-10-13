package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAppointmentController extends AddAppointmentController implements Initializable {

    @FXML private Label mod_appt_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mod_appt_label.setText(Main.langBundle.getString("ModifyAppointment"));
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
