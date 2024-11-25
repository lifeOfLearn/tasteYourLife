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
-- Temporary view structure for view `product_data`
--

DROP TABLE IF EXISTS `product_data`;
/*!50001 DROP VIEW IF EXISTS `product_data`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `product_data` AS SELECT 
 1 AS `sale_id`,
 1 AS `product_id`,
 1 AS `product_name`,
 1 AS `product_info`,
 1 AS `path`,
 1 AS `create_time`,
 1 AS `category_id`,
 1 AS `category_name`,
 1 AS `size`,
 1 AS `price`,
 1 AS `add_id`,
 1 AS `add_name`,
 1 AS `add_price`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `hot`
--

DROP TABLE IF EXISTS `hot`;
/*!50001 DROP VIEW IF EXISTS `hot`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `hot` AS SELECT 
 1 AS `product_id`,
 1 AS `product_name`,
 1 AS `product_info`,
 1 AS `path`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `product_data`
--

/*!50001 DROP VIEW IF EXISTS `product_data`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`tyl`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `product_data` AS select `ps`.`sale_id` AS `sale_id`,`p`.`product_id` AS `product_id`,`p`.`product_name` AS `product_name`,`p`.`product_info` AS `product_info`,`p`.`path` AS `path`,`p`.`create_time` AS `create_time`,`c`.`category_id` AS `category_id`,`c`.`category_name` AS `category_name`,`ps`.`size` AS `size`,`ps`.`price` AS `price`,`pa`.`add_id` AS `add_id`,`pa`.`add_name` AS `add_name`,`pa`.`add_price` AS `add_price` from (((`product_sale` `ps` left join `product_add` `pa` on(`pa`.`add_id` = `ps`.`add_id`)) left join `product` `p` on(`p`.`product_id` = `ps`.`product_id`)) left join `product_category` `c` on(`p`.`category_id` = `c`.`category_id`)) group by `ps`.`sale_id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `hot`
--

/*!50001 DROP VIEW IF EXISTS `hot`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`tyl`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `hot` AS select `p`.`product_id` AS `product_id`,`p`.`product_name` AS `product_name`,`p`.`product_info` AS `product_info`,`p`.`path` AS `path` from (`product` `p` left join `product_sale` `ps` on(`p`.`product_id` = `ps`.`product_id`)) group by `p`.`product_id` order by sum(`ps`.`sales`) desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-05  8:37:22
