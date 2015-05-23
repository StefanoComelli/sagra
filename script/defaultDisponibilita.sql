DELETE FROM prodottigiornaliera;

INSERT INTO prodottigiornaliera (idGiorno, idProdotto)
SELECT idGiorno, idProdotto
FROM listinoprodotti, giorni;

UPDATE prodottigiornaliera
SET disponibilita =999, quantitaVenduta=0, quantitaWarning=0,
scontoGiorno=0, sospensione=0
WHERE idGiorno>0;

COMMIT;
