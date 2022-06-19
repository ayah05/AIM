module com.company {
    requires javafx.controls;
    requires javafx.fxml;
    requires hapi.fhir.base;
    requires hapi.fhir.structures.r4;


    opens com.company to javafx.fxml;
    exports com.company;
}