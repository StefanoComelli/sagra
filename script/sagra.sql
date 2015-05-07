-- phpMyAdmin SQL Dump
-- version 4.3.12
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mag 04, 2015 alle 13:27
-- Versione del server: 5.6.24-log
-- PHP Version: 5.5.24-pl0-gentoo

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sagra`
--
CREATE DATABASE IF NOT EXISTS `sagra` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `sagra`;

-- --------------------------------------------------------

--
-- Struttura della tabella `casse`
--

DROP TABLE IF EXISTS `casse`;
CREATE TABLE IF NOT EXISTS `casse` (
  `idCassa` int(10) NOT NULL,
  `descrizione` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `casse`
--

INSERT INTO `casse` (`idCassa`, `descrizione`) VALUES
(1, 'Cassa 1'),
(2, 'Cassa 2'),
(3, 'Cassa 3');

-- --------------------------------------------------------

--
-- Struttura della tabella `categorieprodotti`
--

DROP TABLE IF EXISTS `categorieprodotti`;
CREATE TABLE IF NOT EXISTS `categorieprodotti` (
  `idCategoriaProdotto` int(10) unsigned NOT NULL,
  `descrizione` text NOT NULL,
  `ordineSequenziale` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `categorieprodotti`
--

INSERT INTO `categorieprodotti` (`idCategoriaProdotto`, `descrizione`, `ordineSequenziale`) VALUES
(1, 'Primi piatti', 10),
(2, 'Secondi Piatti', 20),
(3, 'Contorni', 30),
(4, 'Bevande', 40),
(5, 'Bar', 5);

-- --------------------------------------------------------

--
-- Struttura della tabella `commesse`
--

DROP TABLE IF EXISTS `commesse`;
CREATE TABLE IF NOT EXISTS `commesse` (
  `idCommessa` int(10) unsigned NOT NULL COMMENT 'Chiave primaria',
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
  `descSconto` text
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `giorni`
--

DROP TABLE IF EXISTS `giorni`;
CREATE TABLE IF NOT EXISTS `giorni` (
  `idGiorno` int(11) NOT NULL,
  `data` date NOT NULL,
  `scontoGiorno` decimal(10,2) NOT NULL DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `giorni`
--

INSERT INTO `giorni` (`idGiorno`, `data`, `scontoGiorno`) VALUES
(1, 0x323031352d30352d3239, 15.00),
(2, 0x323031352d30352d3330, 0.00),
(3, 0x323031352d30352d3331, 0.00),
(4, 0x323031352d30362d3031, 15.00),
(5, 0x323031352d30362d3032, 0.00);

-- --------------------------------------------------------

--
-- Struttura della tabella `listinoprodotti`
--

DROP TABLE IF EXISTS `listinoprodotti`;
CREATE TABLE IF NOT EXISTS `listinoprodotti` (
  `idProdotto` int(10) unsigned NOT NULL COMMENT 'chiave primaria tabella',
  `idCategoriaProdotto` int(10) unsigned NOT NULL,
  `nomeProdotto` tinytext NOT NULL COMMENT 'Nome identificativo del prodotto',
  `descrizione` tinytext NOT NULL COMMENT 'Descrizione estesa del prodotto',
  `prezzoUnitario` decimal(10,2) NOT NULL DEFAULT '0.00'
) ENGINE=InnoDB AUTO_INCREMENT=2002 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `listinoprodotti`
--

INSERT INTO `listinoprodotti` (`idProdotto`, `idCategoriaProdotto`, `nomeProdotto`, `descrizione`, `prezzoUnitario`) VALUES
(1000, 1, 'Penne all''arrabbiata', '', 5.00),
(1001, 1, 'Bigoli in salsa', '', 4.50),
(1002, 1, 'Bigoli al sugo d''anitra', '', 5.00),
(1003, 1, 'Gnocchi al ragù', '', 4.00),
(2000, 2, 'Grigliata mista', 'Grigliata di 2 ossetti, una salciccia, una fetta di pancetta e 2 fette di pokenta', 8.30),
(2001, 2, 'Ossetti', 'Grigliata di 3 ossetti con 2 fette di polenta', 8.00);

-- --------------------------------------------------------

--
-- Struttura della tabella `logcasse`
--

DROP TABLE IF EXISTS `logcasse`;
CREATE TABLE IF NOT EXISTS `logcasse` (
  `idCassa` int(10) NOT NULL,
  `idGiorno` int(10) NOT NULL,
  `dataOra` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idOperazione` char(3) NOT NULL,
  `idOperatore` int(10) NOT NULL,
  `descrizione` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `logordini`
--

DROP TABLE IF EXISTS `logordini`;
CREATE TABLE IF NOT EXISTS `logordini` (
  `idRigaLog` int(11) NOT NULL,
  `idOrdine` int(11) NOT NULL,
  `idStatoOrdine` int(11) NOT NULL,
  `dataOra` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `operatori`
--

DROP TABLE IF EXISTS `operatori`;
CREATE TABLE IF NOT EXISTS `operatori` (
  `idOperatore` int(11) NOT NULL,
  `operatore` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `operatori`
--

INSERT INTO `operatori` (`idOperatore`, `operatore`) VALUES
(3, 'Paperino'),
(1, 'Pippo'),
(2, 'Pluto');

-- --------------------------------------------------------

--
-- Struttura della tabella `operazionicassa`
--

DROP TABLE IF EXISTS `operazionicassa`;
CREATE TABLE IF NOT EXISTS `operazionicassa` (
  `idOperazione` char(3) NOT NULL,
  `descrizione` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `operazionicassa`
--

INSERT INTO `operazionicassa` (`idOperazione`, `descrizione`) VALUES
('FND', 'Fondo Cassa'),
('VER', 'Versamento');

-- --------------------------------------------------------

--
-- Struttura della tabella `prodottigiornaliera`
--

DROP TABLE IF EXISTS `prodottigiornaliera`;
CREATE TABLE IF NOT EXISTS `prodottigiornaliera` (
  `idGiorno` int(10) NOT NULL,
  `idProdotto` int(10) unsigned NOT NULL COMMENT 'identifica il prodotto nella tabella listino',
  `disponibilita` int(10) NOT NULL DEFAULT '0' COMMENT 'quantità disponibile all''inizio dell''esercizio',
  `quantitaVenduta` int(10) NOT NULL DEFAULT '0' COMMENT 'quantità venduta fino al momento nel giorno scelto',
  `quantitaWarning` int(10) NOT NULL DEFAULT '0' COMMENT 'Quantità al di sotto della quale visualizzare un warning',
  `scontoGiorno` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Sconto da applicare nel giorno selezionato',
  `sospensione` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Sospensione momentanea della vendita',
  `motivoSospensione` text COMMENT 'Motivo della sospensione'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `prodottigiornaliera`
--

INSERT INTO `prodottigiornaliera` (`idGiorno`, `idProdotto`, `disponibilita`, `quantitaVenduta`, `quantitaWarning`, `scontoGiorno`, `sospensione`, `motivoSospensione`) VALUES
(1, 1000, 999, 0, 0, 0.00, 0, NULL),
(1, 1001, 999, 0, 0, 0.00, 0, NULL),
(1, 1002, 999, 0, 0, 0.00, 0, NULL),
(1, 1003, 999, 0, 0, 0.00, 0, NULL),
(1, 2000, 999, 0, 0, 0.00, 0, NULL),
(1, 2001, 999, 0, 0, 0.00, 0, NULL),
(2, 1000, 999, 0, 0, 0.00, 0, NULL),
(2, 1001, 999, 0, 0, 0.00, 0, NULL),
(2, 1002, 999, 0, 0, 0.00, 0, NULL),
(2, 1003, 999, 0, 0, 0.00, 0, NULL),
(2, 2000, 999, 0, 0, 0.00, 0, NULL),
(2, 2001, 999, 0, 0, 0.00, 0, NULL),
(3, 1000, 999, 0, 0, 0.00, 0, NULL),
(3, 1001, 999, 0, 0, 0.00, 0, NULL),
(3, 1002, 999, 0, 0, 0.00, 0, NULL),
(3, 1003, 999, 0, 0, 0.00, 0, NULL),
(3, 2000, 999, 0, 0, 0.00, 0, NULL),
(3, 2001, 999, 0, 0, 0.00, 0, NULL),
(4, 1000, 999, 0, 0, 0.00, 0, NULL),
(4, 1001, 999, 0, 0, 0.00, 0, NULL),
(4, 1002, 999, 0, 0, 0.00, 0, NULL),
(4, 1003, 999, 0, 0, 0.00, 0, NULL),
(4, 2000, 999, 0, 0, 0.00, 0, NULL),
(4, 2001, 999, 0, 0, 0.00, 0, NULL),
(5, 1000, 999, 0, 0, 0.00, 0, NULL),
(5, 1001, 999, 0, 0, 0.00, 0, NULL),
(5, 1002, 999, 0, 0, 0.00, 0, NULL),
(5, 1003, 999, 0, 0, 0.00, 0, NULL),
(5, 2000, 999, 0, 0, 0.00, 0, NULL),
(5, 2001, 999, 0, 0, 0.00, 0, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `righecommesse`
--

DROP TABLE IF EXISTS `righecommesse`;
CREATE TABLE IF NOT EXISTS `righecommesse` (
  `idRiga` int(11) NOT NULL COMMENT 'Chiave primaria',
  `idCommessa` int(11) NOT NULL COMMENT 'Chiave primaria',
  `idProdotto` int(11) NOT NULL COMMENT 'Identifica il prodotto venduto',
  `quantita` int(3) NOT NULL DEFAULT '1',
  `varianti` text COMMENT 'Varianti applicate alla riga',
  `prezzoListino` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Prezzo listino applicato'
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `sconti`
--

DROP TABLE IF EXISTS `sconti`;
CREATE TABLE IF NOT EXISTS `sconti` (
  `idSconto` int(10) unsigned NOT NULL COMMENT 'Chiave primaria',
  `descrizione` text NOT NULL COMMENT 'Descrizione dello sconto',
  `sconto` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT 'Sconto da applicare'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `sconti`
--

INSERT INTO `sconti` (`idSconto`, `descrizione`, `sconto`) VALUES
(1, 'Sconto operatori sagra', 100.00),
(2, 'Sconto venerdi''', 10.00),
(3, 'Sconto Lunedi''', 10.00);

-- --------------------------------------------------------

--
-- Struttura della tabella `statiordine`
--

DROP TABLE IF EXISTS `statiordine`;
CREATE TABLE IF NOT EXISTS `statiordine` (
  `idStatoOrdine` int(11) NOT NULL,
  `ordineSequenziale` int(11) NOT NULL,
  `descrizione` tinytext NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `statiordine`
--

INSERT INTO `statiordine` (`idStatoOrdine`, `ordineSequenziale`, `descrizione`) VALUES
(1, 1, 'Emissione commessa'),
(2, 10, 'Preso in carico'),
(3, 20, 'Consegnato');

-- --------------------------------------------------------

--
-- Struttura della tabella `variantiprodotti`
--

DROP TABLE IF EXISTS `variantiprodotti`;
CREATE TABLE IF NOT EXISTS `variantiprodotti` (
  `idVariante` int(11) NOT NULL,
  `variante` text NOT NULL,
  `idCategoriaProdotto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `variantiprodotti`
--

INSERT INTO `variantiprodotti` (`idVariante`, `variante`, `idCategoriaProdotto`) VALUES
(1, 'Senza Formaggio', 1),
(2, 'Pomodoro', 1),
(3, 'In bianco', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `vistalistinoreale`
--

DROP TABLE IF EXISTS `vistalistinoreale`;
CREATE TABLE IF NOT EXISTS `vistalistinoreale` (
  `idProdotto` tinyint(4) NOT NULL,
  `idCategoriaProdotto` tinyint(4) NOT NULL,
  `idGiorno` tinyint(4) NOT NULL,
  `nomeProdotto` tinyint(4) NOT NULL,
  `prezzoUnitario` tinyint(4) NOT NULL,
  `descrizione` tinyint(4) NOT NULL,
  `disponibilita` tinyint(4) NOT NULL,
  `quantitaVenduta` tinyint(4) NOT NULL,
  `quantitaWarning` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `casse`
--
ALTER TABLE `casse`
  ADD PRIMARY KEY (`idCassa`);

--
-- Indexes for table `categorieprodotti`
--
ALTER TABLE `categorieprodotti`
  ADD PRIMARY KEY (`idCategoriaProdotto`), ADD UNIQUE KEY `idxOrdineSequenziale` (`ordineSequenziale`);

--
-- Indexes for table `commesse`
--
ALTER TABLE `commesse`
  ADD PRIMARY KEY (`idCommessa`), ADD KEY `idx_commesse_idCassa` (`idCassa`), ADD KEY `idx_commesse_idOperatore` (`idOperatore`), ADD KEY `idx_commesse_giorno` (`idGiorno`), ADD KEY `idx_stato_ordine` (`idStatoOrdine`);

--
-- Indexes for table `giorni`
--
ALTER TABLE `giorni`
  ADD PRIMARY KEY (`idGiorno`), ADD UNIQUE KEY `idxData` (`data`);

--
-- Indexes for table `listinoprodotti`
--
ALTER TABLE `listinoprodotti`
  ADD PRIMARY KEY (`idProdotto`), ADD KEY `idxCategoriaProdotto` (`idCategoriaProdotto`);

--
-- Indexes for table `logcasse`
--
ALTER TABLE `logcasse`
  ADD PRIMARY KEY (`idCassa`,`idGiorno`,`dataOra`);

--
-- Indexes for table `logordini`
--
ALTER TABLE `logordini`
  ADD PRIMARY KEY (`idRigaLog`,`idOrdine`);

--
-- Indexes for table `operatori`
--
ALTER TABLE `operatori`
  ADD PRIMARY KEY (`idOperatore`), ADD UNIQUE KEY `operatore_UNIQUE` (`operatore`);

--
-- Indexes for table `operazionicassa`
--
ALTER TABLE `operazionicassa`
  ADD PRIMARY KEY (`idOperazione`);

--
-- Indexes for table `prodottigiornaliera`
--
ALTER TABLE `prodottigiornaliera`
  ADD PRIMARY KEY (`idGiorno`,`idProdotto`), ADD KEY `idxAttivi` (`sospensione`);

--
-- Indexes for table `righecommesse`
--
ALTER TABLE `righecommesse`
  ADD PRIMARY KEY (`idRiga`), ADD KEY `idx_righecommesse_idProdotto` (`idProdotto`), ADD KEY `idxCommessa` (`idCommessa`);

--
-- Indexes for table `sconti`
--
ALTER TABLE `sconti`
  ADD PRIMARY KEY (`idSconto`);

--
-- Indexes for table `statiordine`
--
ALTER TABLE `statiordine`
  ADD PRIMARY KEY (`idStatoOrdine`), ADD UNIQUE KEY `idx_statiordine_ordineSequenziale` (`ordineSequenziale`);

--
-- Indexes for table `variantiprodotti`
--
ALTER TABLE `variantiprodotti`
  ADD PRIMARY KEY (`idVariante`), ADD KEY `idxCategoriaProdotto` (`idCategoriaProdotto`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categorieprodotti`
--
ALTER TABLE `categorieprodotti`
  MODIFY `idCategoriaProdotto` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `commesse`
--
ALTER TABLE `commesse`
  MODIFY `idCommessa` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Chiave primaria',AUTO_INCREMENT=28;
--
-- AUTO_INCREMENT for table `listinoprodotti`
--
ALTER TABLE `listinoprodotti`
  MODIFY `idProdotto` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'chiave primaria tabella',AUTO_INCREMENT=2002;
--
-- AUTO_INCREMENT for table `logordini`
--
ALTER TABLE `logordini`
  MODIFY `idRigaLog` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `righecommesse`
--
ALTER TABLE `righecommesse`
  MODIFY `idRiga` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Chiave primaria',AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `sconti`
--
ALTER TABLE `sconti`
  MODIFY `idSconto` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Chiave primaria',AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `statiordine`
--
ALTER TABLE `statiordine`
  MODIFY `idStatoOrdine` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
