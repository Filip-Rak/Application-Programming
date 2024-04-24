module org.example.pau3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires javafx.graphics;
    requires com.dlsc.formsfx;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    // Opens the package containing your JPA entities to Hibernate
    opens org.example.pau3.external to org.hibernate.orm.core, javafx.fxml, javafx.base;

    // Opens your main application package to Hibernate and JavaFX
    opens org.example.pau3 to javafx.fxml, org.hibernate.orm.core, javafx.base;

    // Exports your main application package
    exports org.example.pau3;
    exports org.example.pau3.external;
}
