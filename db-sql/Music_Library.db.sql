BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Songs" (
	"Song_ID"	INTEGER,
	"Album_ID"	INTEGER,
	"Title"	TEXT,
	"Track_Number"	INTEGER,
	"Duration"	INTEGER
);
CREATE TABLE IF NOT EXISTS "Albums" (
	"Album_ID"	INTEGER,
	"Artist_ID"	INTEGER,
	"Title"	TEXT,
	"Album_Artwork"	TEXT,
	"Location"	TEXT,
	"Date_Added"	TEXT
);
CREATE TABLE IF NOT EXISTS "Artists" (
	"Artist_ID"	INTEGER,
	"Name"	TEXT
);
INSERT INTO "Songs" VALUES (1,1,'Pan Pipes Percy - Album 1, Song 1',1,1.5);
INSERT INTO "Songs" VALUES (2,1,'Pan Pipes Percy - Album 1, Song 2',2,1.5);
INSERT INTO "Songs" VALUES (3,1,'Pan Pipes Percy - Album 1, Song 3',3,2.5);
INSERT INTO "Songs" VALUES (4,1,'Pan Pipes Percy - Album 1, Song 4',4,1.5);
INSERT INTO "Songs" VALUES (5,1,'Pan Pipes Percy - Album 1, Song 5',5,7.5);
INSERT INTO "Songs" VALUES (6,2,'Pan Pipes Percy - Album 2, Song 1',1,2);
INSERT INTO "Songs" VALUES (7,2,'Pan Pipes Percy - Album 2, Song 2',2,2);
INSERT INTO "Songs" VALUES (8,2,'Pan Pipes Percy - Album 2, Song 3',3,3);
INSERT INTO "Songs" VALUES (9,3,'Pan Pipes Percy - Album 3, Song 1',1,3);
INSERT INTO "Songs" VALUES (10,3,'Pan Pipes Percy - Album 3, Song 2',2,3);
INSERT INTO "Songs" VALUES (11,3,'Pan Pipes Percy - Album 3, Song 3',3,3);
INSERT INTO "Songs" VALUES (12,4,'Music Man - Album 1, Song 1',1,4);
INSERT INTO "Songs" VALUES (13,4,'Music Man - Album 1, Song 2',2,4);
INSERT INTO "Songs" VALUES (14,4,'Music Man - Album 1, Song 3',3,4);
INSERT INTO "Songs" VALUES (15,5,'Music Man - Album 2 Song 1',1,5);
INSERT INTO "Songs" VALUES (16,5,'Music Man - Album 2, Song 2',2,5);
INSERT INTO "Songs" VALUES (17,6,'Musical Pete - Album 1, Song 1',1,6);
INSERT INTO "Songs" VALUES (18,6,'Musical Pete - Album 1, Song 2',2,6);
INSERT INTO "Songs" VALUES (19,6,'Musical Pete - Album 1, Song 3',3,6);
INSERT INTO "Songs" VALUES (20,6,'Musical Pete - Album 1, Song 4',4,6);
INSERT INTO "Songs" VALUES (21,6,'Musical Pete - Album 1, Song 5',5,6);
INSERT INTO "Albums" VALUES (1,1,'Pan Pipes Percy - Album 1',NULL,'C:\','2020-01-05 13:15:43');
INSERT INTO "Albums" VALUES (2,1,'Pan Pipes Percy - Album 2',NULL,'C:\New\','2020-01-05 13:59:58');
INSERT INTO "Albums" VALUES (3,1,'Pan Pipes Percy - Album 3',NULL,'C:\New\','2020-01-05 14:00:07');
INSERT INTO "Albums" VALUES (4,2,'Music Man - Album 1',NULL,'C:\New\','2020-01-05 14:02:25');
INSERT INTO "Albums" VALUES (5,2,'Music Man - Album 2',NULL,'C:\New\','2020-01-05 14:02:45');
INSERT INTO "Albums" VALUES (6,3,'Musical Pete - Album 1',NULL,'C:\New\','2020-01-05 14:03:06');
INSERT INTO "Artists" VALUES (1,'Pan Pipes Percy');
INSERT INTO "Artists" VALUES (2,'Music Man');
INSERT INTO "Artists" VALUES (3,'Musical Pete');
COMMIT;
