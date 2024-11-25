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
-- Table structure for table `product_sale`
--

DROP TABLE IF EXISTS `product_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_sale` (
  `sale_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `size` varchar(10) DEFAULT NULL,
  `add_id` int(11) NOT NULL DEFAULT 0,
  `price` double NOT NULL,
  `sales` int(11) NOT NULL,
  PRIMARY KEY (`sale_id`),
  UNIQUE KEY `item` (`product_id`,`size`,`add_id`),
  KEY `fk_add_idx` (`add_id`),
  CONSTRAINT `fk_add` FOREIGN KEY (`add_id`) REFERENCES `product_add` (`add_id`),
  CONSTRAINT `fk_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_sale`
--

LOCK TABLES `product_sale` WRITE;
/*!40000 ALTER TABLE `product_sale` DISABLE KEYS */;
INSERT INTO `product_sale` VALUES (1,1,'小',1,10,2710),(2,1,'中',1,15,7992),(3,1,'大',1,20,8189),(4,2,'大',1,200,28120),(5,3,'小',1,10,4381),(6,3,'中',1,15,6261),(7,3,'大',1,20,8921),(8,4,'小',1,10,7412),(9,4,'中',1,15,1716),(10,4,'大',1,20,8876),(11,5,'小',1,10,8741),(12,5,'中',1,15,6710),(13,5,'大',1,20,1030),(14,6,NULL,1,20,2715),(15,6,NULL,3,30,6117),(16,6,NULL,4,30,2806),(17,6,NULL,7,40,4229),(18,7,NULL,1,30,3258),(19,7,NULL,3,40,8965),(20,7,NULL,4,40,6382),(21,7,NULL,7,50,1146),(22,8,NULL,1,30,1244),(23,8,NULL,3,40,2016),(24,8,NULL,4,40,2403),(25,8,NULL,7,50,1009),(26,9,NULL,1,30,2089),(27,9,NULL,3,40,2363),(28,9,NULL,4,40,5422),(29,9,NULL,7,50,1245),(30,10,NULL,1,35,5328),(31,10,NULL,3,45,2874),(32,10,NULL,4,45,1720),(33,10,NULL,7,55,2729),(34,11,NULL,1,35,2845),(35,11,NULL,3,45,3328),(36,11,NULL,4,45,3822),(37,11,NULL,7,55,1806),(38,12,NULL,1,40,1829),(39,12,NULL,3,50,3682),(40,12,NULL,4,50,4785),(41,12,NULL,7,60,1208),(42,13,NULL,1,15,4839),(43,13,NULL,3,25,2940),(44,14,NULL,1,15,7812),(45,14,NULL,3,25,1929),(46,15,NULL,1,15,8919),(47,15,NULL,3,25,1082),(48,16,NULL,1,15,3879),(49,16,NULL,3,25,8792),(50,17,NULL,1,20,8172),(51,17,NULL,3,30,3472),(52,18,NULL,1,20,2921),(53,18,NULL,3,30,2981),(54,19,NULL,1,20,2904),(55,19,NULL,3,30,4829),(56,20,NULL,1,20,1239),(57,20,NULL,3,30,4821);
/*!40000 ALTER TABLE `product_sale` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-24  9:33:31
