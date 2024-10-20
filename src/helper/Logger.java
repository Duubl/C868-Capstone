package helper;

import main.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static final String log = "login_activity.txt";

    /**
     * Writes login information to a text file. Is appended the username, result, date and time to the end of the file on every attempt.
     * @param username the username of the user who attempted to sign on.
     * @param success the result of the sign in attempt. Either true or false.
     * @throws IOException
     */

    public static void logLogin(String username, boolean success) throws IOException {
        FileWriter fileWriter = new FileWriter(log, true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss");
        Main.getZoneID();
        fileWriter.write("Attempted sign in with username: [" + username + "] result: [" + success + "] on [" + formatter.format(LocalDateTime.now()) + "]\n");
        fileWriter.close();
    }
}
