DROP TABLE IF EXISTS Patient;
DROP TABLE IF EXISTS Drug;
DROP TABLE IF EXISTS Condition;
DROP TABLE IF EXISTS Interaction;

CREATE TABLE "Patient" (
                           "PatientID" serial PRIMARY KEY,
                           "Drug" varchar(7) REFERENCES "Drug",
                           "Condition" varchar(5) REFERENCES "Condition",
                           "Name" varchar(50) NOT NULL,
                           "Age" int,
                           "Weight" real
);

CREATE TABLE "Condition" (
                             "Code (ICD-10)" varchar(5) PRIMARY KEY,
                             "Name" varchar(50) NOT NULL
);

CREATE TABLE "Drug" (
                        "Code (ATC)" varchar(7) PRIMARY KEY ,
                        "Name" varchar(50) NOT NULL
);


CREATE TABLE "Interaction" (
                               "InteractionID" serial PRIMARY KEY ,
                               "Drug" varchar(7) NOT NULL REFERENCES "Drug",
                               "Drug2" varchar(7) REFERENCES "Drug",
                               "Condition" varchar(5) REFERENCES "Condition",
                               "Hint" varchar(50)
);
--filling in patient names
INSERT INTO "Patient" VALUES (Default,'','','Brigitta Haggis',34,65);
INSERT INTO "Patient" VALUES (Default,'','','Giulietta Cirlos',23,55);
INSERT INTO "Patient" VALUES (Default,'','','Alexio Jarlmann',50,88);
INSERT INTO "Patient" VALUES (Default,'','','Georgiana Labdon',39,70);
INSERT INTO "Patient" VALUES (Default,'','','York Kingscott',27,76);
INSERT INTO "Patient" VALUES (Default,'','','Tim Bettenson',20,65);
INSERT INTO "Patient" VALUES (Default,'','','Mahmud Witling',65,82);
INSERT INTO "Patient" VALUES (Default,'','','Rosana Jantet',45,68);
INSERT INTO "Patient" VALUES (Default,'','','Kathe Camm',70,57);
INSERT INTO "Patient" VALUES (Default,'','','Janett Harris',30,60);
INSERT INTO "Patient" VALUES (Default,'','','Brigitta Haggis',34,65);
INSERT INTO "Patient" VALUES (Default,'','','Alexander Helks',44,90);
INSERT INTO "Patient" VALUES (Default,'','','Martha DeLarosa',50,63);
--filling in drugs and atc codes
INSERT INTO "Drug" VALUES ('N01AX10','Propofol');
INSERT INTO "Drug" VALUES ('N01AH01','Fentanyl');--N01AH - opioid anesthetics
INSERT INTO "Drug" VALUES ('N01AH03','Sufentanil');--N01A (Allgemeinanästhesie);N01B(Lokalanästhetika)
INSERT INTO "Drug" VALUES ('N01AX03 ','Ketamine');--N01AX - other general anesthetics
INSERT INTO "Drug" VALUES ('N05CD08','Midazolam'); -- N05C - hypnotics and sedatives
INSERT INTO "Drug" VALUES ('M03AB01','Suxamethonium');--M03A - muscle relaxants, peripherally acting
INSERT INTO "Drug" VALUES ('M03AC09','Rocuronium Bromide');
INSERT INTO "Drug" VALUES ('N01BB02','Lidocaine'); --N01B Anesthetics, Local
INSERT INTO "Drug" VALUES ('N01AB08','Sevoflurane');
INSERT INTO "Drug" VALUES ('N05BA01','Diazepam');--N05BA Anxiolytics, Benzodiazepine derivates
INSERT INTO "Drug" VALUES ('B01AA03','Warfarin'); --the anticoagulant in the IPS-patient
INSERT INTO "Drug" VALUES ('B01AA04','Phenprocoumon');--the anticoagulant used most in austria: "marcoumar"
INSERT INTO "Drug" VALUES ('B01AC06','Acetylsalicylic acid');--aspirin. used as anticoagulant (mostly <= 100 mg)
INSERT INTO "Drug" VALUES ('N02BA01','Acetylsalicylic acid');--aspirin. used as analgesic (mostly >= 500 mg)

--filling in several conditions
INSERT INTO "Condition" VALUES ('J46','Status asthmaticus');
INSERT INTO "Condition" VALUES ('G71','Primary Myopathies');
INSERT INTO "Condition" VALUES ('J95','Intraoperative and postprocedural complications and disorders of respiratory system, not elsewhere classified.');
INSERT INTO "Condition" VALUES ('I95','Hypotension'); -- contraindication to propofol
INSERT INTO "Condition" VALUES('Z91.012','Allergy to eggs');-- contraindication to propofol
INSERT INTO "Condition" VALUES('Z91.2','Personal history of poor personal hygiene');--contraindication to performing any kind of medical procedure on that person.
INSERT INTO "Condition" VALUES ('T20','Burn and Corrosion of Head, Face, and Neck');--contraindication to suxamethonium (=succinylcholine)
INSERT INTO "Condition" VALUES ('Z33','Pregnant');--idk if z33 is the right code / yess- it is just a status (z) not a condition.. kinda like allergies..dunno of we should use ICD10 codes for allergies as well but probably...
INSERT INTO "Condition" VALUES ('N17','Acute renal failure');
INSERT INTO "Condition" VALUES ('N18','Chronic renal disease');
INSERT INTO "Condition" VALUES ('N19','Unspecified kidney failure');
INSERT INTO "Condition" VALUES ('K70','Alcoholic liver disease');--all K70-77 need special care w/ dosage
INSERT INTO "Condition" VALUES ('K71','Toxic liver disease');
INSERT INTO "Condition" VALUES ('K72','Hepatic failure, not elsewhere classified');
INSERT INTO "Condition" VALUES ('K73','Chronic hepatitis, not elsewhere classified');
INSERT INTO "Condition" VALUES ('K74','Fibrosis and cirrhosis of liver');
INSERT INTO "Condition" VALUES ('K75','Abscess of liver');
INSERT INTO "Condition" VALUES ('K76','Other diseases of liver');
INSERT INTO "Condition" VALUES ('K77','Liver disorders in diseases classified elsewhere');
INSERT INTO "Condition" VALUES ('Z88.0','Personal history of allergy to penicillin');
INSERT INTO "Condition" VALUES ('Z88.1','Personal history of allergy to other antibiotic agents');--any antibiotic except penicillin
INSERT INTO "Condition" VALUES ('Z88.4','Personal history of allergy to anaesthetic agent');--mostly about flouranes, could also be about propofol, ketamine, etomidate,....
INSERT INTO "Condition" VALUES ('Z88.5','Personal history of allergy to narcotic agent');--opioids?
INSERT INTO "Condition" VALUES ('Z88.6','Personal history of allergy to analgesic agent');--NSAIDs? also opioiods mayyyybe?
INSERT INTO "Condition" VALUES ('Z88.9','Personal history of allergy to unspecified drugs, medicaments and biological substances');--everything else
