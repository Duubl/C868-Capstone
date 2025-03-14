package main;

import DAO.UserDAO;
import helper.DatabaseDriver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private static ZoneId zone;
    private static final Locale locale = Locale.getDefault();
    public static ResourceBundle lang_bundle = ResourceBundle.getBundle("language/lang");

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 500);
        stage.setTitle(lang_bundle.getString("LoginTitle"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Gets the current time zone of the user.
     * @return zone as a ZoneId.
     */

    public static ZoneId getZoneID() {
        zone = ZoneId.systemDefault();
        return zone;
    }

    /**
     * Gets the current user's language setting.
     * @return locale.
     */

    public static Locale getLocale() {
        return locale;
    }

    public static void main(String[] args) throws SQLException {
        DatabaseDriver.openConnection();
        UserDAO.addAdminColumn();
        launch();
        DatabaseDriver.closeConnection();
    }
}