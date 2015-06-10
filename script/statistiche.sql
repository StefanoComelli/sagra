-- * Statistiche incasso per giorno e cassa
SELECT 
	g.data, 
	c.idCassa,
	SUM(c.totale), 
	SUM(c.scontoApplicato),
	SUM(c.totalePagato)
FROM 
	sagra.commesse c,
	sagra.giorni g
WHERE 
	c.idGiorno = g.idGiorno
GROUP BY 
	c.idGiorno,
	c.idCassa
ORDER BY 
	c.idGiorno, 
	c.idCassa


-- * Statistiche incasso per giorno
SELECT 
	g.data,
	SUM(c.totale), 
	SUM(c.scontoApplicato),
	SUM(c.totalePagato)
FROM 
	sagra.commesse c,
	sagra.giorni g
WHERE 
	c.idGiorno = g.idGiorno
GROUP BY 
	c.idGiorno
ORDER BY 
	c.idGiorno

-- * Statistiche incasso bevande  per giorno e cassa
SELECT 
	g.data, 
	c.idCassa, 
	SUM(r.quantita * r.prezzoListino),
	SUM(r.quantita * r.prezzoListino) / (c.totale /c.totalePagato)
FROM 
	sagra.commesse c, 
	sagra.giorni g, 
	righecommesse r
WHERE
	c.idGiorno = g.idGiorno
	AND c.idCommessa = r.idCommessa
	AND r.idProdotto < 1000
        and c.totalePagato > 0
GROUP BY 
	c.idGiorno, 
	c.idCassa
ORDER BY 
	c.idGiorno, 
	c.idCassa
	
-- * Statistiche incasso bevande  per giorno
SELECT 
	g.data, 
	SUM(r.quantita * r.prezzoListino),
	SUM(r.quantita * r.prezzoListino) / (c.totale /c.totalePagato)
FROM 
	sagra.commesse c, 
	sagra.giorni g, 
	righecommesse r
WHERE
	c.idGiorno = g.idGiorno
	AND c.idCommessa = r.idCommessa
	AND r.idProdotto < 1000
        and c.totalePagato > 0
GROUP BY 
	c.idGiorno
ORDER BY 
	c.idGiorno
	
-- * Statistiche incasso cucina  per giorno e cassa
SELECT 
	g.data, 
	c.idCassa, 
	SUM(r.quantita * r.prezzoListino),
	SUM(r.quantita * r.prezzoListino) / (c.totale /c.totalePagato)
FROM 
	sagra.commesse c, 
	sagra.giorni g, 
	righecommesse r
WHERE
	c.idGiorno = g.idGiorno
	AND c.idCommessa = r.idCommessa
	AND r.idProdotto >= 1000
        and c.totalePagato > 0
GROUP BY 
	c.idGiorno, 
	c.idCassa
ORDER BY 
	c.idGiorno, 
	c.idCassa
	
-- * Statistiche incasso cucina  per giorno
SELECT 
	g.data, 
	SUM(r.quantita * r.prezzoListino),
	SUM(r.quantita * r.prezzoListino) / (c.totale / c.totalePagato)
FROM 
	sagra.commesse c, 
	sagra.giorni g, 
	righecommesse r
WHERE
	c.idGiorno = g.idGiorno
	AND c.idCommessa = r.idCommessa
	AND r.idProdotto >= 1000
        and c.totalePagato > 0
GROUP BY 
	c.idGiorno
ORDER BY 
	c.idGiorno
	

-- * coperti per giorno
SELECT 
	g.data, 
	SUM(c.coperti)
FROM 
	sagra.commesse c, 
	sagra.giorni g 
WHERE
	c.idGiorno = g.idGiorno
GROUP BY 
	c.idGiorno
ORDER BY 
	c.idGiorno
	


-- * coperti per giorno e cassa
SELECT 
	g.data, 
	c.idCassa, 
	SUM(c.coperti)
FROM 
	sagra.commesse c, 
	sagra.giorni g 
WHERE
	c.idGiorno = g.idGiorno
GROUP BY 
	c.idGiorno, 
	c.idCassa
ORDER BY 
	c.idGiorno, 
	c.idCassa
	
