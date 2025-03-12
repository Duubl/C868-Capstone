package controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import helper.Alerts;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    // Appointment search field
    @FXML private TextField appointment_search;

    // Appointment radio buttons
    @FXML private RadioButton all_appt_rad;
    @FXML private RadioButton week_rad;
    @FXML private RadioButton month_rad;

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

    // Customer search field
    @FXML private TextField customer_search;

    // Customer buttons
    @FXML private Button add_customer_button;
    @FXML private Button delete_customer_button;
    @FXML private Button update_customer_button;

    // Users tab
    @FXML private Tab users_tab;
    @FXML private TableView<User> user_table;
    @FXML private TableColumn<User, Integer> user_id_col;
    @FXML private TableColumn<User, String> username_col;
    @FXML private TableColumn<User, String> user_password_col;
    @FXML private TableColumn<User, Integer> user_admin_col;

    // User Buttons
    @FXML private Button add_user_button;
    @FXML private Button delete_user_button;
    @FXML private Button update_user_button;

    // User search field
    @FXML private TextField user_search;

    // Contacts tab
    @FXML private Tab contacts_tab;

    // Contact buttons
    @FXML private TableView<Contact> contact_table;
    @FXML private TableColumn<Contact, Integer> contact_id_col;
    @FXML private TableColumn<Contact, String> contact_name_col;
    @FXML private TableColumn<Contact, String> contact_email_col;
    @FXML private Button add_contact_button;
    @FXML private Button delete_contact_button;
    @FXML private Button update_contact_button;

    // Contact search field
    @FXML private TextField contact_search;

    // Reporting tab
    @FXML private Tab reporting_tab;

    @FXML private Tab appt_totals_tab;
    @FXML private Tab meetings_per_contact_tab;

    // Contact schedules
    @FXML private TableView<Appointment> contact_schedule_table;
    @FXML private TableColumn<Appointment, Contact> sched_contact_col;
    @FXML private TableColumn<Appointment, Integer> sched_appt_col;
    @FXML private TableColumn<Appointment, String> sched_title_col;
    @FXML private TableColumn<Appointment, String> sched_desc_col;
    @FXML private TableColumn<Appointment, String> sched_loc_col;
    @FXML private TableColumn<Appointment, String> sched_type_col;
    @FXML private TableColumn<Appointment, LocalDateTime> sched_start_col;
    @FXML private TableColumn<Appointment, LocalDateTime> sched_end_col;
    @FXML private TableColumn<Appointment, Customer> sched_cust_col;
    @FXML private ComboBox<Contact> contact_combo;

    // Type
    @FXML private TableView<Map.Entry<String, Integer>> appt_by_type_table;
    @FXML private TableColumn<Map.Entry<String, Integer>, String> appt_by_type_col;
    @FXML private TableColumn<Map.Entry<String, Integer>, Integer> appt_by_type_total_col;

    // Month
    @FXML private TableView<Map.Entry<Integer, Integer>> appt_by_month_table;
    @FXML private TableColumn<Map.Entry<Integer, Integer>, String> appt_by_month_col;
    @FXML private TableColumn<Map.Entry<Integer, Integer>, Integer> appt_by_month_total_col;

    // Meetings per contact
    @FXML private TableView<Contact> meet_count_table;
    @FXML private TableColumn<Contact, Contact> meet_count_contact_col;
    @FXML private TableColumn<Contact, Integer> meet_count_col;

    // Customer, user and appointment to modify
    private static Customer customer_to_modify;
    private static Appointment appointment_to_modify;
    private static User user_to_modify;

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
                refreshTables();
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
                        refreshTables();
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
                confirm.setContentText(Main.lang_bundle.getString("DeleteConfirmAppointment") + " " + selected.getAppointmentTitle() + "?\n" + "ID: " + selected.getAppointmentID() + "\n" + "Type" + ": " + selected.getAppointmentType());
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    AppointmentDAO.deleteAppointment(selected);
                    refreshTables();
                }
            }
        } catch (Exception e) {
            // No appointment selected error
            Alerts.getError(7);
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for appointments by name or ID.
     * @param inputMethodEvent on searching.
     * @throws SQLException
     */

    public void onApptSearch(ActionEvent inputMethodEvent) throws SQLException {
        String searchText = appointment_search.getText().trim();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        for (Appointment appointment : AppointmentDAO.getUserAppointments(UserDAO.getCurrentUser())) {
            if (appointment.getAppointmentTitle().toLowerCase().contains(searchText.toLowerCase()) || String.valueOf(appointment.getAppointmentID()).equals(searchText)) {
                appointments.add(appointment);
            }
        }
        if (appointments.isEmpty()) {
            Alerts.getError(14);
        } else {
            appointment_table.setItems(appointments);
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
                refreshTables();
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
                    refreshTables();
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
                        refreshTables();
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
     * Searches for customers by name or ID.
     * @param inputMethodEvent on searching.
     * @throws SQLException
     */

    public void onCustomerSearch(ActionEvent inputMethodEvent) throws SQLException {
        String searchText = customer_search.getText().trim();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        for (Customer customer : CustomerDAO.getAllCustomers()) {
            if (customer.getCustomerName().toLowerCase().contains(searchText.toLowerCase()) || String.valueOf(customer.getCustomerID()).equals(searchText)) {
                customers.add(customer);
            }
        }
        if (customers.isEmpty()) {
            Alerts.getError(14);
        } else {
            customer_table.setItems(customers);
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

    // User functions

    /**
     * Opens the add user dialog
     * @param actionEvent on add button press
     * @throws IOException
     */

    public void onUserAdd(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/add-user-view.fxml"))));
        stage.setScene(scene);
        stage.setTitle(Main.lang_bundle.getString("AddUser"));
        stage.setResizable(false);
        stage.setOnHidden(e -> {
            try {
                refreshTables();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        stage.show();
    }

    /**
     * Deleted the selected user
     * @param actionEvent on delete button press
     * @throws IOException
     */

    public void onUserDelete(ActionEvent actionEvent) {
        try {
            User selected = user_table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new Exception();
            } else {
                if (selected.getUserID() == UserDAO.getCurrentUser().getUserID()) {
                    // User signed on error
                    Alerts.getError(15);
                    return;
                }
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("You Sure Bud?");
                confirm.setContentText(Main.lang_bundle.getString("DeleteConfirmUser") + " " + selected.getUsername() + "?\n" + "ID: " + selected.getUserID());
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    UserDAO.deleteUser(selected);
                    refreshTables();
                }
            }
        } catch (Exception e) {
            // No appointment selected error
            Alerts.getError(7);
            throw new RuntimeException(e);
        }
    }

    public void onUserModify(ActionEvent actionEvent) {
        try {
            User selected = user_table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new Exception();
            } else {
                user_to_modify = selected;
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/modify-user-view.fxml"))));
                stage.setScene(scene);
                stage.setTitle(Main.lang_bundle.getString("ModifyUser"));
                stage.setResizable(false);
                stage.setOnHidden(e -> {
                    try {
                        refreshTables();
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
     * Searches for users by name or ID.
     * @param inputMethodEvent on searching.
     * @throws SQLException
     */

    public void onUserSearch(ActionEvent inputMethodEvent) {
        String searchText = user_search.getText().trim();
        ObservableList<User> users = FXCollections.observableArrayList();
        for (User user : UserDAO.getUserList()) {
            if (user.getUsername().toLowerCase().contains(searchText.toLowerCase()) || String.valueOf(user.getUserID()).equals(searchText)) {
                users.add(user);
            }
        }
        if (users.isEmpty()) {
            Alerts.getError(14);
        } else {
            user_table.setItems(users);
        }
    }

    // Contacts functions

    public void onContactAdd(ActionEvent actionEvent) {

    }

    public void onContactModify(ActionEvent actionEvent) {

    }

    public void onContactDelete(ActionEvent actionEvent) {
        try {
            Contact selected = contact_table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new Exception();
            } else {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("You Sure Bud?");
                confirm.setContentText(Main.lang_bundle.getString("DeleteConfirmContact") + " " + selected.getContactName() + "?\n" + "ID: " + selected.getContactID());
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    ContactDAO.deleteContact(selected);
                    refreshTables();
                }
            }
        } catch (Exception e) {
            // No appointment selected error
            Alerts.getError(7);
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for users by name or ID.
     * @param inputMethodEvent on searching.
     * @throws SQLException
     */

    public void onContactSearch(ActionEvent inputMethodEvent) {
        String searchText = contact_search.getText().trim();
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        for (Contact contact : ContactDAO.getContactList()) {
            if (contact.getContactName().toLowerCase().contains(searchText.toLowerCase()) || String.valueOf(contact.getContactID()).equals(searchText)) {
                contacts.add(contact);
            }
        }
        if (contacts.isEmpty()) {
            Alerts.getError(14);
        } else {
            contact_table.setItems(contacts);
        }
    }

    // Reporting functions

    /**
     * Refreshes the contact schedule table once a new contact is selected using the combo box
     */

    public void refreshScheduleTable() throws SQLException {
        try {
            Contact selected = contact_combo.getSelectionModel().getSelectedItem();
            if (selected == null) {
                contact_schedule_table.setItems(null);
            } else {
                contact_schedule_table.setItems(ContactDAO.getContactAppointments(selected));
            }
            sched_contact_col.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
            sched_appt_col.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            sched_title_col.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            sched_desc_col.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            sched_loc_col.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            sched_type_col.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            sched_cust_col.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            // Formats the start & end date & times to clearly display the date, time and time zone.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a z");
            sched_start_col.setCellValueFactory(new PropertyValueFactory<>("localStartDateTime"));
            sched_end_col.setCellValueFactory(new PropertyValueFactory<>("localEndDateTime"));
            sched_start_col.setCellFactory(new Callback<>() {
                @Override
                public TableCell<Appointment, LocalDateTime> call(TableColumn<Appointment, LocalDateTime> param) {
                    return new TableCell<>() {
                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                ZonedDateTime local = item.atZone(ZoneId.systemDefault());
                                setText(local.format(formatter));
                            }
                        }
                    };
                }
            });

            sched_end_col.setCellFactory(new Callback<>() {
                @Override
                public TableCell<Appointment, LocalDateTime> call(TableColumn<Appointment, LocalDateTime> param) {
                    return new TableCell<>() {
                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                ZonedDateTime local = item.atZone(ZoneId.systemDefault());
                                setText(local.format(formatter));
                            }
                        }
                    };
                }
            });
            contact_schedule_table.getSortOrder().add(sched_start_col);
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    /**
     * Refreshes the meetings per contact custom report table
     */

    public void refreshMeetingsPerContactTable() {
        try {
            meet_count_table.setItems(ContactDAO.getContactList());
            meet_count_contact_col.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            meet_count_col.setCellValueFactory(data -> {
                Contact contact = data.getValue();
                try {
                    int count = ContactDAO.getAppointmentCount(contact);
                    return new SimpleIntegerProperty(count).asObject();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new SimpleIntegerProperty(0).asObject();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Other miscellaneous functions

    /**
     * Refreshes all the tables
     * @throws SQLException
     */

    public void refreshTables() throws SQLException {
        refreshAppointmentTable();
        refreshCustomerTable();
        refreshUserTable();
        refreshContactTable();
        refreshScheduleTable();
        refreshMeetingsPerContactTable();
        refreshAppointmentByMonthTable();
        refreshAppointmentByTypeTable();
    }

    /**
     * Refreshes the table customer table
     * @throws SQLException
     */

    public void refreshCustomerTable() throws SQLException {
        customer_table.setItems(CustomerDAO.getAllCustomers());
    }

    /**
     * Refreshes the table appointment table
     */

    public void refreshAppointmentTable() throws SQLException {
        if (all_appt_rad.isSelected()) {
            appointment_table.setItems(AppointmentDAO.getUserAppointments(UserDAO.getCurrentUser()));
        } else if (week_rad.isSelected()) {
        appointment_table.setItems(AppointmentDAO.getWeeklyAppointments());
        } else if (month_rad.isSelected()) {
            appointment_table.setItems(AppointmentDAO.getMonthlyAppointments());
        }
    }

    /**
     * Refreshes the user table
     */

    public void refreshUserTable() {
        user_table.setItems(UserDAO.getUserList());
    }

    /**
     * Refreshes the contact table
     */

    public void refreshContactTable() { contact_table.setItems(ContactDAO.getContactList()); }

    /**
     * Refreshes the appointment by type table
     */

    public void refreshAppointmentByTypeTable() {
        // Appointment type totals
        try {
            ObservableList<String> appointment_types = AppointmentDAO.getAppointmentTypes();
            Map<String, Integer> type_counts = appointment_types.stream().collect(Collectors.groupingBy(type -> type, Collectors.summingInt(type -> 1)));
            ObservableList<Map.Entry<String, Integer>> appointment_type_data = FXCollections.observableArrayList(type_counts.entrySet());
            appt_by_type_table.setItems(appointment_type_data);
            appt_by_type_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
            appt_by_type_total_col.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getValue()).asObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Refreshes the appointment by month table
     */

    public void refreshAppointmentByMonthTable() {
        // Appointment month totals
        try {
            // Creates an ObservableList of months and the total appointments for each month
            ObservableList<Integer> months = FXCollections.observableArrayList(Arrays.stream(Month.values()).map(Month::getValue).toList());
            ObservableList<Integer> appointment_month_totals = AppointmentDAO.getMonthlyAppointmentTotals();

            // Maps the total appointments to the month value
            Map<Integer, Integer> month_counts = IntStream.range(0, months.size()).boxed().collect(Collectors.toMap(months::get, appointment_month_totals::get));

            // Makes an ObservableList to use in the table
            ObservableList<Map.Entry<Integer, Integer>> table_values = FXCollections.observableArrayList(month_counts.entrySet());
            appt_by_month_table.setItems(table_values);

            // Replaces the number value of the month with the name of the month for readability
            appt_by_month_col.setCellValueFactory(cellData -> {
                String month_name = Month.of(cellData.getValue().getKey()).name();
                month_name = month_name.substring(0, 1).toUpperCase() + month_name.substring(1).toLowerCase();
                return new SimpleStringProperty(month_name);
            });
            appt_by_month_total_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a user to be modified
     * @return user_to_modify the user being modified
     */

    public static User getUserToModify() { return user_to_modify; }

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
        if (!UserDAO.getCurrentUser().getAdmin()) {
            delete_customer_button.setVisible(false);
            appt_totals_tab.setDisable(true);
            meetings_per_contact_tab.setDisable(true);
            users_tab.setDisable(true);
            contacts_tab.setDisable(true);
        }

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

        // Load user data into user table
        try {
            user_table.setItems(UserDAO.getUserList());
            user_id_col.setCellValueFactory(new PropertyValueFactory<>("userID"));
            username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
            user_password_col.setCellValueFactory(new PropertyValueFactory<>("password"));
            user_admin_col.setCellValueFactory(new PropertyValueFactory<>("admin"));
        } catch (Exception e) { throw new RuntimeException(e); }

        // Load user data into contact table
        try {
            contact_table.setItems(ContactDAO.getContactList());
            contact_id_col.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            contact_name_col.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            contact_email_col.setCellValueFactory(new PropertyValueFactory<>("contactEmail"));
        } catch (Exception e) { throw new RuntimeException(e); }

        // Load all appointment data for the user into the appointment table
        try {
            appointment_table.setItems(AppointmentDAO.getUserAppointments(UserDAO.getCurrentUser()));
            appt_id_col.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            appt_title_col.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            appt_desc_col.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            appt_loc_col.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            appt_contact_col.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
            appt_type_col.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            appt_cust_id_col.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            appt_user_id_col.setCellValueFactory(new PropertyValueFactory<>("userID"));

            // Formats the start & end date & times to clearly display the date, time and time zone.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a z");
            appt_start_col.setCellValueFactory(new PropertyValueFactory<>("localStartDateTime"));
            appt_end_col.setCellValueFactory(new PropertyValueFactory<>("localEndDateTime"));
            appt_start_col.setCellFactory(new Callback<>() {
                @Override
                public TableCell<Appointment, LocalDateTime> call(TableColumn<Appointment, LocalDateTime> param) {
                    return new TableCell<>() {
                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                ZonedDateTime local = item.atZone(ZoneId.systemDefault());
                                setText(local.format(formatter));
                            }
                        }
                    };
                }
            });
            appt_end_col.setCellFactory(new Callback<>() {
                @Override
                public TableCell<Appointment, LocalDateTime> call(TableColumn<Appointment, LocalDateTime> param) {
                    return new TableCell<>() {
                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                ZonedDateTime local = item.atZone(ZoneId.systemDefault());
                                setText(local.format(formatter));
                            }
                        }
                    };
                }
            });
        } catch (SQLException e) { throw new RuntimeException(e); }

        // Checks for appointments coming up soon
        try {
            Appointment appointment = AppointmentDAO.appointmentSoon();
            if (appointment != null) {
                Alerts.customMessage("ID: " + appointment.getAppointmentID() + "\nTitle: " + appointment.getAppointmentTitle() + "\nStarts at: " + appointment.getLocalStartDateTime().format(DateTimeFormatter.ofPattern("h:mm a")));
            } else {
                // No appointments within 15 minutes
                Alerts.getError(13);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }

        // Contact Schedules
        try {
            contact_combo.setItems(ContactDAO.getContactList());
            contact_combo.setConverter(new StringConverter<>() {
                @Override
                public String toString(Contact contact) {
                    return contact.getContactName();
                }

                @Override
                public Contact fromString(String s) {
                    return null;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Appointment type totals
        try {
            ObservableList<String> appointment_types = AppointmentDAO.getAppointmentTypes();
            Map<String, Integer> type_counts = appointment_types.stream().collect(Collectors.groupingBy(type -> type, Collectors.summingInt(type -> 1)));
            ObservableList<Map.Entry<String, Integer>> appointment_type_data = FXCollections.observableArrayList(type_counts.entrySet());
            appt_by_type_table.setItems(appointment_type_data);
            appt_by_type_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
            appt_by_type_total_col.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getValue()).asObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Appointment month totals
        try {
            // Creates an ObservableList of months and the total appointments for each month
            ObservableList<Integer> months = FXCollections.observableArrayList(Arrays.stream(Month.values()).map(Month::getValue).toList());
            ObservableList<Integer> appointment_month_totals = AppointmentDAO.getMonthlyAppointmentTotals();

            // Maps the total appointments to the month value
            Map<Integer, Integer> month_counts = IntStream.range(0, months.size()).boxed().collect(Collectors.toMap(months::get, appointment_month_totals::get));

            // Makes an ObservableList to use in the table
            ObservableList<Map.Entry<Integer, Integer>> table_values = FXCollections.observableArrayList(month_counts.entrySet());
            appt_by_month_table.setItems(table_values);

            // Replaces the number value of the month with the name of the month for readability
            appt_by_month_col.setCellValueFactory(cellData -> {
                String month_name = Month.of(cellData.getValue().getKey()).name();
                month_name = month_name.substring(0, 1).toUpperCase() + month_name.substring(1).toLowerCase();
                return new SimpleStringProperty(month_name);
            });
            appt_by_month_total_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Meetings per contact
        try {
            meet_count_table.setItems(ContactDAO.getContactList());
            meet_count_contact_col.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            meet_count_col.setCellValueFactory(data -> {
                Contact contact = data.getValue();
                try {
                    int count = ContactDAO.getAppointmentCount(contact);
                    return new SimpleIntegerProperty(count).asObject();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new SimpleIntegerProperty(0).asObject();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
