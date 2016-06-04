DELETE FROM righecommesse
WHERE idCommessa IN (SELECT idcommessa FROM commesse WHERE idgiorno=);
DELETE FROM commesse WHERE idgiorno=;
COMMIT;