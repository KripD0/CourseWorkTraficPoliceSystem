module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires postgresql;
    requires java.naming;

    opens com.project to javafx.fxml;
    exports com.project;
    exports com.project.Controllers;
    opens com.project.Controllers to javafx.fxml;
}