package helper;

import main.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static final String log = "login_activity.txt";

    public static void logLogin(String username, boolean success) throws IOException {
        FileWriter fileWriter = new FileWriter(log, true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss");
        Main.getZoneID();
        fileWriter.write("Attempted sign in with username: [" + username + "] result: [" + success + "] on [" + formatter.format(LocalDateTime.now()) + "]\n");
        fileWriter.close();
    }
}
