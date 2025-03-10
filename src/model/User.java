package model;

import java.sql.Date;
import java.time.LocalDateTime;

public class User {
    private int user_id;
    private final boolean administrator;
    private String username;
    private String password;
    private Date create_date;
    private String created_by;
    private LocalDateTime last_update;
    private String last_updated_by;

    public User(int user_id, String username, String password, Date create_date, String created_by, LocalDateTime last_update, String last_updated_by) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.administrator = user_id == 2;
    }

    /**
     * Checks if the given user is an administrator
     * @return true when user is an administrator, false otherwise
     */

    public boolean isAdministrator() {
        return this.administrator;
    }

    /**
     * Gets the user ID
     * @return user_id the user's ID
     */

    public int getUserID() {
        return user_id;
    }

    /**
     * Sets the user ID
     * @param user_id the user's ID
     */

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Gets the username
     * @return username the user's name
     */

    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username the user's username
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's password
     * @return password the user's password
     */

    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's passsword
     * @param password the user's password
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the date the user was created
     * @return create_date the date the user was created
     */

    public Date getCreateDate() {
        return create_date;
    }

    /**
     * Sets the date the user was created
     * @param create_date the date the user was created
     */

    public void setCreateDate(Date create_date) {
        this.create_date = create_date;
    }

    /**
     * Gets the name of the person who created the user
     * @return created_by the name of the person who created the user
     */

    public String getCreatedBy() {
        return created_by;
    }

    /**
     * Sets the name of the person who created the user
     * @param created_by the name of the person who created the user
     */

    public void setCreatedBy(String created_by) {
        this.created_by = created_by;
    }

    /**
     * Gets the last update time for the user
     * @return last_update the last time the user was updated
     */

    public LocalDateTime getLastUpdate() {
        return last_update;
    }

    /**
     * Sets the last update time for the user
     * @param last_update the last time the user was updated
     */

    public void setLastUpdate(LocalDateTime last_update) {
        this.last_update = last_update;
    }

    /**
     * Gets the name of the user who last updated this user
     * @return last_updated_by the name of the person who updated this user last
     */

    public String getLastUpdatedBy() {
        return last_updated_by;
    }

    /**
     * Sets the name of the user who last updated this user
     * @param last_updated_by the name of the person who updated this user last
     */

    public void setLastUpdatedBy(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }
}
