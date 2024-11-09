package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.DivisionDAO;
import helper.Alerts;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import model.Country;
import model.FirstLevelDivision;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

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

    public void onCustSave(ActionEvent actionEvent) throws SQLException {
        String name = cust_name_box.getText();
        String phone = phone_box.getText();
        String address = address_box.getText();
        String postal = postal_box.getText();
        FirstLevelDivision division = (FirstLevelDivision) state_prov_combo.getValue();
        if (checkEmpty()) {
            try {
                CustomerDAO.createCustomer(5, name, phone, address, postal, division);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        onClose(actionEvent);
    }

    /**
     * Closes the add appointment stage without saving.
     * @param actionEvent on pressing the close button.
     */

    public void onClose(ActionEvent actionEvent) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    public boolean checkEmpty() {
        TextField[] fields = {cust_name_box, phone_box, address_box, postal_box};
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                Alerts.getError(6);
                return false;
            }
        }
        return true;
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
            state_prov_combo.setConverter(new StringConverter<FirstLevelDivision>() {
                @Override
                public String toString(FirstLevelDivision division) { return (division != null) ? division.getDivisionName() : ""; }

                @Override
                public FirstLevelDivision fromString(String s) {
                    return null;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads all associated divisions with a given country.
     */

    public void loadDivisions() {
        Country country = (Country) country_combo.getValue();
        try {
            state_prov_combo.setItems(DivisionDAO.getAllCountryDivisions(country));
            state_prov_combo.getSelectionModel().select(0);

            // Converter to show country name rather than object name.
            // Implementation found here: https://stackoverflow.com/questions/73084380/javafx-combobox-show-attribute-of-element
            state_prov_combo.setConverter(new StringConverter<FirstLevelDivision>() {
                @Override
                public String toString(FirstLevelDivision division) { return (division != null) ? division.getDivisionName() : ""; }

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
        country_combo.setItems(CountryDAO.getCountryList());

        // Converter to show country name rather than object name.
        // Implementation found here: https://stackoverflow.com/questions/73084380/javafx-combobox-show-attribute-of-element
        country_combo.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return (country != null) ? country.getCountryName() : "";
            }

            @Override
            public Country fromString(String s) {
                return null;
            }
        });
    }
}
