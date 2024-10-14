package model;

public class FirstLevelDivision {
    private int division_id;
    private String division_name;
    private int country_id;

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
}
