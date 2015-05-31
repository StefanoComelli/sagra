package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import java.util.List;
import model.StatiOrdine;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
     *
     * @param dbConnection
     */
    public StatiOrdineManager(DbConnection dbConnection) {
        super(dbConnection, StatiOrdine.class);
    }

    /**
     *
     * @return
     */
    public List<StatiOrdine> getElencoStati() {
        Criteria cr = getDbConnection().getSession().createCriteria(StatiOrdine.class);
        cr.addOrder(Order.asc("ordineSequenziale"));

        List<StatiOrdine> pojos = null;
        try {
            pojos = cr.list();
        } catch (HibernateException e) {
            LOGGER.error(e);
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
        Criteria cr = getDbConnection().getSession().createCriteria(StatiOrdine.class);
        if (successivo) {
            cr.add(Restrictions.gt("ordineSequenziale", statoOra.getOrdineSequenziale()));
            cr.addOrder(Order.asc("ordineSequenziale"));
        } else {
            cr.add(Restrictions.lt("ordineSequenziale", statoOra.getOrdineSequenziale()));
            cr.addOrder(Order.desc("ordineSequenziale"));
        }

        try {
            statoOrdine = (StatiOrdine) cr.list().get(0);
        } catch (HibernateException e) {
            LOGGER.error(e);
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
        Criteria cr = getDbConnection().getSession().createCriteria(StatiOrdine.class);
        cr.addOrder(Order.asc("ordineSequenziale"));

        try {
            statoOrdine = (StatiOrdine) cr.list().get(0);
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return statoOrdine;
    }

}
