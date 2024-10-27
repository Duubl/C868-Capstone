package DAO;

import helper.DatabaseDriver;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DivisionDAO {

    /**
     * Prepares a statement to get all first level divisions from the database
     * @return division_list a list of all first level divisions
     */

    public static ObservableList<FirstLevelDivision> getDivisionList() {
        ObservableList<FirstLevelDivision> divisions_list = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM first_level_divisions";
            PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int division_id = result.getInt("Division_ID");
                String division = result.getString("Division");
                LocalDateTime created_date = result.getTimestamp("Create_Date").toLocalDateTime();
                String created_by = result.getString("Created_By");
                LocalDateTime last_update = result.getTimestamp("Last_Update").toLocalDateTime();
                String last_updated_by = result.getString("Last_Updated_By");
                int country_id = result.getInt("Country_ID");

                FirstLevelDivision divisions = new FirstLevelDivision(division_id, division, created_date, created_by, last_update, last_updated_by);
                divisions_list.add(divisions);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return divisions_list;
    }
}
