module com.example.controllers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.example.controllers to javafx.fxml;
    opens models to com.fasterxml.jackson.databind;
    exports com.example.controllers;
    exports models;
}