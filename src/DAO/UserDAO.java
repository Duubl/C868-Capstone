package DAO;

import helper.Alerts;
import helper.DatabaseDriver;
import helper.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

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

                User user = new User(user_id, username);
                user_list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user_list;
    }

    /**
     * Searches the database for a user with the provided username and password. Returns the user id if a user exists with the username and password provided.
     * @param username the username to be searched for
     * @param password the password to be searched for
     * @return int of user id returned. -1 when no user is found
     */

    // TODO: Provide error messages for incorrect username, password and for empty fields.

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
                            // Logs the login success then returns the user id that was just signed into.
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
