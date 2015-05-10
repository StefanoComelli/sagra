package Manager;

import abstr.AbstractManager;
import java.util.Date;
import java.util.List;
import model.Giorni;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class GiorniManager extends AbstractManager<Giorni, Integer> {

    private static final Logger LOGGER = Logger.getLogger(GiorniManager.class);

    /**
     * CategorieProdottiManager
     */
    public GiorniManager() {
        super(Giorni.class);
    }

    /**
     *
     * @param data
     * @return
     */
    public Giorni getByDate(Date data) {

        List<Giorni> giorni = null;

        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(Giorni.class);
        cr.add(Restrictions.eq("data", data));
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            giorni = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }

        if (giorni == null || giorni.isEmpty()) {
            return null;
        } else {
            return giorni.get(0);
        }
    }

    /**
     *
     * @return
     */
    public List<Giorni> getOggi() {

        List<Giorni> giorni = null;

        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(Giorni.class);
        cr.add(Restrictions.eq("flgAperto", true));
        cr.addOrder(Order.asc("data"));
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            giorni = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }

        if (giorni == null || giorni.isEmpty()) {
            return null;
        } else {
            return giorni;
        }
    }
}
