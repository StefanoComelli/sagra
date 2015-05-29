package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import keys.LogOrdiniKey;
import model.LogOrdini;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class LogOrdiniManager extends AbstractManager<LogOrdini, LogOrdiniKey> {

    private static final Logger LOGGER = Logger.getLogger(LogOrdiniManager.class);

    /**
     * CategorieProdottiManager
     *
     * @param dbConnection
     */
    public LogOrdiniManager(DbConnection dbConnection) {
        super(dbConnection, LogOrdini.class);
    }

}
