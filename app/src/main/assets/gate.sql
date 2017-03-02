DROP TABLE IF EXISTS "gate_object";
CREATE TABLE "gate_object" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "nameObject" VARCHAR, "phoneNumber" VARCHAR, "isFill" BOOL);
INSERT INTO "gate_object" VALUES(1,'Family house','0643376043','true');
INSERT INTO "gate_object" VALUES(2,'Apartment','0641137975','true');
INSERT INTO "gate_object" VALUES(3,'Local','0640644294','true');
INSERT INTO "gate_object" VALUES(4,'Business space','0643901391','false');
INSERT INTO "gate_object" VALUES(5,'Office','0642133188','false');
