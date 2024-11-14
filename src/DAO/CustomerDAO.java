package DAO;

import helper.DatabaseDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Set;
import java.util.stream.Collectors;

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
            FirstLevelDivision division = DivisionDAO.getDivision(result.getString("Division"));
            Country country = CountryDAO.getCountry(result.getString("Country"));
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
     * @param division the new division
     * @throws SQLException
     */

    public static void updateCustomer(int customer_id, String name, String address, String postal, String phone, FirstLevelDivision division) throws SQLException {
        int division_id = division.getDivisionID();
        String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Updated_By = ?, Last_Update = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setString(2, address);
        statement.setString(3, postal);
        statement.setString(4, phone);
        statement.setString(5, UserDAO.getCurrentUser().getUsername());
        statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
        statement.setInt(7, division_id);
        statement.setInt(8, customer_id);
        statement.executeUpdate();
    }

    /**
     * Creates a customer with the information provided in the database
     * @param customer_id the customer id to be updated
     * @param customer_name the new name to be updated
     * @param customer_address the new address to be updated
     * @param customer_postal the new postal to be updated
     * @param customer_phone the new phone to be updated
     * @param division the new division
     * @throws SQLException
     */

    public static void createCustomer(int customer_id, String customer_name, String customer_address, String customer_postal, String customer_phone, FirstLevelDivision division) throws SQLException {
        String last_updated_by = UserDAO.getCurrentUser().getUsername();
        int division_id = division.getDivisionID();

        String query = "INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setInt(1, customer_id);
        statement.setString(2, customer_name);
        statement.setString(3, customer_address);
        statement.setString(4, customer_postal);
        statement.setString(5, customer_phone);
        statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
        statement.setString(7, last_updated_by);
        statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
        statement.setString(9, last_updated_by);
        statement.setInt(10, division_id);
        statement.executeUpdate();
    }

    /**
     * Deletes the given customer.
     * @param customer the customer to be deleted.
     * @throws SQLException
     */

    public static void deleteCustomer(Customer customer) throws SQLException {
        int customer_id = customer.getCustomerID();
        String query = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setInt(1, customer_id);
        statement.executeUpdate();
    }

    /**
     * Gets a unique customer ID based on the customers in the database.
     * @return id the new unqiue ID.
     * @throws SQLException
     */

    public static int getUniqueCustomerID() throws SQLException {
        ObservableList<Customer> customer_list = getAllCustomers();
        Set<Integer> existing_ids = customer_list.stream().map(Customer::getCustomerID).collect(Collectors.toSet());
        int id = 1;
        while (existing_ids.contains(id)) {
            id++;
        }
        return id;
    }
}
