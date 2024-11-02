package controller;

import DAO.CountryDAO;
import DAO.DivisionDAO;
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
import model.FirstLevelDivision;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController extends AddCustomerController implements Initializable {

    // Labels
    @FXML protected Label modify_cust_label;

    /**
     * Gets the index in the list of divisions.
     * @param customer the customer to find the division index for.
     * @return an integer value indicating the divisions index.
     */

    public int getCustomerDivisionIndex(Customer customer) {
        String division_name = customer.getCustomerDivisionName();
        int i = 0;
        for (FirstLevelDivision d : DivisionDAO.getAllCountryDivisions(customer.getCountry())) {
            if (d.getDivisionName().equals(division_name)) {
                return i;
            } else {
                i++;
            }
        }
        return -1;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Customer to be modified
        Customer selected = GUIController.getCustomerToModify();

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

        // Automatically select the country & division the customer is a part of.
        country_combo.getSelectionModel().select(selected.getCountry().getCountryID() - 1);
        loadDivisions();
        state_prov_combo.getSelectionModel().select(getCustomerDivisionIndex(selected));
    }
}
