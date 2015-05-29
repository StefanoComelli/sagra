package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import model.Varianti;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class OperazioniCassaManager extends AbstractManager<Varianti, Integer> {

    private static final Logger LOGGER = Logger.getLogger(OperazioniCassaManager.class);

    /**
     * CategorieProdottiManager
     *
     * @param dbConnection
     */
    public OperazioniCassaManager(DbConnection dbConnection) {
        super(dbConnection, Varianti.class);
    }
}
