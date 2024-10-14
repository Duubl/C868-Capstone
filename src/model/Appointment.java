package model;

import java.time.LocalDateTime;

public class Appointment {
    private int appointment_id;
    private String appointment_title;
    private String appointment_description;
    private String appointment_location;
    private String appointment_type;
    private LocalDateTime start_date_time;
    private LocalDateTime end_date_time;
    private int customer_id;
    private int username;
    private int contact_id;

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
     * Gets the username
     * @return username the username
     */

    public int getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username the username
     */

    public void setUsername(int username) {
        this.username = username;
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
}
