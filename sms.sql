-- MySQL dump 10.13  Distrib 8.0.28, for Linux (x86_64)
--
-- Host: localhost    Database: sms
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `pay_record`
--

DROP TABLE IF EXISTS `pay_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `studentID` int NOT NULL,
  `operateDate` datetime NOT NULL,
  `targetPaymentID` int NOT NULL,
  `serialNumber` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_record`
--

LOCK TABLES `pay_record` WRITE;
/*!40000 ALTER TABLE `pay_record` DISABLE KEYS */;
INSERT INTO `pay_record` VALUES (1,8,'2022-02-21 20:48:46',2,34556);
/*!40000 ALTER TABLE `pay_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `publishDate` datetime NOT NULL,
  `limitDate` datetime NOT NULL,
  `payedCount` int NOT NULL DEFAULT '0',
  `fee` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (2,'test2','2022-02-20 18:52:58','2022-02-27 10:00:00',1,114514);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `uuid` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons`
--

LOCK TABLES `persons` WRITE;
/*!40000 ALTER TABLE `persons` DISABLE KEYS */;
INSERT INTO `persons` VALUES (1,'admin_user','8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414','ADMIN',''),(2,'test_student1','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','STUDENT',''),(6,'createtest','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','USER',''),(7,'admin','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','USER',''),(9,'createtest3','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','FINANCE','0846dc93-58aa-428f-97a2-849789082098'),(10,'pagingtest1','8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414','ADMIN',''),(11,'pagingtest2','8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414','ADMIN',''),(12,'pagingtest3','8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414','ADMIN',''),(13,'pagingtest4','8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414','ADMIN',''),(14,'pagingtest5','8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414','ADMIN',''),(15,'pagingtest6','8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414','ADMIN',''),(16,'pagingtest7','8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414','ADMIN',''),(18,'JaTAAmikou','3bb8e80b5e01f54d829f8359cd191e6fa2337b0ffebcbefdf0b89374210c8c40','STUDENT','a955c30e-8f37-406b-8b37-359281c5bd5f'),(19,'GmNaxrsIEF','0dd91793f35248070c9eafabae47c76f1b2db9b75ede2a1ac54ac8d4a209e8c8','STUDENT','4789bec8-47bf-4662-bbec-2e03a999a323'),(20,'wfcxHmcwyR','8291d844987ef8d378ef8244606a684a60e103a98e363eebe0b6c9d833171c6a','STUDENT','3ea8ef26-15ed-4ce1-bba2-8d8720399b93'),(21,'AKqcskbPsr','249dbf2048d06b34bab45ccf09a9e9377853bc093e0c1b15a900986244cdab11','STUDENT','82aed28a-2394-41d0-8b59-34ecdfcfcca3'),(22,'finance','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','FINANCE','876d17b7-8188-4eb0-a5a6-ad2962e86616'),(23,'student','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','STUDENT','e37fcb90-e7d0-4986-8970-29d85c690ccd');
/*!40000 ALTER TABLE `persons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `clazz` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `age` int NOT NULL,
  `sex` int NOT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'nohead.jpg',
  `dormitoryID` int NOT NULL,
  `matriculateNum` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telephone` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL,
  `personID` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'teststudent1','1',18,1,'1.jpg',1,'1','somewhere','1111111111',2),(3,'田所浩二','11',24,1,'nohead.jpg',45,'1919810','下北泽114514号','11451419198',17),(4,'test','2',114514,0,'nohead.jpg',2,'114514','dsgfsdgdfgretfregvfdfgdsfg','19198101145',18),(5,'test','2',114514,1,'nohead.jpg',2,'343','dasfrergtegret','11111114514',19),(6,'test3','2',114514,1,'nohead.jpg',2,'234','dsgfdrstwetfdfgdftwetgfdrfgdtfyhre','19198101145',20),(7,'test4','3',114514,1,'nohead.jpg',3,'456','1145141919810','19198101145',21),(8,'student','5',24,0,'nohead.jpg',5,'678','...........','12345678901',23);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-22 14:15:50
