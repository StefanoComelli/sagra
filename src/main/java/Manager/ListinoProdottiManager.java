package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import model.ListinoProdotti;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class ListinoProdottiManager extends AbstractManager<ListinoProdotti, Integer> {

    private static final Logger LOGGER = Logger.getLogger(ListinoProdottiManager.class);

    /**
     * CategorieProdottiManager
     *
     * @param dbConnection
     */
    public ListinoProdottiManager(DbConnection dbConnection) {
        super(dbConnection, ListinoProdotti.class);
    }

}
