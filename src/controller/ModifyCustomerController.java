package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController extends AddCustomerController implements Initializable {

    // Labels
    @FXML protected Label modify_cust_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_cust_label.setText(Main.langBundle.getString("ModifyCustomer"));
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
