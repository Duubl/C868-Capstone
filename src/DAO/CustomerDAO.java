package DAO;

import helper.DatabaseDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    /**
     * Updates the customer with the information provided in the database.
     * @param customer_id the customer id to be updated
     * @param name the new name to be updated
     * @param address the new address to be updated
     * @param postal the new postal to be updated
     * @param phone the new phone to be updated
     * @param last_updated_by the last person who updated the contact
     * @param last_update the new date of last update
     * @param division_id the new division id
     * @throws SQLException
     */

    // TODO: Get current user's username and input it as the last_updated_by value. Get current timestamp and update it as the last_update value.
    public static void updateCustomer(int customer_id, String name, String address, String postal, String phone, String last_updated_by, LocalDateTime last_update, int division_id) throws SQLException {
        String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Updated_By = ?, Last_Update = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setString(2, address);
        statement.setString(3, postal);
        statement.setString(4, phone);
        statement.setString(5, last_updated_by);
        statement.setTimestamp(6, Timestamp.valueOf(last_update));
        statement.setInt(7, division_id);
        statement.setInt(8, customer_id);
        statement.executeUpdate();
    }
}
