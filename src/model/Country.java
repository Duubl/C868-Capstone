package model;

public class Country {
    private int country_id;
    private String country_name;

    /**
     * Gets the country ID
     * @return country_id the country id
     */

    public int getCountryID() {
        return country_id;
    }

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
}
