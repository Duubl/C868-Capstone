package DAO;

import helper.DatabaseDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CustomerDAO {

    /**
     * Gets all the customer data from the local database and adds it to an ObservableList.
     * @return customer_list a list of all customers in the database
     * @throws SQLException
     */

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        String query = "SELECT * FROM customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();

        ObservableList<Customer> customer_list = FXCollections.observableArrayList();

        while (result.next()) {
            int customer_id = result.getInt("Customer_ID");
            String customer_name = result.getString("Customer_Name");
            String customer_address = result.getString("Address");
            String customer_postal = result.getString("Postal_Code");
            String customer_phone = result.getString("Phone");
            LocalDateTime last_update = result.getTimestamp("Last_Update").toLocalDateTime();
            String last_updated_by = result.getString("Last_Updated_By");
            int division_id = result.getInt("Division_ID");
            String division = result.getString("Division");
            String country = result.getString("Country");
            Customer customer = new Customer(customer_id, customer_name, customer_address, customer_postal, customer_phone, last_update, last_updated_by, division_id, division, country);
            customer_list.add(customer);
        }
        return customer_list;
    }
}
