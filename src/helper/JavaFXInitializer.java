package helper;

import javafx.application.Platform;
import java.util.concurrent.CountDownLatch;

public class JavaFXInitializer {
    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            Platform.startup(() -> {}); // Initialize JavaFX
            latch.countDown();
        }).start();

        try {
            latch.await(); // Wait for JavaFX to be ready
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        initialized = true;
    }
}
