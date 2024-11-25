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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` char(36) NOT NULL,
  `payment_type` varchar(15) NOT NULL,
  `payment_fee` double NOT NULL,
  `payment_note` varchar(200) NOT NULL DEFAULT '',
  `shipping_type` varchar(15) NOT NULL,
  `shipping_fee` double NOT NULL,
  `shipping_address` varchar(100) NOT NULL,
  `recipient_name` varchar(45) NOT NULL,
  `recipient_phone` decimal(9,0) NOT NULL,
  `recipient_email` varchar(50) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 0,
  `create_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `update_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `fk_customer_idx` (`customer_id`),
  CONSTRAINT `fk_order_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (6,'f2bc7d2f-c629-476c-be14-97927910c44d','HOME',50,'','HOME',100,'where are you','kevin',912345678,'a401190326@gmail.com',0,'2024-11-02 07:53:30','2024-11-02 07:53:30'),(7,'f2bc7d2f-c629-476c-be14-97927910c44d','STORE',0,'','STORE',65,'全家就是我家','收穫人',912345678,'a401190326@gmail.com',0,'2024-11-02 08:00:21','2024-11-02 08:00:21'),(8,'f2bc7d2f-c629-476c-be14-97927910c44d','PAY',0,'','HOME',100,'全家就是我家','收穫人',912345678,'a401190326@gmail.com',0,'2024-11-02 08:02:16','2024-11-02 08:02:16'),(9,'f2bc7d2f-c629-476c-be14-97927910c44d','PAY',0,'','STORE',65,'全家就是我家','收穫人',912345678,'a401190326@gmail.com',0,'2024-11-02 08:19:49','2024-11-02 08:19:49'),(10,'f2bc7d2f-c629-476c-be14-97927910c44d','PAY',0,'','STORE',65,'全家就是我家','收穫人',912345678,'a401190326@gmail.com',0,'2024-11-02 12:37:15','2024-11-02 12:37:15'),(11,'f2bc7d2f-c629-476c-be14-97927910c44d','PAY',0,'','STORE',65,'全家就是我家','收穫人',912345678,'a401190326@gmail.com',0,'2024-11-02 12:52:52','2024-11-02 12:52:52'),(12,'f2bc7d2f-c629-476c-be14-97927910c44d','PAY',0,'','STORE',65,'全家就是我家','收穫人',912345678,'a401190326@gmail.com',0,'2024-11-02 12:53:48','2024-11-02 12:53:48'),(16,'f2bc7d2f-c629-476c-be14-97927910c44d','PAY',0,'','STORE',65,'全家就是我家','收穫人',912345678,'a401190326@gmail.com',0,'2024-11-03 06:13:21','2024-11-03 06:13:21'),(17,'f2bc7d2f-c629-476c-be14-97927910c44d','HOME',50,'','HOME',100,'全家就是我家2','收穫人2',912345678,'a401190326@gmail.com',0,'2024-11-03 06:15:56','2024-11-03 06:15:56'),(18,'feae4e12-4bc7-47c5-b522-7fa0cefa58d5','STORE',0,'','STORE',65,'台灣離島','123',912345678,'a401190326@gmail.com',0,'2024-11-03 17:22:08','2024-11-03 17:22:08'),(19,'feae4e12-4bc7-47c5-b522-7fa0cefa58d5','HOME',50,'','HOME',100,'台灣離島2','1234',987654321,'a@com.tw',0,'2024-11-03 17:23:44','2024-11-03 17:23:44'),(20,'f2bc7d2f-c629-476c-be14-97927910c44d','HOME',50,'','HOME',100,'where are you','kevin',912345678,'a401190326@gmail.com',0,'2024-11-03 17:26:00','2024-11-03 17:26:00'),(21,'f2bc7d2f-c629-476c-be14-97927910c44d','PAY',0,'','STORE',65,'全家就是我家','asdf',912345678,'a401190326@gmail.com',0,'2024-11-04 00:46:41','2024-11-04 00:46:41'),(22,'f2bc7d2f-c629-476c-be14-97927910c44d','CARD',0,'','STORE',65,'123','123',912345678,'a@com.tw',0,'2024-11-04 01:45:27','2024-11-04 01:45:27'),(23,'f2bc7d2f-c629-476c-be14-97927910c44d','CARD',0,'','STORE',65,'rd','qwer',912345678,'a401190326@gmail.com',0,'2024-11-04 05:32:23','2024-11-04 05:32:23');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-05  8:37:21
