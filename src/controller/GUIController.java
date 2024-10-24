package controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    // Appointments tab
    @FXML private Tab appts_tab;
    @FXML private TableView appointment_table;
    @FXML private TableColumn appt_id_col;
    @FXML private TableColumn appt_title_col;
    @FXML private TableColumn appt_desc_col;
    @FXML private TableColumn appt_loc_col;
    @FXML private TableColumn appt_contact_col;
    @FXML private TableColumn appt_type_col;
    @FXML private TableColumn appt_start_col;
    @FXML private TableColumn appt_end_col;
    @FXML private TableColumn appt_cust_id_col;
    @FXML private TableColumn appt_user_id_col;

    // Appointment buttons
    @FXML private Button add_appt_button;
    @FXML private Button delete_appt_button;
    @FXML private Button update_appt_button;

    // Customer tab
    @FXML private Tab customers_tab;
    @FXML private TableView customer_table;
    @FXML private TableColumn cust_id_col;
    @FXML private TableColumn cust_name_col;
    @FXML private TableColumn cust_phone_col;
    @FXML private TableColumn cust_address_col;
    @FXML private TableColumn cust_postal_col;
    @FXML private TableColumn cust_state_prov_col;
    @FXML private TableColumn cust_country_col;

    // Customer buttons
    @FXML private Button add_customer_button;
    @FXML private Button delete_customer_button;
    @FXML private Button update_customer_button;

    // Reporting tab
    @FXML private Tab reporting_tab;

    // Reporting buttons

    // Appointment functions

    /**
     * Opens the add appointment stage on the add button press.
     * @param actionEvent on pressing the add button.
     * @throws IOException
     */

    public void onApptAdd(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/add-appt-view.fxml")));
        stage.setScene(scene);
        stage.setTitle(Main.langBundle.getString("AddAppointment"));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Modifies the selected appointment on modify button press and appointment is selected.
     * @param actionEvent on pressing the modify button.
     * @throws IOException
     */

    public void onApptModify(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/modify-appt-view.fxml")));
        stage.setScene(scene);
        stage.setTitle(Main.langBundle.getString("ModifyAppointment"));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Deletes the selected appointment on delete button press.
     * @param actionEvent on pressing the delete button.
     */

    public void onApptDelete(ActionEvent actionEvent) {

    }

    // Customer functions

    public void onCustomerAdd(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/add-customer-view.fxml")));
        stage.setScene(scene);
        stage.setTitle(Main.langBundle.getString("AddCustomer"));
        stage.setResizable(false);
        stage.show();
    }

    public void onCustomerDelete(ActionEvent actionEvent) {

    }

    public void onCustomerModify(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/modify-customer-view.fxml")));
        stage.setScene(scene);
        stage.setTitle(Main.langBundle.getString("ModifyCustomer"));
        stage.setResizable(false);
        stage.show();
    }

    // Reporting functions

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Localize appointments menu text
        appts_tab.setText(Main.langBundle.getString("Appointments"));
        appt_id_col.setText(Main.langBundle.getString("ApptID"));
        appt_title_col.setText(Main.langBundle.getString("Title"));
        appt_desc_col.setText(Main.langBundle.getString("Desc"));
        appt_loc_col.setText(Main.langBundle.getString("Location"));
        appt_contact_col.setText(Main.langBundle.getString("Contact"));
        appt_type_col.setText(Main.langBundle.getString("Type"));
        appt_start_col.setText(Main.langBundle.getString("Start"));
        appt_end_col.setText(Main.langBundle.getString("End"));
        appt_cust_id_col.setText(Main.langBundle.getString("CustID"));
        appt_user_id_col.setText(Main.langBundle.getString("UserID"));

        add_appt_button.setText(Main.langBundle.getString("Add"));
        delete_appt_button.setText(Main.langBundle.getString("Delete"));
        update_appt_button.setText(Main.langBundle.getString("Update"));

        // Localize customers menu text
        customers_tab.setText(Main.langBundle.getString("Customers"));
        cust_id_col.setText(Main.langBundle.getString("CustID"));
        cust_name_col.setText(Main.langBundle.getString("CustName"));
        cust_phone_col.setText(Main.langBundle.getString("Phone"));
        cust_address_col.setText(Main.langBundle.getString("Address"));
        cust_postal_col.setText(Main.langBundle.getString("Postal"));
        cust_state_prov_col.setText(Main.langBundle.getString("StateProv"));
        cust_country_col.setText(Main.langBundle.getString("Country"));

        add_customer_button.setText(Main.langBundle.getString("Add"));
        delete_customer_button.setText(Main.langBundle.getString("Delete"));
        update_customer_button.setText(Main.langBundle.getString("Update"));

        // Localize reporting menu text
        reporting_tab.setText(Main.langBundle.getString("Reporting"));

        // Load customer data into customer table
        try {
            customer_table.setItems(CustomerDAO.getAllCustomers());
            cust_id_col.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            cust_name_col.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            cust_phone_col.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            cust_address_col.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            cust_postal_col.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            cust_state_prov_col.setCellValueFactory(new PropertyValueFactory<>("division"));
            cust_country_col.setCellValueFactory(new PropertyValueFactory<>("country"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Load all appointment data for the user into the appointment table
        try {
            appointment_table.setItems(AppointmentDAO.getUserAppointments(Main.getUserID()));
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
