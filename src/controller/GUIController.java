package controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import helper.Alerts;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    // Appointments tab
    @FXML private Tab appts_tab;
    @FXML private TableView<Appointment> appointment_table;
    @FXML private TableColumn<Appointment, Integer> appt_id_col;
    @FXML private TableColumn<Appointment, String> appt_title_col;
    @FXML private TableColumn<Appointment, String> appt_desc_col;
    @FXML private TableColumn<Appointment, String> appt_loc_col;
    @FXML private TableColumn<Appointment, Contact> appt_contact_col;
    @FXML private TableColumn<Appointment, String> appt_type_col;
    @FXML private TableColumn<Appointment, LocalDateTime> appt_start_col;
    @FXML private TableColumn<Appointment, LocalDateTime> appt_end_col;
    @FXML private TableColumn<Appointment, Integer> appt_cust_id_col;
    @FXML private TableColumn<Appointment, Integer> appt_user_id_col;

    // Appointment buttons
    @FXML private Button add_appt_button;
    @FXML private Button delete_appt_button;
    @FXML private Button update_appt_button;

    // Customer tab
    @FXML private Tab customers_tab;
    @FXML private TableView<Customer> customer_table;
    @FXML private TableColumn<Customer, Integer> cust_id_col;
    @FXML private TableColumn<Customer, String> cust_name_col;
    @FXML private TableColumn<Customer, String> cust_phone_col;
    @FXML private TableColumn<Customer, String> cust_address_col;
    @FXML private TableColumn<Customer, String> cust_postal_col;
    @FXML private TableColumn<Customer, FirstLevelDivision> cust_state_prov_col;
    @FXML private TableColumn<Customer, Country> cust_country_col;

    // Customer buttons
    @FXML private Button add_customer_button;
    @FXML private Button delete_customer_button;
    @FXML private Button update_customer_button;

    // Reporting tab
    @FXML private Tab reporting_tab;

    // Customer and appointment to modify
    private static Customer customer_to_modify;
    private static Appointment appointment_to_modify;

    // Reporting buttons

    // Appointment functions
    /**
     * Opens the add appointment stage on the add button press.
     * Lambda expression is used to trigger an event when the appointment addition screen is opened. The event is handled and causes the appointment table to be refreshed automatically, displaying the additions the user made.
     * @param actionEvent on pressing the add button.
     * @throws IOException
     */

    public void onApptAdd(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/add-appt-view.fxml"))));
        stage.setScene(scene);
        stage.setTitle(Main.lang_bundle.getString("AddAppointment"));
        stage.setResizable(false);
        stage.setOnHidden(e -> {
            try {
                refreshAppointmentTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        stage.show();
    }

    /**
     * Modifies the selected appointment on modify button press and appointment is selected.
     * Lambda expression is used to trigger an event when the appointment modification screen is opened. The event is handled and causes the appointment table to be refreshed automatically, displaying the updates the user made.
     * @param actionEvent on pressing the modify button.
     * @throws IOException
     */

    public void onApptModify(ActionEvent actionEvent) throws IOException {
        try {
            Appointment selected = appointment_table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new Exception();
            } else {
                appointment_to_modify = selected;
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/modify-appt-view.fxml"))));
                stage.setScene(scene);
                stage.setTitle(Main.lang_bundle.getString("ModifyAppointment"));
                stage.setResizable(false);
                stage.setOnHidden(e -> {
                    try {
                        refreshAppointmentTable();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                stage.show();
            }
        } catch (Exception e) {
            // No appointment selected error
            Alerts.getError(7);
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes the selected appointment on delete button press.
     * @param actionEvent on pressing the delete button.
     */

    public void onApptDelete(ActionEvent actionEvent) {
        try {
            Appointment selected = appointment_table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new Exception();
            } else {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("You Sure Bud?");
                confirm.setContentText(Main.lang_bundle.getString("DeleteConfirmAppointment") + " " + selected.getAppointmentTitle() + "?");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    AppointmentDAO.deleteAppointment(selected);
                    refreshAppointmentTable();
                }
            }
        } catch (Exception e) {
            // No appointment selected error
            Alerts.getError(7);
            throw new RuntimeException(e);
        }
    }

    // Customer functions

    /**
     * Opens the add customer dialog.
     * Lambda expression is used to trigger an event when the customer addition screen is opened. The event is handled and causes the customer table to be refreshed automatically, displaying the additions the user made.
     * @param actionEvent on pressing the add customer button.
     */

    public void onCustomerAdd(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/add-customer-view.fxml"))));
        stage.setScene(scene);
        stage.setTitle(Main.lang_bundle.getString("AddCustomer"));
        stage.setResizable(false);
        stage.setOnHidden(e -> {
            try {
                refreshCustomerTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        stage.show();
    }

    /**
     * Confirms customer delete & checks for assigned appointments
     * @param actionEvent onCustomerDelete button
     **/

    public void onCustomerDelete(ActionEvent actionEvent) {
        try {
            Customer selected = customer_table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new Exception();
            } else {
                if (checkCustomerAppointments(selected)) {
                    // Customer has existing appointments error
                    Alerts.getError(12);
                    return;
                }
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("You Sure Bud?");
                confirm.setContentText(Main.lang_bundle.getString("DeleteConfirmCustomer") + " " + selected.getCustomerName() + "?");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    CustomerDAO.deleteCustomer(selected);
                    refreshCustomerTable();
                }
            }
        } catch (Exception e) {
            // No customer selected error
            Alerts.getError(5);
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the modify customer screen
     * Lambda expression is used to trigger an event when the customer modification screen is opened. The event is handled and causes the customer table to be refreshed automatically, displaying the updates the user made.
     * @param actionEvent onCustomerModify button
     **/

    public void onCustomerModify(ActionEvent actionEvent) {
        try {
            Customer selected = customer_table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new Exception();
            } else {
                customer_to_modify = selected;
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/modify-customer-view.fxml"))));
                stage.setScene(scene);
                stage.setTitle(Main.lang_bundle.getString("ModifyCustomer"));
                stage.setResizable(false);
                stage.setOnHidden(e -> {
                    try {
                        refreshCustomerTable();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                stage.show();
            }
        } catch (Exception e) {
            // No customer selected error
            Alerts.getError(5);
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks for assigned appointments for the given customer.
     * @param customer the customer to be checked for appointments.
     * @return true when an appointment is found, false otherwise
     * @throws SQLException
     */

    public boolean checkCustomerAppointments(Customer customer) throws SQLException {
        ObservableList<Appointment> appointment_list = AppointmentDAO.getAppointmentList();
        for (Appointment appointment : appointment_list) {
            if (customer.getCustomerID() == appointment.getCustomerID()) {
                return true;
            }
        }
        return false;
    }

    // Reporting functions

    // Other miscellaneous functions

    /**
     * Refreshes the table customer table
     */

    public void refreshCustomerTable() throws SQLException {
        customer_table.setItems(CustomerDAO.getAllCustomers());
    }

    /**
     * Refreshes the table appointment table
     */

    public void refreshAppointmentTable() throws SQLException {
        appointment_table.setItems(AppointmentDAO.getUserAppointments(UserDAO.getCurrentUser()));
    }

    /**
     * Gets a customer to be modified
     * @return customer_to_modify the customer being modified
     */

    public static Customer getCustomerToModify() {
        return customer_to_modify;
    }

    /**
     * Gets an appointment to be modified
     * @return appointment_to_modify the customer being modified
     */

    public static Appointment getAppointmentToModify() {
        return appointment_to_modify;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load customer data into customer table
        try {
            customer_table.setItems(CustomerDAO.getAllCustomers());
            cust_id_col.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            cust_name_col.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            cust_phone_col.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            cust_address_col.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            cust_postal_col.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            cust_state_prov_col.setCellValueFactory(new PropertyValueFactory<>("customerDivisionName"));
            cust_country_col.setCellValueFactory(new PropertyValueFactory<>("customerCountryName"));
        } catch (SQLException e) { throw new RuntimeException(e); }

        // Load all appointment data for the user into the appointment table
        try {
            appointment_table.setItems(AppointmentDAO.getUserAppointments(UserDAO.getCurrentUser()));
            appt_id_col.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            appt_title_col.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            appt_desc_col.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            appt_loc_col.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            appt_contact_col.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
            appt_type_col.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            appt_start_col.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            appt_end_col.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            appt_cust_id_col.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            appt_user_id_col.setCellValueFactory(new PropertyValueFactory<>("userID"));
        } catch (SQLException e) { throw new RuntimeException(e); }

        try {
            Appointment appointment = AppointmentDAO.appointmentSoon();
            if (appointment != null) {
                Alerts.customMessage("ID: " + appointment.getAppointmentID() + "\nTitle: " + appointment.getAppointmentTitle() + "\nStarts at: " + appointment.getLocalStartDateTime().format(DateTimeFormatter.ofPattern("h:mm a")));
            } else {
                // No appointments within 15 minutes
                Alerts.getError(13);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}
