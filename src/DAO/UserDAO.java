package DAO;

import helper.Alerts;
import helper.DatabaseDriver;
import helper.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import main.Main;
import model.Customer;
import model.User;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Comparator;

public class UserDAO {

    private static User current_user;

    /**
     * Gets the current signed-in user
     * @return current_user the currently signed in user
     */

    public static User getCurrentUser() {
        return current_user;
    }

    /**
     * Sets the currently signed in user
     * @param user the user to be signed in
     */

    public static void setCurrentUser(User user) {
        current_user = user;
    }

    /**
     * Adds an admin column
     */

    public static void addAdminColumn() {
        try {
            String query = "SELECT COUNT(*) " +
                    "FROM information_schema.columns " +
                    "WHERE table_name = 'users' " +
                    "AND column_name = 'admin'";
            PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            // checks if column exists, if it doesn't, create it
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            String query = "ALTER TABLE users ADD admin BIT";
            PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prepares a statement to get all users from the local database.
     * @return user_list a list of all users.
     */

    public static ObservableList<User> getUserList() {
        ObservableList<User> user_list = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM users";
            PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int user_id = result.getInt("User_ID");
                String username = result.getString("User_Name");
                String password = result.getString("Password");
                Date create_date = result.getDate("Create_Date");
                String created_by = result.getString("Created_By");
                LocalDateTime last_update = result.getTimestamp("Last_Update").toLocalDateTime();
                String last_updated_by = result.getString("Last_Updated_By");

                User user = new User(user_id, username, password, create_date, created_by, last_update, last_updated_by);
                user_list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        user_list.sort(Comparator.comparing(User::getUserID));
        return user_list;
    }

    /**
     * Searches the database for a user with the provided username and password. Returns the user id if a user exists with the username and password provided.
     * @param username the username to be searched for
     * @param password the password to be searched for
     * @return int of user id returned. -1 when no user is found
     */

    public static int validateUser(String username, String password) throws IOException {
        if (!username.isEmpty()) {
            if (!password.isEmpty()) {
                try {
                    String query = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password + "'";
                    PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
                    ResultSet result = statement.executeQuery();
                    result.next();
                    if (result.getString("User_Name").equals(username)) {
                        if (result.getString("Password").equals(password)) {

                            // Logs the login success then returns the user id that was just signed in to.
                            Logger.logLogin(username, true);

                            return result.getInt("User_ID");
                        }
                    }
                } catch (SQLException | IOException e) {
                    // Incorrect login credentials. Gets an error then logs the failed login attempt.
                    Alerts.getError(1);
                    Logger.logLogin(username, false);
                    throw new RuntimeException(e);
                }
                return -1;
            }
            // Blank username error
            Alerts.getError(3);
            return -1;
        }
        // Blank password error
        Alerts.getError(2);
        return -1;
    }
}
