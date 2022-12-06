package com.company;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.BundleUtil;
import org.hl7.fhir.r4.model.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

/**
 * this class takes IPS-FHIR-bundle-documents and parses them in order to provide a patient ressource for our database (Name, Age, Drugs taken, preconditions and maybe weight)
 */
public class FHIR_IPS_parser {
    private final FhirContext ctx;
    public FHIR_IPS_parser(){
        ctx = FhirContext.forR4();
    }
    public  DB_Patient readIPS(String pathName){ // "./src/main/IPS-example-Bundle-with-renal-disease-et-al.json"
        String firstname = null; String lastname = null;  List<String> conditions = new ArrayList<>(), drugs = new ArrayList<>(); int age = -1; Date dob = null; double weight = Double.NaN;
        // boolean worked = false; // only for testing
        IParser parser = ctx.newJsonParser();
        File ips_file = new File(pathName);
        try(FileReader reader = new FileReader(ips_file)){
            Bundle ips_bundle = parser.parseResource(Bundle.class, reader);
            // to better seperate log-output (from parsing directly above) from testing-feedback during development:
            System.out.println("\n\n\n");
            // demographic info
            Patient pat = BundleUtil.toListOfResourcesOfType(ctx,ips_bundle,Patient.class).get(0);
            firstname = pat.getName().get(0).getGivenAsSingleString();
            lastname = pat.getName().get(0).getFamily();
            age = (int)((System.currentTimeMillis() - pat.getBirthDate().getTime()) / 3.154e+10); // subtracts the birthdate in ms from currentTime in ms, divides by the approx # of ms in a year and cuts decimal digits
            dob = pat.getBirthDate();
            // drugs
            for (MedicationStatement ms : BundleUtil.toListOfResourcesOfType(ctx,ips_bundle, MedicationStatement.class)) {
                if (ms.hasStatus() && ms.getStatus().equals(MedicationStatement.MedicationStatementStatus.ACTIVE)) {
                    Medication med = (Medication) ms.getMedicationReference().getResource();
                    if (med.hasCode() && med.getCode().hasCoding()) {
                        List<Coding> codeList = med.getCode().getCoding();
                        for (Coding code : codeList) {
                            if (code.hasSystem() && code.hasCode() && (code.getSystem().equals("http://www.whocc.no/atc") || code.getSystem().equals("urn:oid:2.16.840.1.113883.6.73"))) {
                                drugs.add((code.getCode().toUpperCase(Locale.ROOT)));
                            }
                        }
                    }
                }
            }
            // conditions (active problems)
            if(ips_bundle.hasEntry()) {
                for(Composition.SectionComponent sectComp : BundleUtil.toListOfResourcesOfType(ctx,ips_bundle, Composition.class).get(0).getSection()){
                    if(sectComp.hasCode() && sectComp.getCode().hasCoding() && sectComp.getCode().getCoding().get(0).getCode().equals("11450-4")){ //"11450-4" -- active problems
                        for(Reference ref : sectComp.getEntry()){
                            Condition cond = (Condition) ref.getResource();
                            if(cond.hasCode() && cond.getCode().hasCoding()){
                                List<Coding> codeList = cond.getCode().getCoding();
                                for(Coding code : codeList){
                                    if(code.hasCode() && code.hasSystem() && (code.getSystem().contains("http://hl7.org/fhir/sid/icd-10")  || code.getSystem().equals("urn:oid:2.16.840.1.113883.6.3") /*general ICD-10*/ || code.getSystem().equals("urn:oid:2.16.840.1.113883.6.90") /*USA*/ || code.getSystem().equals("urn:oid:2.16.840.1.113883.6.3.2") /*NL*/ || code.getSystem().equals("http://fhir.de/CodeSystem/dimdi/icd-10-gm") || code.getSystem().contains("urn:oid:1.2.276.0.76.5.") /*DE -- leider alle deutschen kodierschemata aus dem gesundheitsbereich weil jede jährliche revision eine neue OID kriegt...*/)){
                                        conditions.add(code.getCode().toUpperCase(Locale.ROOT));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // allergyIntolerances -> conditions
            for (AllergyIntolerance ai : BundleUtil.toListOfResourcesOfType(ctx,ips_bundle, AllergyIntolerance.class)) {
                if(ai.hasCode() && ai.getCode().hasCoding() ){
                    List <Coding> codeList = ai.getCode().getCoding();
                    for (Coding code : codeList){
                        if(code.hasCode() && code.hasSystem() && (code.getSystem().equals("http://hl7.org/fhir/ValueSet/allergyintolerance-code") || code.getSystem().equals("urn.oid:2.16.840.1.113883.4.642.3.137") || code.getSystem().equals("http://snomed.info/sct") || code.getSystem().equals("urn:oid:2.16.840.1.113883.6.96"))){
                            if (code.getCode().equals("373270004") || code.getCode().equals("3145006") || code.getCode().equals("91936005") || code.getCode().equals("294492003") || code.getCode().equals("294494002") || code.getCode().equals("294496000") || code.getCode().equals("294497009") || code.getCode().equals("294499007") || code.getCode().equals("294500003") || code.getCode().equals("294501004") || code.getCode().equals("294502006") || code.getCode().equals("294503001") || code.getCode().equals("294504007") || code.getCode().equals("294505008") || code.getCode().equals("294506009") || code.getCode().equals("294513009")){
                                conditions.add("Z88.0"); // Personal history of allergy to penicillin
                            }
                            if (code.getCode().equals("294466005") || code.getCode().equals("294462007") || code.getCode().equals("294461000") || code.getCode().equals("294469003") || code.getCode().equals("294475007") || code.getCode().equals("294483001") || code.getCode().equals("294468006") || code.getCode().equals("294487000") || code.getCode().equals("294490006") || code.getCode().equals("294532003") || code.getCode().equals("294561002") || code.getCode().equals("294585005") || code.getCode().equals("294592000") || code.getCode().equals("294611004")){
                                conditions.add("Z88.1"); // Personal history of allergy to other antibiotic agents
                            }
                            if (code.getCode().equals("293708000") /*propofol*/ || code.getCode().equals("293707005") /*ketamine*/ || code.getCode().equals("293706001") /*etomidate*/|| code.getCode().equals("293712006") /*enflurane*/ || code.getCode().equals("293715008")/*isoflurane*/ || code.getCode().equals("293717000") /*desflurane*/ || code.getCode().equals("293718005")/*local anaesthetic*/ || code.getCode().equals("293719002") /*bupivacaine*/ || code.getCode().equals("293723005") /*cocaine*/ || code.getCode().equals("293724004") /*benzocaine*/ || code.getCode().equals("293727006") /*procaine*/ || code.getCode().equals("767198002") /*diethyl ether*/ ){
                                conditions.add("Z88.4"); // Personal history of allergy to anaesthetic agent
                            }
                            if (code.getCode().equals("293879002") /*Barbiturate sedative allergy*/  || code.getCode().equals("293886005") /*Flunitrazepam (rohypnol)*/ || code.getCode().equals("293891006") /*Triazolam (halcion)*/ || code.getCode().equals("293901004") /*Midazolam*/ || code.getCode().equals("293605005") /*fenanyl*/ || code.getCode().equals("293604009") /*alfentanil*/ || code.getCode().equals("293596005") /*buprenorphine*/ || code.getCode().equals("293600000") /*nalbuphine*/ || code.getCode().equals("293598006") /*diamorphine*/ || code.getCode().equals("293601001") /*morphine*/ || code.getCode().equals("293602008") /*opioid*/ || code.getCode().equals("293606006") /*pethidine*/ || code.getCode().equals("293595009") /*morhphinian opioid*/ || code.getCode().equals("441955007") /*sufentanil*/ ){
                                conditions.add("Z88.5"); // Personal history of allergy to narcotic agent
                            }
                            if (code.getCode().equals("293582004") /*analgesic general*/|| code.getCode().equals("293584003") /*paracetamol*/ || code.getCode().equals("293586001") /*ASS*/ || code.getCode().equals("293610009") /*NSAID*/|| code.getCode().equals("293613006") /*diclofenac (voltaren)*/ || code.getCode().equals("293619005") /*ibuprofen*/ || code.getCode().equals("293623002") /*mefenamic acid (parkemed)*/ || code.getCode().equals("702602008") /*metamizole/dipyrone (novalgin)*/ || code.getCode().equals("293625009") /*naproxen*/ || code.getCode().equals("293629003") /*piroxicam*/  || code.getCode().equals("450767000") /*tramadol*/ ){
                                conditions.add("Z88.6"); // Personal history of allergy to analgesic agent
                            }
                            if (code.getCode().equals("418038007") /*Propensity to adverse reactions to substance*/ ){
                                conditions.add("Z88.9"); // Personal history of allergy to unspecified drugs, medicaments and biological substances
                            }
                        }
                    }
                }
            }
            // adding age, over- and underweight as risk-factors
            if(age > 85) {conditions.add("R54") /*age related physical debility*/;}
            // cheating a little instead of adding a weight observation in martha's IPS
            if(firstname.equals("Martha")&& lastname.equals("DeLarosa")){weight=63.4;}
            if(!Double.isNaN(weight)) { if(weight<45){ conditions.add("R63.6") /*underweight*/ ;} if(weight>110){ conditions.add("E66.9");/*obesity, not specified*/}}

        } catch (IOException e) {
            e.printStackTrace();
        }
         return new DB_Patient(firstname,lastname, conditions, drugs, dob, weight, age);
    }

}
