package DAO;

import helper.DatabaseDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ContactDAO {
    public static ObservableList<Contact> getCountryList() {
        ObservableList<Contact> contact_list = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM contacts";
            PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int contact_id = result.getInt("Contact_ID");
                String contact_name = result.getString("Contact_Name");
                String email = result.getString("Email");

                Contact contact = new Contact(contact_id, contact_name, email);
                contact_list.add(contact);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contact_list;
    }
}
