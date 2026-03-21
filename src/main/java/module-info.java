module com.example.intfrontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.intfrontend to javafx.fxml;
    exports com.example.intfrontend;
}