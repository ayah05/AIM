DROP TABLE IF EXISTS "Patient" CASCADE;
DROP TABLE IF EXISTS "Drug" CASCADE;
DROP TABLE IF EXISTS "Condition" CASCADE;
DROP TABLE IF EXISTS "Interaction" CASCADE;


GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;

CREATE TABLE "Patient" (
                           "PatientID" serial PRIMARY KEY,
                           "Drug" varchar(50) , --REFERENCES "Drug"
                           "Condition" varchar(50) , --REFERENCES "Condition"
                           "Firstname" varchar(100) NOT NULL,
                           "Lastname" varchar(100) NOT NULL,
                           "Age" int,
                           "Weight" real
);

CREATE TABLE "Condition" (
                             "Code (ICD-10)" varchar(7) PRIMARY KEY,
                             "Name" varchar(100) NOT NULL
);

CREATE TABLE "Drug" (
                        "Code (ATC)" varchar(8) PRIMARY KEY,
                        "Name" varchar(100) NOT NULL
);


CREATE TABLE "Interaction" (
                               "InteractionID" serial PRIMARY KEY ,
                               "Drug" varchar(8) NOT NULL, -- REFERENCES "Drug"
                               "Drug2orCond" varchar(8) NOT NULL,
                               "Hint" varchar(500)
);

TRUNCATE TABLE "Patient";

INSERT INTO "Patient" VALUES (1,'R03AC02,N05BA01','J45,K70','Brigitta','Haggis',34,65.6) ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (2,'S01EB01,N05BA01','H40.21,K70','Giulietta','Cirlos',23,55.7)ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (3,'B01AA04,N05BA01','I74,K70,H40.21','Alexio', 'Jarlmann',50,88.3)ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (4,'N05BA01','R57,H40.21','Georgiana','Labdon',39,70.4)ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (5,'N07AA01','G70.0','York','Kingscott',27,76.2)ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (6,'N05BA01','K74,Z88.4','Tim','Bettenson',20,65.1)ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (7,'N05BA01,C08DB01','G47.30','Mahmud','Witling',65,82.4)ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (8,'C09AA02','N18','Rosana','Jantet',45,66.6666)ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (9,'A10AB01','Z91.012,E10','Kathe','Camm',70,57.9)ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (10,'C08DB01','I25','Janett','Harris',30,60.1)ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (11,'D11AH08','L20,T07','Mena','Arafa',34,65.2)ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (12,'C01BD04,A10BC01,N05BA01','I48,E11','Alexander','Helks',44,90.3)ON CONFLICT DO NOTHING;
INSERT INTO "Patient" VALUES (13,'L02BG03,G02CX04,J01FA09,B01AA03','N18,N95.1,Z88.0','Martha','DeLarosa',50,63.4)ON CONFLICT DO NOTHING;

--filling in drugs and atc codes
INSERT INTO "Drug" VALUES ('N01AX10','Propofol')ON CONFLICT DO NOTHING;
INSERT INTO "Drug" VALUES ('N01AH01','Fentanyl')ON CONFLICT DO NOTHING;--N01AH - opioid anesthetics
INSERT INTO "Drug" VALUES ('N01AH03','Sufentanil')ON CONFLICT DO NOTHING;--N01A (Allgemeinanästhesie);N01B(Lokalanästhetika)
INSERT INTO "Drug" VALUES ('N01AX03','Ketamine')ON CONFLICT DO NOTHING;--N01AX - other general anesthetics
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
INSERT INTO "Drug" VALUES ('J01FA09','Clarithromycin')ON CONFLICT DO NOTHING; --contraindicated to fentanyl, Clarithromycin is a Macrolide --protease inhibitorS ist kein medikament......contraindicated to fentanyl
INSERT INTO "Drug" VALUES ('C08DB01','Diltiazem')ON CONFLICT DO NOTHING;--contraindicated to fentanyl (Diltiazem is a Benzothiazepine derivative)
INSERT INTO "Drug" VALUES ('J02AC02','Itraconazole')ON CONFLICT DO NOTHING;--contraindicated to fentanyl (Itraconazole is an antimycotics for systemic use)
INSERT INTO "Drug" VALUES ('N06AF01','Isocarboxazid')ON CONFLICT DO NOTHING;--contraindicated to fentanyl (is a Monoamine oxidase inhibitors (MAOIs))
INSERT INTO "Drug" VALUES ('C01BD04','Dofetilide') ON CONFLICT DO NOTHING;--C01BD - Antiarrhythmics, class III (mostly used for patients with atrial fibrillation)
INSERT INTO "Drug" VALUES ('D11AH08','Abrocitinib') ON CONFLICT DO NOTHING;--D11AH-Agents for dermatitis, excluding corticosteroids
INSERT INTO "Drug" VALUES ('A10AB01','Insulin (Human)') ON CONFLICT DO NOTHING;--drug for diabetes patients
INSERT INTO "Drug" VALUES ('A10BC01','Glymidine') ON CONFLICT DO NOTHING; --drug for diabetes patients
INSERT INTO "Drug" VALUES ('A10BJ01','Exenatide') ON CONFLICT DO NOTHING;--drug for diabetes patients
INSERT INTO "Drug" VALUES ('A10XA01','Tolrestat') ON CONFLICT DO NOTHING;--drug for diabetes patients
INSERT INTO "Drug" VALUES ('C09AA02','Enalapril') ON CONFLICT DO NOTHING; --drug for patients with chronic renal disease
INSERT INTO "Drug" VALUES ('G02CX04','Cimicifugae rhizoma') ON CONFLICT DO NOTHING; --common name:Black cohosh
INSERT INTO "Drug" VALUES ('L02BG03','Anastrozole') ON CONFLICT DO NOTHING;
INSERT INTO "Drug" VALUES ('N07AA01','Neostigmine') ON CONFLICT DO NOTHING; -- drug for patients with Myasthenia gravis
INSERT INTO "Drug" VALUES ('S01EB01','Pilocarpine') ON CONFLICT DO NOTHING; -- drug for patients with Acute angle-closure glaucoma
INSERT INTO "Drug" VALUES ('R03AC02','Salbutamol') ON CONFLICT DO NOTHING; --drug for patients with Asthma

--filling in several conditions
INSERT INTO "Condition" VALUES ('J46','Status asthmaticus') ON CONFLICT DO NOTHING;--contraindicated to fentanyl
INSERT INTO "Condition" VALUES ('J45','Asthma bronchiale') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('G71','Primary Myopathies')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('J96','Acute respiratory failure, unspecified whether with hypoxia or hypercapnia')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('G72','Other myopathies')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('J44.1','Chronic obstructive pulmonary disease with (acute) exacerbation')ON CONFLICT DO NOTHING;--contraindicated to fentanyl
INSERT INTO "Condition" VALUES ('E66.2','Morbid (severe) obesity with alveolar hypoventilation')ON CONFLICT DO NOTHING;--contraindicated to fentanyl
INSERT INTO "Condition" VALUES ('I95','Hypotension')ON CONFLICT DO NOTHING; -- contraindication to propofol
INSERT INTO "Condition" VALUES('Z91.012','Allergy to eggs')ON CONFLICT DO NOTHING;-- contraindication to propofol
INSERT INTO "Condition" VALUES('Z91.2','Personal history of poor personal hygiene')ON CONFLICT DO NOTHING;--contraindication to performing any kind of medical procedure on that person.
INSERT INTO "Condition" VALUES ('T20','Burn and Corrosion of Head, Face, and Neck')ON CONFLICT DO NOTHING;--contraindication to suxamethonium (=succinylcholine) (M03AB01)
INSERT INTO "Condition" VALUES ('Z33.3','Pregnant state')ON CONFLICT DO NOTHING;
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
INSERT INTO "Condition" VALUES ('I74','Arterial embolism and thrombosis') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('I48','Atrial fibrillation and flutter') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('Z88.0','Personal history of allergy to penicillin')ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('Z88.1','Personal history of allergy to other antibiotic agents')ON CONFLICT DO NOTHING;--any antibiotic except penicillin
INSERT INTO "Condition" VALUES ('Z88.4','Personal history of allergy to anaesthetic agent')ON CONFLICT DO NOTHING;--mostly about flouranes, could also be about propofol, ketamine, etomidate,....
INSERT INTO "Condition" VALUES ('Z88.5','Personal history of allergy to narcotic agent')ON CONFLICT DO NOTHING;--opioids?
INSERT INTO "Condition" VALUES ('Z88.6','Personal history of allergy to analgesic agent')ON CONFLICT DO NOTHING;--NSAIDs? also opioiods mayyyybe?
INSERT INTO "Condition" VALUES ('Z88.9','Personal history of allergy to unspecified drugs, medicaments and biological substances')ON CONFLICT DO NOTHING;--everything else
INSERT INTO "Condition" VALUES ('O90','Complications of the puerperium, not elsewhere classified')ON CONFLICT DO NOTHING;--everything else
INSERT INTO "Condition" VALUES ('O94','Sequelae of complication of pregnancy, childbirth, and the puerperium')ON CONFLICT DO NOTHING;--everything else
INSERT INTO "Condition" VALUES ('Z39.1','Encounter for care and examination of lactating mother')ON CONFLICT DO NOTHING;--everything else
INSERT INTO "Condition" VALUES ('F20.9','Schizophrenia, unspecified') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('H40.21','Acute angle-closure glaucoma') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('R57','Shock, not elsewhere classified') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('E88.09','Other disorders of plasma-protein metabolism, not elsewhere classified') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('T07','Unspecified multiple injuries') ON CONFLICT DO NOTHING;--Polytrauma
INSERT INTO "Condition" VALUES ('T88.3','Malignant hyperthermia due to anesthesia, initial encounter') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('G70.0','Myasthenia gravis') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('G47.30','Sleep apnea, unspecified') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('K27.9','Peptic ulcer, site unspecified, unspecified as acute or chronic, without hemorrhage or perforation') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('K29','Gastritis und Duodenitis') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('E10','Type 1 diabetes mellitus') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('E11','Type 2 diabetes mellitus') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('N95.1','Menopausal and female climacteric states') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('I25','Chronic ischemic heart disease') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('L20','Atopic dermatitis') ON CONFLICT DO NOTHING;

--filling in several interactions
TRUNCATE TABLE "Interaction";
INSERT INTO "Interaction" VALUES (1,'N01AX10','Z91.012','Propofol might cause an allergic reaction to patients allergic to eggs, egg products, soy, or soy products. {information available on DOI:10.1016/j.jpainsymman.2010.07.001}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (2,'N01AX10','I95','Caution is necessry for patients with abnormally low blood pressure. {information available on PMID:28613634}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (3,'N01AH01','J46','The use of fentanyl is contraindicated in patients with respiratory depression or obstructive airway diseases (i.e., asthma, COPD, obstructive sleep apnea, obesity hyperventilation, also know as, Pickwickian syndrome). {information available on PMID:29083586}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (4,'N01AH01','J44','The use of fentanyl is contraindicated in patients with respiratory depression or obstructive airway diseases (i.e., asthma, COPD, obstructive sleep apnea, obesity hyperventilation, also know as, Pickwickian syndrome). {information available on PMID:29083586}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (5,'N01AH01','E66','The use of fentanyl is contraindicated in patients with respiratory depression or obstructive airway diseases (i.e., asthma, COPD, obstructive sleep apnea, obesity hyperventilation, also know as, Pickwickian syndrome). {information available on PMID:29083586}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (6,'N01AH01','K70','The use of fentanyl is contraindicated in patients with liver failure. {information available on PMID:29083586}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (7,'N01AH01','K71','The use of fentanyl is contraindicated in patients with liver failure. {information available on PMID:29083586}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (8,'N01AH01','K72','The use of fentanyl is contraindicated in patients with liver failure. {information available on PMID:29083586}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (9,'N01AH01','J01FA09','Fentanyl should not be used with certain medications such as CYP3A4 inhibitors like macrolide antibiotics or azole-antifungal agents, and protease inhibitors because they may increase plasma concentrations of fentanyl, extending the opioid drug action and exacerbating the opioid-induced respiratory depression (OIRD). {information available on PMID:29083586}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (10,'N01AH01','J05AE','Fentanyl should not be used with certain medications such as CYP3A4 inhibitors like macrolide antibiotics or azole-antifungal agents, and protease inhibitors because they may increase plasma concentrations of fentanyl, extending the opioid drug action and exacerbating the opioid-induced respiratory depression (OIRD). {information available on PMID:29083586}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (11,'N01AH01','C08DB01','Fentanyl should not be used with certain medications such as CYP3A4 inhibitors like macrolide antibiotics or azole-antifungal agents, and protease inhibitors because they may increase plasma concentrations of fentanyl, extending the opioid drug action and exacerbating the opioid-induced respiratory depression (OIRD). {information available on PMID:29083586}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (12,'N01AH01','J02AC02','Fentanyl should not be used with certain medications such as CYP3A4 inhibitors like macrolide antibiotics or azole-antifungal agents, and protease inhibitors because they may increase plasma concentrations of fentanyl, extending the opioid drug action and exacerbating the opioid-induced respiratory depression (OIRD). {information available on PMID:29083586}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (13,'N01AH01','N06AF01','Fentanyl is contraindicated if a patient has used a monoamine oxidase inhibitor in the previous 14 days. {information available on PMID:29083586}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (14,'N01AH03','B01AC06','The risk or severity of hypertension can be increased when Sufentanil is combined with Acetylsalicylic acid - severity:minor.  {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (15,'N01AH03','N05BA01','The risk or severity of adverse effects can be increased when Sufentanil is combined with 1,2-Benzodiazepine - severity:moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (16,'N01AX03','N05BA01','The risk or severity of adverse effects can be increased when Ketamine is combined with 1,2-Benzodiazepine - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (17,'N01AX03','Z33.3','It is not recommended to use Ketamine during obstetrics, pregnancy, or breastfeeding as it is unknown if this medication passes into breast milk. {information available on PMID:29262083}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (18,'N01AX03','O90','It is not recommended to use Ketamine during obstetrics, pregnancy, or breastfeeding as it is unknown if this medication passes into breast milk. {information available on PMID:29262083}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (19,'N01AX03','O94','It is not recommended to use Ketamine during obstetrics, pregnancy, or breastfeeding as it is unknown if this medication passes into breast milk. {information available on PMID:29262083}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (20,'N01AX03','Z39','It is not recommended to use Ketamine during obstetrics, pregnancy, or breastfeeding as it is unknown if this medication passes into breast milk. {information available on PMID:29262083}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (21,'N01AX03','F20','Ketamine is contraindicated in patients with schizophrenia due to the potential for exacerbating the underlying condition. {information available on PMID: 29262083}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (22,'N05CD08','H40.21','Benzodiazepines are contraindicated in patients with acute narrow-angle glaucoma, {information available on Accessdata.fda.gov.')ON CONFLICT DO NOTHING;--Midazolam is a Benzodiazepine
INSERT INTO "Interaction" VALUES (23,'N05CD08','I95','Contraindications for the use of midazolam include acute angle-closure glaucoma, hypotension, and shock. {information available on PMID:30726006.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (24,'N05CD08','R57','Contraindications for the use of midazolam include acute angle-closure glaucoma, hypotension, and shock. {information available on PMID:30726006.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (25,'N05CD08','N17','Careful dose adjustment of midazolam is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (26,'N05CD08','N18','Careful dose adjustment of midazolam is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (27,'N05CD08','N19','Careful dose adjustment of midazolam is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (28,'N05CD08','K70','Careful dose adjustment of midazolam is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (29,'N05CD08','K71','Careful dose adjustment of midazolam is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (30,'N05CD08','K72','Careful dose adjustment of midazolam is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (31,'N05CD08','K73','Careful dose adjustment of midazolam is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (32,'N05CD08','K74','Careful dose adjustment of midazolam is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (33,'N05CD08','K75','Careful dose adjustment of midazolam is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (34,'N05CD08','K76','Careful dose adjustment of midazolam is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (35,'N05CD08','K77','Careful dose adjustment of midazolam is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (36,'M03AB01','T20','Succinylcholine is contraindicated in patients with known decreased plasma cholinesterase activity, recent burns or trauma within 24 to 72 hours, and muscle myopathies. {information available on PMID:29763160}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (37,'M03AB01','E88.09','Succinylcholine is contraindicated in patients with known decreased plasma cholinesterase activity, recent burns or trauma within 24 to 72 hours, and muscle myopathies. {information available on PMID:29763160}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (38,'M03AB01','T07','Succinylcholine is contraindicated in patients with known decreased plasma cholinesterase activity, recent burns or trauma within 24 to 72 hours, and muscle myopathies. {information available on PMID:29763160}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (39,'M03AB01','G71','Succinylcholine is contraindicated in patients with known decreased plasma cholinesterase activity, recent burns or trauma within 24 to 72 hours, and muscle myopathies. {information available on PMID:29763160}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (40,'M03AB01','G72','Succinylcholine is contraindicated in patients with known decreased plasma cholinesterase activity, recent burns or trauma within 24 to 72 hours, and muscle myopathies. {information available on PMID:29763160}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (41,'M03AC09','Z88','The absolute contraindication to using Rocuronium would be a documented allergic reaction to the drug. {information available on PMID:30969710}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (42,'M03AC09','N05CD08','Midazolam may increase the central nervous system depressant (CNS depressant) activities of Rocuronium - severity:moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (43,'N01BB02','Z88','Lidocaine is contraindicated in patients with a known severe adverse reaction.  {information available on PMID:30969703}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (44,'N01BB02','N01AH01','The risk or severity of adverse effects can be increased when Lidocaine is combined with Fentanyl - severity:moderate.  {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (45,'N01BB02','C01BD04','Dofetilide may increase the arrhythmogenic activities of Lidocaine - severity:moderate.  {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (46,'N01AB08','T88','Sevoflurane is contraindicated in any patient with known or suspected susceptibility to malignant hyperthermia.  {information available on PMID:30521202}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (47,'N01AB08','Z88.4','Sevoflurane is contraindicated in patients with known hypersensitivity to sevoflurane or any other halogenated anesthetics.  {information available on PMID:30521202}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (48,'N01AB08','Z88.5','Sevoflurane is contraindicated in patients with known hypersensitivity to sevoflurane or any other halogenated anesthetics.  {information available on PMID:30521202}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (49,'N01AB08','Z88.6','Sevoflurane is contraindicated in patients with known hypersensitivity to sevoflurane or any other halogenated anesthetics.  {information available on PMID:30521202}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (50,'N01AB08','Z88.9','Sevoflurane is contraindicated in patients with known hypersensitivity to sevoflurane or any other halogenated anesthetics.  {information available on PMID:30521202}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (51,'N01AB08','T88.3','Patients who have genetic contraindications, such as those that carry gene variations for malignant hyperthermia, should avoid anesthetic gases.  {information available on PMID:32119427}.')ON CONFLICT DO NOTHING;--sevofluran is an inhalation anesthetic
INSERT INTO "Interaction" VALUES (52,'N01AB08','D11AH08','The risk or severity of bleeding and thrombocytopenia can be increased when Sevoflurane is combined with Abrocitinib - severity: major.  {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (53,'N05BA01','J96','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (54,'N05BA01','G47.30','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (55,'N05BA01','K70','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (56,'N05BA01','K71','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (57,'N05BA01','K72','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (58,'N05BA01','K73','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (59,'N05BA01','K74','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (60,'N05BA01','K75','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (61,'N05BA01','K76','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (62,'N05BA01','K77','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (63,'N05BA01','H40.21','Diazepam is permissible in patients with open-angle glaucoma receiving appropriate therapy but is contraindicated in acute narrow-angle glaucoma. {information available on PMID:30725707}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (64,'N02BA01','K27','Aspirin increases the risk of GI bleeding in patients who already suffer from peptic ulcer disease or gastritis. {information available on PMID:30085574}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (65,'N02BA01','K29','Aspirin increases the risk of GI bleeding in patients who already suffer from peptic ulcer disease or gastritis. {information available on PMID: 30085574}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (66,'N02BA01','B01AA03','Acetylsalicylic acid may increase the anticoagulant activities of Warfarin - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (67,'N01AX10','B01AA04','The metabolism of Phenprocoumon can be decreased when combined with Propofol - severity: major. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (68,'N01AB08','B01AA04','The risk or severity of bleeding can be increased when Sevoflurane is combined with Phenprocoumon - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;

INSERT INTO "Interaction" VALUES (69,'B01AC06','A10AB01','The risk or severity of hypoglycemia can be increased when Acetylsalicylic acid is combined with Insulin human. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (70,'C08DB01','A10AB01','The risk or severity of hypoglycemia can be increased when Diltiazem is combined with Insulin human. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (71,'N06AF01','A10AB01','Isocarboxazid may increase the hypoglycemic activities of Insulin human. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;

INSERT INTO "Interaction" VALUES (72,'B01AA03','A10BJ01','Exenatide can cause an increase in the absorption of Warfarin resulting in an increased serum concentration and potentially a worsening of adverse effects. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (73,'B01AA04','A10BJ01','Exenatide can cause an increase in the absorption of Phenprocoumon resulting in an increased serum concentration and potentially a worsening of adverse effects. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (74,'B01AC06','A10BJ01','The risk or severity of hypoglycemia can be increased when Acetylsalicylic acid is combined with Exenatide. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (75,'C08DB01','A10BJ01','The risk or severity of hypoglycemia can be increased when Diltiazem is combined with Exenatide - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (76,'N06AF01','A10BJ01','Isocarboxazid may increase the hypoglycemic activities of Exenatide. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;

INSERT INTO "Interaction" VALUES (77,'N01AX10','C09AA02','The risk or severity of myopathy, rhabdomyolysis, and myoglobinuria can be increased when Enalapril is combined with Propofol. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (78,'N01AH01','C09AA02','Fentanyl may decrease the antihypertensive activities of Enalapril - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (79,'N01AH03','C09AA02','Sufentanil may decrease the antihypertensive activities of Enalapril. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (80,'M03AB01','C09AA02','The risk or severity of hyperkalemia can be increased when Succinylcholine is combined with Enalapril. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (81,'N01AB08','C09AA02','Sevoflurane may decrease the antihypertensive activities of Enalapril. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (82,'B01AC06','C09AA02','The therapeutic efficacy of Enalapril can be decreased when used in combination with Acetylsalicylic acid. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (83,'J01FA09','C09AA02','The excretion of Enalapril can be decreased when combined with Clarithromycin. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (84,'C08DB01','C09AA02','Diltiazem may increase the hypotensive activities of Enalapril. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (85,'J02AC02','C09AA02','The risk or severity of hyperkalemia can be increased when Enalapril is combined with Itraconazole. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (86,'N06AF01','C09AA02','Isocarboxazid may increase the hypotensive activities of Enalapril. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;

INSERT INTO "Interaction" VALUES (87,'N01AX10','L02BG03','The metabolism of Anastrozole can be decreased when combined with Propofol. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (88,'N01AX03','L02BG03','The metabolism of Ketamine can be decreased when combined with Anastrozole. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (89,'B01AA04','L02BG03','The metabolism of Phenprocoumon can be decreased when combined with Anastrozole. - severity: major. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (90,'C08DB01','L02BG03','The metabolism of Anastrozole can be decreased when combined with Diltiazem. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;

INSERT INTO "Interaction" VALUES (91,'M03AB01','N07AA01','The metabolism of Succinylcholine can be decreased when combined with Neostigmine. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (92,'M03AC09','N07AA01','The therapeutic efficacy of Rocuronium can be decreased when used in combination with Neostigmine. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;

INSERT INTO "Interaction" VALUES (93,'N01AH01','S01EB01','The metabolism of Fentanyl can be decreased when combined with Pilocarpine. - severity: major. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (94,'N01AX03','S01EB01','The risk or severity of adverse effects can be increased when Ketamine is combined with Pilocarpine. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (95,'N05CD08','S01EB01','The serum concentration of Midazolam can be increased when it is combined with Pilocarpine. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (96,'N01AB08','S01EB01','The metabolism of Sevoflurane can be decreased when combined with Pilocarpine. - major: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (97,'B01AA03','S01EB01','The serum concentration of Warfarin can be increased when it is combined with Pilocarpine. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;

INSERT INTO "Interaction" VALUES (98,'N01AX10','R03AC02','The risk or severity of QTc prolongation can be increased when Propofol is combined with Salbutamol. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (99,'N01AH01','R03AC02','The risk or severity of hypertension can be increased when Fentanyl is combined with Salbutamol. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (100,'N01AH03','R03AC02','The risk or severity of hypertension can be increased when Sufentanil is combined with Salbutamol. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (101,'N01AX03','R03AC02','Salbutamol may decrease the excretion rate of Ketamine which could result in a higher serum level. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (102,'N05CD08','R03AC02','Midazolam may decrease the excretion rate of Salbutamol which could result in a higher serum level. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (103,'M03AC09','R03AC02','The risk or severity of Tachycardia can be increased when Rocuronium is combined with Salbutamol. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (104,'N01AB08','R03AC02','The risk or severity of QTc prolongation can be increased when Salbutamol is combined with Sevoflurane. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (105,'N05BA01','R03AC02','Diazepam may decrease the excretion rate of Salbutamol which could result in a higher serum level. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (106,'B01AA03','R03AC02','Salbutamol may decrease the excretion rate of Warfarin which could result in a higher serum level. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (107,'B01AC06','R03AC02','The risk or severity of hypertension can be increased when Acetylsalicylic acid is combined with Salbutamol - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (108,'J01FA09','R03AC02','The risk or severity of QTc prolongation can be increased when Salbutamol is combined with Clarithromycin. - severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (109,'C08DB01','R03AC02','Salbutamol may decrease the antihypertensive activities of Diltiazem.- severity: minor. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (110,'J02AC02','R03AC02','The risk or severity of QTc prolongation can be increased when Salbutamol is combined with Itraconazole. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (111,'N06AF01','R03AC02','The risk or severity of hypertension can be increased when Salbutamol is combined with Isocarboxazid. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (112,'C01BD04','R03AC02','The risk or severity of QTc prolongation can be increased when Salbutamol is combined with Dofetilide. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;

INSERT INTO "Interaction" VALUES (113,'N01AH01','G02CX04','The metabolism of Fentanyl can be decreased when combined with Black cohosh. - severity: major. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (114,'N05CD08','G02CX04','The serum concentration of Midazolam can be increased when it is combined with Black cohosh. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (115,'N01BB02','G02CX04','The metabolism of Lidocaine can be decreased when combined with Black cohosh. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (116,'B01AA03','G02CX04','The serum concentration of Warfarin can be increased when it is combined with Black cohosh. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;
INSERT INTO "Interaction" VALUES (117,'C08DB01','G02CX04','The metabolism of Diltiazem can be decreased when combined with Black cohosh. - severity: moderate. {information available on DrugBank}.')ON CONFLICT DO NOTHING;

--INSERT INTO "Interaction" VALUES (118,'G02CX04','N18','WORKS!')ON CONFLICT DO NOTHING;

