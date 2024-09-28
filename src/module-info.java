module duble.wgu.c195 {
    requires javafx.controls;
    requires javafx.fxml;

    opens controller to javafx.fxml;
    //opens model to javafx.fxml;
    opens main to javafx.fxml;
    exports controller;
    //exports model;
    exports main;
}