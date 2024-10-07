package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    // Appointments tab
    @FXML private Tab appts_tab;
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
    }
}
