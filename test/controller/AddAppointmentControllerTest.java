package controller;

import helper.JavaFXInitializer;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

public class AddAppointmentControllerTest extends ApplicationTest {

    private static AddAppointmentController controller;

    @BeforeAll
    public static void setup() {
        JavaFXInitializer.init();
        controller = new AddAppointmentController();
    }

    @Test
    public void testCheckValidHoursValid() {
        Platform.runLater(() -> {
            DatePicker start_date = new DatePicker();
            DatePicker end_date = new DatePicker();
            ComboBox<LocalTime> start_time = new ComboBox<>();
            ComboBox<LocalTime> end_time = new ComboBox<>();

            // Appointment is over one day for 10 minutes.
            start_date.setValue(LocalDate.of(2025, 1, 1));
            end_date.setValue(LocalDate.of(2025, 1, 1));
            start_time.setValue(LocalTime.of(12, 0));
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

            // Start and end dates & times are the same
            start_date.setValue(LocalDate.of(2025, 1, 1));
            end_date.setValue(LocalDate.of(2025, 1, 1));
            start_time.setValue(LocalTime.of(12, 0));
            end_time.setValue(LocalTime.of(12, 0));

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
    void testcheckValidHoursInvalid() {
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
        });
    }
}
