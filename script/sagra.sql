/*
SQLyog Community v12.2.4 (64 bit)
MySQL - 5.7.11-log : Database - sagra
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sagra` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `sagra`;

/*Table structure for table `casse` */

DROP TABLE IF EXISTS `casse`;

CREATE TABLE `casse` (
  `idCassa` int(10) NOT NULL,
  `descrizione` tinytext NOT NULL,
  PRIMARY KEY (`idCassa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `casse` */

insert  into `casse`(`idCassa`,`descrizione`) values 
(1,'Cassa 1'),
(2,'Cassa 2'),
(3,'Cassa 3');

/*Table structure for table `categorieprodotti` */

DROP TABLE IF EXISTS `categorieprodotti`;

CREATE TABLE `categorieprodotti` (
  `idCategoriaProdotto` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `descrizione` text NOT NULL,
  `ordineSequenziale` int(11) NOT NULL,
  PRIMARY KEY (`idCategoriaProdotto`),
  UNIQUE KEY `idxOrdineSequenziale` (`ordineSequenziale`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `categorieprodotti` */

insert  into `categorieprodotti`(`idCategoriaProdotto`,`descrizione`,`ordineSequenziale`) values 
(1,'Primi Piatti',20),
(2,'Secondi Piatti',30),
(3,'Contorni',40),
(4,'Bevande',10),
(5,'Dolci',50);

/*Table structure for table `commesse` */

DROP TABLE IF EXISTS `commesse`;

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
  `orario` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idCommessa`),
  KEY `idx_commesse_idCassa` (`idCassa`),
  KEY `idx_commesse_idOperatore` (`idOperatore`),
  KEY `idx_commesse_giorno` (`idGiorno`),
  KEY `idx_stato_ordine` (`idStatoOrdine`)
) ENGINE=InnoDB AUTO_INCREMENT=422 DEFAULT CHARSET=utf8;

/*Data for the table `commesse` */

/*Table structure for table `giorni` */

DROP TABLE IF EXISTS `giorni`;

CREATE TABLE `giorni` (
  `idGiorno` int(11) NOT NULL,
  `data` date NOT NULL,
  `scontoGiorno` decimal(10,2) NOT NULL DEFAULT '0.00',
  `flgAperto` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`idGiorno`),
  UNIQUE KEY `idxData` (`data`),
  KEY `idxAperto` (`flgAperto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `giorni` */

insert  into `giorni`(`idGiorno`,`data`,`scontoGiorno`,`flgAperto`) values 
(1,'2016-06-02','0.00',0),
(2,'2016-06-03','10.00',1),
(3,'2016-06-04','0.00',0),
(4,'2016-06-05','0.00',0),
(5,'2016-06-06','10.00',0),
(6,'2016-06-07','0.00',0);

/*Table structure for table `listinoprodotti` */

DROP TABLE IF EXISTS `listinoprodotti`;

CREATE TABLE `listinoprodotti` (
  `idProdotto` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'chiave primaria tabella',
  `idCategoriaProdotto` int(10) unsigned NOT NULL,
  `nomeProdotto` tinytext NOT NULL COMMENT 'Nome identificativo del prodotto',
  `prezzoUnitario` decimal(10,2) NOT NULL,
  `descrizione` tinytext COMMENT 'Descrizione estesa del prodotto',
  PRIMARY KEY (`idProdotto`),
  KEY `idxCategoriaProdotto` (`idCategoriaProdotto`)
) ENGINE=InnoDB AUTO_INCREMENT=5002 DEFAULT CHARSET=utf8;

/*Data for the table `listinoprodotti` */

insert  into `listinoprodotti`(`idProdotto`,`idCategoriaProdotto`,`nomeProdotto`,`prezzoUnitario`,`descrizione`) values 
(101,4,'Ombra Rosso','1.00',NULL),
(102,4,'Ombra Bianco','1.00',NULL),
(201,4,'Vino Rosso Spina 1LT','4.50',NULL),
(202,4,'Vino Rosso Spina 1/2 LT','2.50',NULL),
(203,4,'Vino Bianco Spina 1LT','4.50',NULL),
(204,4,'Vino Bianco Spina 1/2 LT','2.50',NULL),
(301,4,'Prosecco Spina 1LT','5.50',NULL),
(302,4,'Prosecco Spina 1/2 LT','3.00',NULL),
(303,4,'Prosecco Spina Ombra','1.00',NULL),
(304,4,'Raboso Spina 1LT','5.50',NULL),
(305,4,'Raboso Spina 1/2 LT','3.00',NULL),
(306,4,'Raboso Spina Ombra','1.00',NULL),
(401,4,'1 Bott. prosecco','8.00',NULL),
(502,4,'Birra Spina Media','2.50',NULL),
(503,4,'Birra Spina Grande','3.50',NULL),
(601,4,'Caraffa Birra 1LT','5.50',NULL),
(702,4,'Coca Spina Media','1.50',NULL),
(703,4,'Coca Spina Grande','2.50',NULL),
(801,4,'Caraffa Coca 1LT','5.00',NULL),
(901,4,'Acqua Naturale 1LT','0.70',NULL),
(902,4,'Acqua Naturale 1/2 LT','0.40',NULL),
(903,4,'Acqua Gasata 1LT','0.70',NULL),
(904,4,'Acqua Gasata 1/2 LT','0.40',NULL),
(905,4,'Acqua Gasata Bottiglia','1.00',NULL),
(906,4,'Acqua Naturale Bottiglia','1.00',NULL),
(1001,1,'Tagliatelle Pomodoro','4.00',NULL),
(1002,1,'Tagliatelle Ragù','4.00',NULL),
(1003,1,'Tagliatelle Boscaiola','4.00',NULL),
(1004,1,'Gnocchi Pomodoro','4.00',NULL),
(1005,1,'Gnocchi Ragù','4.00',''),
(1006,1,'Gnocchi Boscaiola','4.00',NULL),
(1008,1,'Bigoli in salsa','4.00',''),
(1009,1,'Bigoli al sugo d\'anitra','4.50',''),
(1011,1,'Lasagne alla Bolognese','3.00','Pasticcio'),
(1012,1,'Pasticcio di Pesce','5.50',NULL),
(2000,2,'Costine e Salsiccia','5.50','Grigliata di 2 ossetti, una salciccia, una fetta di pancetta e 2 fette di pokenta'),
(2001,2,'Costine','5.50','Grigliata di 3 ossetti con 2 fette di polenta'),
(2002,2,'Salsicce','5.50',NULL),
(2003,2,'Bistecca di Puledro','7.00',NULL),
(2005,2,'Galletto alla Griglia','5.50',NULL),
(2006,2,'Piatto Freddo','5.00','Affettati misti'),
(2007,2,'Formaggio','3.00',NULL),
(2008,2,'Frittura Mista','7.80',NULL),
(2009,2,'Baccalà Vicentina','7.80',NULL),
(2010,2,'Baccalà Mantecato','7.80',NULL),
(2011,2,'Trippa Parmigiana','5.50',NULL),
(2012,2,'Soppressa e Formaggio','4.50',NULL),
(2013,2,'Soppressa','4.50',NULL),
(3001,3,'Fagioli con Verdure','2.00',NULL),
(3002,3,'Verdura Mista fresca','2.00',NULL),
(3003,3,'Funghi Misto Bosco','2.00',NULL),
(3004,3,'Patate Fritte','2.00',NULL),
(3005,3,'Pane','0.50',NULL),
(3006,3,'Aggiunta Polenta','0.50',NULL),
(3007,3,'Verdure Grigliate','2.00',NULL),
(5001,5,'Dolce','1.50',NULL);

/*Table structure for table `logcasse` */

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

/*Data for the table `logcasse` */

/*Table structure for table `logordini` */

DROP TABLE IF EXISTS `logordini`;

CREATE TABLE `logordini` (
  `idRigaLog` int(11) NOT NULL AUTO_INCREMENT,
  `idOrdine` int(11) NOT NULL,
  `idStatoOrdine` int(11) NOT NULL,
  `dataOra` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idRigaLog`,`idOrdine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `logordini` */

/*Table structure for table `operatori` */

DROP TABLE IF EXISTS `operatori`;

CREATE TABLE `operatori` (
  `idOperatore` int(11) NOT NULL,
  `operatore` char(10) NOT NULL,
  PRIMARY KEY (`idOperatore`),
  UNIQUE KEY `operatore_UNIQUE` (`operatore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `operatori` */

insert  into `operatori`(`idOperatore`,`operatore`) values 
(3,'Alberto'),
(2,'Alessio'),
(10,'Andrea'),
(5,'Enzo'),
(8,'Fabio'),
(6,'Lorena'),
(11,'Lorenzo'),
(7,'Marco'),
(1,'Marisa'),
(12,'Mauro'),
(9,'Nicoletta'),
(4,'Stefano');

/*Table structure for table `operazionicassa` */

DROP TABLE IF EXISTS `operazionicassa`;

CREATE TABLE `operazionicassa` (
  `idOperazione` char(3) NOT NULL,
  `descrizione` varchar(45) NOT NULL,
  PRIMARY KEY (`idOperazione`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `operazionicassa` */

insert  into `operazionicassa`(`idOperazione`,`descrizione`) values 
('FND','Fondo Cassa'),
('VER','Versamento');

/*Table structure for table `prodottigiornaliera` */

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

/*Data for the table `prodottigiornaliera` */

insert  into `prodottigiornaliera`(`idGiorno`,`idProdotto`,`disponibilita`,`quantitaVenduta`,`quantitaWarning`,`scontoGiorno`,`sospensione`,`motivoSospensione`) values 
(1,101,999,0,0,'0.00',0,NULL),
(1,102,999,0,0,'0.00',0,NULL),
(1,201,999,0,0,'0.00',0,NULL),
(1,202,999,0,0,'0.00',0,NULL),
(1,203,999,0,0,'0.00',0,NULL),
(1,204,999,0,0,'0.00',0,NULL),
(1,301,999,0,0,'0.00',0,NULL),
(1,302,999,0,0,'0.00',0,NULL),
(1,303,999,0,0,'0.00',0,NULL),
(1,304,999,0,0,'0.00',0,NULL),
(1,305,999,0,0,'0.00',0,NULL),
(1,306,999,0,0,'0.00',0,NULL),
(1,401,999,0,0,'0.00',0,NULL),
(1,502,999,0,0,'0.00',0,NULL),
(1,503,999,0,0,'0.00',0,NULL),
(1,601,999,0,0,'0.00',0,NULL),
(1,702,999,0,0,'0.00',0,NULL),
(1,703,999,0,0,'0.00',0,NULL),
(1,801,999,0,0,'0.00',0,NULL),
(1,901,999,0,0,'0.00',0,NULL),
(1,902,999,0,0,'0.00',0,NULL),
(1,903,999,0,0,'0.00',0,NULL),
(1,904,999,0,0,'0.00',0,NULL),
(1,905,999,0,0,'0.00',0,NULL),
(1,906,999,0,0,'0.00',0,NULL),
(1,1001,999,0,0,'0.00',0,NULL),
(1,1002,999,0,0,'0.00',0,NULL),
(1,1003,999,0,0,'0.00',0,NULL),
(1,1004,999,0,0,'0.00',0,NULL),
(1,1005,999,0,0,'0.00',0,NULL),
(1,1006,999,0,0,'0.00',0,NULL),
(1,1008,999,0,0,'0.00',0,NULL),
(1,1009,999,0,0,'0.00',0,NULL),
(1,1011,999,0,0,'0.00',0,NULL),
(1,1012,999,0,0,'0.00',0,NULL),
(1,2000,999,0,0,'0.00',0,NULL),
(1,2001,999,0,0,'0.00',0,NULL),
(1,2002,999,0,0,'0.00',0,NULL),
(1,2003,999,0,0,'0.00',0,NULL),
(1,2005,999,0,0,'0.00',0,NULL),
(1,2006,999,0,0,'0.00',0,NULL),
(1,2007,999,0,0,'0.00',0,NULL),
(1,2008,999,0,0,'0.00',0,NULL),
(1,2009,999,0,0,'0.00',0,NULL),
(1,2010,999,0,0,'0.00',0,NULL),
(1,2011,999,0,0,'0.00',0,NULL),
(1,2012,999,0,0,'0.00',0,NULL),
(1,2013,999,0,0,'0.00',0,NULL),
(1,3001,999,0,0,'0.00',0,NULL),
(1,3002,999,0,0,'0.00',0,NULL),
(1,3003,999,0,0,'0.00',0,NULL),
(1,3004,999,0,0,'0.00',0,NULL),
(1,3005,999,0,0,'0.00',0,NULL),
(1,3006,999,0,0,'0.00',0,NULL),
(1,3007,999,0,0,'0.00',0,NULL),
(1,5001,999,0,0,'0.00',0,NULL),
(2,101,999,0,0,'0.00',0,NULL),
(2,102,999,0,0,'0.00',0,NULL),
(2,201,999,0,0,'0.00',0,NULL),
(2,202,999,0,0,'0.00',0,NULL),
(2,203,999,0,0,'0.00',0,NULL),
(2,204,999,0,0,'0.00',0,NULL),
(2,301,999,0,0,'0.00',0,NULL),
(2,302,999,0,0,'0.00',0,NULL),
(2,303,999,0,0,'0.00',0,NULL),
(2,304,999,0,0,'0.00',0,NULL),
(2,305,999,0,0,'0.00',0,NULL),
(2,306,999,0,0,'0.00',0,NULL),
(2,401,999,0,0,'0.00',0,NULL),
(2,502,999,0,0,'0.00',0,NULL),
(2,503,999,0,0,'0.00',0,NULL),
(2,601,999,0,0,'0.00',0,NULL),
(2,702,999,0,0,'0.00',0,NULL),
(2,703,999,0,0,'0.00',0,NULL),
(2,801,999,0,0,'0.00',0,NULL),
(2,901,999,0,0,'0.00',0,NULL),
(2,902,999,0,0,'0.00',0,NULL),
(2,903,999,0,0,'0.00',0,NULL),
(2,904,999,0,0,'0.00',0,NULL),
(2,905,999,0,0,'0.00',0,NULL),
(2,906,999,0,0,'0.00',0,NULL),
(2,1001,999,0,0,'0.00',0,NULL),
(2,1002,999,0,0,'0.00',0,NULL),
(2,1003,999,0,0,'0.00',0,NULL),
(2,1004,999,0,0,'0.00',0,NULL),
(2,1005,999,0,0,'0.00',0,NULL),
(2,1006,999,0,0,'0.00',0,NULL),
(2,1008,999,0,0,'0.00',0,NULL),
(2,1009,999,0,0,'0.00',0,NULL),
(2,1011,999,0,0,'0.00',0,NULL),
(2,1012,999,0,0,'0.00',0,NULL),
(2,2000,999,0,0,'0.00',0,NULL),
(2,2001,999,0,0,'0.00',0,NULL),
(2,2002,999,0,0,'0.00',0,NULL),
(2,2003,999,0,0,'0.00',0,NULL),
(2,2005,999,0,0,'0.00',0,NULL),
(2,2006,999,0,0,'0.00',0,NULL),
(2,2007,999,0,0,'0.00',0,NULL),
(2,2008,999,0,0,'0.00',0,NULL),
(2,2009,999,0,0,'0.00',0,NULL),
(2,2010,999,0,0,'0.00',0,NULL),
(2,2011,999,0,0,'0.00',0,NULL),
(2,2012,999,0,0,'0.00',0,NULL),
(2,2013,999,0,0,'0.00',0,NULL),
(2,3001,999,0,0,'0.00',0,NULL),
(2,3002,999,0,0,'0.00',0,NULL),
(2,3003,999,0,0,'0.00',0,NULL),
(2,3004,999,0,0,'0.00',0,NULL),
(2,3005,999,0,0,'0.00',0,NULL),
(2,3006,999,0,0,'0.00',0,NULL),
(2,3007,999,0,0,'0.00',0,NULL),
(2,5001,999,0,0,'0.00',0,NULL),
(3,101,999,0,0,'0.00',0,NULL),
(3,102,999,0,0,'0.00',0,NULL),
(3,201,999,0,0,'0.00',0,NULL),
(3,202,999,0,0,'0.00',0,NULL),
(3,203,999,0,0,'0.00',0,NULL),
(3,204,999,0,0,'0.00',0,NULL),
(3,301,999,0,0,'0.00',0,NULL),
(3,302,999,0,0,'0.00',0,NULL),
(3,303,999,0,0,'0.00',0,NULL),
(3,304,999,0,0,'0.00',0,NULL),
(3,305,999,0,0,'0.00',0,NULL),
(3,306,999,0,0,'0.00',0,NULL),
(3,401,999,0,0,'0.00',0,NULL),
(3,502,999,0,0,'0.00',0,NULL),
(3,503,999,0,0,'0.00',0,NULL),
(3,601,999,0,0,'0.00',0,NULL),
(3,702,999,0,0,'0.00',0,NULL),
(3,703,999,0,0,'0.00',0,NULL),
(3,801,999,0,0,'0.00',0,NULL),
(3,901,999,0,0,'0.00',0,NULL),
(3,902,999,0,0,'0.00',0,NULL),
(3,903,999,0,0,'0.00',0,NULL),
(3,904,999,0,0,'0.00',0,NULL),
(3,905,999,0,0,'0.00',0,NULL),
(3,906,999,0,0,'0.00',0,NULL),
(3,1001,999,0,0,'0.00',0,NULL),
(3,1002,999,0,0,'0.00',0,NULL),
(3,1003,999,0,0,'0.00',0,NULL),
(3,1004,999,0,0,'0.00',0,NULL),
(3,1005,999,0,0,'0.00',0,NULL),
(3,1006,999,0,0,'0.00',0,NULL),
(3,1008,999,0,0,'0.00',0,NULL),
(3,1009,999,0,0,'0.00',0,NULL),
(3,1011,999,0,0,'0.00',0,NULL),
(3,1012,999,0,0,'0.00',0,NULL),
(3,2000,999,0,0,'0.00',0,NULL),
(3,2001,999,0,0,'0.00',0,NULL),
(3,2002,999,0,0,'0.00',0,NULL),
(3,2003,999,0,0,'0.00',0,NULL),
(3,2005,999,0,0,'0.00',0,NULL),
(3,2006,999,0,0,'0.00',0,NULL),
(3,2007,999,0,0,'0.00',0,NULL),
(3,2008,999,0,0,'0.00',0,NULL),
(3,2009,999,0,0,'0.00',0,NULL),
(3,2010,999,0,0,'0.00',0,NULL),
(3,2011,999,0,0,'0.00',0,NULL),
(3,2012,999,0,0,'0.00',0,NULL),
(3,2013,999,0,0,'0.00',0,NULL),
(3,3001,999,0,0,'0.00',0,NULL),
(3,3002,999,0,0,'0.00',0,NULL),
(3,3003,999,0,0,'0.00',0,NULL),
(3,3004,999,0,0,'0.00',0,NULL),
(3,3005,999,0,0,'0.00',0,NULL),
(3,3006,999,0,0,'0.00',0,NULL),
(3,3007,999,0,0,'0.00',0,NULL),
(3,5001,999,0,0,'0.00',0,NULL),
(4,101,999,0,0,'0.00',0,NULL),
(4,102,999,0,0,'0.00',0,NULL),
(4,201,999,0,0,'0.00',0,NULL),
(4,202,999,0,0,'0.00',0,NULL),
(4,203,999,0,0,'0.00',0,NULL),
(4,204,999,0,0,'0.00',0,NULL),
(4,301,999,0,0,'0.00',0,NULL),
(4,302,999,0,0,'0.00',0,NULL),
(4,303,999,0,0,'0.00',0,NULL),
(4,304,999,0,0,'0.00',0,NULL),
(4,305,999,0,0,'0.00',0,NULL),
(4,306,999,0,0,'0.00',0,NULL),
(4,401,999,0,0,'0.00',0,NULL),
(4,502,999,0,0,'0.00',0,NULL),
(4,503,999,0,0,'0.00',0,NULL),
(4,601,999,0,0,'0.00',0,NULL),
(4,702,999,0,0,'0.00',0,NULL),
(4,703,999,0,0,'0.00',0,NULL),
(4,801,999,0,0,'0.00',0,NULL),
(4,901,999,0,0,'0.00',0,NULL),
(4,902,999,0,0,'0.00',0,NULL),
(4,903,999,0,0,'0.00',0,NULL),
(4,904,999,0,0,'0.00',0,NULL),
(4,905,999,0,0,'0.00',0,NULL),
(4,906,999,0,0,'0.00',0,NULL),
(4,1001,999,0,0,'0.00',0,NULL),
(4,1002,999,0,0,'0.00',0,NULL),
(4,1003,999,0,0,'0.00',0,NULL),
(4,1004,999,0,0,'0.00',0,NULL),
(4,1005,999,0,0,'0.00',0,NULL),
(4,1006,999,0,0,'0.00',0,NULL),
(4,1008,999,0,0,'0.00',0,NULL),
(4,1009,999,0,0,'0.00',0,NULL),
(4,1011,999,0,0,'0.00',0,NULL),
(4,1012,999,0,0,'0.00',0,NULL),
(4,2000,999,0,0,'0.00',0,NULL),
(4,2001,999,0,0,'0.00',0,NULL),
(4,2002,999,0,0,'0.00',0,NULL),
(4,2003,999,0,0,'0.00',0,NULL),
(4,2005,999,0,0,'0.00',0,NULL),
(4,2006,999,0,0,'0.00',0,NULL),
(4,2007,999,0,0,'0.00',0,NULL),
(4,2008,999,0,0,'0.00',0,NULL),
(4,2009,999,0,0,'0.00',0,NULL),
(4,2010,999,0,0,'0.00',0,NULL),
(4,2011,999,0,0,'0.00',0,NULL),
(4,2012,999,0,0,'0.00',0,NULL),
(4,2013,999,0,0,'0.00',0,NULL),
(4,3001,999,0,0,'0.00',0,NULL),
(4,3002,999,0,0,'0.00',0,NULL),
(4,3003,999,0,0,'0.00',0,NULL),
(4,3004,999,0,0,'0.00',0,NULL),
(4,3005,999,0,0,'0.00',0,NULL),
(4,3006,999,0,0,'0.00',0,NULL),
(4,3007,999,0,0,'0.00',0,NULL),
(4,5001,999,0,0,'0.00',0,NULL),
(5,101,999,0,0,'0.00',0,NULL),
(5,102,999,0,0,'0.00',0,NULL),
(5,201,999,0,0,'0.00',0,NULL),
(5,202,999,0,0,'0.00',0,NULL),
(5,203,999,0,0,'0.00',0,NULL),
(5,204,999,0,0,'0.00',0,NULL),
(5,301,999,0,0,'0.00',0,NULL),
(5,302,999,0,0,'0.00',0,NULL),
(5,303,999,0,0,'0.00',0,NULL),
(5,304,999,0,0,'0.00',0,NULL),
(5,305,999,0,0,'0.00',0,NULL),
(5,306,999,0,0,'0.00',0,NULL),
(5,401,999,0,0,'0.00',0,NULL),
(5,502,999,0,0,'0.00',0,NULL),
(5,503,999,0,0,'0.00',0,NULL),
(5,601,999,0,0,'0.00',0,NULL),
(5,702,999,0,0,'0.00',0,NULL),
(5,703,999,0,0,'0.00',0,NULL),
(5,801,999,0,0,'0.00',0,NULL),
(5,901,999,0,0,'0.00',0,NULL),
(5,902,999,0,0,'0.00',0,NULL),
(5,903,999,0,0,'0.00',0,NULL),
(5,904,999,0,0,'0.00',0,NULL),
(5,905,999,0,0,'0.00',0,NULL),
(5,906,999,0,0,'0.00',0,NULL),
(5,1001,999,0,0,'0.00',0,NULL),
(5,1002,999,0,0,'0.00',0,NULL),
(5,1003,999,0,0,'0.00',0,NULL),
(5,1004,999,0,0,'0.00',0,NULL),
(5,1005,999,0,0,'0.00',0,NULL),
(5,1006,999,0,0,'0.00',0,NULL),
(5,1008,999,0,0,'0.00',0,NULL),
(5,1009,999,0,0,'0.00',0,NULL),
(5,1011,999,0,0,'0.00',0,NULL),
(5,1012,999,0,0,'0.00',0,NULL),
(5,2000,999,0,0,'0.00',0,NULL),
(5,2001,999,0,0,'0.00',0,NULL),
(5,2002,999,0,0,'0.00',0,NULL),
(5,2003,999,0,0,'0.00',0,NULL),
(5,2005,999,0,0,'0.00',0,NULL),
(5,2006,999,0,0,'0.00',0,NULL),
(5,2007,999,0,0,'0.00',0,NULL),
(5,2008,999,0,0,'0.00',0,NULL),
(5,2009,999,0,0,'0.00',0,NULL),
(5,2010,999,0,0,'0.00',0,NULL),
(5,2011,999,0,0,'0.00',0,NULL),
(5,2012,999,0,0,'0.00',0,NULL),
(5,2013,999,0,0,'0.00',0,NULL),
(5,3001,999,0,0,'0.00',0,NULL),
(5,3002,999,0,0,'0.00',0,NULL),
(5,3003,999,0,0,'0.00',0,NULL),
(5,3004,999,0,0,'0.00',0,NULL),
(5,3005,999,0,0,'0.00',0,NULL),
(5,3006,999,0,0,'0.00',0,NULL),
(5,3007,999,0,0,'0.00',0,NULL),
(5,5001,999,0,0,'0.00',0,NULL),
(6,101,999,0,0,'0.00',0,NULL),
(6,102,999,0,0,'0.00',0,NULL),
(6,201,999,0,0,'0.00',0,NULL),
(6,202,999,0,0,'0.00',0,NULL),
(6,203,999,0,0,'0.00',0,NULL),
(6,204,999,0,0,'0.00',0,NULL),
(6,301,999,0,0,'0.00',0,NULL),
(6,302,999,0,0,'0.00',0,NULL),
(6,303,999,0,0,'0.00',0,NULL),
(6,304,999,0,0,'0.00',0,NULL),
(6,305,999,0,0,'0.00',0,NULL),
(6,306,999,0,0,'0.00',0,NULL),
(6,401,999,0,0,'0.00',0,NULL),
(6,502,999,0,0,'0.00',0,NULL),
(6,503,999,0,0,'0.00',0,NULL),
(6,601,999,0,0,'0.00',0,NULL),
(6,702,999,0,0,'0.00',0,NULL),
(6,703,999,0,0,'0.00',0,NULL),
(6,801,999,0,0,'0.00',0,NULL),
(6,901,999,0,0,'0.00',0,NULL),
(6,902,999,0,0,'0.00',0,NULL),
(6,903,999,0,0,'0.00',0,NULL),
(6,904,999,0,0,'0.00',0,NULL),
(6,905,999,0,0,'0.00',0,NULL),
(6,906,999,0,0,'0.00',0,NULL),
(6,1001,999,0,0,'0.00',0,NULL),
(6,1002,999,0,0,'0.00',0,NULL),
(6,1003,999,0,0,'0.00',0,NULL),
(6,1004,999,0,0,'0.00',0,NULL),
(6,1005,999,0,0,'0.00',0,NULL),
(6,1006,999,0,0,'0.00',0,NULL),
(6,1008,999,0,0,'0.00',0,NULL),
(6,1009,999,0,0,'0.00',0,NULL),
(6,1011,999,0,0,'0.00',0,NULL),
(6,1012,999,0,0,'0.00',0,NULL),
(6,2000,999,0,0,'0.00',0,NULL),
(6,2001,999,0,0,'0.00',0,NULL),
(6,2002,999,0,0,'0.00',0,NULL),
(6,2003,999,0,0,'0.00',0,NULL),
(6,2005,999,0,0,'0.00',0,NULL),
(6,2006,999,0,0,'0.00',0,NULL),
(6,2007,999,0,0,'0.00',0,NULL),
(6,2008,999,0,0,'0.00',0,NULL),
(6,2009,999,0,0,'0.00',0,NULL),
(6,2010,999,0,0,'0.00',0,NULL),
(6,2011,999,0,0,'0.00',0,NULL),
(6,2012,999,0,0,'0.00',0,NULL),
(6,2013,999,0,0,'0.00',0,NULL),
(6,3001,999,0,0,'0.00',0,NULL),
(6,3002,999,0,0,'0.00',0,NULL),
(6,3003,999,0,0,'0.00',0,NULL),
(6,3004,999,0,0,'0.00',0,NULL),
(6,3005,999,0,0,'0.00',0,NULL),
(6,3006,999,0,0,'0.00',0,NULL),
(6,3007,999,0,0,'0.00',0,NULL),
(6,5001,999,0,0,'0.00',0,NULL);

/*Table structure for table `righecommesse` */

DROP TABLE IF EXISTS `righecommesse`;

CREATE TABLE `righecommesse` (
  `idRiga` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Chiave primaria',
  `idCommessa` int(11) NOT NULL COMMENT 'Chiave primaria',
  `idProdotto` int(11) NOT NULL COMMENT 'Identifica il prodotto venduto',
  `quantita` int(3) NOT NULL DEFAULT '1',
  `varianti` text COMMENT 'Varianti applicate alla riga',
  `prezzoListino` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Prezzo listino applicato',
  `prezzoNetto` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Prezzo netto',
  PRIMARY KEY (`idRiga`),
  KEY `idx_righecommesse_idProdotto` (`idProdotto`),
  KEY `idxCommessa` (`idCommessa`)
) ENGINE=InnoDB AUTO_INCREMENT=2092 DEFAULT CHARSET=utf8;

/*Data for the table `righecommesse` */

/*Table structure for table `sconti` */

DROP TABLE IF EXISTS `sconti`;

CREATE TABLE `sconti` (
  `idSconto` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Chiave primaria',
  `descrizione` text NOT NULL COMMENT 'Descrizione dello sconto',
  `sconto` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT 'Sconto da applicare',
  PRIMARY KEY (`idSconto`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `sconti` */

insert  into `sconti`(`idSconto`,`descrizione`,`sconto`) values 
(1,'Sconto operatori sagra','100.00'),
(2,'Omaggio','100.00');

/*Table structure for table `statiordine` */

DROP TABLE IF EXISTS `statiordine`;

CREATE TABLE `statiordine` (
  `idStatoOrdine` int(11) NOT NULL AUTO_INCREMENT,
  `ordineSequenziale` int(11) NOT NULL,
  `descrizione` tinytext NOT NULL,
  PRIMARY KEY (`idStatoOrdine`),
  UNIQUE KEY `idx_statiordine_ordineSequenziale` (`ordineSequenziale`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `statiordine` */

insert  into `statiordine`(`idStatoOrdine`,`ordineSequenziale`,`descrizione`) values 
(1,1,'Emissione commessa'),
(2,10,'Preso in carico'),
(3,20,'Consegnato');

/*Table structure for table `variantiprodotti` */

DROP TABLE IF EXISTS `variantiprodotti`;

CREATE TABLE `variantiprodotti` (
  `idVariante` int(11) NOT NULL,
  `variante` text NOT NULL,
  `idCategoriaProdotto` int(11) NOT NULL,
  PRIMARY KEY (`idVariante`),
  KEY `idxCategoriaProdotto` (`idCategoriaProdotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `variantiprodotti` */

insert  into `variantiprodotti`(`idVariante`,`variante`,`idCategoriaProdotto`) values 
(1,'Senza Formaggio',1),
(2,'Pomodoro',1),
(3,'In bianco',1),
(4,'Arrabbiata',1),
(5,'Ragù',1),
(6,'No cipolle',3),
(7,'No peperoni',3),
(8,'Ragù anitra',1),
(9,'In salsa',1),
(10,'Con pane',1),
(11,'No melanzane',1),
(12,'Senza Sale',1),
(13,'Ben cotto',1);

/*Table structure for table `vistalistinoreale` */

DROP TABLE IF EXISTS `vistalistinoreale`;

/*!50001 DROP VIEW IF EXISTS `vistalistinoreale` */;
/*!50001 DROP TABLE IF EXISTS `vistalistinoreale` */;

/*!50001 CREATE TABLE  `vistalistinoreale`(
 `idProdotto` int(10) unsigned ,
 `idCategoriaProdotto` int(10) unsigned ,
 `idGiorno` int(10) ,
 `nomeProdotto` tinytext ,
 `prezzoUnitario` decimal(10,2) ,
 `descrizione` tinytext ,
 `disponibilita` int(10) ,
 `quantitaVenduta` int(10) ,
 `quantitaWarning` int(10) 
)*/;

/*View structure for view vistalistinoreale */

/*!50001 DROP TABLE IF EXISTS `vistalistinoreale` */;
/*!50001 DROP VIEW IF EXISTS `vistalistinoreale` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vistalistinoreale` AS select `l`.`idProdotto` AS `idProdotto`,`l`.`idCategoriaProdotto` AS `idCategoriaProdotto`,`g`.`idGiorno` AS `idGiorno`,`l`.`nomeProdotto` AS `nomeProdotto`,`l`.`prezzoUnitario` AS `prezzoUnitario`,`l`.`descrizione` AS `descrizione`,`g`.`disponibilita` AS `disponibilita`,`g`.`quantitaVenduta` AS `quantitaVenduta`,`g`.`quantitaWarning` AS `quantitaWarning` from ((`listinoprodotti` `l` join `prodottigiornaliera` `g` on((`l`.`idProdotto` = `g`.`idProdotto`))) join `categorieprodotti` `cp` on((`l`.`idCategoriaProdotto` = `cp`.`idCategoriaProdotto`))) where (`g`.`sospensione` = 0) order by `cp`.`ordineSequenziale`,`l`.`nomeProdotto` */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
