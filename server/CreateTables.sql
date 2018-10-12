-- Daniel Delago

-- Tables dropped in the reverse order that they were created to prevent reference errors
DROP TABLE IF EXISTS `SuggestedPartners`;
DROP TABLE IF EXISTS `LikedUsers`;
--DROP TABLE IF EXISTS `Chat`;
DROP TABLE IF EXISTS `Hobbies`;
-- DROP TABLE IF EXISTS `Languages`;
-- DROP TABLE IF EXISTS `Login`;
DROP TABLE IF EXISTS `Users`;

-- Tables that do not reference other tables: 
CREATE TABLE `Users` (
	`ID` 		int NOT NULL,
	`Username` 	varchar(50) NOT NULL,
	`Password` 	varchar(50) NOT NULL,
	`Name` 		varchar(50) NOT NULL,
	`Email` 	varchar(50) NOT NULL,
	`DOB` 		char(10) NOT NULL,
	`Gender` 	varchar(10) NOT NULL,
	`Location` 	varchar(50) NOT NULL,
	`Languages` 		varchar(50) NOT NULL,
	`ProfilePicBytes` 	blob NOT NULL,
	`InterestedInMen` 	varchar(10) NOT NULL DEFAULT "0",
	`InterestedInWomen` varchar(10) NOT NULL DEFAULT "0",
	PRIMARY KEY (`ID`)
); 

-- Tables that do reference other tables: 
-- CREATE TABLE `Login` (
-- 	`Username` 	varchar(50) NOT NULL,
-- 	`Password` 	varchar(50) NOT NULL,
-- 	`UserID` 	int NOT NULL,
-- 	PRIMARY KEY (`Username`, `Password`),
-- 	FOREIGN KEY (`UserID`) REFERENCES Users(`ID`)
-- );

-- CREATE TABLE `Languages` (
-- 	`UserID` 	int NOT NULL,
-- 	`English` 	char(1) NOT NULL DEFAULT '0',
-- 	`Malay` 	char(1) NOT NULL DEFAULT '0',
-- 	`Mandarin` 	char(1) NOT NULL DEFAULT '0',
-- 	`Tamil` 	char(1) NOT NULL DEFAULT '0',
-- 	PRIMARY KEY (`UserID`),
-- 	FOREIGN KEY (`UserID`) REFERENCES Users(`ID`)
-- );

-- Alter when new hobbies added (Add tanking to hobbies)
CREATE TABLE `Hobbies` (
	`UserID` 	int NOT NULL,
	`Fitness` 	varchar(5) NOT NULL DEFAULT 'false',
	`Music` 	varchar(5) NOT NULL DEFAULT 'false',
	`Dancing` 	varchar(5) NOT NULL DEFAULT 'false',
	`Reading` 	varchar(5) NOT NULL DEFAULT 'false',
	`Walking` 	varchar(5) NOT NULL DEFAULT 'false',
	`Traveling` varchar(5) NOT NULL DEFAULT 'false',
	`Eating` 	varchar(5) NOT NULL DEFAULT 'false',
	`Crafts` 	varchar(5) NOT NULL DEFAULT 'false',
	`Fishing` 	varchar(5) NOT NULL DEFAULT 'false',
	`Hiking` 	varchar(5) NOT NULL DEFAULT 'false',
	`Animals` 	varchar(5) NOT NULL DEFAULT 'false',
	PRIMARY KEY (`UserID`),
	FOREIGN KEY (`UserID`) REFERENCES Users(`ID`)
);

CREATE TABLE `SuggestedPartners` (
	`UserID` 	int NOT NULL,
	`PartnerID` int NOT NULL,
	PRIMARY KEY (`UserID`, `PartnerID`),
	FOREIGN KEY (`UserID`) REFERENCES Users(`ID`),
	FOREIGN KEY (`PartnerID`) REFERENCES Users(`ID`)
);

CREATE TABLE `LikedUsers` (
	`UserID` 	int NOT NULL,
	`LikesID` 	int NOT NULL,
	PRIMARY KEY (`UserID`, `LikesID`),
	FOREIGN KEY (`UserID`) REFERENCES Users(`ID`),
	FOREIGN KEY (`LikesID`) REFERENCES Users(`ID`)
);

--CREATE TABLE `Chat` (
--	`SenderID`		int NOT NULL,
--	`ReceiverID` 	int NOT NULL,
--	`Datestamp`		date NOT NULL DEFAULT '0000-00-00',
--	`Timestamp`		time DEFAULT NULL,
--	`Message`		varchar(255) NOT NULL,
--	PRIMARY KEY (`SenderID`, `ReceiverID`, `Datestamp`, `Timestamp`, `Message`),
--	FOREIGN KEY (`SenderID`) REFERENCES Users(`ID`),
--	FOREIGN KEY (`ReceiverID`) REFERENCES Users(`ID`)
--);
