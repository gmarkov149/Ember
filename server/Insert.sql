-- Daniel Delago

LOCK TABLES `User` WRITE;
INSERT INTO `User`
VALUES
	(1,'nahmed','Nuha Ahmed', 'nahmed@gmail.com', '10/26/1998', 'Female', 'Los Angeles', '1', '0'),
	(9,'ddelago','Daniel Delago', 'ddelago@gmail.com', '05/07/1996', 'Male', 'Singapore', '0', '1'),
	(4,'kramos','Kevin Ramos', 'kramos@gmail.com', '1/1/1994', 'Male', 'Puerto Rico', '0', '1');
UNLOCK TABLES;

LOCK TABLES `Login` WRITE;
INSERT INTO `Login`
VALUES
	('nahmed', '1234', 1),
	('ddelago', '1234', 9),
	('kramos', '1234', 4);
UNLOCK TABLES;

LOCK TABLES `Languages` WRITE;
INSERT INTO `Languages`
VALUES
	(1, '1', '0', '0', '1'),
	(9, '1', '1', '0', '0'),
	(4, '1', '0', '0', '1');
UNLOCK TABLES;

LOCK TABLES `Media` WRITE;
INSERT INTO `Media`
VALUES
	(1, 900, '/fake/path/pic.jpg'),
	(9, 900, '/fake/path/pic.jpg'),
	(4, 480, '/fake/path/pic.jpg');
UNLOCK TABLES;

LOCK TABLES `Hobbies` WRITE;
INSERT INTO `Hobbies`
VALUES
	(1, '0', '1', '0', '0', '1', '1', '1', '0', '0', '0', '1'),
	(9, '0', '1', '0', '1', '0', '1', '0', '0', '1', '0', '1'),
	(4, '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1');
UNLOCK TABLES;

LOCK TABLES `Matches` WRITE;
INSERT INTO `Matches`
VALUES
	(1, 9, '/fake/path/chat.txt'),
	(9, 1, '/fake/path/chat.txt');
UNLOCK TABLES;

LOCK TABLES `SuggestedPartners` WRITE;
INSERT INTO `SuggestedPartners`
VALUES
	(1, 9),
	(9, 1),
	(4, 9);
UNLOCK TABLES;

LOCK TABLES `LikedUsers` WRITE;
INSERT INTO `LikedUsers`
VALUES
	(1, 9),
	(1, 4),
	(9, 1),
	(4, 1);
UNLOCK TABLES;