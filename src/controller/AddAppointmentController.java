package controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import helper.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.Main;
import model.Contact;
import model.Country;
import model.Customer;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
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
    @FXML protected ComboBox user_combo;
    @FXML protected ComboBox cust_combo;

    // Bottom buttons
    @FXML protected Button close_button;
    @FXML protected Button save_button;

    /**
     * Checks if any of the fields are empty.
     * @return true when all fields have values, false otherwise.
     */

    public boolean checkEmpty() {
        TextField[] fields = {appt_title_box, appt_desc_box, appt_loc_box, appt_type_box};
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                Alerts.getError(6);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the start and end dates are invalid. Checks if the scheduled meeting time is outside business hours.
     * @return true when meeting is within business hours, start date is before end date and there are no overlapping meetings.
     */

    // TODO: Check for overlapping appointments & start times after end times.

    public boolean checkValidHours() {
        LocalDateTime current_date_time = LocalDateTime.now();
        ZoneId est_zone = ZoneId.of("America/New_York");
        ZonedDateTime est_date_time = current_date_time.atZone(ZoneId.systemDefault()).withZoneSameInstant(est_zone);

        int hour = est_date_time.getHour();

        if (start_date_combo.getValue().isAfter(end_date_combo.getValue())) {
            // Invalid start & end dates.
            Alerts.getError(9);
            return false;
        }
        if (hour >= 8 && hour < 22) {
            return true;
        } else {
            // Scheduling outside business hour error.
            Alerts.getError(8);
            return false;
        }
    }

    /**
     * Creates a new appointment on pressing the save button while adding a new appointment.
     * @param actionEvent on save button press.
     */

    public void onApptSave(ActionEvent actionEvent) {
        String title = appt_title_box.getText();
        String desc = appt_desc_box.getText();
        String location = appt_loc_box.getText();
        String type = appt_type_box.getText();
        Contact contact = (Contact) contact_combo.getValue();
        User user = (User) user_combo.getValue();
        Customer customer = (Customer) cust_combo.getValue();

        ZonedDateTime zoned_start_time = ZonedDateTime.of(LocalDate.now(), (LocalTime) start_time_combo.getValue(), Main.getZoneID());
        ZonedDateTime zoned_end_time = ZonedDateTime.of(LocalDate.now(), (LocalTime) end_time_combo.getValue(), Main.getZoneID());

        ZonedDateTime utc_start_time = zoned_start_time.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utc_end_time = zoned_end_time.withZoneSameInstant(ZoneId.of("UTC"));

        if (checkEmpty()) {
            if (checkValidHours()) {
                try {
                    AppointmentDAO.createAppointment(AppointmentDAO.getUniqueAppointmentID(), title, desc, location, type, contact, utc_start_time.toLocalDateTime(), utc_end_time.toLocalDateTime(), user, customer);
                    onClose(actionEvent);
                } catch (SQLException e) {
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

    /**
     * Generates a list of times spanning from 8 to 22 in increments of 15 minutes.
     * @return a list of times spanning from 8 to 22 in increments of 15 minutes.
     */

    public static ObservableList<LocalTime> generateTimeList() {
        ObservableList<LocalTime> time_list = FXCollections.observableArrayList();

        LocalTime start_time = LocalTime.of(8, 0);
        LocalTime end_time = LocalTime.of(22, 0);

        LocalTime current_time = start_time;
        while (!current_time.isAfter(end_time)) {
            time_list.add(current_time);
            current_time = current_time.plusMinutes(15);
        }

        return time_list;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
        contact_combo.setItems(ContactDAO.getContactList());
        contact_combo.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {
                return (contact != null) ? contact.getContactName() : "";
            }

            @Override
            public Contact fromString(String s) {
                return null;
            }
        });

        user_combo.setItems(UserDAO.getUserList());
        user_combo.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return (user != null) ? user.getUsername() : "";
            }

            @Override
            public User fromString(String s) {
                return null;
            }
        });

        cust_combo.setItems(CustomerDAO.getAllCustomers());
        cust_combo.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return (customer != null) ? customer.getCustomerName() : "";
            }

            @Override
            public Customer fromString(String s) {
                return null;
            }
        });

        start_time_combo.setItems(generateTimeList());
        end_time_combo.setItems(generateTimeList());
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
