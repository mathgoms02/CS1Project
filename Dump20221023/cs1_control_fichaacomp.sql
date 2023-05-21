-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: cs1_control
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `fichaacomp`
--

DROP TABLE IF EXISTS `fichaacomp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fichaacomp` (
  `idFichaAcomp` int NOT NULL AUTO_INCREMENT,
  `peca` int DEFAULT NULL,
  `qtd` int DEFAULT NULL,
  `problema` int DEFAULT NULL,
  `turno` int DEFAULT NULL,
  `data` varchar(45) DEFAULT NULL,
  `acao` varchar(100) DEFAULT NULL,
  `obs` varchar(100) DEFAULT NULL,
  `re` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idFichaAcomp`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fichaacomp`
--

LOCK TABLES `fichaacomp` WRITE;
/*!40000 ALTER TABLE `fichaacomp` DISABLE KEYS */;
INSERT INTO `fichaacomp` VALUES (29,2588,1,1,1,'19/10/2022','refugar','','52952'),(30,2588,3,4,2,'19/10/2022','estudar','não tem como ver problema em tal setor','52952'),(31,2588,2,1,2,'19/10/2022','teste1','teste1','52952'),(32,2588,2,1,2,'19/10/2022','teste2','','52952'),(33,2588,1,1,2,'21/10/2022','refuga','analise de processo','52952'),(34,2588,1,32,1,'21/10/2022','teste','','52952'),(35,2588,1,32,1,'22/10/2022','teste','1','blabla');
/*!40000 ALTER TABLE `fichaacomp` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-23 18:49:56
