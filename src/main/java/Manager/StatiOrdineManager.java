package Manager;

import abstr.AbstractManager;
import static abstr.AbstractManager.getFactory;
import java.util.List;
import model.StatiOrdine;
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
public class StatiOrdineManager extends AbstractManager<StatiOrdine, Integer> {

    private static final Logger LOGGER = Logger.getLogger(StatiOrdine.class);

    /**
     * CategorieProdottiManager
     */
    public StatiOrdineManager() {
        super(StatiOrdine.class);
    }

    /**
     *
     * @return
     */
    public List<StatiOrdine> getElencoStati() {
        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(StatiOrdine.class);
        cr.addOrder(Order.asc("ordineSequenziale"));
        Transaction tx = null;
        List<StatiOrdine> pojos = null;
        try {
            tx = session.beginTransaction();
            pojos = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }
        return pojos;
    }

    /**
     *
     * @param statoOra
     * @param successivo
     * @return
     */
    private StatiOrdine getStato(StatiOrdine statoOra, boolean successivo) {
        StatiOrdine statoOrdine = null;
        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(StatiOrdine.class);
        if (successivo) {
            cr.add(Restrictions.gt("ordineSequenziale", statoOra.getOrdineSequenziale()));
            cr.addOrder(Order.asc("ordineSequenziale"));
        } else {
            cr.add(Restrictions.lt("ordineSequenziale", statoOra.getOrdineSequenziale()));
            cr.addOrder(Order.desc("ordineSequenziale"));
        }

        Transaction tx = null;
        List<StatiOrdine> pojos = null;
        try {
            tx = session.beginTransaction();
            statoOrdine = (StatiOrdine) cr.list().get(0);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }
        return statoOrdine;
    }

    /**
     *
     * @param statoOra
     * @return
     */
    public StatiOrdine getNext(StatiOrdine statoOra) {
        return getStato(statoOra, true);
    }

    /**
     *
     * @param statoOra
     * @return
     */
    public StatiOrdine getPrev(StatiOrdine statoOra) {
        return getStato(statoOra, false);
    }

    /**
     *
     * @return
     */
    public StatiOrdine getDefault() {
        StatiOrdine statoOrdine = null;
        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(StatiOrdine.class);
        cr.addOrder(Order.asc("ordineSequenziale"));

        Transaction tx = null;
        List<StatiOrdine> pojos = null;
        try {
            tx = session.beginTransaction();
            statoOrdine = (StatiOrdine) cr.list().get(0);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }
        return statoOrdine;
    }

}
