package DAO;

import helper.DatabaseDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CountryDAO {

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
}
