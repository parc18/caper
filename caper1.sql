-- MySQL dump 10.13  Distrib 5.7.21, for macos10.13 (x86_64)
--
-- Host: localhost    Database: caper
-- ------------------------------------------------------
-- Server version	5.7.21

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
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking` (
  `booking_id` int(11) NOT NULL,
  `booking_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `no_of_tickets` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`booking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_prices`
--

DROP TABLE IF EXISTS `booking_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking_prices` (
  `booking_id` int(11) NOT NULL,
  `price_id` int(11) NOT NULL,
  KEY `booking_id` (`booking_id`),
  KEY `price_id` (`price_id`),
  CONSTRAINT `booking_prices_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`),
  CONSTRAINT `booking_prices_ibfk_2` FOREIGN KEY (`price_id`) REFERENCES `price` (`price_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_prices`
--

LOCK TABLES `booking_prices` WRITE;
/*!40000 ALTER TABLE `booking_prices` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking_prices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_type` int(11) DEFAULT NULL,
  `eventdate` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `venue` varchar(20) DEFAULT NULL,
  `event_name` varchar(20) DEFAULT NULL,
  `timings` varchar(20) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `event_city` varchar(15) DEFAULT NULL,
  `max_participants` int(11) DEFAULT NULL,
  `event_city_id` tinyint(2) DEFAULT NULL,
  `img_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (18,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',NULL,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(19,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',NULL,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(20,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',NULL,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(21,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(22,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(23,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(24,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(25,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(26,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(27,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(28,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(29,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(30,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(31,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(32,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(33,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(34,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(35,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(36,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(37,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(38,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(39,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(40,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(41,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(42,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(43,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(44,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(45,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(46,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(47,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(48,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(49,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(50,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(51,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(52,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Mg road','Caper\'s badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',99,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg'),(53,1,'2018-12-22',1,'Amazibng gmaes and fun at place','Koramangala','Victor badminton','11:30 AM - 10:30 PM','2018-05-01 08:41:13','Bangalore',999,1,'https://blog.bufferapp.com/wp-content/uploads/2014/05/copenhagen-39.jpg');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizer`
--

DROP TABLE IF EXISTS `organizer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organizer` (
  `organizer_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  PRIMARY KEY (`organizer_id`),
  KEY `FK_orgsEvent` (`event_id`),
  CONSTRAINT `FK_orgsEvent` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizer`
--

LOCK TABLES `organizer` WRITE;
/*!40000 ALTER TABLE `organizer` DISABLE KEYS */;
INSERT INTO `organizer` VALUES (1,1);
/*!40000 ALTER TABLE `organizer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price`
--

DROP TABLE IF EXISTS `price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price` (
  `price_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `price_currency` varchar(5) DEFAULT NULL,
  `price_amount` int(11) DEFAULT NULL,
  `description` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`price_id`),
  KEY `FK_eventPrice` (`event_id`),
  CONSTRAINT `FK_eventPrice` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price`
--

LOCK TABLES `price` WRITE;
/*!40000 ALTER TABLE `price` DISABLE KEYS */;
INSERT INTO `price` VALUES (1,1,'INR',999,'U-19'),(2,1,'INR',999,'U-11'),(3,1,'INR',999,'U-15'),(4,2,'INR',1499,'U-15'),(5,2,'INR',1499,'U-11'),(6,2,'INR',1499,'U-19');
/*!40000 ALTER TABLE `price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponser`
--

DROP TABLE IF EXISTS `sponser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponser` (
  `sponser_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  PRIMARY KEY (`sponser_id`),
  KEY `FK_sponEvent` (`event_id`),
  CONSTRAINT `FK_sponEvent` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponser`
--

LOCK TABLES `sponser` WRITE;
/*!40000 ALTER TABLE `sponser` DISABLE KEYS */;
INSERT INTO `sponser` VALUES (1,1);
/*!40000 ALTER TABLE `sponser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `event_id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  KEY `event_id` (`event_id`),
  KEY `booking_id` (`booking_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`),
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`),
  CONSTRAINT `ticket_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(20) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `passcode` varchar(16) NOT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `city` varchar(12) DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL,
  `welcomedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'manish','premi','manishpremi20@gmail.com','abcc','21212121','aa',1,'2018-03-12 11:48:48'),(2,'PREMI','Manish','sample@ss.com','IXY6J','222222222','bangalore',1,'2018-03-17 06:54:13'),(3,'PREMI','Manish','sample@ss.com','EPXD5','222222222','bangalore',1,'2018-03-17 06:54:56'),(4,'deepa','kk','sample@ss.com','HNMME','8888888888','bangalore',1,'2018-03-17 06:59:55'),(5,'deepa','kk','sample@ss.comss','ERMGS','8888888888','bangalore',1,'2018-03-17 07:15:06'),(6,'deepa','kk','sample@ss.comss','Wsquare','8888888888','bangalore',1,'2018-03-17 08:16:42'),(14,'rtika','kn','samsle@ss.comss','47WI7','7411286811','bangalore',1,'2018-04-14 17:56:45'),(15,'rtika','kn','qbahjaas@ss.comss','174SB','7411598241','bangalore',1,'2018-04-14 18:21:42'),(25,'rtika','kn','qmasqq@ss.comss','8VQFS','7353067866','bangalore',1,'2018-04-14 20:40:37'),(26,'rtika','kn','qmqq@ss.comss','2AKE5','7411286816','bangalore',12,'2018-04-14 20:46:46');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-01 17:18:11
