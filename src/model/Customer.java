package model;

import java.time.LocalDateTime;

public class Customer {

    private int customer_id;
    private String customer_name;
    private String customer_address;
    private String customer_postal_code;
    private String customer_phone;
    private LocalDateTime created_at;
    private String created_by;
    private LocalDateTime last_updated;
    private String last_updated_by;
    private int customer_division_id;

    public Customer(int customer_id, String customer_name, String customer_address, String customer_postal, String customer_phone, LocalDateTime last_updated, String last_updated_by, int division_id) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_address = customer_address;
        this.customer_postal_code = customer_postal;
        this.customer_phone = customer_phone;
        this.last_updated = last_updated;
        this.last_updated_by = last_updated_by;
        this.customer_division_id = division_id;
    }

    /**
     * Gets the customer ID
     * @return customer_id
     */

    public int getCustomerID() { return customer_id; }

    /**
     * Sets the customer ID
     * @return customer_id
     */

    public void setCustomerID(int customer_id) { this.customer_id = customer_id; }

    /**
     * Gets the customer name
     * @return customer_name
     */

    public String getCustomerName() {
        return customer_name;
    }

    /**
     * Sets the customer name
     * @param customer_name customer's name
     */

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }

    /**
     * Gets the customer address
     * @return customer_address the customer's address
     */

    public String getCustomerAddress() {
        return customer_address;
    }

    /**
     * Sets the customer address
     * @param customer_address customer's address
     */

    public void setCustomerAddress(String customer_address) {
        this.customer_address = customer_address;
    }

    /**
     * Gets the customer postal code
     * @return customer_postal_code customer's postal code
     */

    public String getCustomerPostalCode() {
        return customer_postal_code;
    }

    /**
     * Sets the customer postal code
     * @param customer_postal_code customer's postal code
     */

    public void setCustomerPostalCode(String customer_postal_code) {
        this.customer_postal_code = customer_postal_code;
    }

    /**
     * Gets the customer phone
     * @return customer_phone customer's phone number
     */

    public String getCustomerPhone() {
        return customer_phone;
    }

    /**
     * Sets the customer's phone number
     * @param customer_phone customer's phone number
     */

    public void setCustomerPhone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    /**
     * Gets the customer record created at time
     * @return created_at the time the customer record was created
     */

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    /**
     * Sets the time the customer's record was created
     * @param created_at the time the record was created
     */

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    /**
     * Gets the person the customer's record was created by
     * @return created_by the person who created the customer record
     */

    public String getCreatedBy() {
        return created_by;
    }

    /**
     * Sets the person who created the customer's record
     * @param created_by the person who created the record
     */

    public void setCreatedBy(String created_by) {
        this.created_by = created_by;
    }

    /**
     * Gets the last time the customer's record was updated
     * @return last_updated the last time the record was updated
     */

    public LocalDateTime getLastUpdated() {
        return last_updated;
    }

    /**
     * Sets the last time the customer's record was updated
     * @param last_updated the last time the record was updated
     */

    public void setLastUpdated(LocalDateTime last_updated) {
        this.last_updated = last_updated;
    }

    /**
     * Gets the last person who updated the customer's record
     * @return last_updated_by the person who last updated the customer's record
     */

    public String getLastUpdatedBy() {
        return last_updated_by;
    }

    /**
     * Sets the last person who updated the customer's record
     * @param last_updated_by the person who last updated the customer's record
     */

    public void setLastUpatedBy(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    /**
     * Gets the customer's division ID
     * @return customer_division_id the division ID for the customer
     */

    public int getCustomerDivisionID() {
        return customer_division_id;
    }

    /**
     * Sets the customer's division ID
     * @param customer_division_id the division ID for the customer
     */

    public void setCustomerDivisionID(int customer_division_id) {
        this.customer_division_id = customer_division_id;
    }
}
