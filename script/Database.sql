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
INSERT INTO `casse` VALUES (1,'Cassa 1'),(2,'Cassa 2'),(3,'Cassa 3'),(4,'Cassa Ordini');
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
  `ordineSequenziale` int(11) NOT NULL,
  PRIMARY KEY (`idCategoriaProdotto`),
  UNIQUE KEY `idxOrdineSequenziale` (`ordineSequenziale`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorieprodotti`
--

LOCK TABLES `categorieprodotti` WRITE;
/*!40000 ALTER TABLE `categorieprodotti` DISABLE KEYS */;
INSERT INTO `categorieprodotti` VALUES (1,'Primi Piatti',20),(2,'Secondi Piatti',30),(3,'Contorni',40),(4,'Bevande',10),(5,'Dolci',50);
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
  `totale` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'Totale commessa',
  `scontoApplicato` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'Sconto totale applicato',
  `totalePagato` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'Totale commessa',
  `totaleContanti` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'Totale contanti',
  `nomeCliente` text NOT NULL COMMENT 'Nome del cliente',
  `totaleResto` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'Resto',
  `tavoloCliente` text CHARACTER SET ascii NOT NULL COMMENT 'Tavolo cliente',
  `note` varchar(80) DEFAULT NULL,
  `coperti` int(10) NOT NULL DEFAULT '0' COMMENT 'Numero coperti',
  `descSconto` text,
  `asporto` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idCommessa`),
  KEY `idx_commesse_idCassa` (`idCassa`),
  KEY `idx_commesse_idOperatore` (`idOperatore`),
  KEY `idx_commesse_giorno` (`idGiorno`),
  KEY `idx_stato_ordine` (`idStatoOrdine`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
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
  `data` date NOT NULL,
  `scontoGiorno` decimal(10,2) NOT NULL DEFAULT '0.00',
  `flgAperto` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`idGiorno`),
  UNIQUE KEY `idxData` (`data`),
  KEY `idxAperto` (`flgAperto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `giorni`
--

LOCK TABLES `giorni` WRITE;
/*!40000 ALTER TABLE `giorni` DISABLE KEYS */;
INSERT INTO `giorni` VALUES (1,'2015-06-05',10.00,1),(2,'2015-06-06',0.00,1),(3,'2015-06-07',0.00,1),(4,'2015-06-08',10.00,1),(5,'2015-06-09',0.00,1);
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
  `prezzoUnitario` decimal(10,2) NOT NULL,
  `descrizione` tinytext COMMENT 'Descrizione estesa del prodotto',
  PRIMARY KEY (`idProdotto`),
  KEY `idxCategoriaProdotto` (`idCategoriaProdotto`)
) ENGINE=InnoDB AUTO_INCREMENT=5002 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listinoprodotti`
--

LOCK TABLES `listinoprodotti` WRITE;
/*!40000 ALTER TABLE `listinoprodotti` DISABLE KEYS */;
INSERT INTO `listinoprodotti` VALUES (101,4,'Ombra Rosso',1.00,NULL),(102,4,'Ombra Bianco',1.00,NULL),(201,4,'Vino Rosso Spina 1LT',4.50,NULL),(202,4,'Vino Rosso Spina 1/2 LT',2.50,NULL),(203,4,'Vino Bianco Spina 1LT',4.50,NULL),(204,4,'Vino Bianco Spina 1/2 LT',2.50,NULL),(301,4,'Prosecco Spina 1LT',5.50,NULL),(302,4,'Prosecco Spina 1/2 LT',3.00,NULL),(303,4,'Prosecco Spina Ombra',1.00,NULL),(304,4,'Raboso Spina 1LT',5.50,NULL),(305,4,'Raboso Spina 1/2 LT',3.00,NULL),(306,4,'Raboso Spina Ombra',1.00,NULL),(401,4,'1 Bott. prosecco',8.00,NULL),(501,4,'Birra Spina Piccola',1.50,NULL),(502,4,'Birra Spina Media',2.50,NULL),(503,4,'Birra Spina Grande',3.50,NULL),(601,4,'Caraffa Birra 1LT',5.50,NULL),(701,4,'Coca Spina Piccola',1.00,NULL),(702,4,'Coca Spina Media',1.50,NULL),(703,4,'Coca Spina Grande',2.50,NULL),(704,4,'Fanta Spina Piccola',1.00,NULL),(705,4,'Fanta Spina Media',1.50,NULL),(706,4,'Fanta Spina Grande',2.50,NULL),(801,4,'Caraffa Coca 1LT',5.00,NULL),(802,4,'Caraffa Fanta 1LT',5.00,NULL),(901,4,'Acqua Naturale 1LT',0.70,NULL),(902,4,'Acqua Naturale 1/2 LT',0.40,NULL),(903,4,'Acqua Gasata 1LT',0.70,NULL),(904,4,'Acqua Gasata 1/2 LT',0.40,NULL),(1001,1,'Tagliatelle Pomodoro',4.00,NULL),(1002,1,'Tagliatelle Ragù',4.00,NULL),(1003,1,'Tagliatelle Boscaiola',4.00,NULL),(1004,1,'Gnocchi Pomodoro',4.00,NULL),(1005,1,'Gnocchi Ragù',4.00,''),(1006,1,'Gnocchi Boscaiola',4.00,NULL),(1007,1,'Penne Arrabbiata',4.00,''),(1008,1,'Spaghetti in salsa',4.00,''),(1009,1,'Bigoli al sugo d\'anitra',4.50,''),(2000,2,'Costine e Salsiccia',5.50,'Grigliata di 2 ossetti, una salciccia, una fetta di pancetta e 2 fette di pokenta'),(2001,2,'Costine',5.50,'Grigliata di 3 ossetti con 2 fette di polenta'),(2002,2,'Salsicce',5.50,NULL),(2003,2,'Bistecca di Puledro',7.00,NULL),(2004,2,'Spezzatino di Cavallo',6.50,NULL),(2005,2,'Galletto alla Griglia',5.50,NULL),(2006,2,'Piatto Freddo',5.00,'Affettati misti'),(2007,2,'Formaggio',3.00,NULL),(2008,2,'Frittura Mista',7.80,NULL),(2009,2,'Baccalà Vicentina',7.80,NULL),(2010,2,'Baccalà Mantecato',7.80,NULL),(2011,2,'Trippa Parmigiana',5.50,NULL),(2012,2,'Soppressa e Formaggio',4.50,NULL),(2013,2,'Soppressa',4.50,NULL),(3001,3,'Fagioli con Verdure',2.00,NULL),(3002,3,'Verdura Mista fresca',2.00,NULL),(3003,3,'Funghi Misto Bosco',2.00,NULL),(3004,3,'Patate Fritte',2.00,NULL),(3005,3,'Pane',0.50,NULL),(3006,3,'Aggiunta Polenta',0.50,NULL),(5001,5,'Dolce',1.50,NULL);
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
INSERT INTO `operatori` VALUES (3,'Alberto'),(2,'Alessio'),(5,'Enzo'),(8,'Fabio'),(6,'Lorena'),(7,'Marco'),(1,'Marisa'),(9,'Nicoletta'),(4,'Stefano');
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
  `disponibilita` int(10) NOT NULL DEFAULT '0' COMMENT 'quantità disponibile all''inizio dell''esercizio',
  `quantitaVenduta` int(10) NOT NULL DEFAULT '0' COMMENT 'quantità venduta fino al momento nel giorno scelto',
  `quantitaWarning` int(10) NOT NULL DEFAULT '0' COMMENT 'Quantità al di sotto della quale visualizzare un warning',
  `scontoGiorno` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Sconto da applicare nel giorno selezionato',
  `sospensione` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Sospensione momentanea della vendita',
  `motivoSospensione` text COMMENT 'Motivo della sospensione',
  PRIMARY KEY (`idGiorno`,`idProdotto`),
  KEY `idxAttivi` (`sospensione`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodottigiornaliera`
--

LOCK TABLES `prodottigiornaliera` WRITE;
/*!40000 ALTER TABLE `prodottigiornaliera` DISABLE KEYS */;
INSERT INTO `prodottigiornaliera` VALUES (1,101,999,0,0,0.00,0,NULL),(1,102,999,0,0,0.00,0,NULL),(1,201,999,0,0,0.00,0,NULL),(1,202,999,0,0,0.00,0,NULL),(1,203,999,0,0,0.00,0,NULL),(1,204,999,0,0,0.00,0,NULL),(1,301,999,0,0,0.00,0,NULL),(1,302,999,0,0,0.00,0,NULL),(1,303,999,0,0,0.00,0,NULL),(1,304,999,0,0,0.00,0,NULL),(1,305,999,0,0,0.00,0,NULL),(1,306,999,0,0,0.00,0,NULL),(1,401,999,0,0,0.00,0,NULL),(1,501,999,0,0,0.00,0,NULL),(1,502,999,0,0,0.00,0,NULL),(1,503,999,0,0,0.00,0,NULL),(1,601,999,0,0,0.00,0,NULL),(1,701,999,0,0,0.00,0,NULL),(1,702,999,0,0,0.00,0,NULL),(1,703,999,0,0,0.00,0,NULL),(1,704,999,0,0,0.00,0,NULL),(1,705,999,0,0,0.00,0,NULL),(1,706,999,0,0,0.00,0,NULL),(1,801,999,0,0,0.00,0,NULL),(1,802,999,0,0,0.00,0,NULL),(1,901,999,0,0,0.00,0,NULL),(1,902,999,0,0,0.00,0,NULL),(1,903,999,0,0,0.00,0,NULL),(1,904,999,0,0,0.00,0,NULL),(1,1001,999,0,0,0.00,0,NULL),(1,1002,999,0,0,0.00,0,NULL),(1,1003,999,0,0,0.00,0,NULL),(1,1004,999,0,0,0.00,0,NULL),(1,1005,999,0,0,0.00,0,NULL),(1,1006,999,0,0,0.00,0,NULL),(1,1007,999,0,0,0.00,0,NULL),(1,1008,999,0,0,0.00,0,NULL),(1,1009,999,0,0,0.00,0,NULL),(1,2000,999,0,0,0.00,0,NULL),(1,2001,999,0,0,0.00,0,NULL),(1,2002,999,0,0,0.00,0,NULL),(1,2003,999,0,0,0.00,0,NULL),(1,2004,999,0,0,0.00,0,NULL),(1,2005,999,0,0,0.00,0,NULL),(1,2006,999,0,0,0.00,0,NULL),(1,2007,999,0,0,0.00,0,NULL),(1,2008,999,0,0,0.00,0,NULL),(1,2009,999,0,0,0.00,0,NULL),(1,2010,999,0,0,0.00,0,NULL),(1,2011,999,0,0,0.00,0,NULL),(1,2012,999,0,0,0.00,0,NULL),(1,2013,999,0,0,0.00,0,NULL),(1,3001,999,0,0,0.00,0,NULL),(1,3002,999,0,0,0.00,0,NULL),(1,3003,999,0,0,0.00,0,NULL),(1,3004,999,0,0,0.00,0,NULL),(1,3005,999,0,0,0.00,0,NULL),(1,3006,999,0,0,0.00,0,NULL),(1,5001,999,0,0,0.00,0,NULL),(2,101,999,0,0,0.00,0,NULL),(2,102,999,0,0,0.00,0,NULL),(2,201,999,0,0,0.00,0,NULL),(2,202,999,0,0,0.00,0,NULL),(2,203,999,0,0,0.00,0,NULL),(2,204,999,0,0,0.00,0,NULL),(2,301,999,0,0,0.00,0,NULL),(2,302,999,0,0,0.00,0,NULL),(2,303,999,0,0,0.00,0,NULL),(2,304,999,0,0,0.00,0,NULL),(2,305,999,0,0,0.00,0,NULL),(2,306,999,0,0,0.00,0,NULL),(2,401,999,0,0,0.00,0,NULL),(2,501,999,0,0,0.00,0,NULL),(2,502,999,0,0,0.00,0,NULL),(2,503,999,0,0,0.00,0,NULL),(2,601,999,0,0,0.00,0,NULL),(2,701,999,0,0,0.00,0,NULL),(2,702,999,0,0,0.00,0,NULL),(2,703,999,0,0,0.00,0,NULL),(2,704,999,0,0,0.00,0,NULL),(2,705,999,0,0,0.00,0,NULL),(2,706,999,0,0,0.00,0,NULL),(2,801,999,0,0,0.00,0,NULL),(2,802,999,0,0,0.00,0,NULL),(2,901,999,0,0,0.00,0,NULL),(2,902,999,0,0,0.00,0,NULL),(2,903,999,0,0,0.00,0,NULL),(2,904,999,0,0,0.00,0,NULL),(2,1001,999,0,0,0.00,0,NULL),(2,1002,999,0,0,0.00,0,NULL),(2,1003,999,0,0,0.00,0,NULL),(2,1004,999,0,0,0.00,0,NULL),(2,1005,999,0,0,0.00,0,NULL),(2,1006,999,0,0,0.00,0,NULL),(2,1007,999,0,0,0.00,0,NULL),(2,1008,999,0,0,0.00,0,NULL),(2,1009,999,0,0,0.00,0,NULL),(2,2000,999,0,0,0.00,0,NULL),(2,2001,999,0,0,0.00,0,NULL),(2,2002,999,0,0,0.00,0,NULL),(2,2003,999,0,0,0.00,0,NULL),(2,2004,999,0,0,0.00,0,NULL),(2,2005,999,0,0,0.00,0,NULL),(2,2006,999,0,0,0.00,0,NULL),(2,2007,999,0,0,0.00,0,NULL),(2,2008,999,0,0,0.00,0,NULL),(2,2009,999,0,0,0.00,0,NULL),(2,2010,999,0,0,0.00,0,NULL),(2,2011,999,0,0,0.00,0,NULL),(2,2012,999,0,0,0.00,0,NULL),(2,2013,999,0,0,0.00,0,NULL),(2,3001,999,0,0,0.00,0,NULL),(2,3002,999,0,0,0.00,0,NULL),(2,3003,999,0,0,0.00,0,NULL),(2,3004,999,0,0,0.00,0,NULL),(2,3005,999,0,0,0.00,0,NULL),(2,3006,999,0,0,0.00,0,NULL),(2,5001,999,0,0,0.00,0,NULL),(3,101,999,0,0,0.00,0,NULL),(3,102,999,0,0,0.00,0,NULL),(3,201,999,0,0,0.00,0,NULL),(3,202,999,0,0,0.00,0,NULL),(3,203,999,0,0,0.00,0,NULL),(3,204,999,0,0,0.00,0,NULL),(3,301,999,0,0,0.00,0,NULL),(3,302,999,0,0,0.00,0,NULL),(3,303,999,0,0,0.00,0,NULL),(3,304,999,0,0,0.00,0,NULL),(3,305,999,0,0,0.00,0,NULL),(3,306,999,0,0,0.00,0,NULL),(3,401,999,0,0,0.00,0,NULL),(3,501,999,0,0,0.00,0,NULL),(3,502,999,0,0,0.00,0,NULL),(3,503,999,0,0,0.00,0,NULL),(3,601,999,0,0,0.00,0,NULL),(3,701,999,0,0,0.00,0,NULL),(3,702,999,0,0,0.00,0,NULL),(3,703,999,0,0,0.00,0,NULL),(3,704,999,0,0,0.00,0,NULL),(3,705,999,0,0,0.00,0,NULL),(3,706,999,0,0,0.00,0,NULL),(3,801,999,0,0,0.00,0,NULL),(3,802,999,0,0,0.00,0,NULL),(3,901,999,0,0,0.00,0,NULL),(3,902,999,0,0,0.00,0,NULL),(3,903,999,0,0,0.00,0,NULL),(3,904,999,0,0,0.00,0,NULL),(3,1001,999,0,0,0.00,0,NULL),(3,1002,999,0,0,0.00,0,NULL),(3,1003,999,0,0,0.00,0,NULL),(3,1004,999,0,0,0.00,0,NULL),(3,1005,999,0,0,0.00,0,NULL),(3,1006,999,0,0,0.00,0,NULL),(3,1007,999,0,0,0.00,0,NULL),(3,1008,999,0,0,0.00,0,NULL),(3,1009,999,0,0,0.00,0,NULL),(3,2000,999,0,0,0.00,0,NULL),(3,2001,999,0,0,0.00,0,NULL),(3,2002,999,0,0,0.00,0,NULL),(3,2003,999,0,0,0.00,0,NULL),(3,2004,999,0,0,0.00,0,NULL),(3,2005,999,0,0,0.00,0,NULL),(3,2006,999,0,0,0.00,0,NULL),(3,2007,999,0,0,0.00,0,NULL),(3,2008,999,0,0,0.00,0,NULL),(3,2009,999,0,0,0.00,0,NULL),(3,2010,999,0,0,0.00,0,NULL),(3,2011,999,0,0,0.00,0,NULL),(3,2012,999,0,0,0.00,0,NULL),(3,2013,999,0,0,0.00,0,NULL),(3,3001,999,0,0,0.00,0,NULL),(3,3002,999,0,0,0.00,0,NULL),(3,3003,999,0,0,0.00,0,NULL),(3,3004,999,0,0,0.00,0,NULL),(3,3005,999,0,0,0.00,0,NULL),(3,3006,999,0,0,0.00,0,NULL),(3,5001,999,0,0,0.00,0,NULL),(4,101,999,0,0,0.00,0,NULL),(4,102,999,0,0,0.00,0,NULL),(4,201,999,0,0,0.00,0,NULL),(4,202,999,0,0,0.00,0,NULL),(4,203,999,0,0,0.00,0,NULL),(4,204,999,0,0,0.00,0,NULL),(4,301,999,0,0,0.00,0,NULL),(4,302,999,0,0,0.00,0,NULL),(4,303,999,0,0,0.00,0,NULL),(4,304,999,0,0,0.00,0,NULL),(4,305,999,0,0,0.00,0,NULL),(4,306,999,0,0,0.00,0,NULL),(4,401,999,0,0,0.00,0,NULL),(4,501,999,0,0,0.00,0,NULL),(4,502,999,0,0,0.00,0,NULL),(4,503,999,0,0,0.00,0,NULL),(4,601,999,0,0,0.00,0,NULL),(4,701,999,0,0,0.00,0,NULL),(4,702,999,0,0,0.00,0,NULL),(4,703,999,0,0,0.00,0,NULL),(4,704,999,0,0,0.00,0,NULL),(4,705,999,0,0,0.00,0,NULL),(4,706,999,0,0,0.00,0,NULL),(4,801,999,0,0,0.00,0,NULL),(4,802,999,0,0,0.00,0,NULL),(4,901,999,0,0,0.00,0,NULL),(4,902,999,0,0,0.00,0,NULL),(4,903,999,0,0,0.00,0,NULL),(4,904,999,0,0,0.00,0,NULL),(4,1001,999,0,0,0.00,0,NULL),(4,1002,999,0,0,0.00,0,NULL),(4,1003,999,0,0,0.00,0,NULL),(4,1004,999,0,0,0.00,0,NULL),(4,1005,999,0,0,0.00,0,NULL),(4,1006,999,0,0,0.00,0,NULL),(4,1007,999,0,0,0.00,0,NULL),(4,1008,999,0,0,0.00,0,NULL),(4,1009,999,0,0,0.00,0,NULL),(4,2000,999,0,0,0.00,0,NULL),(4,2001,999,0,0,0.00,0,NULL),(4,2002,999,0,0,0.00,0,NULL),(4,2003,999,0,0,0.00,0,NULL),(4,2004,999,0,0,0.00,0,NULL),(4,2005,999,0,0,0.00,0,NULL),(4,2006,999,0,0,0.00,0,NULL),(4,2007,999,0,0,0.00,0,NULL),(4,2008,999,0,0,0.00,0,NULL),(4,2009,999,0,0,0.00,0,NULL),(4,2010,999,0,0,0.00,0,NULL),(4,2011,999,0,0,0.00,0,NULL),(4,2012,999,0,0,0.00,0,NULL),(4,2013,999,0,0,0.00,0,NULL),(4,3001,999,0,0,0.00,0,NULL),(4,3002,999,0,0,0.00,0,NULL),(4,3003,999,0,0,0.00,0,NULL),(4,3004,999,0,0,0.00,0,NULL),(4,3005,999,0,0,0.00,0,NULL),(4,3006,999,0,0,0.00,0,NULL),(4,5001,999,0,0,0.00,0,NULL),(5,101,999,0,0,0.00,0,NULL),(5,102,999,0,0,0.00,0,NULL),(5,201,999,0,0,0.00,0,NULL),(5,202,999,0,0,0.00,0,NULL),(5,203,999,0,0,0.00,0,NULL),(5,204,999,0,0,0.00,0,NULL),(5,301,999,0,0,0.00,0,NULL),(5,302,999,0,0,0.00,0,NULL),(5,303,999,0,0,0.00,0,NULL),(5,304,999,0,0,0.00,0,NULL),(5,305,999,0,0,0.00,0,NULL),(5,306,999,0,0,0.00,0,NULL),(5,401,999,0,0,0.00,0,NULL),(5,501,999,0,0,0.00,0,NULL),(5,502,999,0,0,0.00,0,NULL),(5,503,999,0,0,0.00,0,NULL),(5,601,999,0,0,0.00,0,NULL),(5,701,999,0,0,0.00,0,NULL),(5,702,999,0,0,0.00,0,NULL),(5,703,999,0,0,0.00,0,NULL),(5,704,999,0,0,0.00,0,NULL),(5,705,999,0,0,0.00,0,NULL),(5,706,999,0,0,0.00,0,NULL),(5,801,999,0,0,0.00,0,NULL),(5,802,999,0,0,0.00,0,NULL),(5,901,999,0,0,0.00,0,NULL),(5,902,999,0,0,0.00,0,NULL),(5,903,999,0,0,0.00,0,NULL),(5,904,999,0,0,0.00,0,NULL),(5,1001,999,0,0,0.00,0,NULL),(5,1002,999,0,0,0.00,0,NULL),(5,1003,999,0,0,0.00,0,NULL),(5,1004,999,0,0,0.00,0,NULL),(5,1005,999,0,0,0.00,0,NULL),(5,1006,999,0,0,0.00,0,NULL),(5,1007,999,0,0,0.00,0,NULL),(5,1008,999,0,0,0.00,0,NULL),(5,1009,999,0,0,0.00,0,NULL),(5,2000,999,0,0,0.00,0,NULL),(5,2001,999,0,0,0.00,0,NULL),(5,2002,999,0,0,0.00,0,NULL),(5,2003,999,0,0,0.00,0,NULL),(5,2004,999,0,0,0.00,0,NULL),(5,2005,999,0,0,0.00,0,NULL),(5,2006,999,0,0,0.00,0,NULL),(5,2007,999,0,0,0.00,0,NULL),(5,2008,999,0,0,0.00,0,NULL),(5,2009,999,0,0,0.00,0,NULL),(5,2010,999,0,0,0.00,0,NULL),(5,2011,999,0,0,0.00,0,NULL),(5,2012,999,0,0,0.00,0,NULL),(5,2013,999,0,0,0.00,0,NULL),(5,3001,999,0,0,0.00,0,NULL),(5,3002,999,0,0,0.00,0,NULL),(5,3003,999,0,0,0.00,0,NULL),(5,3004,999,0,0,0.00,0,NULL),(5,3005,999,0,0,0.00,0,NULL),(5,3006,999,0,0,0.00,0,NULL),(5,5001,999,0,0,0.00,0,NULL);
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
  `idProdotto` int(11) NOT NULL COMMENT 'Identifica il prodotto venduto',
  `quantita` int(3) NOT NULL DEFAULT '1',
  `varianti` text COMMENT 'Varianti applicate alla riga',
  `prezzoListino` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Prezzo listino applicato',
  PRIMARY KEY (`idRiga`),
  KEY `idx_righecommesse_idProdotto` (`idProdotto`),
  KEY `idxCommessa` (`idCommessa`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8;
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
  `sconto` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT 'Sconto da applicare',
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
  UNIQUE KEY `idx_statiordine_ordineSequenziale` (`ordineSequenziale`)
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
INSERT INTO `variantiprodotti` VALUES (1,'Senza Formaggio',1),(2,'Pomodoro',1),(3,'In bianco',1),(4,'Arrabbiata',1),(5,'Ragù',1),(6,'No cipolle',3),(7,'No peperoni',3),(8,'Ragù anitra',1),(9,'In salsa',1);
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
  `descrizione` tinyint NOT NULL,
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
/*!50001 VIEW `vistalistinoreale` AS select `l`.`idProdotto` AS `idProdotto`,`l`.`idCategoriaProdotto` AS `idCategoriaProdotto`,`g`.`idGiorno` AS `idGiorno`,`l`.`nomeProdotto` AS `nomeProdotto`,`l`.`prezzoUnitario` AS `prezzoUnitario`,`l`.`descrizione` AS `descrizione`,`g`.`disponibilita` AS `disponibilita`,`g`.`quantitaVenduta` AS `quantitaVenduta`,`g`.`quantitaWarning` AS `quantitaWarning` from ((`listinoprodotti` `l` join `prodottigiornaliera` `g` on((`l`.`idProdotto` = `g`.`idProdotto`))) join `categorieprodotti` `cp` on((`l`.`idCategoriaProdotto` = `cp`.`idCategoriaProdotto`))) where (`g`.`sospensione` = 0) order by `cp`.`ordineSequenziale`,`l`.`nomeProdotto` */;
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

-- Dump completed on 2015-05-30 15:01:55
