DROP TABLE IF EXISTS Patient;
DROP TABLE IF EXISTS Drug;
DROP TABLE IF EXISTS Condition;
DROP TABLE IF EXISTS Interaction;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO ayah05;
CREATE TABLE "Patient" (
                           "PatientID" serial PRIMARY KEY,
                           "Drug" varchar(7) REFERENCES "Drug",
                           "Condition" varchar(7) REFERENCES "Condition",
                           "Name" varchar(50) NOT NULL,
                           "Age" int,
                           "Weight" real
);

CREATE TABLE "Condition" (
                             "Code (ICD-10)" varchar(7) PRIMARY KEY,
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
INSERT INTO "Drug" VALUES ('N01AX10','Propofol')ON CONFLICT DO NOTHING;
INSERT INTO "Drug" VALUES ('N01AH01','Fentanyl')ON CONFLICT DO NOTHING;--N01AH - opioid anesthetics
INSERT INTO "Drug" VALUES ('N01AH03','Sufentanil')ON CONFLICT DO NOTHING;--N01A (Allgemeinanästhesie);N01B(Lokalanästhetika)
INSERT INTO "Drug" VALUES ('N01AX03 ','Ketamine')ON CONFLICT DO NOTHING;--N01AX - other general anesthetics
INSERT INTO "Drug" VALUES ('N05CD08','Midazolam')ON CONFLICT DO NOTHING; -- N05C - hypnotics and sedatives
INSERT INTO "Drug" VALUES ('M03AB01','Suxamethonium')ON CONFLICT DO NOTHING;--M03A - muscle relaxants, peripherally acting
INSERT INTO "Drug" VALUES ('M03AC09','Rocuronium Bromide')ON CONFLICT DO NOTHING;
INSERT INTO "Drug" VALUES ('N01BB02','Lidocaine')ON CONFLICT DO NOTHING; --N01B Anesthetics, Local
INSERT INTO "Drug" VALUES ('N01AB08','Sevoflurane')ON CONFLICT DO NOTHING;
INSERT INTO "Drug" VALUES ('N05BA01','Diazepam')ON CONFLICT DO NOTHING;--N05BA Anxiolytics, Benzodiazepine derivates
INSERT INTO "Drug" VALUES ('B01AA03','Warfarin')ON CONFLICT DO NOTHING; --the anticoagulant in the IPS-patient
INSERT INTO "Drug" VALUES ('B01AA04','Phenprocoumon')ON CONFLICT DO NOTHING;--the anticoagulant used most in austria: "marcoumar"
INSERT INTO "Drug" VALUES ('B01AC06','Acetylsalicylic acid')ON CONFLICT DO NOTHING;--aspirin. used as anticoagulant (mostly <= 100 mg)
INSERT INTO "Drug" VALUES ('N02BA01','Acetylsalicylic acid')ON CONFLICT DO NOTHING;--aspirin. used as analgesic (mostly >= 500 mg)

--filling in several conditions
INSERT INTO "Condition" VALUES ('J46','Status asthmaticus') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('G71','Primary Myopathies')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('J95','Intraoperative and postprocedural complications and disorders of respiratory system, not elsewhere classified.')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('I95','Hypotension')ON CONFLICT DO NOTHING; -- contraindication to propofol
INSERT INTO "Condition" VALUES('Z91.012','Allergy to eggs')ON CONFLICT DO NOTHING;-- contraindication to propofol
INSERT INTO "Condition" VALUES('Z91.2','Personal history of poor personal hygiene')ON CONFLICT DO NOTHING;--contraindication to performing any kind of medical procedure on that person.
INSERT INTO "Condition" VALUES ('T20','Burn and Corrosion of Head, Face, and Neck')ON CONFLICT DO NOTHING;--contraindication to suxamethonium (=succinylcholine) (M03AB01)
INSERT INTO "Condition" VALUES ('Z33','Pregnant state')ON CONFLICT DO NOTHING;--idk if z33 is the right code / offictial wording sounds just a little better than improvised :p but yess pregnant just a status (z) not a condition.. kinda like allergies..dunno of we should use ICD10 codes for allergies as well but probably...
INSERT INTO "Condition" VALUES ('N17','Acute renal failure')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('N18','Chronic renal disease')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('N19','Unspecified kidney failure')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('K70','Alcoholic liver disease')ON CONFLICT DO NOTHING;--all K70-77 need special care w/ dosage
INSERT INTO "Condition" VALUES ('K71','Toxic liver disease')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('K72','Hepatic failure, not elsewhere classified')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('K73','Chronic hepatitis, not elsewhere classified')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('K74','Fibrosis and cirrhosis of liver')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('K75','Abscess of liver')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('K76','Other diseases of liver')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('K77','Liver disorders in diseases classified elsewhere')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('Z88.0','Personal history of allergy to penicillin')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('Z88.1','Personal history of allergy to other antibiotic agents')ON CONFLICT DO NOTHING;--any antibiotic except penicillin
INSERT INTO "Condition" VALUES ('Z88.4','Personal history of allergy to anaesthetic agent')ON CONFLICT DO NOTHING;--mostly about flouranes, could also be about propofol, ketamine, etomidate,....
INSERT INTO "Condition" VALUES ('Z88.5','Personal history of allergy to narcotic agent')ON CONFLICT DO NOTHING;--opioids?
INSERT INTO "Condition" VALUES ('Z88.6','Personal history of allergy to analgesic agent')ON CONFLICT DO NOTHING;--NSAIDs? also opioiods mayyyybe?
INSERT INTO "Condition" VALUES ('Z88.9','Personal history of allergy to unspecified drugs, medicaments and biological substances')ON CONFLICT DO NOTHING;--everything else


INSERT INTO "Interaction" VALUES (DEFAULT,'N01AX10','','Z91.012','Propofol might cause an allergic reaction to patients allergic to eggs, egg products, soy, or soy products {information available on DOI: 10.1016/j.jpainsymman.2010.07.001}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AX10','','I95','Caution is necessry for patients with abnormally low blood pressure {information available on PMID:28613634}.')