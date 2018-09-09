-- Daniel Delago

-- Tables dropped in the reverse order that they were created to prevent reference errors
DROP TABLE IF EXISTS `Matches`;
DROP TABLE IF EXISTS `SuggestedPartners`;
DROP TABLE IF EXISTS `LikedUsers`;
DROP TABLE IF EXISTS `Hobbies`;
DROP TABLE IF EXISTS `Media`;
DROP TABLE IF EXISTS `Languages`;
DROP TABLE IF EXISTS `Login`;
DROP TABLE IF EXISTS `User`;

-- Tables that do not reference other tables: 
CREATE TABLE `User` (
	`ID` 		int NOT NULL,
	`Username` 	varchar(50) NOT NULL,
	`Name` 		varchar(50) NOT NULL,
	`Email` 	varchar(50) NOT NULL,
	`DOB` 		char(10) NOT NULL,
	`Gender` 	varchar(10) NOT NULL,
	`Location` 	varchar(50) NOT NULL,
	`InterestedInMen` 	char(1) NOT NULL DEFAULT '0',
	`InterestedInWomen` char(1) NOT NULL DEFAULT '0',
	PRIMARY KEY (`ID`)
); 

-- Tables that do reference other tables: 
CREATE TABLE `Login` (
	`Username` 	varchar(50) NOT NULL,
	`Password` 	varchar(50) NOT NULL,
	`UserID` 	int NOT NULL,
	PRIMARY KEY (`Username`, `Password`),
	FOREIGN KEY (`UserID`) REFERENCES User(`ID`)
);

CREATE TABLE `Languages` (
	`UserID` 	int NOT NULL,
	`English` 	char(1) NOT NULL DEFAULT '0',
	`Malay` 	char(1) NOT NULL DEFAULT '0',
	`Mandarin` 	char(1) NOT NULL DEFAULT '0',
	`Tamil` 	char(1) NOT NULL DEFAULT '0',
	PRIMARY KEY (`UserID`),
	FOREIGN KEY (`UserID`) REFERENCES User(`ID`)
);

CREATE TABLE `Media` (
	`UserID` 			int NOT NULL,
	`ProfilePicBytes` 	int NOT NULL,
	`ProfilePicPath` 	varchar(255) NOT NULL,
	PRIMARY KEY (`UserID`),
	FOREIGN KEY (`UserID`) REFERENCES User(`ID`)
);

-- Alter when new hobbies added (Add tanking to hobbies)
CREATE TABLE `Hobbies` (
	`UserID` 	int NOT NULL,
	`Fitness` 	char(1) NOT NULL DEFAULT '0',
	`Music` 	char(1) NOT NULL DEFAULT '0',
	`Dancing` 	char(1) NOT NULL DEFAULT '0',
	`Reading` 	char(1) NOT NULL DEFAULT '0',
	`Walking` 	char(1) NOT NULL DEFAULT '0',
	`Traveling` char(1) NOT NULL DEFAULT '0',
	`Eating` 	char(1) NOT NULL DEFAULT '0',
	`Crafts` 	char(1) NOT NULL DEFAULT '0',
	`Fishing` 	char(1) NOT NULL DEFAULT '0',
	`Hiking` 	char(1) NOT NULL DEFAULT '0',
	`Animals` 	char(1) NOT NULL DEFAULT '0',
	PRIMARY KEY (`UserID`),
	FOREIGN KEY (`UserID`) REFERENCES User(`ID`)
);

-- New entry for each new match/Suggestion/Like
CREATE TABLE `Matches` (
	`UserID` 	int NOT NULL,
	`MatchID` 	int NOT NULL,
	`ChatPath`	varchar(255) NOT NULL,
	PRIMARY KEY (`UserID`, `MatchID`),
	FOREIGN KEY (`UserID`) REFERENCES User(`ID`),
	FOREIGN KEY (`MatchID`) REFERENCES User(`ID`)
);

CREATE TABLE `SuggestedPartners` (
	`UserID` 	int NOT NULL,
	`PartnerID` int NOT NULL,
	PRIMARY KEY (`UserID`, `PartnerID`),
	FOREIGN KEY (`UserID`) REFERENCES User(`ID`),
	FOREIGN KEY (`PartnerID`) REFERENCES User(`ID`)
);

CREATE TABLE `LikedUsers` (
	`UserID` 	int NOT NULL,
	`LikesID` 	int NOT NULL,
	PRIMARY KEY (`UserID`, `LikesID`),
	FOREIGN KEY (`UserID`) REFERENCES User(`ID`),
	FOREIGN KEY (`LikesID`) REFERENCES User(`ID`)
);
