-- Daniel Delago

LOCK TABLES `Users` WRITE;
INSERT INTO `Users`
VALUES
	(1,'nahmed','Nuha Ahmed', 'nahmed@gmail.com', '10/26/1998', 'Female', 'Los Angeles', 900, '/fake/path/pic.jpg', '1', '0'),
	(9,'ddelago','Daniel Delago', 'ddelago@gmail.com', '05/07/1996', 'Male', 'Singapore', 900, '/fake/path/pic.jpg', '0', '1'),
	(4,'kramos','Kevin Ramos', 'kramos@gmail.com', '1/1/1994', 'Male', 'Puerto Rico', 480, '/fake/path/pic.jpg', '0', '1');
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
	(1, 4, '/fake/path/chat.txt'),
	(1, 9, '/fake/path/chat.txt'),
	(4, 1, '/fake/path/chat.txt'),
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

LOCK TABLES `Chat` WRITE;
INSERT INTO `Chat`
VALUES
	(9, 1, '2018-10-06', '11:30:00', 'four plus four equals 8 but you plus me equals date?'),
	(1, 9, '2018-10-06', '11:30:30', 'No.'),
	(9, 1, '2018-10-06', '11:30:45', 'plz'),
	(4, 9, '2018-10-04', '15:00:00', 'Wanna go to the gym?'),
	(9, 4, '2018-10-04', '15:02:30', 'Sure, what time?'),
	(4, 9, '2018-10-04', '15:04:00', 'After class');
UNLOCK TABLES;