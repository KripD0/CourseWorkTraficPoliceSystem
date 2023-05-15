module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires org.postgresql.jdbc;
    requires java.desktop;

    opens com.project to javafx.fxml;
    exports com.project.controllers;
    exports com.project.classesForTables;
    opens com.project.controllers to javafx.fxml;
    exports com.project;
    exports com.project.auxiliary;
    opens com.project.auxiliary to javafx.fxml;
}