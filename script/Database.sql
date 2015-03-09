CREATE DATABASE  IF NOT EXISTS `sagra` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sagra`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: sagra
-- ------------------------------------------------------
-- Server version	5.6.22-log

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
-- Table structure for table `casse`
--

DROP TABLE IF EXISTS `casse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `casse` (
  `idCassa` int(10) NOT NULL,
  `descrizione` tinytext NOT NULL,
  PRIMARY KEY (`idCassa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `casse`
--

LOCK TABLES `casse` WRITE;
/*!40000 ALTER TABLE `casse` DISABLE KEYS */;
INSERT INTO `casse` VALUES (1,'Cassa 1'),(2,'Cassa 2'),(3,'Cassa 3');
/*!40000 ALTER TABLE `casse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorieprodotti`
--

DROP TABLE IF EXISTS `categorieprodotti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categorieprodotti` (
  `idCategoriaProdotto` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `descrizione` text NOT NULL,
  `ordineSequenziale` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCategoriaProdotto`),
  KEY `idxOrdineSequenziale` (`ordineSequenziale`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorieprodotti`
--

LOCK TABLES `categorieprodotti` WRITE;
/*!40000 ALTER TABLE `categorieprodotti` DISABLE KEYS */;
INSERT INTO `categorieprodotti` VALUES (1,'Primi piatti',10),(2,'Secondi Piatti',20),(3,'Contorni',30),(4,'Bevande',40),(5,'Bar',5);
/*!40000 ALTER TABLE `categorieprodotti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commesse`
--

DROP TABLE IF EXISTS `commesse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commesse` (
  `idCommessa` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Chiave primaria',
  `idGiorno` int(10) NOT NULL,
  `idCassa` int(10) unsigned NOT NULL,
  `idOperatore` int(11) NOT NULL,
  `idStatoOrdine` int(10) NOT NULL,
  `totalePagato` decimal(10,2) unsigned NOT NULL COMMENT 'Totale commessa',
  `totaleContanti` decimal(10,2) unsigned NOT NULL COMMENT 'Totale contanti',
  `totaleResto` decimal(10,2) unsigned NOT NULL COMMENT 'Resto',
  `scontoApplicato` decimal(10,2) unsigned NOT NULL COMMENT 'Sconto totale applicato',
  `nomeCliente` text NOT NULL COMMENT 'Nome del cliente',
  `tavoloClente` text CHARACTER SET ascii NOT NULL COMMENT 'Tavolo cliente',
  `note` varchar(80) NOT NULL,
  PRIMARY KEY (`idCommessa`),
  KEY `idx_commesse_idCassa` (`idCassa`),
  KEY `idx_commesse_idOperatore` (`idOperatore`),
  KEY `idx_commesse_giorno` (`idGiorno`),
  KEY `idx_stato_ordine` (`idStatoOrdine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commesse`
--

LOCK TABLES `commesse` WRITE;
/*!40000 ALTER TABLE `commesse` DISABLE KEYS */;
/*!40000 ALTER TABLE `commesse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `giorni`
--

DROP TABLE IF EXISTS `giorni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `giorni` (
  `idGiorno` int(11) NOT NULL,
  `data` date DEFAULT NULL,
  `scontoGiorno` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`idGiorno`),
  UNIQUE KEY `idxData` (`data`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `giorni`
--

LOCK TABLES `giorni` WRITE;
/*!40000 ALTER TABLE `giorni` DISABLE KEYS */;
INSERT INTO `giorni` VALUES (1,'2015-05-29',15.00),(2,'2015-05-30',0.00),(3,'2015-05-31',0.00),(4,'2015-06-01',15.00),(5,'2015-06-02',0.00);
/*!40000 ALTER TABLE `giorni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listinoprodotti`
--

DROP TABLE IF EXISTS `listinoprodotti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listinoprodotti` (
  `idProdotto` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'chiave primaria tabella',
  `idCategoriaProdotto` int(10) unsigned NOT NULL,
  `nomeProdotto` tinytext NOT NULL COMMENT 'Nome identificativo del prodotto',
  `descrizione` tinytext NOT NULL COMMENT 'Descrizione estesa del prodotto',
  `prezzoUnitario` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idProdotto`),
  KEY `idxCategoriaProdotto` (`idCategoriaProdotto`)
) ENGINE=InnoDB AUTO_INCREMENT=2002 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listinoprodotti`
--

LOCK TABLES `listinoprodotti` WRITE;
/*!40000 ALTER TABLE `listinoprodotti` DISABLE KEYS */;
INSERT INTO `listinoprodotti` VALUES (1000,1,'Penne all\'arrabbiata','',5.00),(1001,1,'Bigoli in salsa','',4.50),(1002,1,'Bigoli al sugo d\'anitra','',5.00),(1003,1,'Gnocchi al ragù','',4.00),(2000,2,'Grigliata mista','Grigliata di 2 ossetti, una salciccia, una fetta di pancetta e 2 fette di pokenta',8.30),(2001,2,'Ossetti','Grigliata di 3 ossetti con 2 fette di polenta',8.00);
/*!40000 ALTER TABLE `listinoprodotti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logcasse`
--

DROP TABLE IF EXISTS `logcasse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logcasse` (
  `idCassa` int(10) NOT NULL,
  `idGiorno` int(10) NOT NULL,
  `dataOra` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idOperazione` char(3) NOT NULL,
  `idOperatore` int(10) NOT NULL,
  `descrizione` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCassa`,`idGiorno`,`dataOra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logcasse`
--

LOCK TABLES `logcasse` WRITE;
/*!40000 ALTER TABLE `logcasse` DISABLE KEYS */;
/*!40000 ALTER TABLE `logcasse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logordini`
--

DROP TABLE IF EXISTS `logordini`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logordini` (
  `idRigaLog` int(11) NOT NULL AUTO_INCREMENT,
  `idOrdine` int(11) NOT NULL,
  `idStatoOrdine` int(11) NOT NULL,
  `dataOra` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idRigaLog`,`idOrdine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logordini`
--

LOCK TABLES `logordini` WRITE;
/*!40000 ALTER TABLE `logordini` DISABLE KEYS */;
/*!40000 ALTER TABLE `logordini` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operatori`
--

DROP TABLE IF EXISTS `operatori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operatori` (
  `idOperatore` int(11) NOT NULL,
  `operatore` char(10) NOT NULL,
  PRIMARY KEY (`idOperatore`),
  UNIQUE KEY `operatore_UNIQUE` (`operatore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operatori`
--

LOCK TABLES `operatori` WRITE;
/*!40000 ALTER TABLE `operatori` DISABLE KEYS */;
INSERT INTO `operatori` VALUES (3,'Paperino'),(1,'Pippo'),(2,'Pluto');
/*!40000 ALTER TABLE `operatori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operazionicassa`
--

DROP TABLE IF EXISTS `operazionicassa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operazionicassa` (
  `idOperazione` char(3) NOT NULL,
  `descrizione` varchar(45) NOT NULL,
  PRIMARY KEY (`idOperazione`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operazionicassa`
--

LOCK TABLES `operazionicassa` WRITE;
/*!40000 ALTER TABLE `operazionicassa` DISABLE KEYS */;
INSERT INTO `operazionicassa` VALUES ('FND','Fondo Cassa'),('VER','Versamento');
/*!40000 ALTER TABLE `operazionicassa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodottigiornaliera`
--

DROP TABLE IF EXISTS `prodottigiornaliera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prodottigiornaliera` (
  `idGiorno` int(10) NOT NULL,
  `idProdotto` int(10) unsigned NOT NULL COMMENT 'identifica il prodotto nella tabella listino',
  `disponibilita` int(10) unsigned NOT NULL COMMENT 'quantità disponibile all''inizio dell''esercizio',
  `quantitaVenduta` int(10) unsigned NOT NULL COMMENT 'quantità venduta fino al momento nel giorno scelto',
  `quantitaWarning` int(10) unsigned NOT NULL COMMENT 'Quantità al di sotto della quale visualizzare un warning',
  `scontoGiorno` decimal(10,2) unsigned NOT NULL COMMENT 'Sconto da applicare nel giorno selezionato',
  `sospensione` tinyint(1) NOT NULL COMMENT 'Sospensione momentanea della vendita',
  `motivoSospensione` text COMMENT 'Motivo della sospensione',
  PRIMARY KEY (`idGiorno`,`idProdotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodottigiornaliera`
--

LOCK TABLES `prodottigiornaliera` WRITE;
/*!40000 ALTER TABLE `prodottigiornaliera` DISABLE KEYS */;
INSERT INTO `prodottigiornaliera` VALUES (1,1001,100,0,0,0.00,0,' ');
/*!40000 ALTER TABLE `prodottigiornaliera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `righecommesse`
--

DROP TABLE IF EXISTS `righecommesse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `righecommesse` (
  `idRiga` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Chiave primaria',
  `idCommessa` int(11) NOT NULL COMMENT 'Chiave primaria',
  `idProdotto` int(11) DEFAULT NULL COMMENT 'Identifica il prodotto venduto',
  `varianti` text NOT NULL COMMENT 'Varianti applicate alla riga',
  `prezzoListino` decimal(10,2) NOT NULL COMMENT 'Prezzo listino applicato',
  `scontoApplicato` decimal(10,2) NOT NULL COMMENT 'Sconto applicato',
  PRIMARY KEY (`idRiga`,`idCommessa`),
  KEY `idx_righecommesse_idProdotto` (`idProdotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `righecommesse`
--

LOCK TABLES `righecommesse` WRITE;
/*!40000 ALTER TABLE `righecommesse` DISABLE KEYS */;
/*!40000 ALTER TABLE `righecommesse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sconti`
--

DROP TABLE IF EXISTS `sconti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sconti` (
  `idSconto` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Chiave primaria',
  `descrizione` text NOT NULL COMMENT 'Descrizione dello sconto',
  `sconto` decimal(5,2) NOT NULL COMMENT 'Sconto da applicare',
  PRIMARY KEY (`idSconto`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sconti`
--

LOCK TABLES `sconti` WRITE;
/*!40000 ALTER TABLE `sconti` DISABLE KEYS */;
INSERT INTO `sconti` VALUES (1,'Sconto operatori sagra',100.00),(2,'Sconto venerdi\'',10.00),(3,'Sconto Lunedi\'',10.00);
/*!40000 ALTER TABLE `sconti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statiordine`
--

DROP TABLE IF EXISTS `statiordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statiordine` (
  `idStatoOrdine` int(11) NOT NULL AUTO_INCREMENT,
  `ordineSequenziale` int(11) NOT NULL,
  `descrizione` tinytext NOT NULL,
  PRIMARY KEY (`idStatoOrdine`),
  KEY `idx_statiordine_ordineSequenziale` (`ordineSequenziale`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statiordine`
--

LOCK TABLES `statiordine` WRITE;
/*!40000 ALTER TABLE `statiordine` DISABLE KEYS */;
INSERT INTO `statiordine` VALUES (1,1,'Emissione commessa'),(2,10,'Preso in carico'),(3,20,'Consegnato');
/*!40000 ALTER TABLE `statiordine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variantiprodotti`
--

DROP TABLE IF EXISTS `variantiprodotti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `variantiprodotti` (
  `idVariante` int(11) NOT NULL,
  `variante` text NOT NULL,
  `idCategoriaProdotto` int(11) NOT NULL,
  PRIMARY KEY (`idVariante`),
  KEY `idxCategoriaProdotto` (`idCategoriaProdotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variantiprodotti`
--

LOCK TABLES `variantiprodotti` WRITE;
/*!40000 ALTER TABLE `variantiprodotti` DISABLE KEYS */;
/*!40000 ALTER TABLE `variantiprodotti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `vistalistinoreale`
--

DROP TABLE IF EXISTS `vistalistinoreale`;
/*!50001 DROP VIEW IF EXISTS `vistalistinoreale`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `vistalistinoreale` (
  `idProdotto` tinyint NOT NULL,
  `idCategoriaProdotto` tinyint NOT NULL,
  `idGiorno` tinyint NOT NULL,
  `nomeProdotto` tinyint NOT NULL,
  `prezzoUnitario` tinyint NOT NULL,
  `disponibilita` tinyint NOT NULL,
  `quantitaVenduta` tinyint NOT NULL,
  `quantitaWarning` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vistalistinoreale`
--

/*!50001 DROP TABLE IF EXISTS `vistalistinoreale`*/;
/*!50001 DROP VIEW IF EXISTS `vistalistinoreale`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vistalistinoreale` AS select `l`.`idProdotto` AS `idProdotto`,`l`.`idCategoriaProdotto` AS `idCategoriaProdotto`,`g`.`idGiorno` AS `idGiorno`,`l`.`nomeProdotto` AS `nomeProdotto`,`l`.`prezzoUnitario` AS `prezzoUnitario`,`g`.`disponibilita` AS `disponibilita`,`g`.`quantitaVenduta` AS `quantitaVenduta`,`g`.`quantitaWarning` AS `quantitaWarning` from ((`listinoprodotti` `l` join `prodottigiornaliera` `g` on((`l`.`idProdotto` = `g`.`idProdotto`))) join `categorieprodotti` `cp` on((`l`.`idCategoriaProdotto` = `cp`.`idCategoriaProdotto`))) where (`g`.`sospensione` = 0) order by `cp`.`ordineSequenziale`,`l`.`nomeProdotto` */;
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

-- Dump completed on 2015-03-09 21:06:26
