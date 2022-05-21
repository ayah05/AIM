DROP TABLE IF EXISTS Patient;
DROP TABLE IF EXISTS Drug;
DROP TABLE IF EXISTS Condition;
DROP TABLE IF EXISTS Interaction;

CREATE TABLE Patient (
                           "PatientID" serial,
                           "Drug" varchar(7),
                           "Condition" varchar(5),
                           "Name" varchar(50),
                           "Age" int,
                           "Weight" int,
                           PRIMARY KEY ("PatientID")
);

CREATE TABLE Condition (
                             "Code (ICD-10)" varchar(5),
                             "Name" varchar(50),
                             PRIMARY KEY ("Code (ICD-10)")
);

CREATE TABLE Drug (
                        "Code (ATC)" varchar(7),
                        "Name" varchar(50),
                        PRIMARY KEY ("Code (ATC)")
);

CREATE TABLE Interaction (
                               "InteractionID" serial,
                               "Drug" varchar(7),
                               "Drug2 | Condition" varchar(7),
                               "Hint" varchar(50),
                               PRIMARY KEY ("InteractionID")
);

