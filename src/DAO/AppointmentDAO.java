package DAO;

import helper.Alerts;
import helper.DatabaseDriver;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class AppointmentDAO {

    /**
     * Gets all the appointment data from the local database and adds it to an ObservableList.
     *
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
        appointment_list.sort(Comparator.comparing(Appointment::getAppointmentID));
        return appointment_list;
    }

    /**
     * Creates an appointment with the information provided in the database
     *
     * @param appointment_id the id for the appointment
     * @param title          the title of the appointment
     * @param desc           the description for the appointment
     * @param location       the location of the appointment
     * @param type           the type of appointment
     * @param contact        the contact assigned to the appointment
     * @param start          the start time & date of the appointment
     * @param end            the end time & date of the appointment
     * @param customer       the customer assigned to the appointment
     * @throws SQLException
     */

    public static void createAppointment(int appointment_id, String title, String desc, String location, String type, Contact contact, LocalDateTime start, LocalDateTime end, User user, Customer customer) throws SQLException {
        String last_updated_by = UserDAO.getCurrentUser().getUsername();
        int user_id = user.getUserID();
        int contact_id = contact.getContactID();
        int customer_id = customer.getCustomerID();

        String query = "INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setInt(1, appointment_id);
        statement.setString(2, title);
        statement.setString(3, desc);
        statement.setString(4, location);
        statement.setString(5, type);
        statement.setTimestamp(6, Timestamp.valueOf(start));
        statement.setTimestamp(7, Timestamp.valueOf(end));
        statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
        statement.setString(9, last_updated_by);
        statement.setTimestamp(10, Timestamp.valueOf(LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
        statement.setString(11, last_updated_by);
        statement.setInt(12, customer_id);
        statement.setInt(13, user_id);
        statement.setInt(14, contact_id);
        statement.executeUpdate();
    }

    /**
     * Updates an appointment with the information provided in the database
     *
     * @param appointment_id the id for the appointment
     * @param title          the title of the appointment
     * @param desc           the description for the appointment
     * @param location       the location of the appointment
     * @param type           the type of appointment
     * @param contact        the contact assigned to the appointment
     * @param start          the start time & date of the appointment
     * @param end            the end time & date of the appointment
     * @param customer       the customer assigned to the appointment
     * @throws SQLException
     */

    public static void updateAppointment(int appointment_id, String title, String desc, String location, String type, Contact contact, LocalDateTime start, LocalDateTime end, User user, Customer customer) throws SQLException {
        String last_updated_by = UserDAO.getCurrentUser().getUsername();
        int user_id = user.getUserID();
        int contact_id = contact.getContactID();
        int customer_id = customer.getCustomerID();

        String query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, desc);
        statement.setString(3, location);
        statement.setString(4, type);
        statement.setTimestamp(5, Timestamp.valueOf(start));
        statement.setTimestamp(6, Timestamp.valueOf(end));
        statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
        statement.setString(8, last_updated_by);
        statement.setInt(9, customer_id);
        statement.setInt(10, user_id);
        statement.setInt(11, contact_id);
        statement.setInt(12, appointment_id);
        statement.executeUpdate();
    }

    /**
     * Creates a list of all the appointments assigned to a user.
     *
     * @param user the user to be searched for.
     * @return user_appointments all the appointments assigned to a user.
     * @throws SQLException
     */

    public static ObservableList<Appointment> getUserAppointments(User user) throws SQLException {
        ObservableList<Appointment> appointment_list = getAppointmentList();
        ObservableList<Appointment> user_appointments = FXCollections.observableArrayList();
        for (Appointment appointment : appointment_list) {
            if (appointment.getUserID() == user.getUserID()) {
                user_appointments.add(appointment);
            }
        }
        user_appointments.sort(Comparator.comparing(Appointment::getAppointmentID));
        return user_appointments;
    }

    /**
     * Deletes the given appointment.
     *
     * @param appointment the appointment to be deleted.
     * @throws SQLException
     */

    public static void deleteAppointment(Appointment appointment) throws SQLException {
        int appointment_id = appointment.getAppointmentID();
        String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setInt(1, appointment_id);
        statement.executeUpdate();
    }

    /**
     * Gets a unique appointment ID based on the appointments in the database.
     *
     * @return id the new unqiue ID.
     * @throws SQLException
     */

    public static int getUniqueAppointmentID() throws SQLException {
        ObservableList<Appointment> appointment_list = getAppointmentList();
        Set<Integer> existing_ids = appointment_list.stream().map(Appointment::getAppointmentID).collect(Collectors.toSet());
        int id = 1;
        while (existing_ids.contains(id)) {
            id++;
        }
        return id;
    }

    /**
     * Checks if another appointment exists at the times for the current user.
     *
     * @param start the start time to check.
     * @param end   the end time to check.
     * @return true an appointment exists, false otherwise.
     * @throws SQLException
     */

    public static boolean appointmentExistsAtTime(int id, LocalDateTime start, LocalDateTime end) throws SQLException {
        ObservableList<Appointment> appointment_list = getUserAppointments(UserDAO.getCurrentUser());
        for (Appointment appointment : appointment_list) {
            if (appointment.getAppointmentID() == id) {
                continue;
            }
            if (appointment.getStartDateTime().isBefore(end) && appointment.getEndDateTime().isAfter(start)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an appointment is within 15 minutes of the user's local time.
     *
     * @return true if an appointment is found 15 or fewer minutes after the user's current local time.
     * @throws SQLException
     */

    public static Appointment appointmentSoon() throws SQLException {
        ObservableList<Appointment> appointments = getUserAppointments(UserDAO.getCurrentUser());
        LocalDateTime local = ZonedDateTime.now(ZoneId.systemDefault()).toLocalDateTime();
        for (Appointment appointment : appointments) {
            LocalDateTime appointment_local_time = appointment.getLocalStartDateTime();
            Duration duration = Duration.between(local, appointment_local_time);
            long minutes_to_appointment = duration.toMinutes();
            if (minutes_to_appointment >= 0 && minutes_to_appointment <= 15) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * Iterates through all the appointments for a given user and returns the ones in the current week.
     *
     * @return an ObservableList containing all the weekly appointments.
     * @throws SQLException
     */

    public static ObservableList<Appointment> getWeeklyAppointments() throws SQLException {
        ObservableList<Appointment> all_appointments = getUserAppointments(UserDAO.getCurrentUser());
        LocalDate today = LocalDate.now();
        LocalDate week_start = today.with(DayOfWeek.MONDAY);
        LocalDate week_end = today.with(DayOfWeek.SUNDAY);
        return FXCollections.observableArrayList(all_appointments.stream()
                .filter(appointment -> !appointment.getStartDateTime().toLocalDate().isBefore(week_start) &&
                        !appointment.getStartDateTime().toLocalDate().isAfter(week_end)).collect(Collectors.toList()));
    }

    /**
     * Iterates through all the appointments for a given user and returns the ones in the current month.
     *
     * @return an ObservableList containing all the monthly appointments.
     * @throws SQLException
     */

    public static ObservableList<Appointment> getMonthlyAppointments() throws SQLException {
        ObservableList<Appointment> all_appointments = getUserAppointments(UserDAO.getCurrentUser());
        LocalDate today = LocalDate.now();
        int current_month = today.getMonthValue();
        int current_year = today.getYear();
        return FXCollections.observableArrayList(all_appointments.stream()
                .filter(appointment -> appointment.getStartDateTime().toLocalDate().getMonthValue() == current_month &&
                        appointment.getStartDateTime().toLocalDate().getYear() == current_year).toList());
    }

    /**
     * Creates a list of all the total appointments for a given month
     * @return month_totals the total appointments for a given month sorted by month value
     * @throws SQLException
     */

    public static ObservableList<Integer> getMonthlyAppointmentTotals() throws SQLException {
        ObservableList<Appointment> appointment_list = getAppointmentList();
        ObservableList<Integer> month_totals = FXCollections.observableArrayList();
        for (Month month : Month.values()) {
            int month_total = 0;
            for (Appointment appointment : appointment_list) {
                if (appointment.getStartDateTime().getMonth().equals(month)) {
                    month_total++;
                }
            }
            month_totals.add(month_total);
        }
        return month_totals;
    }

    /**
     * Gets all the types of appointments.
     *
     * @return ObservableArrayList containing all the appointment types.
     * @throws SQLException
     */

    public static ObservableList<String> getAppointmentTypes() throws SQLException {
        ObservableList<Appointment> appointment_list = getAppointmentList();
        return FXCollections.observableArrayList(appointment_list.stream().map(Appointment::getAppointmentType).collect(Collectors.toList()));
    }
}
