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
import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

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
        String checkColumnQuery = "SELECT column_name FROM information_schema.columns WHERE table_name = 'users' AND column_name = 'admin'";
        String alterTableQuery = "ALTER TABLE users ADD COLUMN admin BOOLEAN DEFAULT FALSE";
        String updateAdmin = "UPDATE users SET admin = 1 WHERE User_Name = 'admin'";

        try (Statement statement = DatabaseDriver.connection.createStatement();
             ResultSet resultSet = statement.executeQuery(checkColumnQuery)) {

            if (!resultSet.next()) { // Column doesn't exist
                try (Statement alterStatement = DatabaseDriver.connection.createStatement()) {
                    alterStatement.executeUpdate(alterTableQuery);
                    System.out.println("Column admin added to users table.");
                    try (Statement updateStatement = DatabaseDriver.connection.createStatement()) {
                        updateStatement.executeUpdate(updateAdmin);
                        System.out.println("Updated admin value for admin");
                    }
                }
            } else {
                System.out.println("Column admin already exists in users table.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new user with the information provided in the database
     * @param id the id for the new user
     * @param username the username for the new user
     * @param password the password for the new user
     * @param admin whether the user is an administrator or not
     * @throws SQLException
     */

    public static void createUser(int id, String username, String password, boolean admin) throws SQLException {
        String last_updated_by = UserDAO.getCurrentUser().getUsername();
        int int_value = admin ? 1 : 0;

        String query = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setString(2, username);
        statement.setString(3, password);
        statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
        statement.setString(5, last_updated_by);
        statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
        statement.setString(7, last_updated_by);
        statement.setInt(8, int_value);
        statement.executeUpdate();
    }

    /**
     * Updates the user with the information provided in the database.
     * @param id the id of the user to be updated
     * @param username the new username for the user
     * @param password the new password for the user
     * @param admin whether the user will be an administrator or not
     * @throws SQLException
     */

    public static void updateUser(int id, String username, String password, boolean admin) throws SQLException {
        String last_updated_by = UserDAO.getCurrentUser().getUsername();
        int int_value = admin ? 1 : 0;

        String query = "UPDATE users SET User_Name = ?, Password = ?, Last_Update = ?, Last_Updated_by = ?, admin = ? WHERE User_ID = ?";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
        statement.setString(4, last_updated_by);
        statement.setInt(5, int_value);
        statement.setInt(6, id);
        statement.executeUpdate();
    }

    /**
     * Deletes the given user.
     * @param user the user to be deleted.
     * @throws SQLException
     */

    public static void deleteUser(User user) throws SQLException {
        int user_id = user.getUserID();
        String query = "DELETE FROM users WHERE User_ID = ?";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setInt(1, user_id);
        statement.executeUpdate();
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
                boolean admin = result.getBoolean("admin");

                User user = new User(user_id, username, password, create_date, created_by, last_update, last_updated_by, admin);
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

    /**
     * Gets a unique customer ID based on the customers in the database.
     * @return id the new unqiue ID.
     * @throws SQLException
     */

    public static int getUniqueUserID() throws SQLException {
        ObservableList<User> user_list = getUserList();
        Set<Integer> existing_ids = user_list.stream().map(User::getUserID).collect(Collectors.toSet());
        int id = 1;
        while (existing_ids.contains(id)) {
            id++;
        }
        return id;
    }
}
