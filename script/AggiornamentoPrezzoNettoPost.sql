UPDATE
	commesse t, 
	righecommesse r
SET
	r.PrezzoNetto = r.PrezzoListino - r.prezzoListino / t.totale * t.ScontoApplicato 
WHERE
	t.idCommessa = r.idCommessa
	AND t.scontoApplicato <> 0;
COMMIT;

UPDATE
	commesse t, 
	righecommesse r
SET
	r.PrezzoNetto = r.prezzoListino
WHERE
	t.idCommessa = r.idCommessa
	AND t.scontoApplicato = 0;

COMMIT;