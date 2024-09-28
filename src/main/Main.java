package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;

public class Main extends Application {

    public static ZoneId zone;
    public static Locale locale = Locale.getDefault();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 500);
        stage.setTitle("Log In");
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

    public static void main(String[] args) {
        launch();
    }
}