BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Songs" (
	"Song_ID"	INTEGER,
	"Album_ID"	INTEGER NOT NULL,
	"Title"	TEXT NOT NULL COLLATE NOCASE,
	PRIMARY KEY("Song_ID"),
	FOREIGN KEY("Album_ID") REFERENCES "Albums"("Album_ID") ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS "Albums" (
	"Album_ID"	INTEGER,
	"Artist_ID"	INTEGER,
	"Title"	TEXT NOT NULL COLLATE NOCASE,
	"Location"	TEXT NOT NULL COLLATE NOCASE,
	FOREIGN KEY("Artist_ID") REFERENCES "Artists"("Artist_ID") ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY("Album_ID")
);
CREATE TABLE IF NOT EXISTS "Artists" (
	"Artist_ID"	INTEGER,
	"Name"	TEXT NOT NULL COLLATE NOCASE,
	PRIMARY KEY("Artist_ID")
);
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (1,1,'Pan Pipes Percy - Album 1, Song 1');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (2,1,'Pan Pipes Percy - Album 1, Song 2');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (3,1,'Pan Pipes Percy - Album 1, Song 3');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (4,1,'Pan Pipes Percy - Album 1, Song 4');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (5,1,'Pan Pipes Percy - Album 1, Song 5');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (6,2,'Pan Pipes Percy - Album 2, Song 1');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (7,2,'Pan Pipes Percy - Album 2, Song 2');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (8,2,'Pan Pipes Percy - Album 2, Song 3');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (9,3,'Pan Pipes Percy - Album 3, Song 1');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (10,3,'Pan Pipes Percy - Album 3, Song 2');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (11,3,'Pan Pipes Percy - Album 3, Song 3');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (12,4,'Metal Mad Mavis - Album 1, Song 1');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (13,4,'Metal Mad Mavis - Album 1, Song 2');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (14,4,'Metal Mad Mavis - Album 1, Song 3');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (15,5,'Metal Mad Mavis - Album 2, Song 1');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (16,5,'Metal Mad Mavis - Album 2 Song 2');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (17,6,'Classical Colin - Album 1, Song 1');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (18,6,'Classical Colin - Album 1, Song 2');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (19,6,'Classical Colin - Album 1, Song 3');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (20,6,'Classical Colin - Album 1, Song 4');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (21,6,'Classical Colin - Album 1, Song 5');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (22,7,'Raving Ralph - Album 1, Song 1');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (23,7,'Raving Ralph - Album 1, Song 2');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (24,7,'Raving Ralph - Album 1, Song 3');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (25,7,'Raving Ralph - Album 1, Song 4');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (26,8,'Raving Ralph - Album 2, Song 1');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (27,8,'Raving Ralph - Album 2, Song 2');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (28,9,'Raving Ralph - Album 3, Song 1');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (29,9,'Raving Ralph - Album 3, Song 2');
INSERT INTO "Songs" ("Song_ID","Album_ID","Title") VALUES (30,9,'Raving Ralph - Album 3, Song 3');
INSERT INTO "Albums" ("Album_ID","Artist_ID","Title","Location") VALUES (1,1,'Pan Pipes Percy - Album 1','C:\default\');
INSERT INTO "Albums" ("Album_ID","Artist_ID","Title","Location") VALUES (2,1,'Pan Pipes Percy - Album 2','C:\default\');
INSERT INTO "Albums" ("Album_ID","Artist_ID","Title","Location") VALUES (3,1,'Pan Pipes Percy - Album 3','C:\default\');
INSERT INTO "Albums" ("Album_ID","Artist_ID","Title","Location") VALUES (4,2,'Metal Mad Mavis - Album 1','D:\default\');
INSERT INTO "Albums" ("Album_ID","Artist_ID","Title","Location") VALUES (5,2,'Metal Mad Mavis - Album 2','D:\default\');
INSERT INTO "Albums" ("Album_ID","Artist_ID","Title","Location") VALUES (6,3,'Classical Colin - Album 1','E:\default\');
INSERT INTO "Albums" ("Album_ID","Artist_ID","Title","Location") VALUES (7,4,'Raving Ralph - Album 1','C:\default\');
INSERT INTO "Albums" ("Album_ID","Artist_ID","Title","Location") VALUES (8,4,'Raving Ralph - Album 2','C:\default\');
INSERT INTO "Albums" ("Album_ID","Artist_ID","Title","Location") VALUES (9,4,'Raving Ralph - Album 3','C:\default\');
INSERT INTO "Artists" ("Artist_ID","Name") VALUES (1,'Pan Pipes Percy');
INSERT INTO "Artists" ("Artist_ID","Name") VALUES (2,'Metal Mad Mavis');
INSERT INTO "Artists" ("Artist_ID","Name") VALUES (3,'Classical Colin');
INSERT INTO "Artists" ("Artist_ID","Name") VALUES (4,'Raving Ralph');
COMMIT;
