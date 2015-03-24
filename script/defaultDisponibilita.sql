insert into prodottigiornaliera (idGiorno, idProdotto)
select idGiorno, idProdotto
from listinoprodotti, giorni

update prodottigiornaliera
set disponibilita =999, quantitaVenduta=0, quantitaWarning=0,
scontoGiorno=0, sospensione=0
where idGiorno>0
