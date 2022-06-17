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
--filling in several conditions
INSERT INTO "Condition" VALUES ('J46','Status asthmaticus');
INSERT INTO "Condition" VALUES ('G71','Primary Myopathies');
INSERT INTO "Condition" VALUES ('J95','Intraoperative and postprocedural complications and disorders of respiratory system, not elsewhere classified.');
INSERT INTO "Condition" VALUES ('I95','Hypotension'); -- contraindication to propofol
INSERT INTO "Condition" VALUES('Z91.012','Allergy to eggs');-- contraindication to propofol
INSERT INTO "Condition" VALUES ('T20','Burn and Corrosion of Head, Face, and Neck');--contraindication to suxamethonium (=succinylcholine)
INSERT INTO "Condition" VALUES ('Z33','Pregnant');--idk if z33 is the right code