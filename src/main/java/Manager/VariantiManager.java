package Manager;

import abstr.AbstractManager;
import java.util.List;
import model.Varianti;
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
public class VariantiManager extends AbstractManager<Varianti, Integer> {

    private static final Logger LOGGER = Logger.getLogger(VariantiManager.class);

    /**
     * CategorieProdottiManager
     */
    public VariantiManager() {
        super(Varianti.class);
    }

    /**
     *
     * @return
     */
    public List<Varianti> getAllSorted() {
        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(Varianti.class);
        cr.addOrder(Order.asc("categoriaProdotto.id"));
        cr.addOrder(Order.asc("id"));
        Transaction tx = null;
        List<Varianti> varianti = null;
        try {
            tx = session.beginTransaction();
            varianti = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }
        return varianti;
    }
}
