package controller;

import DAO.AppointmentDAO;
import helper.DatabaseDriver;
import helper.JavaFXInitializer;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit.ApplicationTest;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

public class AddAppointmentControllerTest extends ApplicationTest {

    private static AddAppointmentController controller;

    @BeforeAll
    public static void setup() {
        JavaFXInitializer.init();
        controller = new AddAppointmentController();
    }

    @BeforeEach
    public void insertTestData() throws SQLException {
        DatabaseDriver.openConnection();
        String query = "INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.setInt(1, 100);
        statement.setString(2, "unit test");
        statement.setString(3, "unit test");
        statement.setString(4, "unit test");
        statement.setString(5, "unit test");
        statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.of(2025, 1, 1, 12, 0)));
        statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.of(2025, 1, 1, 12, 10)));
        statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
        statement.setString(9, "unit test");
        statement.setTimestamp(10, Timestamp.valueOf(LocalDateTime.ofInstant(OffsetDateTime.now().toInstant(), ZoneOffset.UTC)));
        statement.setString(11, "unit test");
        statement.setInt(12, 1);
        statement.setInt(13, 1);
        statement.setInt(14, 1);
        statement.executeUpdate();
    }

    @AfterEach
    public void cleanupDatabase() throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID = 100";
        PreparedStatement statement = DatabaseDriver.connection.prepareStatement(query);
        statement.executeUpdate();
    }

    @AfterAll
    public static void closeDatabase () {
        if (DatabaseDriver.connection != null) {
            DatabaseDriver.closeConnection();
        }
    }

    @Test
    public void testCheckValidHoursValid() {
        Platform.runLater(() -> {
            DatePicker start_date = new DatePicker();
            DatePicker end_date = new DatePicker();
            ComboBox<LocalTime> start_time = new ComboBox<>();
            ComboBox<LocalTime> end_time = new ComboBox<>();

            // Appointment is over one day for 10 minutes from 12:10 - 12:20.
            start_date.setValue(LocalDate.of(2025, 1, 1));
            end_date.setValue(LocalDate.of(2025, 1, 1));
            start_time.setValue(LocalTime.of(12, 10));
            end_time.setValue(LocalTime.of(12, 20));

            controller.start_date_combo = start_date;
            controller.end_date_combo = end_date;
            controller.start_time_combo = start_time;
            controller.end_time_combo = end_time;

            try {
                assertTrue(controller.checkValidHours(0));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Start and end dates & times are the same
            start_date.setValue(LocalDate.of(2025, 1, 1));
            end_date.setValue(LocalDate.of(2025, 1, 1));
            start_time.setValue(LocalTime.of(12, 10));
            end_time.setValue(LocalTime.of(12, 10));

            controller.start_date_combo = start_date;
            controller.end_date_combo = end_date;
            controller.start_time_combo = start_time;
            controller.end_time_combo = end_time;

            try {
                assertTrue(controller.checkValidHours(0));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Appointment lasts a year
            start_date.setValue(LocalDate.of(2025, 1, 1));
            end_date.setValue(LocalDate.of(2026, 1, 1));
            start_time.setValue(LocalTime.of(1, 0));
            end_time.setValue(LocalTime.of(1, 10));

            controller.start_date_combo = start_date;
            controller.end_date_combo = end_date;
            controller.start_time_combo = start_time;
            controller.end_time_combo = end_time;

            try {
                assertTrue(controller.checkValidHours(0));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testCheckValidHoursInvalid() {
        Platform.runLater(() -> {
            DatePicker start_date = new DatePicker();
            DatePicker end_date = new DatePicker();
            ComboBox<LocalTime> start_time = new ComboBox<>();
            ComboBox<LocalTime> end_time = new ComboBox<>();

            // End time is before start time
            start_date.setValue(LocalDate.of(2025, 1, 1));
            end_date.setValue(LocalDate.of(2025, 1, 1));
            start_time.setValue(LocalTime.of(12, 0));
            end_time.setValue(LocalTime.of(11, 0));

            controller.start_date_combo = start_date;
            controller.end_date_combo = end_date;
            controller.start_time_combo = start_time;
            controller.end_time_combo = end_time;

            try {
                assertFalse(controller.checkValidHours(0));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Start date is after end date
            start_date.setValue(LocalDate.of(2025, 1, 2));
            end_date.setValue(LocalDate.of(2025, 1, 1));
            start_time.setValue(LocalTime.of(12, 0));
            end_time.setValue(LocalTime.of(12, 10));

            controller.start_date_combo = start_date;
            controller.end_date_combo = end_date;
            controller.start_time_combo = start_time;
            controller.end_time_combo = end_time;

            try {
                assertFalse(controller.checkValidHours(0));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Start year is after end date
            start_date.setValue(LocalDate.of(2025, 1, 1));
            end_date.setValue(LocalDate.of(2024, 1, 1));
            start_time.setValue(LocalTime.of(12, 0));
            end_time.setValue(LocalTime.of(12, 10));

            controller.start_date_combo = start_date;
            controller.end_date_combo = end_date;
            controller.start_time_combo = start_time;
            controller.end_time_combo = end_time;

            try {
                assertFalse(controller.checkValidHours(0));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Appointment overlaps with test appointment completely.
            start_date.setValue(LocalDate.of(2025, 1, 1));
            end_date.setValue(LocalDate.of(2025, 1, 1));
            start_time.setValue(LocalTime.of(12, 0));
            end_time.setValue(LocalTime.of(12, 10));

            controller.start_date_combo = start_date;
            controller.end_date_combo = end_date;
            controller.start_time_combo = start_time;
            controller.end_time_combo = end_time;

            try {
                assertFalse(controller.checkValidHours(0));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Appointment ends during existing appointment
            start_date.setValue(LocalDate.of(2025, 1, 1));
            end_date.setValue(LocalDate.of(2025, 1, 1));
            start_time.setValue(LocalTime.of(11, 30));
            end_time.setValue(LocalTime.of(12, 5));

            controller.start_date_combo = start_date;
            controller.end_date_combo = end_date;
            controller.start_time_combo = start_time;
            controller.end_time_combo = end_time;

            try {
                assertFalse(controller.checkValidHours(0));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Appointment starts during existing appointment
            start_date.setValue(LocalDate.of(2025, 1, 1));
            end_date.setValue(LocalDate.of(2025, 1, 1));
            start_time.setValue(LocalTime.of(12, 5));
            end_time.setValue(LocalTime.of(12, 30));

            controller.start_date_combo = start_date;
            controller.end_date_combo = end_date;
            controller.start_time_combo = start_time;
            controller.end_time_combo = end_time;

            try {
                assertFalse(controller.checkValidHours(0));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Appointment encapsulates another appointment
            start_date.setValue(LocalDate.of(2025, 1, 1));
            end_date.setValue(LocalDate.of(2025, 1, 1));
            start_time.setValue(LocalTime.of(11, 0));
            end_time.setValue(LocalTime.of(1, 0));

            controller.start_date_combo = start_date;
            controller.end_date_combo = end_date;
            controller.start_time_combo = start_time;
            controller.end_time_combo = end_time;

            try {
                assertFalse(controller.checkValidHours(0));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
