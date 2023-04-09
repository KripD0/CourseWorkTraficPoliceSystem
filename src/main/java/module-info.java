module com.example.courseworktraficpolicesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires postgresql;


    opens com.example.courseworktraficpolicesystem to javafx.fxml;
    exports com.example.courseworktraficpolicesystem;
    exports Controllers;
    opens Controllers to javafx.fxml;
}