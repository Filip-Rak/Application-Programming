module org.example.pau3 {
    requires javafx.controls;
    requires javafx.fxml;
    opens org.example.pau3.external to javafx.fxml, javafx.base;

    requires com.dlsc.formsfx;

    opens org.example.pau3 to javafx.fxml;
    exports org.example.pau3;
}