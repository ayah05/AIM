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
                           "Weight" int
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

