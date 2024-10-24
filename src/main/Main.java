package main;

import helper.DatabaseDriver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private static ZoneId zone;
    private static final Locale locale = Locale.getDefault();
    public static ResourceBundle langBundle = ResourceBundle.getBundle("language/lang");
    private static int user_id;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 500);
        stage.setTitle(langBundle.getString("LoginTitle"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Gets the current time zone of the user.
     * @return zone as a ZoneId.
     */

    public static ZoneId getZoneID() {
        zone = ZonedDateTime.now().getZone();
        return zone;
    }

    /**
     * Gets the current user's language setting.
     * @return locale.
     */

    public static Locale getLocale() {
        return locale;
    }

    /**
     * Gets the current user's user ID.
     * @return user_id the current user's user ID.
     */

    public static int getUserID() {
        return user_id;
    }

    /**
     * Sets the current user's user ID.
     * @param id the user id to be set.
     */

    public static void setUserID(int id) {
        user_id = id;
    }

    public static void main(String[] args) {
        DatabaseDriver.openConnection();
        launch();
        DatabaseDriver.closeConnection();
    }
}