package DAO;

import helper.DatabaseDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class ContactDAO {

    /**
     * Prepares a statement to get all contacts from the local database.
     * @return contact_list a list of all contacts.
     */

    public static ObservableList<Contact> getContactList() {
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
        contact_list.sort(Comparator.comparing(Contact::getContactID));
        return contact_list;
    }

    /**
     * Creates a new contact with the information provided in the database
     * @param id the id for the new contact
     * @param name the name for the new contact
     * @param email the email for the new contact
     * @throws SQLException
     */

    public static void createContact(int id, String name, String email) throws SQLException {
        String query = "INSERT INTO contacts VALUES (?, ?, ?)";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setString(3, email);
        statement.executeUpdate();
    }

    /**
     * Updates the contact with the information provided in the database.
     * @param id the id for the contact
     * @param name the name for the contact
     * @param email the email for the contact
     * @throws SQLException
     */

    public static void updateContact(int id, String name, String email) throws SQLException {
        String query = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ?";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setString(2, email);
        statement.setInt(3, id);
        statement.executeUpdate();
    }

    /**
     * Deletes the given contact.
     * @param contact the contact to be deleted.
     * @throws SQLException
     */

    public static void deleteContact(Contact contact) throws SQLException {
        int contact_id = contact.getContactID();
        String query = "DELETE FROM contacts WHERE Contact_ID = ?";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setInt(1, contact_id);
        statement.executeUpdate();
    }

    /**
     * Gets all appointments assigned to the given contact
     * @param contact the contact to be checked for appointments
     * @return an ObservableList of all the appointments assigned to the contact
     * @throws SQLException
     */

    public static ObservableList<Appointment> getContactAppointments(Contact contact) throws SQLException {
        ObservableList<Appointment> all_appointments = AppointmentDAO.getAppointmentList();
        return FXCollections.observableArrayList(all_appointments.stream().filter(appointment -> appointment.getContactID() == contact.getContactID()).toList());
    }

    /**
     * Returns the number of appointments assigned to a given contact
     * @param contact the contact to be checked for appointments
     * @return an integer representing the number of appointments assigned to a given contact
     * @throws SQLException
     */

    public static int getAppointmentCount(Contact contact) throws SQLException {
        ObservableList<Appointment> all_appointments = getContactAppointments(contact);
        return all_appointments.size();
    }

    /**
     * Gets a unique contact ID based on the contacts in the database.
     * @return id the new unqiue ID.
     * @throws SQLException
     */

    public static int getUniqueContactID() {
        ObservableList<Contact> contact_list = getContactList();
        Set<Integer> existing_ids = contact_list.stream().map(Contact::getContactID).collect(Collectors.toSet());
        int id = 1;
        while (existing_ids.contains(id)) {
            id++;
        }
        return id;
    }
}
