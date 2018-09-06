-- Class: CSE 3330
-- Semester: Spring 2018
-- Student Name: Delago, Daniel, dbd0927
-- Student ID: 1001060927
-- Assignment: project #1

-- Tables dropped in the order that they were created
DROP TABLE IF EXISTS `Reservation`;
DROP TABLE IF EXISTS `FlightLegInstance`;
DROP TABLE IF EXISTS `FlightInstance`;
DROP TABLE IF EXISTS `FlightLeg`;
DROP TABLE IF EXISTS `PlaneSeats`;
DROP TABLE IF EXISTS `Plane`;
DROP TABLE IF EXISTS `Passenger`;
DROP TABLE IF EXISTS `Pilot`;
DROP TABLE IF EXISTS `Flight`;
DROP TABLE IF EXISTS `PlaneType`;
DROP TABLE IF EXISTS `Airport`;

-- Tables that do not reference other tables: 
CREATE TABLE `Airport` (
	`Code` char(3) NOT NULL DEFAULT '',
	`City` varchar(50) DEFAULT NULL,
	`State` char(2) DEFAULT NULL,
	PRIMARY KEY (`Code`)
); 

CREATE TABLE `PlaneType` (
	`Maker` varchar(50) NOT NULL DEFAULT '',
	`Model` varchar(50) NOT NULL DEFAULT '',
	`FlyingSpeed` int DEFAULT NULL,
	`GroundSpeed` int DEFAULT NULL,
	PRIMARY KEY (`Maker`,`Model`)
);

CREATE TABLE `Flight` (
	`FLNO` int NOT NULL DEFAULT 0,
	`Meal` varchar(50) DEFAULT NULL,
	`Smoking` char(2) DEFAULT NULL,
	PRIMARY KEY (`FLNO`)
);

CREATE TABLE `Pilot` (
	`ID` int DEFAULT 0,
	`Name` varchar(50) DEFAULT NULL,
	`DateHired` date DEFAULT NULL,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `Passenger` (
	`ID` int NOT NULL DEFAULT 0,
	`Name` varchar(50) DEFAULT NULL,
	`Phone` char(13) DEFAULT NULL,
	PRIMARY KEY (`ID`)
);

-- Tables that do reference other tables: 
CREATE TABLE `Plane` (
	`ID` int NOT NULL DEFAULT 0,
	`Maker` varchar(50) NOT NULL DEFAULT '',
	`Model` varchar(50) NOT NULL DEFAULT '',
	`LastMaint` date DEFAULT NULL,
	`LastMaintA` char(3) NOT NULL DEFAULT '',
	PRIMARY KEY (`ID`),
	FOREIGN KEY (`LastMaintA`) REFERENCES Airport(`Code`),
	FOREIGN KEY (`Maker`, `Model`) REFERENCES PlaneType(`Maker`, `Model`)
);

CREATE TABLE `PlaneSeats` (
	`Maker` varchar(50) NOT NULL DEFAULT '',
	`Model` varchar(50) NOT NULL DEFAULT '',
	`SeatType` char(1) NOT NULL DEFAULT '',
	`NoOfSeats` int DEFAULT NULL,
	PRIMARY KEY (`Maker`, `Model`, `SeatType`),
	FOREIGN KEY (`Maker`, `Model`) REFERENCES PlaneType(`Maker`, `Model`)
);

CREATE TABLE `FlightLeg` (
	`FLNO` int NOT NULL DEFAULT 0,
	`Seq`int NOT NULL DEFAULT 0,
	`FromA` char(3) NOT NULL DEFAULT '',
	`ToA` char(3) NOT NULL DEFAULT '',
	`DeptTime` datetime DEFAULT NULL,
	`ArrTime` datetime DEFAULT NULL,
	`Plane` int NOT NULL DEFAULT 0,
	PRIMARY KEY (`FLNO`, `Seq`),
	FOREIGN KEY (`FLNO`) REFERENCES Flight(`FLNO`),
	FOREIGN KEY (`FromA`) REFERENCES Airport(`Code`),
	FOREIGN KEY (`ToA`) REFERENCES Airport(`Code`),
	FOREIGN KEY (`Plane`) REFERENCES Plane(`ID`)
);

CREATE TABLE `FlightInstance` (
	`FLNO` int NOT NULL DEFAULT 0,
	`FDate` date NOT NULL DEFAULT '0000-0-0',
	PRIMARY KEY (`FLNO`, `FDate`),
	FOREIGN KEY (`FLNO`) REFERENCES Flight(`FLNO`)
);

CREATE TABLE `FlightLegInstance` (
	`Seq`int NOT NULL DEFAULT 0,
	`FLNO` int NOT NULL DEFAULT 0,
	`FDate` date NOT NULL DEFAULT '0000-0-0',
	`ActDept`datetime DEFAULT NULL,
	`ActArr`datetime DEFAULT NULL,
	`Pilot` int DEFAULT NULL,
	PRIMARY KEY (`Seq`, `FLNO`,`FDate`),
	FOREIGN KEY (`FLNO`, `Seq`) REFERENCES FlightLeg(`FLNO`, `Seq`),
	FOREIGN KEY (`FLNO`, `FDate`) REFERENCES FlightInstance(`FLNO`, `FDate`),
	FOREIGN KEY (`Pilot`) REFERENCES Pilot(`ID`)
);

CREATE TABLE `Reservation` (
	`PassID`int NOT NULL DEFAULT 0,
	`FLNO` int NOT NULL DEFAULT 0,
	`FDate` date NOT NULL DEFAULT '0000-0-0',
	`FromA` char(3) NOT NULL DEFAULT '',
	`ToA` char(3) NOT NULL DEFAULT '',
	`SeatClass` char(1) DEFAULT NULL,
	`DateBooked` date DEFAULT NULL,
	`DateCancelled` date DEFAULT NULL,
	PRIMARY KEY (`PassID`, `FLNO`,`FDate`),
	FOREIGN KEY (`PassID`) REFERENCES Passenger(`ID`),
	FOREIGN KEY (`FLNO`, `FDate`) REFERENCES FlightInstance(`FLNO`, `FDate`),
	FOREIGN KEY (`FromA`) REFERENCES Airport(`Code`),
	FOREIGN KEY (`ToA`) REFERENCES Airport(`Code`)
);

-- LOCK TABLES `Airport` WRITE;
INSERT INTO `Airport`
VALUES
	('DFW','Dallas','TX'),
	('LOG','BOSTON','MA'),
	('ORD','Chicago', 'IL'),
	('MDW','Chicago', 'IL'),
	('JFK','New York','NY'),
	('LGA','New York','NY'),
	('INT','Houston','TX'),
	('LAX','Los Angeles','CA');
-- UNLOCK TABLES;

INSERT INTO `PlaneType`
VALUES
	('MD','MD11',600,180),
	('MD','SUPER80',500,170),
	('BOEING','727',510,160),
	('BOEING','757',650,160),
	('AIRBUS','A300',620,150),
	('AIRBUS','A320',700,180);

INSERT INTO `Flight`
VALUES
	(1000,'Bistro','Y'),
	(1010,'Meal','N'),
	(1020,'Meal','Y'),
	(1030,'Snack','N'),
	(1040,'Meal','N');

INSERT INTO `Pilot`
VALUES
	(1,'Jones','1990-5-10'),
	(2,'Adams','1990-6-1'),
	(3,'Walker','1991-7-2'),
	(4,'Flores','1992-4-1'),
	(5,'Thompson','1992-4-10'),
	(6,'Dean','1993-9-2'),
	(7,'Carter','1994-8-1'),
	(8,'Mango','1995-5-2');

INSERT INTO `Passenger`
VALUES
	(1,'Jones','(972)999-1111'),
	(2,'James','(214)111-9999'),
	(3,'Henry','(214)222-1111'),
	(4,'Luis','(972)111-3333'),
	(5,'Howard','(972)333-1111'),
	(6,'Frank','(214)111-5555'),
	(7,'Frankel','(972)111-2222'),
	(8,'Bushnell','(214)111-4444'),
	(9,'Camden','(214)222-5555'),
	(10,'Max','(972)444-1111'),
	(11,'Flores','(214)333-6666'),
	(12,'Clinton','(214)222-5555');

INSERT INTO `Plane`
VALUES
	(1,'MD','MD11','2002-09-03','DFW'),
	(2,'MD','MD11','2002-09-04','MDW'),
	(3,'MD','SUPER80','2002-09-01','LAX'),
	(4,'MD','SUPER80','2002-09-03','ORD'),
	(5,'MD','SUPER80','2002-09-06','LGA'),
	(6,'BOEING','727','2002-09-01','DFW'),
	(7,'BOEING','757','2002-10-02','LAX'),
	(8,'AIRBUS','A300','2002-09-01','INT'),
	(9,'AIRBUS','A320','2002-09-04','LOG');

INSERT INTO `PlaneSeats`
VALUES
	('MD','MD11','F',20),
	('MD','MD11','E',150),
	('MD','SUPER80','F',10),
	('MD','SUPER80','E',90),
	('BOEING','727','F',10),
	('BOEING','727','E',110),
	('BOEING','757','F',20),
	('BOEING','757','E',160),
	('AIRBUS','A300','F',20),
	('AIRBUS','A300','E',160),
	('AIRBUS','A320','F',30),
	('AIRBUS','A320','E',200);

INSERT INTO `FlightLeg`
VALUES
	(1000,1,'DFW','LOG','2001-1-1 10:20','2001-1-1 13:40',7),
	(1010,1,'LAX','ORD','2001-1-1 13:10','2001-1-1 16:20',3),
	(1010,2,'ORD','JFK','2001-1-1 17:10','2001-1-1 20:20',3),
	(1020,1,'LOG','JFK','2001-1-1 5:40','2001-1-1 6:20',9),
	(1020,2,'JFK','DFW','2001-1-1 7:20','2001-1-1 10:20',9),
	(1020,3,'DFW','INT','2001-1-1 11:10','2001-1-1 11:40',7),
	(1020,4,'INT','LAX','2001-1-1 12:20','2001-1-1 15:10',7),
	(1030,1,'LAX','INT','2001-1-1 11:20','2001-1-1 16:10',6),
	(1030,2,'INT','DFW','2001-1-1 17:20','2001-1-1 18:00',6),
	(1040,1,'LAX','LGA','2002-1-1 15:30','2001-1-1 21:00',1);

INSERT INTO `FlightInstance`
VALUES
	(1000,'2002-10-5'),
	(1000,'2002-10-6'),
	(1000,'2002-10-7'),
	(1010,'2002-10-5'),
	(1010,'2002-10-6'),
	(1020,'2002-10-5'),
	(1030,'2002-10-5'),
	(1040,'2002-10-7');

INSERT INTO `FlightLegInstance`
VALUES
	(1,1000,'2002-10-5','2002-1-1 10:10','2002-1-1 13:10',3),
	(1,1000,'2002-10-6','2002-1-1 10:30','2002-1-1 14:20',8),
	(1,1010,'2002-10-5','2002-1-1 13:20','2002-1-1 17:10',1),
	(2,1010,'2002-10-5','2002-1-1 18:00','2002-1-1 21:00',1),
	(1,1010,'2002-10-6','2002-1-1 13:10','2002-1-1 16:10',3),
	(2,1010,'2002-10-6','2002-1-1 17:00','2002-1-1 20:30',6),
	(1,1020,'2002-10-5','2002-1-1 5:40','2002-1-1 6:30',5),
	(2,1020,'2002-10-5','2002-1-1 7:30','2002-1-1 10:40',5),
	(3,1020,'2002-10-5','2002-1-1 11:30','2002-1-1 12:20',5),
	(4,1020,'2002-10-5','2002-1-1 13:00','2002-1-1 16:00',2),
	(1,1030,'2002-10-5','2002-1-1 11:20','2002-1-1 16:10',8),
	(2,1030,'2002-10-5','2002-1-1 17:20','2002-1-1 18:40',8),
	(1,1000,'2002-10-7',NULL,NULL,NULL),
	(1,1040,'2002-10-7',NULL,NULL,NULL);

INSERT INTO `Reservation`
VALUES
	(1,1000,'2002-10-5','DFW','LOG','E','2002-9-5',NULL),
	(1,1020,'2002-10-5','LOG','JFK','E','2002-9-14',NULL),
	(2,1020,'2002-10-5','LOG','INT','E','2002-9-4',NULL),
	(3,1020,'2002-10-5','JFK','LAX','E','2002-9-19',NULL),
	(4,1020,'2002-10-5','LOG','LAX','E','2002-9-10',NULL),
	(5,1020,'2002-10-5','LOG','DFW','F','2002-9-29',NULL),
	(6,1010,'2002-10-5','LAX','JFK','E','2002-9-19',NULL),
	(7,1010,'2002-10-5','LAX','ORD','E','2002-9-27',NULL),
	(8,1030,'2002-10-5','LAX','DFW','F','2002-10-5',NULL),
	(3,1010,'2002-10-6','LAX','JFK','E','2002-9-14',NULL),
	(9,1010,'2002-10-6','LAX','JFK','E','2002-9-9',NULL),
	(10,1010,'2002-10-6','ORD','JFK','E','2002-9-7','2002-9-19'),
	(11,1000,'2002-10-6','DFW','LOG','E','2002-9-9',NULL),
	(12,1000,'2002-10-6','DFW','LOG','E','2002-9-19',NULL),
	(1,1010,'2002-10-6','ORD','JFK','E','2002-9-15',NULL),
	(1,1040,'2002-10-7','LAX','LGA','E','2002-10-1',NULL);


-- QUERY EXAMPLES
-- 1. Retrieve the ID, the model, and the last maintenance date for each plan that was made by 'AIRBUS'
SELECT ID, Model, LastMaint
FROM Plane
WHERE Maker = 'Airbus';

-- Expected output:
-- +----+-------+------------+
-- | ID | Model | LastMaint  |
-- +----+-------+------------+
-- |  5 | 380   | 2015-09-27 |
-- |  6 | 380   | 2015-10-01 |
-- |  7 | 340   | 2015-10-09 |
-- |  8 | 340   | 2015-10-10 |
-- +----+-------+------------+


-- 2. Retrieve the names of passengers who had reservations on flights with meals.
 SELECT DISTINCT Name 
 FROM Passenger, Reservation, Flight
 WHERE ID=PassID AND Reservation.FLNO=Flight.FLNO AND Meal=1;

-- Expected output:
-- +---------+
-- | Name    |
-- +---------+
-- | James   | 
-- | Robert  | 
-- | Paul    | 
-- | William | 
-- +---------+


-- 3. Retrieve the names of pilots who flew FROM any airport in 'TX'.
SELECT DISTINCT Name 
FROM Pilot, FlightLegInstance, FlightLeg, Airport 
WHERE ID=Pilot AND FlightLegInstance.FLNO=FlightLeg.FLNO AND FromA=Code and State='TX';

-- Expected output:
-- +---------+
-- | Name    |
-- +---------+
-- | Madison |
-- | Ryan    |
-- | Walker  |
-- | Samuel  |
-- +---------+


-- 4. Retrieve the total number of flights for the passenger 'Jones'.
SELECT COUNT(*) 
FROM Passenger, Reservation, Flight 
WHERE ID=PassID AND Reservation.FLNO=Flight.FLNO AND Name='Jones';

-- Expected output:
-- +----------+
-- | COUNT(*) |
-- +----------+
-- |        0 |
-- +----------+


-- 5. Retrieve every plane maker, model plane type that lands in CA.
SELECT Maker,Model
FROM Plane, FlightLeg, Airport
WHERE ID=FlightLeg.Plane AND ToA=Code AND State='CA';

-- Expected output:
-- +--------+-------+
-- | Maker  | Model |
-- +--------+-------+
-- | Airbus | 380   |
-- +--------+-------+


-- 6. Retrieve the Maker and the total number of planes made by this Maker, for Makers who
-- make any plane with total number of seats more than 350.
SELECT P.Maker, Count(*) 
FROM Plane AS P, PlaneType AS PT, PlaneSeats AS PS 
WHERE P.Maker=PT.Maker AND PT.Maker=PS.Maker AND NoOfSeats > 350;

-- Expected output:
-- +-------+----------+
-- | Maker | Count(*) |
-- +-------+----------+
-- | NULL  |        0 |
-- +-------+----------+


-- 7. Retrieve all flight numbers that depart from or arrive at airport DFW
SELECT DISTINCT FLNO 
FROM FlightLeg, Airport 
WHERE FromA='DFW' OR ToA='DFW';

-- Expected output:
-- +------+
-- | FLNO |
-- +------+
-- | 100  |
-- | 300  |
-- | 600  |
-- | 700  |
-- +------+


-- 8. Retrieve the Maker, Model, and total number of seats of planes that has more than 300 seats in total.
SELECT DISTINCT P.Maker, P.Model, PS.NoOfSeats 
FROM Plane AS P, PlaneSeats AS PS 
WHERE PS.NoOfSeats > 300 AND P.Model=PS.Model;
