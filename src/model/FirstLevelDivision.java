package model;

import java.time.LocalDateTime;

public class FirstLevelDivision {
    private int division_id;
    private String division_name;
    private LocalDateTime created_date;
    private String created_by;
    private LocalDateTime last_updated;
    private String last_updated_by;
    private int country_id;

    public FirstLevelDivision(int division_id, String division, LocalDateTime created_date, String created_by, LocalDateTime last_updated, String last_updated_by, int country_id) {
        this.division_id = division_id;
        this.division_name = division;
        this.created_date = created_date;
        this.created_by = created_by;
        this.last_updated = last_updated;
        this.last_updated_by = last_updated_by;
        this.country_id = country_id;
    }

    /**
     * Gets the division ID
     * @return division_id the ID for the division
     */

    public int getDivisionID() {
        return division_id;
    }

    /**
     * Sets the division ID
     * @param division_id the ID for the division
     */

    public void setDivisionID(int division_id) {
        this.division_id = division_id;
    }

    /**
     * Gets the division name
     * @return division_name the name of the division
     */

    public String getDivisionName() {
        return division_name;
    }

    /**
     * Gets the division name
     * @param division_name the name of the division
     */

    public void setDivisionName(String division_name) {
        this.division_name = division_name;
    }

    /**
     * Gets the country ID
     * @return country_id the ID for the country
     */

    public int getCountryID() {
        return country_id;
    }

    /**
     * Sets the division ID
     * @param country_id the ID for the country
     */

    public void setCountryID(int country_id) {
        this.country_id = country_id;
    }

    /**
     * Sets the creation date
     * @return create_date the date of creation
     */

    public LocalDateTime getCreateDate() {
        return created_date;
    }

    /**
     * Sets the creation date
     * @param created_date the date of creation
     */

    public void setCreateDate(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    /**
     * Gets the name of the person who created the division
     * @return created_by the person who created the division
     */

    public String getCreatedBy() {
        return created_by;
    }

    /**
     * Sets the name of the person who created the division
     * @param created_by the person who created the division
     */

    public void setCreatedBy(String created_by) {
        this.created_by = created_by;
    }

    /**
     * Gets the date & time the division was last updated
     * @return last_updated the date & time the division was last updated
     */

    public LocalDateTime getLastUpdated() {
        return last_updated;
    }

    /**
     * Sets the date & time the division was last updated
     * @param last_updated the date & time the division was last updated
     */

    public void setLastUpdated(LocalDateTime last_updated) {
        this.last_updated = last_updated;
    }

    /**
     * Gets the name of the person who last updated the division
     * @return last_updated_by the name of the person who last updated the division
     */

    public String getLastUpdatedBy() {
        return last_updated_by;
    }

    /**
     * Sets the name of the last person who updated the division
     * @param last_updated_by the name of the person who last updated the division
     */

    public void setLastUpdatedBy(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }
}
