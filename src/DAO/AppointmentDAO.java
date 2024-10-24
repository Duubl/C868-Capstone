package DAO;

import helper.DatabaseDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AppointmentDAO {

    /**
     * Gets all the appointment data from the local database and adds it to an ObservableList.
     * @return appointment_list a list of all the appointments in the database
     * @throws SQLException
     */

    public static ObservableList<Appointment> getAppointmentList() throws SQLException {
        String query = "SELECT * FROM appointments";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();

        ObservableList<Appointment> appointment_list = FXCollections.observableArrayList();

        while (result.next()) {
            int appointment_id = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
            String created_by = result.getString("Created_By");
            LocalDateTime last_update = result.getTimestamp("Last_Update").toLocalDateTime();
            String last_updated_by = result.getString("Last_Updated_By");
            int customer_id = result.getInt("Customer_ID");
            int user_id = result.getInt("User_ID");
            int contact_id = result.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointment_id, title, description, location, type, start, end, created_by, last_update, last_updated_by, customer_id, user_id, contact_id);
            appointment_list.add(appointment);
        }
        return appointment_list;
    }

    /**
     * Creates a list of all the appointments assigned to a user with the given id.
     * @return user_appointments all the appointments assigned to a user with the given id
     * @param id the user id to be searched for
     * @throws SQLException
     */

    public static ObservableList<Appointment> getUserAppointments(int id) throws SQLException {
        ObservableList<Appointment> appointment_list = getAppointmentList();
        ObservableList<Appointment> user_appointments = FXCollections.observableArrayList();
        for (Appointment appointment : appointment_list) {
            if (appointment.getUserID() == id) {
                user_appointments.add(appointment);
            }
        }
        return user_appointments;
    }
}
