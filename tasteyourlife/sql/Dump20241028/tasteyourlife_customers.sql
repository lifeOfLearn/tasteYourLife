-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 192.168.58.58    Database: tasteyourlife
-- ------------------------------------------------------
-- Server version	5.5.5-10.11.6-MariaDB-0+deb12u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` char(36) NOT NULL,
  `client_id` char(36) NOT NULL,
  `token_id` char(36) NOT NULL,
  `name` varchar(45) NOT NULL,
  `phone` decimal(9,0) NOT NULL,
  `email` varchar(50) NOT NULL,
  `birthday` date NOT NULL,
  `rocid` varchar(10) NOT NULL,
  `password` varchar(100) NOT NULL,
  `gender` enum('M','F','O','N') DEFAULT 'N',
  `bloodtype` enum('O','A','B','AB','N') DEFAULT 'N',
  `address` varchar(100) DEFAULT '',
  `status` enum('Y','N') DEFAULT 'N',
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `client_id_UNIQUE` (`client_id`),
  UNIQUE KEY `token_id_UNIQUE` (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='會員';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES ('f2bc7d2f-c629-476c-be14-97927910c44d','b91a9516-4df3-424d-8676-c5f636604b0b','9a33612f-8abd-11ef-a5d7-e45f01c03bca','kevin',930445550,'a987654251@gmail.com','1994-01-01','A123123123','$2a$10$9RGP29XrWQrgE4G5SvMhgecwg.oqs8S6g9RbZJj1JzDtF96TNnMj6','M','O','地址','Y','2024-10-14 22:20:41','2024-10-15 18:36:52');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-28 16:26:08
