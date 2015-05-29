package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import model.Casse;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class CasseManager extends AbstractManager<Casse, Integer> {

    private static final Logger LOGGER = Logger.getLogger(Casse.class);

    /**
     * CategorieProdottiManager
     *
     * @param dbConnection
     */
    public CasseManager(DbConnection dbConnection) {
        super(dbConnection, Casse.class);
    }
}
