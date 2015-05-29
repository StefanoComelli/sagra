package Manager;

import abstr.AbstractManagerGiorno;
import database.DbConnection;
import keys.ProdottiGiornalieraKey;
import model.ProdottiGiornaliera;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class ProdottiGiornalieraManager extends AbstractManagerGiorno<ProdottiGiornaliera, ProdottiGiornalieraKey> {

    private static final Logger LOGGER = Logger.getLogger(ProdottiGiornalieraManager.class);

    /**
     * CategorieProdottiManager
     *
     * @param dbConnection
     */
    public ProdottiGiornalieraManager(DbConnection dbConnection) {
        super(dbConnection, ProdottiGiornaliera.class);
    }

}
