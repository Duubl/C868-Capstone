package helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseDriver {

    // Establish connection
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String database_name = "client_schedule";
    private static final String jdbc_url = protocol + vendor + location + database_name + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    public static Connection connection; // Connection interface

    // Username & password
    private static final String username = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password

    // If you can't connect, make sure the database is running.
    // Run in services.msc look for MySQL80 and start the process.

    /**
     * Opens a connection to the local client_schedule database.
     */

    public static void openConnection() {
        try {
            Class.forName(driver); // Locate driver
            connection = DriverManager.getConnection(jdbc_url, username, password); // Reference connection object
            System.out.println("Connection to " + database_name + " successful!");
        }
        catch (Exception e) {
            // Displays an error when connection fails
            Alerts.getError(4);
            System.out.println("Failure connecting to " + database_name);
        }
    }

    /**
     * Closes the connection to the local client_schedule database.
     */

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection to " + database_name + " closed!");
        }
        catch (Exception e) {
            System.out.println("Failure closing connection to " + database_name);
        }
    }

}
