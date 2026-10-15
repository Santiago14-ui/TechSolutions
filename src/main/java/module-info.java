module java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java;

    opens app to javafx.fxml;
    exports app;

    opens viewController to javafx.fxml;
    exports viewController;

    opens model to javafx.fxml;
    exports model;

    opens controller to javafx.fxml;
    exports controller;

    opens factory to javafx.fxml;
    exports factory;

    opens adapter to javafx.fxml;
    exports adapter;

    opens builder to javafx.fxml;
    exports builder;

    opens decorator to javafx.fxml;
    exports decorator;

    opens external to javafx.fxml;
    exports external;

    opens service to javafx.fxml;
    exports service;
}