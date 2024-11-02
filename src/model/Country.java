package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Country {
    private int country_id;
    private String country_name;
    private LocalDateTime create_date;
    private String created_by;
    private LocalDateTime last_updated;
    private String last_updated_by;

    public Country(int country_id, String country, LocalDateTime created_date, String created_by, LocalDateTime last_updated, String last_updated_by) {
        this.country_id = country_id;
        this.country_name = country;
        this.create_date = created_date;
        this.created_by = created_by;
        this.last_updated = last_updated;
        this.last_updated_by = last_updated_by;
    }

    /**
     * Gets the country ID
     * @return country_id the country id
     */

    public int getCountryID() { return country_id; }

    /**
     * Sets the country ID
     * @param country_id the country ID
     */

    public void setCountryID(int country_id) {
        this.country_id = country_id;
    }

    /**
     * Gets the country name
     * @return country_name the country name
     */

    public String getCountryName() {
        return country_name;
    }

    /**
     * Sets the country name
     * @param country_name the country name
     */

    public void setCountryName(String country_name) {
        this.country_name = country_name;
    }

    /**
     * Gets the creation date for the country
     * @return create_date the date of creation
     */

    public LocalDateTime getCreateDate() {
        return create_date;
    }

    /**
     * Sets the creation date for the country
     * @param create_date the date of creation
     */

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    /**
     * Gets the name of the person who created the country
     * @return created_by the name of the person who created the country
     */

    public String getCreatedBy() {
        return created_by;
    }

    /**
     * Sets the name of the person who created the country
     * @param created_by the name of the person who created the country
     */

    public void setCreatedBy(String created_by) {
        this.created_by = created_by;
    }

    /**
     * Gets the date & time the country was last updated
     * @return last_updated the date & time the country was last updated
     */

    public LocalDateTime getLastUpdated() {
        return last_updated;
    }

    /**
     * Sets the date & time the country was last updated
     * @param last_updated the date & time the country was last updated
     */

    public void setLast_updated(LocalDateTime last_updated) {
        this.last_updated = last_updated;
    }

    /**
     * Gets the name of the last person who updated the country
     * @return last_updated_by the name of the last person who updated the country
     */

    public String getLastUpdatedBy() {
        return last_updated_by;
    }

    /**
     * Sets the name of the last person who updated the country
     * @param last_updated_by the name of the last person who updated the country
     */

    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }
}
