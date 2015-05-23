package Manager;

import abstr.AbstractManager;
import java.util.List;
import model.CategorieProdotti;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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
     */
    public CategorieProdottiManager() {
        super(CategorieProdotti.class);
    }

    /**
     *
     * @return
     */
    public List<CategorieProdotti> getAllSorted() {
        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(CategorieProdotti.class);
        cr.addOrder(Order.asc("ordineSequenziale"));
        Transaction tx = null;
        List<CategorieProdotti> categorie = null;
        try {
            tx = session.beginTransaction();
            categorie = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }
        return categorie;
    }
}
