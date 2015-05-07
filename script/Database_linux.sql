SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+01:00";

CREATE DATABASE  IF NOT EXISTS `sagra`;
USE `sagra`;

--
-- Table structure for table `casse`
--

DROP TABLE IF EXISTS `casse`;
CREATE TABLE `casse` (
  `idCassa` int(10) NOT NULL,
  `descrizione` tinytext NOT NULL,
  PRIMARY KEY (`idCassa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `casse`
--

INSERT INTO `casse` VALUES (1,'Cassa 1'),(2,'Cassa 2'),(3,'Cassa 3');

--
-- Table structure for table `categorieprodotti`
--

DROP TABLE IF EXISTS `categorieprodotti`;
CREATE TABLE `categorieprodotti` (
  `idCategoriaProdotto` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `descrizione` text NOT NULL,
  `ordineSequenziale` int(11) NOT NULL,
  PRIMARY KEY (`idCategoriaProdotto`),
  UNIQUE KEY `idxOrdineSequenziale` (`ordineSequenziale`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `categorieprodotti`
--

INSERT INTO `categorieprodotti` VALUES (1,'Primi piatti',10),(2,'Secondi Piatti',20),(3,'Contorni',30),(4,'Bevande',40),(5,'Bar',5);

--
-- Table structure for table `commesse`
--

DROP TABLE IF EXISTS `commesse`;
CREATE TABLE `commesse` (
  `idCommessa` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Chiave primaria',
  `idGiorno` int(10) NOT NULL,
  `idCassa` int(10) unsigned NOT NULL,
  `idOperatore` int(11) NOT NULL,
  `idStatoOrdine` int(10) NOT NULL,
  `totalePagato` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'Totale commessa',
  `totaleContanti` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'Totale contanti',
  `totaleResto` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'Resto',
  `scontoApplicato` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'Sconto totale applicato',
  `nomeCliente` text NOT NULL COMMENT 'Nome del cliente',
  `tavoloCliente` text CHARACTER SET ascii NOT NULL COMMENT 'Tavolo cliente',
  `note` varchar(80) DEFAULT NULL,
  `coperti` int(10) NOT NULL DEFAULT '0' COMMENT 'Numero coperti',
  `descSconto` text,
  PRIMARY KEY (`idCommessa`),
  KEY `idx_commesse_idCassa` (`idCassa`),
  KEY `idx_commesse_idOperatore` (`idOperatore`),
  KEY `idx_commesse_giorno` (`idGiorno`),
  KEY `idx_stato_ordine` (`idStatoOrdine`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `commesse`
--

--
-- Table structure for table `giorni`
--

DROP TABLE IF EXISTS `giorni`;
CREATE TABLE `giorni` (
  `idGiorno` int(11) NOT NULL,
  `data` date NOT NULL,
  `scontoGiorno` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`idGiorno`),
  UNIQUE KEY `idxData` (`data`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `giorni`
--

INSERT INTO `giorni` VALUES (1,'2015-05-29',15.00),(2,'2015-05-30',0.00),(3,'2015-05-31',0.00),(4,'2015-06-01',15.00),(5,'2015-06-02',0.00);

--
-- Table structure for table `listinoprodotti`
--

DROP TABLE IF EXISTS `listinoprodotti`;
CREATE TABLE `listinoprodotti` (
  `idProdotto` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'chiave primaria tabella',
  `idCategoriaProdotto` int(10) unsigned NOT NULL,
  `nomeProdotto` tinytext NOT NULL COMMENT 'Nome identificativo del prodotto',
  `descrizione` tinytext NOT NULL COMMENT 'Descrizione estesa del prodotto',
  `prezzoUnitario` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`idProdotto`),
  KEY `idxCategoriaProdotto` (`idCategoriaProdotto`)
) ENGINE=InnoDB AUTO_INCREMENT=2002 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `listinoprodotti`
--

INSERT INTO `listinoprodotti` VALUES (1000,1,'Penne all\'arrabbiata','',5.00),(1001,1,'Bigoli in salsa','',4.50),(1002,1,'Bigoli al sugo d\'anitra','',5.00),(1003,1,'Gnocchi al ragù','',4.00),(2000,2,'Grigliata mista','Grigliata di 2 ossetti, una salciccia, una fetta di pancetta e 2 fette di pokenta',8.30),(2001,2,'Ossetti','Grigliata di 3 ossetti con 2 fette di polenta',8.00);

--
-- Table structure for table `logcasse`
--

DROP TABLE IF EXISTS `logcasse`;
CREATE TABLE `logcasse` (
  `idCassa` int(10) NOT NULL,
  `idGiorno` int(10) NOT NULL,
  `dataOra` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idOperazione` char(3) NOT NULL,
  `idOperatore` int(10) NOT NULL,
  `descrizione` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCassa`,`idGiorno`,`dataOra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `logordini`
--

DROP TABLE IF EXISTS `logordini`;
CREATE TABLE `logordini` (
  `idRigaLog` int(11) NOT NULL AUTO_INCREMENT,
  `idOrdine` int(11) NOT NULL,
  `idStatoOrdine` int(11) NOT NULL,
  `dataOra` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idRigaLog`,`idOrdine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `operatori`
--

DROP TABLE IF EXISTS `operatori`;
CREATE TABLE `operatori` (
  `idOperatore` int(11) NOT NULL,
  `operatore` char(10) NOT NULL,
  PRIMARY KEY (`idOperatore`),
  UNIQUE KEY `operatore_UNIQUE` (`operatore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `operatori`
--

INSERT INTO `operatori` VALUES (1,'Pippo'),(2,'Pluto'),(3,'Paperino');

--
-- Table structure for table `operazionicassa`
--

DROP TABLE IF EXISTS `operazionicassa`;
CREATE TABLE `operazionicassa` (
  `idOperazione` char(3) NOT NULL,
  `descrizione` varchar(45) NOT NULL,
  PRIMARY KEY (`idOperazione`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `operazionicassa`
--

INSERT INTO `operazionicassa` VALUES ('FND','Fondo Cassa'),('VER','Versamento');

--
-- Table structure for table `prodottigiornaliera`
--

DROP TABLE IF EXISTS `prodottigiornaliera`;
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

--
-- Dumping data for table `prodottigiornaliera`
--

INSERT INTO `prodottigiornaliera` VALUES (1,1000,999,0,0,0.00,0,NULL),(1,1001,999,0,0,0.00,0,NULL),(1,1002,999,0,0,0.00,0,NULL),(1,1003,999,0,0,0.00,0,NULL),(1,2000,999,0,0,0.00,0,NULL),(1,2001,999,0,0,0.00,0,NULL),(2,1000,999,0,0,0.00,0,NULL),(2,1001,999,0,0,0.00,0,NULL),(2,1002,999,0,0,0.00,0,NULL),(2,1003,999,0,0,0.00,0,NULL),(2,2000,999,0,0,0.00,0,NULL),(2,2001,999,0,0,0.00,0,NULL),(3,1000,999,0,0,0.00,0,NULL),(3,1001,999,0,0,0.00,0,NULL),(3,1002,999,0,0,0.00,0,NULL),(3,1003,999,0,0,0.00,0,NULL),(3,2000,999,0,0,0.00,0,NULL),(3,2001,999,0,0,0.00,0,NULL),(4,1000,999,0,0,0.00,0,NULL),(4,1001,999,0,0,0.00,0,NULL),(4,1002,999,0,0,0.00,0,NULL),(4,1003,999,0,0,0.00,0,NULL),(4,2000,999,0,0,0.00,0,NULL),(4,2001,999,0,0,0.00,0,NULL),(5,1000,999,0,0,0.00,0,NULL),(5,1001,999,0,0,0.00,0,NULL),(5,1002,999,0,0,0.00,0,NULL),(5,1003,999,0,0,0.00,0,NULL),(5,2000,999,0,0,0.00,0,NULL),(5,2001,999,0,0,0.00,0,NULL);

--
-- Table structure for table `righecommesse`
--

DROP TABLE IF EXISTS `righecommesse`;
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

--
-- Table structure for table `sconti`
--

DROP TABLE IF EXISTS `sconti`;
CREATE TABLE `sconti` (
  `idSconto` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Chiave primaria',
  `descrizione` text NOT NULL COMMENT 'Descrizione dello sconto',
  `sconto` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT 'Sconto da applicare',
  PRIMARY KEY (`idSconto`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sconti`
--

INSERT INTO `sconti` VALUES (1,'Sconto operatori sagra',100.00),(2,'Sconto venerdi\'',10.00),(3,'Sconto Lunedi\'',10.00);

--
-- Table structure for table `statiordine`
--

DROP TABLE IF EXISTS `statiordine`;
CREATE TABLE `statiordine` (
  `idStatoOrdine` int(11) NOT NULL AUTO_INCREMENT,
  `ordineSequenziale` int(11) NOT NULL,
  `descrizione` tinytext NOT NULL,
  PRIMARY KEY (`idStatoOrdine`),
  UNIQUE KEY `idx_statiordine_ordineSequenziale` (`ordineSequenziale`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `statiordine`
--

INSERT INTO `statiordine` VALUES (1,1,'Emissione commessa'),(2,10,'Preso in carico'),(3,20,'Consegnato');

--
-- Table structure for table `variantiprodotti`
--

DROP TABLE IF EXISTS `variantiprodotti`;
CREATE TABLE `variantiprodotti` (
  `idVariante` int(11) NOT NULL,
  `variante` text NOT NULL,
  `idCategoriaProdotto` int(11) NOT NULL,
  PRIMARY KEY (`idVariante`),
  KEY `idxCategoriaProdotto` (`idCategoriaProdotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `variantiprodotti`
--

INSERT INTO `variantiprodotti` VALUES (1,'Senza Formaggio',1),(2,'Pomodoro',1),(3,'In bianco',1);

--
-- Temporary table structure for view `vistalistinoreale`
--

DROP TABLE IF EXISTS `vistalistinoreale`;
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
