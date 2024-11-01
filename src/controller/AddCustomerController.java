package controller;

import DAO.CountryDAO;
import DAO.DivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.Main;
import model.Country;
import model.FirstLevelDivision;

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

    /**
     * Loads all associated divisions with a given country.
     * @param actionEvent event to load division combo with associated divisions of selected country.
     */

    public void loadDivisions(ActionEvent actionEvent) {
        Country country = (Country) country_combo.getValue();
        try {
            state_prov_combo.setItems(DivisionDAO.getAllCountryDivisions(country));
            state_prov_combo.getSelectionModel().select(0);
            // Converter to show country name rather than object name.
            // Implementation found here: https://stackoverflow.com/questions/73084380/javafx-combobox-show-attribute-of-element

            //TODO: Also automatically load divisions on first startup.
            state_prov_combo.setConverter(new StringConverter<FirstLevelDivision>() {
                @Override
                public String toString(FirstLevelDivision division) {
                    return (division != null ) ? division.getDivisionName() : "";
                }

                @Override
                public FirstLevelDivision fromString(String s) {
                    return null;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_cust_label.setText(Main.lang_bundle.getString("AddCustomer"));
        cust_name_label.setText(Main.lang_bundle.getString("CustName") + ":");
        phone_label.setText(Main.lang_bundle.getString("Phone") + ":");
        address_label.setText(Main.lang_bundle.getString("Address") + ":");
        postal_label.setText(Main.lang_bundle.getString("Postal") + ":");
        state_prov_label.setText(Main.lang_bundle.getString("StateProv") + ":");
        country_label.setText(Main.lang_bundle.getString("Country") + ":");
        close_button.setText(Main.lang_bundle.getString("Close"));
        save_button.setText(Main.lang_bundle.getString("Save"));

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
        country_combo.getSelectionModel().select(0);
    }
}
