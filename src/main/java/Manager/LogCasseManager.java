package Manager;

import abstr.AbstractManagerGiorno;
import database.DbConnection;
import keys.LogCasseKey;
import model.LogCasse;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class LogCasseManager extends AbstractManagerGiorno<LogCasse, LogCasseKey> {

    private static final Logger LOGGER = Logger.getLogger(LogCasseManager.class);

    /**
     * CategorieProdottiManager
     *
     * @param dbConnection
     */
    public LogCasseManager(DbConnection dbConnection) {
        super(dbConnection, LogCasse.class);
    }

}
