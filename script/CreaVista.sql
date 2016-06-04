

create view `vistalistinoreale` (
	`idProdotto` int (10),
	`idCategoriaProdotto` int (10),
	`idGiorno` int (10),
	`nomeProdotto` text ,
	`prezzoUnitario` Decimal (12),
	`descrizione` text ,
	`disponibilita` int (10),
	`quantitaVenduta` int (10),
	`quantitaWarning` int (10)
); 
