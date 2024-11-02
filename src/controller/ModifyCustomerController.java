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

    // Customer to be modified
    private Customer selected;

    /**
     * Gets the index in the list of countries.
     * @param customer the customer's country to find the index for.
     * @return an integer value indicating the countries index.
     */

    public int getCustomerCountryIndex(Customer customer) {
        return switch (customer.getCountry().getCountryName()) {
            case "UK" -> 1;
            case "Canada" -> 2;
            case "U.S" -> 3;
            default -> 0;
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selected = GUIController.getCustomerToModify();

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
        country_combo.getSelectionModel().select(getCustomerCountryIndex(selected));
        loadDivisions();
    }
}
