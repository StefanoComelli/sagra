package Manager;

import abstr.AbstractManager;
import static abstr.AbstractManager.getFactory;
import java.util.List;
import model.Operatori;
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
public class OperatoriManager extends AbstractManager<Operatori, Integer> {

    private static final Logger LOGGER = Logger.getLogger(Operatori.class);

    /**
     * CategorieProdottiManager
     */
    public OperatoriManager() {
        super(Operatori.class);
    }

    /**
     *
     * @return
     */
    public List<Operatori> getAllSorted() {
        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(Operatori.class);
        cr.addOrder(Order.asc("operatore"));
        Transaction tx = null;
        List<Operatori> operatori = null;
        try {
            tx = session.beginTransaction();
            operatori = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }
        return operatori;
    }
}
