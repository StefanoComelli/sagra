package Manager;

import abstr.AbstractManager;
import model.ListinoProdotti;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class ListinoProdottiManager extends AbstractManager<ListinoProdotti, Integer> {

    private static final Logger LOGGER = Logger.getLogger(ListinoProdottiManager.class);

    /**
     * CategorieProdottiManager
     */
    public ListinoProdottiManager() {
        super(ListinoProdotti.class);
    }

}
