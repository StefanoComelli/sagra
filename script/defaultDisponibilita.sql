DELETE FROM prodottigiornaliera;

INSERT INTO prodottigiornaliera (idGiorno, idProdotto)
SELECT idGiorno, idProdotto
FROM listinoprodotti, giorni;

UPDATE prodottigiornaliera
SET disponibilita =999, quantitaVenduta=0, quantitaWarning=0,
scontoGiorno=0, sospensione=0
WHERE idGiorno>0;

delete from prodottigiornaliera
where idGiorno=1 
and idProdotto not in(1010,2000,2001,2002,2006,2012,
3002,3003,3004,3001,202,204,502,703,706);

delete from prodottigiornaliera
where idGiorno>1 
and idProdotto in(1010);

COMMIT;
