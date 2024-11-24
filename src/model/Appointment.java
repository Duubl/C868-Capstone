package model;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Appointment {
    private int appointment_id;
    private String appointment_title;
    private String appointment_description;
    private String appointment_location;
    private String appointment_type;
    private LocalDateTime start_date_time;
    private LocalDateTime end_date_time;
    private String created_by;
    private LocalDateTime last_update;
    private String last_updated_by;
    private int customer_id;
    private int user_id;
    private int contact_id;

    public Appointment(int appointment_id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, String created_by, LocalDateTime last_update, String last_updated_by, int customer_id, int user_id, int contact_id) {
        this.appointment_id = appointment_id;
        this.appointment_title = title;
        this.appointment_description = description;
        this.appointment_location = location;
        this.appointment_type = type;
        this.start_date_time = start;
        this.end_date_time = end;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
    }

    /**
     * Gets the appointment ID
     * @return appointment_id the ID for the appointment
     */

    public int getAppointmentID() {
        return appointment_id;
    }

    /**
     * Sets the appointment ID
     * @param appointment_id the ID for the appointment
     */

    public void setAppointmentID(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    /**
     * Gets the appointment title
     * @return appointment_title the title for the appointment
     */

    public String getAppointmentTitle() {
        return appointment_title;
    }

    /**
     * Sets the appointment title
     * @param appointment_title the title for the appointment
     */

    public void setAppointmentTitle(String appointment_title) {
        this.appointment_title = appointment_title;
    }

    /**
     * Gets the appointment description
     * @return appointment_description the description for the appointment
     */

    public String getAppointmentDescription() {
        return appointment_description;
    }

    /**
     * Sets the appointment description
     * @param appointment_description the description for the appointment
     */

    public void setAppointmentDescription(String appointment_description) {
        this.appointment_description = appointment_description;
    }

    /**
     * Gets the appointment location
     * @return appointment_location the location for the appointment
     */

    public String getAppointmentLocation() {
        return appointment_location;
    }

    /**
     * Sets the appointment location
     * @param appointment_location the location for the appointment
     */

    public void setAppointmentLocation(String appointment_location) {
        this.appointment_location = appointment_location;
    }

    /**
     * Gets the appointment type
     * @return appointment_type the type for the appointment
     */

    public String getAppointmentType() {
        return appointment_type;
    }

    /**
     * Sets the appointment type
     * @param appointment_type the type for the appointment
     */

    public void setAppointmentType(String appointment_type) {
        this.appointment_type = appointment_type;
    }

    /**
     * Gets the appointment start date & time
     * @return start_date_time the appointment start date & time
     */

    public LocalDateTime getStartDateTime() {
        return start_date_time;
    }

    /**
     * Gets the local appointment start date & time
     * @return local_start_date_time the appointment local start date & time
     */

    public LocalDateTime getLocalStartDateTime() {
        return this.getStartDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Sets the appointment start date & time
     * @param start_date_time the appointment start date & time
     */

    public void setStartDateTime(LocalDateTime start_date_time) {
        this.start_date_time = start_date_time;
    }

    /**
     * Gets the appointment end date & time
     * @return end_date_time the appointment end date & time
     */

    public LocalDateTime getEndDateTime() {
        return end_date_time;
    }

    /**
     * Gets the local appointment end date & time
     * @return local_end_date_time the appointment local end date & time
     */

    public LocalDateTime getLocalEndDateTime() {
        return this.getEndDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Sets the appointment end date & time
     * @param end_date_time the appointment end date & time
     */

    public void setEndDateTime(LocalDateTime end_date_time) {
        this.end_date_time = end_date_time;
    }

    /**
     * Gets customer ID
     * @return customer_id the customer's ID
     */

    public int getCustomerID() {
        return customer_id;
    }

    /**
     * Gets the customer ID
     * @param customer_id the customer'd ID
     */

    public void setCustomerID(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Gets the user id
     * @return user_id the user id
     */

    public int getUserID() {
        return user_id;
    }

    /**
     * Sets the user id
     * @param user_id the user id
     */

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Gets the contact ID
     * @return contact_id the contact's ID
     */

    public int getContactID() {
        return contact_id;
    }

    /**
     * Sets the contact ID
     * @param contact_id the contact's ID
     */

    public void setContactID(int contact_id) {
        this.contact_id = contact_id;
    }

    /**
     * Gets the name of the person who created the appointment
     * @return created_by the name of the person who created the appointment
     */

    public String getCreatedBy() {
        return created_by;
    }

    /**
     * Sets the name of the person who created the appointment
     * @param created_by the name of the person who created the appointment
     */

    public void setCreatedBy(String created_by) {
        this.created_by = created_by;
    }

    /**
     * Gets date and time of the person who last updated the appointment
     * @return last_update the date and time when the appointment was last updated
     */

    public LocalDateTime getLastUpdate() {
        return last_update;
    }

    /**
     * Sets date and time of the person who last updated the appointment
     * @param last_update the date and time when the appointment was last updated
     */

    public LocalDateTime setLastUpdate(LocalDateTime last_update) {
        return this.last_update = last_update;
    }

    /**
     * Gets the name of the person who last updated the appointment
     * @return last_updated_by the name of the person who last updated the appointment
     */

    public String getLastUpdatedBy() {
        return last_updated_by;
    }

    /**
     * Sets the name of the person who last updated the appointment
     * @param last_updated_by the name of the person who last updated the appointment
     */

    public void setLastUpdatedBy(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }
}
