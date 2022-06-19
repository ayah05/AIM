package com.company;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.BundleUtil;
import org.hl7.fhir.r4.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Period;
import java.util.Date;

/**
 * this class takes IPS-FHIR-bundle-documents and parses them in order to provide a patient ressource for our database (Name, Age, Drugs taken, preconditions and maybe weight)
 */
public class FHIR_IPS_parser {
    private final FhirContext ctx;
    public FHIR_IPS_parser(){
        ctx = FhirContext.forR4();
    }
    public /* DB_Patient */ boolean readIPS(String pathName){ // "./src/main/IPS-example-Bundle-with-renal-disease-et-al.json"
        String name = null; String[] conditions = null, drugs = null; int age = -1;
        boolean worked = false;
        IParser parser = ctx.newJsonParser();
        File ips_file = new File(pathName);
        try(FileReader reader = new FileReader(ips_file)){
            Bundle ips_bundle = parser.parseResource(Bundle.class, reader);
            // to better seperate log-output (from parsing) from testing-feedback during development:
            System.out.println("\n\n\n");
            // demographic info
            Patient pat = BundleUtil.toListOfResourcesOfType(ctx,ips_bundle,Patient.class).get(0);
            name = pat.getName().get(0).getGivenAsSingleString() +" " + pat.getName().get(0).getFamily();
            age = (int)((System.currentTimeMillis() - pat.getBirthDate().getTime()) / 3.154e+10); // subtracts the birthdate in ms from currentTime in ms, divides by the approx # of ms in a year and cuts decimal digits
            // drugs

            // test output:
            System.out.println(

            );

            worked = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return worked;
        // return new DB_Patient(name, conditions, drugs, age);
    }

}
