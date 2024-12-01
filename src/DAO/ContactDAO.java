package DAO;

import helper.DatabaseDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

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
}
