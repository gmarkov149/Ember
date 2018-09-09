-- Class: CSE 3330
-- Semester: Spring 2018
-- Student Name: Delago, Daniel, dbd0927
-- Student ID: 1001060927
-- Assignment: project #4

-- MySQL dump 10.13  Distrib 5.6.14, for osx10.7 (x86_64)
--
-- Host: localhost    Database: project1
-- ------------------------------------------------------
-- Server version	5.6.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Airport`
--

DROP TABLE IF EXISTS `Airport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Airport` (
`Code` char(3) NOT NULL DEFAULT '',
`City` varchar(50) DEFAULT NULL,
`State` char(2) DEFAULT NULL,
PRIMARY KEY (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Airport`
--

LOCK TABLES `Airport` WRITE;
/*!40000 ALTER TABLE `Airport` DISABLE KEYS */;
INSERT INTO `Airport` VALUES ('BWI','Baltimore','MD'),('DFW','Dallas','TX'),('FLL','Ft. Lauderdale','FL'),('IAH','Houston','TX'),('JFK','New York','NY'),('LAX','Los Angeles','CA'),('LGA','New York','NY'),('ORD','Chicago','IL'),('SFO','San Francisco','CA');
/*!40000 ALTER TABLE `Airport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Flight`
--

DROP TABLE IF EXISTS `Flight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Flight` (
`FLNO` varchar(10) NOT NULL DEFAULT '',
`Meal` tinyint(1) DEFAULT NULL,
`Smoking` tinyint(1) DEFAULT NULL,
PRIMARY KEY (`FLNO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Flight`
--

LOCK TABLES `Flight` WRITE;
/*!40000 ALTER TABLE `Flight` DISABLE KEYS */;
INSERT INTO `Flight` VALUES ('100',1,0),('200',1,0),('300',0,0),('400',0,0),('500',1,0),('600',1,0),('700',1,0);
/*!40000 ALTER TABLE `Flight` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FlightInstance`
--

DROP TABLE IF EXISTS `FlightInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FlightInstance` (
`FLNO` varchar(10) NOT NULL DEFAULT '',
`FDate` date NOT NULL DEFAULT '0000-00-00',
PRIMARY KEY (`FLNO`,`FDate`),
CONSTRAINT `flightinstance_ibfk_1` FOREIGN KEY (`FLNO`) REFERENCES `Flight` (`FLNO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FlightInstance`
--

LOCK TABLES `FlightInstance` WRITE;
/*!40000 ALTER TABLE `FlightInstance` DISABLE KEYS */;
INSERT INTO `FlightInstance` VALUES ('100','2015-10-06'),('100','2015-10-08'),('200','2015-10-06'),('200','2015-10-08'),('300','2015-10-06'),('300','2015-10-08'),('400','2015-10-06'),('400','2015-10-08'),('500','2015-10-06'),('500','2015-10-08'),('600','2015-10-06'),('600','2015-10-08'),('700','2015-10-06'),('700','2015-10-08');
/*!40000 ALTER TABLE `FlightInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FlightLeg`
--

DROP TABLE IF EXISTS `FlightLeg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FlightLeg` (
`FLNO` varchar(10) NOT NULL DEFAULT '',
`Seq` int(11) NOT NULL DEFAULT '0',
`FromA` char(3) DEFAULT NULL,
`ToA` char(3) DEFAULT NULL,
`DeptTime` time DEFAULT NULL,
`ArrTime` time DEFAULT NULL,
`Plane` int(11) DEFAULT NULL,
PRIMARY KEY (`FLNO`,`Seq`),
KEY `FromA` (`FromA`),
KEY `ToA` (`ToA`),
KEY `Plane` (`Plane`),
CONSTRAINT `flightleg_ibfk_1` FOREIGN KEY (`FLNO`) REFERENCES `Flight` (`FLNO`),
CONSTRAINT `flightleg_ibfk_2` FOREIGN KEY (`FromA`) REFERENCES `Airport` (`Code`),
CONSTRAINT `flightleg_ibfk_3` FOREIGN KEY (`ToA`) REFERENCES `Airport` (`Code`),
CONSTRAINT `flightleg_ibfk_4` FOREIGN KEY (`Plane`) REFERENCES `Plane` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FlightLeg`
--

LOCK TABLES `FlightLeg` WRITE;
/*!40000 ALTER TABLE `FlightLeg` DISABLE KEYS */;
INSERT INTO `FlightLeg` VALUES ('100',1,'ORD','DFW','11:30:00','13:30:00',8),('200',1,'SFO','ORD','07:30:00','10:30:00',2),('200',2,'ORD','LGA','11:30:00','14:00:00',2),('300',1,'DFW','JFK','14:30:00','17:30:00',3),('400',1,'LAX','IAH','08:30:00','11:30:00',1),('400',2,'IAH','FLL','13:00:00','15:00:00',1),('500',1,'FLL','ORD','11:00:00','14:00:00',5),('500',2,'ORD','LAX','15:00:00','18:30:00',6),('600',1,'DFW','BWI','16:00:00','19:00:00',8),('700',1,'LGA','DFW','10:00:00','13:00:00',7);
/*!40000 ALTER TABLE `FlightLeg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FlightLegInstance`
--

DROP TABLE IF EXISTS `FlightLegInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FlightLegInstance` (
`FLNO` varchar(10) NOT NULL DEFAULT '',
`Seq` int(11) NOT NULL DEFAULT '0',
`FDate` date NOT NULL DEFAULT '0000-00-00',
`ActDept` time DEFAULT NULL,
`ActArr` time DEFAULT NULL,
`Pilot` int(11) DEFAULT NULL,
PRIMARY KEY (`FLNO`,`Seq`,`FDate`),
KEY `FLNO` (`FLNO`,`FDate`),
KEY `Pilot` (`Pilot`),
CONSTRAINT `flightleginstance_ibfk_1` FOREIGN KEY (`FLNO`, `Seq`) REFERENCES `FlightLeg` (`FLNO`, `Seq`),
CONSTRAINT `flightleginstance_ibfk_2` FOREIGN KEY (`FLNO`, `FDate`) REFERENCES `FlightInstance` (`FLNO`, `FDate`),
CONSTRAINT `flightleginstance_ibfk_3` FOREIGN KEY (`Pilot`) REFERENCES `Pilot` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FlightLegInstance`
--

LOCK TABLES `FlightLegInstance` WRITE;
/*!40000 ALTER TABLE `FlightLegInstance` DISABLE KEYS */;
INSERT INTO `FlightLegInstance` VALUES ('100',1,'2015-10-06','11:30:00','13:30:00',1),('100',1,'2015-10-08','11:30:00','13:30:00',4),('200',1,'2015-10-06','07:30:00','10:30:00',2),('200',1,'2015-10-08','07:30:00','10:30:00',6),('200',2,'2015-10-06','11:30:00','14:00:00',2),('200',2,'2015-10-08','11:30:00','14:00:00',6),('300',1,'2015-10-06','14:30:00','17:30:00',7),('300',1,'2015-10-08','14:30:00','17:30:00',8),('400',1,'2015-10-06','08:30:00','11:30:00',5),('400',1,'2015-10-08','08:30:00','11:30:00',3),('400',2,'2015-10-06','13:00:00','15:00:00',5),('400',2,'2015-10-08','13:00:00','15:00:00',3),('500',1,'2015-10-06','11:00:00','14:00:00',6),('500',1,'2015-10-08','11:00:00','14:00:00',1),('500',2,'2015-10-06','15:00:00','18:30:00',4),('500',2,'2015-10-08','15:00:00','18:30:00',1),('600',1,'2015-10-06','16:00:00','19:00:00',3),('600',1,'2015-10-08','16:00:00','19:00:00',5),('700',1,'2015-10-06','10:00:00','13:00:00',8),('700',1,'2015-10-08','10:00:00','13:00:00',2);
/*!40000 ALTER TABLE `FlightLegInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Passenger`
--

DROP TABLE IF EXISTS `Passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Passenger` (
`ID` int(11) NOT NULL DEFAULT '0',
`name` varchar(50) DEFAULT NULL,
`phone` varchar(50) DEFAULT NULL,
PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Passenger`
--

LOCK TABLES `Passenger` WRITE;
/*!40000 ALTER TABLE `Passenger` DISABLE KEYS */;
INSERT INTO `Passenger` VALUES (1,'James','2141112222'),(2,'Peter','8172224444'),(3,'Robert','9723333434'),(4,'Michael','8172229898'),(5,'Paul','2145557878'),(6,'William','2143332222'),(7,'Harry','3121112222');
/*!40000 ALTER TABLE `Passenger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pilot`
--

DROP TABLE IF EXISTS `Pilot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pilot` (
`ID` int(11) NOT NULL DEFAULT '0',
`Name` varchar(50) DEFAULT NULL,
`DateHired` date DEFAULT NULL,
PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pilot`
--

LOCK TABLES `Pilot` WRITE;
/*!40000 ALTER TABLE `Pilot` DISABLE KEYS */;
INSERT INTO `Pilot` VALUES (1,'Jones','2015-01-25'),(2,'Smith','2015-03-21'),(3,'Walker','2015-07-23'),(4,'Daniel','2015-04-29'),(5,'Samuel','2015-07-20'),(6,'Allan','2015-02-15'),(7,'Madison','2015-01-14'),(8,'Ryan','2015-05-01');
/*!40000 ALTER TABLE `Pilot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Plane`
--

DROP TABLE IF EXISTS `Plane`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Plane` (
`ID` int(11) NOT NULL DEFAULT '0',
`Maker` varchar(20) NOT NULL,
`Model` varchar(10) NOT NULL,
`LastMaint` date DEFAULT NULL,
`LastMaintA` char(3) DEFAULT NULL,
PRIMARY KEY (`ID`),
KEY `Maker` (`Maker`,`Model`),
KEY `LastMaintA` (`LastMaintA`),
CONSTRAINT `plane_ibfk_1` FOREIGN KEY (`Maker`, `Model`) REFERENCES `PlaneType` (`Maker`, `Model`),
CONSTRAINT `plane_ibfk_2` FOREIGN KEY (`LastMaintA`) REFERENCES `Airport` (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Plane`
--

LOCK TABLES `Plane` WRITE;
/*!40000 ALTER TABLE `Plane` DISABLE KEYS */;
INSERT INTO `Plane` VALUES (1,'Boeing','747','2015-08-12','LAX'),(2,'Boeing','777','2015-08-14','DFW'),(3,'Boeing','777','2015-09-14','DFW'),(4,'Boeing','787','2015-09-22','ORD'),(5,'Airbus','380','2015-09-27','JFK'),(6,'Airbus','380','2015-10-01','LGA'),(7,'Airbus','340','2015-10-09','FLL'),(8,'Airbus','340','2015-10-10','LAX');
/*!40000 ALTER TABLE `Plane` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PlaneSeats`
--

DROP TABLE IF EXISTS `PlaneSeats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PlaneSeats` (
`Maker` varchar(20) NOT NULL DEFAULT '',
`Model` varchar(10) NOT NULL DEFAULT '',
`SeatType` char(1) NOT NULL DEFAULT '',
`NoOfSeats` int(11) DEFAULT NULL,
PRIMARY KEY (`Maker`,`Model`,`SeatType`),
CONSTRAINT `planeseats_ibfk_1` FOREIGN KEY (`Maker`, `Model`) REFERENCES `PlaneType` (`Maker`, `Model`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PlaneSeats`
--

LOCK TABLES `PlaneSeats` WRITE;
/*!40000 ALTER TABLE `PlaneSeats` DISABLE KEYS */;
INSERT INTO `PlaneSeats` VALUES ('Airbus','340','E',220),('Airbus','340','F',20),('Airbus','380','E',300),('Airbus','380','F',60),('Boeing','747','E',300),('Boeing','747','F',40),('Boeing','777','E',200),('Boeing','777','F',20),('Boeing','787','E',250),('Boeing','787','F',25);
/*!40000 ALTER TABLE `PlaneSeats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PlaneType`
--

DROP TABLE IF EXISTS `PlaneType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PlaneType` (
`Maker` varchar(20) NOT NULL DEFAULT '',
`Model` varchar(10) NOT NULL DEFAULT '',
`FlyingSpeed` int(11) DEFAULT NULL,
`GroundSpeed` int(11) DEFAULT NULL,
PRIMARY KEY (`Maker`,`Model`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PlaneType`
--

LOCK TABLES `PlaneType` WRITE;
/*!40000 ALTER TABLE `PlaneType` DISABLE KEYS */;
INSERT INTO `PlaneType` VALUES ('Airbus','340',600,150),('Airbus','380',600,150),('Boeing','747',600,150),('Boeing','777',600,150),('Boeing','787',600,150);
/*!40000 ALTER TABLE `PlaneType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reservation`
--

DROP TABLE IF EXISTS `Reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reservation` (
`PassID` int(11) NOT NULL DEFAULT '0',
`FLNO` varchar(10) NOT NULL DEFAULT '',
`FDate` date NOT NULL DEFAULT '0000-00-00',
`FromA` char(3) DEFAULT NULL,
`ToA` char(3) DEFAULT NULL,
`SeatClass` char(1) DEFAULT NULL,
`DateBooked` date DEFAULT NULL,
`DateCancelled` date DEFAULT NULL,
PRIMARY KEY (`PassID`,`FLNO`,`FDate`),
KEY `FLNO` (`FLNO`,`FDate`),
KEY `FromA` (`FromA`),
KEY `ToA` (`ToA`),
CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`FLNO`, `FDate`) REFERENCES `FlightInstance` (`FLNO`, `FDate`),
CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`PassID`) REFERENCES `Passenger` (`ID`),
CONSTRAINT `reservation_ibfk_3` FOREIGN KEY (`FromA`) REFERENCES `Airport` (`Code`),
CONSTRAINT `reservation_ibfk_4` FOREIGN KEY (`ToA`) REFERENCES `Airport` (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reservation`
--

LOCK TABLES `Reservation` WRITE;
/*!40000 ALTER TABLE `Reservation` DISABLE KEYS */;
INSERT INTO `Reservation` VALUES (1,'100','2015-10-06','ORD','DFW','E','2015-09-12',NULL),(1,'600','2015-10-06','DFW','BWI','F','2015-09-12',NULL),(1,'700','2015-10-08','LGA','DFW','E','2015-09-12',NULL),(2,'300','2015-10-06','DFW','JFK','F','2015-10-06',NULL),(3,'200','2015-10-08','ORD','LGA','F','2015-10-06',NULL),(3,'300','2015-10-06','DFW','JFK','F','2015-10-02',NULL),(4,'300','2015-10-06','DFW','JFK','F','2015-10-06',NULL),(4,'400','2015-10-08','IAH','FLL','F','2015-10-06',NULL),(5,'500','2015-10-06','ORD','LAX','E','2015-09-12',NULL),(6,'600','2015-10-08','DFW','BWI','F','2015-09-10',NULL),(7,'300','2015-10-08','DFW','JFK','F','2015-10-08',NULL);
/*!40000 ALTER TABLE `Reservation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-13 10:17:54