module com.company {
    requires javafx.controls;
    requires javafx.fxml;
    requires hapi.fhir.base;
    requires hapi.fhir.structures.r4;
    requires org.hl7.fhir.r4;
    requires javafx.graphics;
    requires java.sql;
    requires java.smartcardio;
    requires org.jetbrains.annotations;


    opens com.company to javafx.fxml;
    exports com.company;
}