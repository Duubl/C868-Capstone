package controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import main.Main;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.chrono.Chronology;
import java.util.ResourceBundle;

public class ModifyAppointmentController extends AddAppointmentController implements Initializable {

    @FXML private Label modify_appt_id;
    @FXML private Label mod_appt_label;
    private static Appointment selected;

    /**
     * Saves the modifications to the appointment.
     * @param actionEvent on save button press.
     */

    @Override
    public void onApptSave(ActionEvent actionEvent) throws SQLException {
        String title = appt_title_box.getText();
        String desc = appt_desc_box.getText();
        String location = appt_loc_box.getText();
        String type = appt_type_box.getText();
        Contact contact = contact_combo.getValue();
        User user = user_combo.getValue();
        Customer customer = cust_combo.getValue();
        int id = selected.getAppointmentID();

        LocalDate start_date = start_date_combo.getValue();
        LocalDate end_date = end_date_combo.getValue();
        LocalTime start_time = start_time_combo.getValue();
        LocalTime end_time = end_time_combo.getValue();

        ZonedDateTime zoned_start_time = ZonedDateTime.of(start_date, start_time, Main.getZoneID());
        ZonedDateTime zoned_end_time = ZonedDateTime.of(end_date, end_time, Main.getZoneID());

        ZonedDateTime utc_start_time = zoned_start_time.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utc_end_time = zoned_end_time.withZoneSameInstant(ZoneId.of("UTC"));

        if (checkEmpty()) {
            if (checkValidHours(id)) {
                try {
                    AppointmentDAO.updateAppointment(id, title, desc, location, type, contact, utc_start_time.toLocalDateTime(), utc_end_time.toLocalDateTime(), user, customer);
                    onClose(actionEvent);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selected = GUIController.getAppointmentToModify();

        modify_appt_id.setText(Integer.toString(selected.getAppointmentID()));
        appt_title_box.setText(selected.getAppointmentTitle());
        appt_desc_box.setText(selected.getAppointmentDescription());
        appt_loc_box.setText(selected.getAppointmentLocation());
        appt_type_box.setText(selected.getAppointmentType());

        try {
            contact_combo.setItems(ContactDAO.getContactList());
            contact_combo.setConverter(new StringConverter<>() {
                @Override
                public String toString(Contact contact) { return (contact != null) ? contact.getContactName() : ""; }

                @Override
                public Contact fromString(String s) { return null; }});

            user_combo.setItems(UserDAO.getUserList());
            user_combo.setConverter(new StringConverter<>() {
                @Override
                public String toString(User user) { return (user != null) ? user.getUsername() : ""; }

                @Override
                public User fromString(String s) { return null; }});

            cust_combo.setItems(CustomerDAO.getAllCustomers());
            cust_combo.setConverter(new StringConverter<>() {
                @Override
                public String toString(Customer customer) { return (customer != null) ? customer.getCustomerName() : ""; }

                @Override
                public Customer fromString(String s) { return null; }});

            start_time_combo.setItems(generateTimeList());
            end_time_combo.setItems(generateTimeList());

        } catch (SQLException e) { throw new RuntimeException(e); }

        contact_combo.getSelectionModel().select(selected.getContactID() - 1);
        user_combo.getSelectionModel().select(selected.getUserID() - 1);
        cust_combo.getSelectionModel().select(selected.getCustomerID() - 1);
        start_date_combo.setValue(selected.getLocalStartDateTime().toLocalDate());
        end_date_combo.setValue(selected.getLocalEndDateTime().toLocalDate());
        start_time_combo.getSelectionModel().select(selected.getLocalStartDateTime().toLocalTime());
        end_time_combo.getSelectionModel().select(selected.getLocalEndDateTime().toLocalTime());
    }
}
