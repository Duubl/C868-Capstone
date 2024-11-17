package controller;

import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.time.chrono.Chronology;
import java.util.ResourceBundle;

public class ModifyAppointmentController extends AddAppointmentController implements Initializable {

    @FXML private Label mod_appt_label;
    private static Appointment selected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selected = GUIController.getAppointmentToModify();

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
        start_date_combo.setValue(selected.getStartDateTime().toLocalDate());
        end_date_combo.setValue(selected.getEndDateTime().toLocalDate());
        start_time_combo.getSelectionModel().select(selected.getStartDateTime().toLocalTime());
        end_time_combo.getSelectionModel().select(selected.getEndDateTime().toLocalTime());
    }
}
