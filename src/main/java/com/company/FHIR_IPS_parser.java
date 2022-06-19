package com.company;
import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.r4.model.*;

/**
 * this class takes IPS-FHIR-bundle-documents and parses them in order to provide a patient ressource for our database (Name, Age, Drugs taken, preconditions and maybe weight)
 */
public class FHIR_IPS_parser {
    FhirContext ctx;
    public FHIR_IPS_parser(){
        ctx = FhirContext.forR4();
    }


}
