package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import java.util.List;
import model.CategorieProdotti;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class CategorieProdottiManager extends AbstractManager<CategorieProdotti, Integer> {

    private static final Logger LOGGER = Logger.getLogger(CategorieProdottiManager.class);

    /**
     * CategorieProdottiManager
     *
     * @param dbConnection
     */
    public CategorieProdottiManager(DbConnection dbConnection) {
        super(dbConnection, CategorieProdotti.class);
    }

    /**
     *
     * @return
     */
    public List<CategorieProdotti> getAllSorted() {
        Criteria cr = getDbConnection().getSession().createCriteria(CategorieProdotti.class);
        cr.addOrder(Order.asc("ordineSequenziale"));
        Transaction tx = null;
        List<CategorieProdotti> categorie = null;
        try {
            tx = getDbConnection().getSession().beginTransaction();
            categorie = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        }
        return categorie;
    }
}
