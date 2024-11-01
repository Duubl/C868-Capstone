package controller;

import DAO.CountryDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.Main;
import model.Country;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController extends AddCustomerController implements Initializable {

    // Labels
    @FXML protected Label modify_cust_label;

    // Customer to be modified
    private Customer selected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selected = GUIController.getCustomerToModify();

        cust_name_box.setText(selected.getCustomerName());
        phone_box.setText(selected.getCustomerPhone());
        address_box.setText(selected.getCustomerAddress());
        postal_box.setText(selected.getCustomerPostalCode());

        country_combo.setItems(CountryDAO.getCountryList());

        // Converter to show country name rather than object name.
        // Implementation found here: https://stackoverflow.com/questions/73084380/javafx-combobox-show-attribute-of-element
        country_combo.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country.getCountryName();
            }

            @Override
            public Country fromString(String s) {
                return null;
            }
        });
        // Automatically select the country the customer is a part of.
        country_combo.getSelectionModel().select(0);

        add_cust_label.setText(Main.lang_bundle.getString("ModifyCustomer"));
        cust_name_label.setText(Main.lang_bundle.getString("CustName") + ":");
        phone_label.setText(Main.lang_bundle.getString("Phone") + ":");
        address_label.setText(Main.lang_bundle.getString("Address") + ":");
        postal_label.setText(Main.lang_bundle.getString("Postal") + ":");
        state_prov_label.setText(Main.lang_bundle.getString("StateProv") + ":");
        country_label.setText(Main.lang_bundle.getString("Country") + ":");
        close_button.setText(Main.lang_bundle.getString("Close"));
        save_button.setText(Main.lang_bundle.getString("Save"));
    }
}
