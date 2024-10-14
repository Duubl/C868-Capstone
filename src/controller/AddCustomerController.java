package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    // Labels
    @FXML protected Label add_cust_label;
    @FXML protected Label cust_name_label;
    @FXML protected Label phone_label;
    @FXML protected Label address_label;
    @FXML protected Label postal_label;
    @FXML protected Label state_prov_label;
    @FXML protected Label country_label;

    // Data entry
    @FXML protected TextField cust_name_box;
    @FXML protected TextField phone_box;
    @FXML protected TextField address_box;
    @FXML protected TextField postal_box;
    @FXML protected ComboBox state_prov_combo;
    @FXML protected ComboBox country_combo;

    // Bottom buttons
    @FXML protected Button close_button;
    @FXML protected Button save_button;

    public void onCustSave(ActionEvent actionEvent) {

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
        add_cust_label.setText(Main.langBundle.getString("AddCustomer"));
        cust_name_label.setText(Main.langBundle.getString("CustName") + ":");
        phone_label.setText(Main.langBundle.getString("Phone") + ":");
        address_label.setText(Main.langBundle.getString("Address") + ":");
        postal_label.setText(Main.langBundle.getString("Postal") + ":");
        state_prov_label.setText(Main.langBundle.getString("StateProv") + ":");
        country_label.setText(Main.langBundle.getString("Country") + ":");
        close_button.setText(Main.langBundle.getString("Close"));
        save_button.setText(Main.langBundle.getString("Save"));
    }
}
