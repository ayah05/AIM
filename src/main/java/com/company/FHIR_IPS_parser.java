package com.company;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
        File ips = new File(pathName);
        try(FileReader reader = new FileReader(ips)){
            // char[] chars = new char[(int) ips.length()];
            // reader.read(chars);
            // String ipsContent = new String(chars);
            Bundle IPS = parser.parseResource(Bundle.class, reader);
            worked = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return worked;
        // return new DB_Patient(name, conditions, drugs, age);
    }

}
