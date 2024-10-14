package model;

public class Contact {
    private int contact_id;
    private String contact_name;
    private String contact_email;

    /**
     * Gets the contact ID
     * @return contact_id the contact's ID
     */

    public int getContactID() {
        return contact_id;
    }

    /**
     * Sets the contact ID
     * @param contact_id the contatct's ID
     */

    public void setContactID(int contact_id) {
        this.contact_id = contact_id;
    }

    /**
     * Gets the contact name
     * @return contact_name the contact's name
     */

    public String getContactName() {
        return contact_name;
    }

    /**
     * Sets the contact name
     * @param contact_name the contact's name
     */

    public void setContactName(String contact_name) {
        this.contact_name = contact_name;
    }

    /**
     * Gets the contact email
     * @return contact_email the contact's email
     */

    public String getContactEmail() {
        return contact_email;
    }

    /**
     * Sets the contact email
     * @param contact_email the contact's emails
     */

    public void setContactEmail(String contact_email) {
        this.contact_email = contact_email;
    }
}
