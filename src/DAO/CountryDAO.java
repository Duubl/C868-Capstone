package DAO;

import helper.DatabaseDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CountryDAO {

    /**
     * Prepares a statement to get all countries from the local database.
     * @return country_list a list of all countries.
     */

    public static ObservableList<Country> getCountryList() {
        ObservableList<Country> country_list = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM countries";
            PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int country_id = result.getInt("Country_ID");
                String country = result.getString("Country");
                LocalDateTime created_date = result.getTimestamp("Create_Date").toLocalDateTime();
                String created_by = result.getString("Created_By");
                LocalDateTime last_update = result.getTimestamp("Last_Update").toLocalDateTime();
                String last_updated_by = result.getString("Last_Updated_By");
                Country countries = new Country(country_id, country, created_date, created_by, last_update, last_updated_by);
                country_list.add(countries);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return country_list;
    }

    /**
     * Gets a country by name.
     * @param name the name of the country to be searched for.
     * @return the country with the given name.
     */

    public static Country getCountry(String name) {
        for (Country country : getCountryList()) {
            if (name.equals(country.getCountryName())) {
                return country;
            }
        }
        return null;
    }
}
