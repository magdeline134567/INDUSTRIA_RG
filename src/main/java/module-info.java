module com.example.pa {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires transitive javafx.base;
    requires java.sql;
    requires java.desktop;
    requires java.mail;
    requires org.jfree.jfreechart;
    requires jasperreports;

    opens besededatos to javafx.fxml, javafx.graphics, javafx.base;
    opens besededatos.controllers to javafx.fxml, javafx.graphics, javafx.base;
    opens besededatos.models to javafx.base;
    exports besededatos.controllers;
    exports besededatos.models;
    exports besededatos.config;
    exports besededatos.utils;
    exports besededatos;
}