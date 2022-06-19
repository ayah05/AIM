DROP TABLE IF EXISTS Patient;
DROP TABLE IF EXISTS Drug;
DROP TABLE IF EXISTS Condition;
DROP TABLE IF EXISTS Interaction;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO ayah05, kulturaffe;

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
INSERT INTO "Drug" VALUES ('J01FA','Macrolides')ON CONFLICT DO NOTHING; --contraindicated to fentanyl
INSERT INTO "Drug" VALUES ('J05AE',' Protease inhibitors')ON CONFLICT DO NOTHING; --contraindicated to fentanyl
INSERT INTO "Drug" VALUES ('C08DB01','Diltiazem')ON CONFLICT DO NOTHING;--contraindicated to fentanyl (Diltiazem is a Benzothiazepine derivative)
INSERT INTO "Drug" VALUES ('J02AC02','Itraconazole')ON CONFLICT DO NOTHING;--contraindicated to fentanyl (Itraconazole is an antimycotics for systemic use)
INSERT INTO "Drug" VALUES ('N06AF01','Isocarboxazid')ON CONFLICT DO NOTHING;--contraindicated to fentanyl (is a Monoamine oxidase inhibitors (MAOIs))
INSERT INTO "Drug" VALUES ('C01BD04','Dofetilide') ON CONFLICT DO NOTHING;--C01BD - Antiarrhythmics, class III
INSERT INTO "Drug" VALUES ('D11AH08','Abrocitinib') ON CONFLICT DO NOTHING;--D11AH-Agents for dermatitis, excluding corticosteroids

--filling in several conditions
INSERT INTO "Condition" VALUES ('J46','Status asthmaticus') ON CONFLICT DO NOTHING;--contraindicated to fentanyl
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
INSERT INTO "Condition" VALUES ('R57.9','Shock, unspecified') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('E88.09','Other disorders of plasma-protein metabolism, not elsewhere classified') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('T07','Unspecified multiple injuries') ON CONFLICT DO NOTHING;--Polytrauma
INSERT INTO "Condition" VALUES ('T88.3','Malignant hyperthermia due to anesthesia, initial encounter') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('G70.0','Myasthenia gravis') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('G47.30','Sleep apnea, unspecified') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('H40.21','Acute angle-closure glaucoma') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('K27.9','Peptic ulcer, site unspecified, unspecified as acute or chronic, without hemorrhage or perforation') ON CONFLICT DO NOTHING;
INSERT INTO "Condition" VALUES ('K29','Gastritis und Duodenitis') ON CONFLICT DO NOTHING;


--filling in several interactions
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AX10','Z91.012','Propofol might cause an allergic reaction to patients allergic to eggs, egg products, soy, or soy products. {information available on DOI:10.1016/j.jpainsymman.2010.07.001}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AX10','I95','Caution is necessry for patients with abnormally low blood pressure. {information available on PMID:28613634}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH01','J46','The use of fentanyl is contraindicated in patients with respiratory depression or obstructive airway diseases (i.e., asthma, COPD, obstructive sleep apnea, obesity hyperventilation, also know as, Pickwickian syndrome). {information available on PMID:29083586}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH01','J44.1','The use of fentanyl is contraindicated in patients with respiratory depression or obstructive airway diseases (i.e., asthma, COPD, obstructive sleep apnea, obesity hyperventilation, also know as, Pickwickian syndrome). {information available on PMID:29083586}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH01','E66.2','The use of fentanyl is contraindicated in patients with respiratory depression or obstructive airway diseases (i.e., asthma, COPD, obstructive sleep apnea, obesity hyperventilation, also know as, Pickwickian syndrome). {information available on PMID:29083586}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH01','K70','The use of fentanyl is contraindicated in patients with liver failure. {information available on PMID:29083586}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH01','K71','The use of fentanyl is contraindicated in patients with liver failure. {information available on PMID:29083586}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH01','K72','The use of fentanyl is contraindicated in patients with liver failure. {information available on PMID:29083586}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH01','J01FA','Fentanyl should not be used with certain medications such as CYP3A4 inhibitors like macrolide antibiotics or azole-antifungal agents, and protease inhibitors because they may increase plasma concentrations of fentanyl, extending the opioid drug action and exacerbating the opioid-induced respiratory depression (OIRD). {information available on PMID:29083586}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH01','J05AE','Fentanyl should not be used with certain medications such as CYP3A4 inhibitors like macrolide antibiotics or azole-antifungal agents, and protease inhibitors because they may increase plasma concentrations of fentanyl, extending the opioid drug action and exacerbating the opioid-induced respiratory depression (OIRD). {information available on PMID:29083586}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH01','C08DB01','Fentanyl should not be used with certain medications such as CYP3A4 inhibitors like macrolide antibiotics or azole-antifungal agents, and protease inhibitors because they may increase plasma concentrations of fentanyl, extending the opioid drug action and exacerbating the opioid-induced respiratory depression (OIRD). {information available on PMID:29083586}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH01','J02AC02','Fentanyl should not be used with certain medications such as CYP3A4 inhibitors like macrolide antibiotics or azole-antifungal agents, and protease inhibitors because they may increase plasma concentrations of fentanyl, extending the opioid drug action and exacerbating the opioid-induced respiratory depression (OIRD). {information available on PMID:29083586}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH01','N06AF01','Fentanyl is contraindicated if a patient has used a monoamine oxidase inhibitor in the previous 14 days. {information available on PMID:29083586}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH03','B01AC06','The risk or severity of hypertension can be increased when Sufentanil is combined with Acetylsalicylic acid - severity:minor.  {information available on DrugBank}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AH03','N05BA01','The risk or severity of adverse effects can be increased when Sufentanil is combined with 1,2-Benzodiazepine - severity:moderate. {information available on DrugBank}.');
INSERT INTO "Interaction" VALUES (DEFAULT,'N01AX03','N05BA01','The risk or severity of adverse effects can be increased when Ketamine is combined with 1,2-Benzodiazepine - severity: moderate. {information available on DrugBank}.');
INSERT INTO "Interaction" VALUES (Default,'N01AX03','Z33.3','It is not recommended to use Ketamine during obstetrics, pregnancy, or breastfeeding as it is unknown if this medication passes into breast milk. {information available on PMID:29262083}.');
INSERT INTO "Interaction" VALUES (Default,'N01AX03','O90','It is not recommended to use Ketamine during obstetrics, pregnancy, or breastfeeding as it is unknown if this medication passes into breast milk. {information available on PMID:29262083}.');
INSERT INTO "Interaction" VALUES (Default,'N01AX03','O94','It is not recommended to use Ketamine during obstetrics, pregnancy, or breastfeeding as it is unknown if this medication passes into breast milk. {information available on PMID:29262083}.');
INSERT INTO "Interaction" VALUES (Default,'N01AX03','Z39.1','It is not recommended to use Ketamine during obstetrics, pregnancy, or breastfeeding as it is unknown if this medication passes into breast milk. {information available on PMID:29262083}.');
INSERT INTO "Interaction" VALUES (Default,'N01AX03','F20.9','Ketamine is contraindicated in patients with schizophrenia due to the potential for exacerbating the underlying condition. {information available on PMID: 29262083}.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','H40.21','Benzodiazepines are contraindicated in patients with acute narrow-angle glaucoma, {information available on Accessdata.fda.gov.');--Midazolam is a Benzodiazepine
INSERT INTO "Interaction" VALUES (Default,'N05CD08','I95','Contraindications for the use of midazolam include acute angle-closure glaucoma, hypotension, and shock. {information available on PMID:30726006.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','R57.9','Contraindications for the use of midazolam include acute angle-closure glaucoma, hypotension, and shock. {information available on PMID:30726006.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','N17','Careful dose adjustment is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','N18','Careful dose adjustment is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','N19','Careful dose adjustment is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','K70','Careful dose adjustment is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','K71','Careful dose adjustment is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','K72','Careful dose adjustment is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','K73','Careful dose adjustment is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','K74','Careful dose adjustment is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','K75','Careful dose adjustment is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','K76','Careful dose adjustment is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.');
INSERT INTO "Interaction" VALUES (Default,'N05CD08','K77','Careful dose adjustment is necessary in cases of kidney and liver diseases, alcohol, and drug-dependent individuals. {information available on PMID:30726006}.');
INSERT INTO "Interaction" VALUES (Default,'M03AB01','T20','Succinylcholine is contraindicated in patients with known decreased plasma cholinesterase activity, recent burns or trauma within 24 to 72 hours, and muscle myopathies. {information available on PMID:29763160}.');
INSERT INTO "Interaction" VALUES (Default,'M03AB01','E88.09','Succinylcholine is contraindicated in patients with known decreased plasma cholinesterase activity, recent burns or trauma within 24 to 72 hours, and muscle myopathies. {information available on PMID:29763160}.');
INSERT INTO "Interaction" VALUES (Default,'M03AB01','T07','Succinylcholine is contraindicated in patients with known decreased plasma cholinesterase activity, recent burns or trauma within 24 to 72 hours, and muscle myopathies. {information available on PMID:29763160}.');
INSERT INTO "Interaction" VALUES (Default,'M03AB01','G71','Succinylcholine is contraindicated in patients with known decreased plasma cholinesterase activity, recent burns or trauma within 24 to 72 hours, and muscle myopathies. {information available on PMID:29763160}.');
INSERT INTO "Interaction" VALUES (Default,'M03AB01','G72','Succinylcholine is contraindicated in patients with known decreased plasma cholinesterase activity, recent burns or trauma within 24 to 72 hours, and muscle myopathies. {information available on PMID:29763160}.');
INSERT INTO "Interaction" VALUES (Default,'M03AC09','Z88.4','The absolute contraindication to using Rocuronium would be a documented allergic reaction to the drug. {information available on PMID:30969710}.');
INSERT INTO "Interaction" VALUES (Default,'M03AC09','N05CD08','Midazolam may increase the central nervous system depressant (CNS depressant) activities of Rocuronium - severity:moderate. {information available on DrugBank}.');
INSERT INTO "Interaction" VALUES (Default,'N01BB02','Z88.4','Lidocaine is contraindicated in patients with a known severe adverse reaction.  {information available on PMID:30969703}.');
INSERT INTO "Interaction" VALUES (Default,'N01BB02','N01AH01','The risk or severity of adverse effects can be increased when Lidocaine is combined with Fentanyl - severity:moderate.  {information available on DrugBank}.');
INSERT INTO "Interaction" VALUES (Default,'N01BB02','C01BD04','Dofetilide may increase the arrhythmogenic activities of Lidocaine - severity:moderate.  {information available on DrugBank}.');
INSERT INTO "Interaction" VALUES (Default,'N01AB08','T88.3','Sevoflurane is contraindicated in any patient with known or suspected susceptibility to malignant hyperthermia.  {information available on PMID:30521202}.');
INSERT INTO "Interaction" VALUES (Default,'N01AB08','Z88.4','Sevoflurane is contraindicated in patients with known hypersensitivity to sevoflurane or any other halogenated anesthetics.  {information available on PMID:30521202}.');
INSERT INTO "Interaction" VALUES (Default,'N01AB08','Z88.5','Sevoflurane is contraindicated in patients with known hypersensitivity to sevoflurane or any other halogenated anesthetics.  {information available on PMID:30521202}.');
INSERT INTO "Interaction" VALUES (Default,'N01AB08','Z88.6','Sevoflurane is contraindicated in patients with known hypersensitivity to sevoflurane or any other halogenated anesthetics.  {information available on PMID:30521202}.');
INSERT INTO "Interaction" VALUES (Default,'N01AB08','Z88.9','Sevoflurane is contraindicated in patients with known hypersensitivity to sevoflurane or any other halogenated anesthetics.  {information available on PMID:30521202}.');
INSERT INTO "Interaction" VALUES (Default,'N01AB08','T88.3','Patients who have genetic contraindications, such as those that carry gene variations for malignant hyperthermia, should avoid anesthetic gases.  {information available on PMID:32119427}.');--sevofluran is an inhalation anesthetic//is this the right ICD-10 code???
INSERT INTO "Interaction" VALUES (Default,'N01AB08','D11AH08','The risk or severity of bleeding and thrombocytopenia can be increased when Sevoflurane is combined with Abrocitinib - severity: major.  {information available on DrugBank}.');
INSERT INTO "Interaction" VALUES (Default,'N05BA01','J96','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.');
INSERT INTO "Interaction" VALUES (Default,'N05BA01','G47.30','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.');
INSERT INTO "Interaction" VALUES (Default,'N05BA01','K70','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.');
INSERT INTO "Interaction" VALUES (Default,'N05BA01','K71','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.');
INSERT INTO "Interaction" VALUES (Default,'N05BA01','K72','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.');
INSERT INTO "Interaction" VALUES (Default,'N05BA01','K73','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.');
INSERT INTO "Interaction" VALUES (Default,'N05BA01','K74','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.');
INSERT INTO "Interaction" VALUES (Default,'N05BA01','K75','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.');
INSERT INTO "Interaction" VALUES (Default,'N05BA01','K76','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.');
INSERT INTO "Interaction" VALUES (Default,'N05BA01','K77','Contraindications to Diazepam include patients with severe respiratory insufficiency, myasthenia gravis, sleep apnea syndrome, and severe hepatic insufficiency.  {information available on PMID:30725707}.');
INSERT INTO "Interaction" VALUES (Default,'N05BA01','H40.21','It is permissible in patients with open-angle glaucoma receiving appropriate therapy but is contraindicated in acute narrow-angle glaucoma. {information available on PMID:30725707}.');
INSERT INTO "Interaction" VALUES (Default,'N02BA01','K27.9','Aspirin increases the risk of GI bleeding in patients who already suffer from peptic ulcer disease or gastritis. {information available on PMID:30085574}.');
INSERT INTO "Interaction" VALUES (Default,'N02BA01','K29','Aspirin increases the risk of GI bleeding in patients who already suffer from peptic ulcer disease or gastritis. {information available on PMID: 30085574}.');
INSERT INTO "Interaction" VALUES (Default,'N02BA01','B01AA03','Acetylsalicylic acid may increase the anticoagulant activities of Warfarin - severity: moderate. {information available on DrugBank}.');
INSERT INTO "Interaction" VALUES (Default,'N01AX10','B01AA04','The metabolism of Phenprocoumon can be decreased when combined with Propofol - severity: major. {information available on DrugBank}.');
INSERT INTO "Interaction" VALUES (Default,'N01AB08','B01AA04','The risk or severity of bleeding can be increased when Sevoflurane is combined with Phenprocoumon - severity: moderate. {information available on DrugBank}.');
























