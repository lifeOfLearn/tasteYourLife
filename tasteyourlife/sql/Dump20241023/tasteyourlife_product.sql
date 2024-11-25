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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `product_name` varchar(45) NOT NULL DEFAULT '',
  `product_info` varchar(100) DEFAULT '',
  `path` varchar(100) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `update_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,1,'就是杯紅茶','無趣的茶茶','1.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(2,1,'台灣價值紅茶','自己的價值自己掌握','2.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(3,1,'瀉藥奶茶','昨日和同事不愉快嗎?點這杯就對了','3.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(4,1,'謎漿','一切都是謎','4.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(5,1,'痘漿','每一口都喝得到果粒','5.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(6,2,'經典蛋餅','不要問點就對了','6.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(7,2,'氣死蛋餅','好的早晨由暴走開始','7.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(8,2,'勒狗蛋餅','點擊救贖!?','8.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(9,2,'餵魚蛋餅','養魚第一首選','9.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(10,2,'香肌蛋餅','不要亂想','10.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(11,2,'數餅蛋餅','孩童必點','11.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(12,2,'循跡蛋餅','好吃又好玩，樂趣無窮','12.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(13,3,'草莓族薄片吐司','熟悉的味道最對味','13.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(14,3,'翹課哩薄片吐司','品嘗過不後悔','14.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(15,3,'想輸薄片吐司','酥是件很簡單的事情!','15.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(16,3,'哪有薄片吐司','這有','16.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(17,3,'草霉厚騙吐司','!?好像哪裡不對勁','17.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(18,3,'敲蚵蠣厚變吐司','所以是蚵蠣還是吐司','18.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(19,3,'相輸後變屠屍','內涵火藥味','19.png','2024-10-18 18:32:03','2024-10-18 18:32:03'),(20,3,'奶油後變吐絲','有點特別的吐司?','20.png','2024-10-18 18:32:03','2024-10-18 18:32:03');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-23  9:18:33
