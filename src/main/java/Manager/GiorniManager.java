package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import java.util.Date;
import java.util.List;
import model.Giorni;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
     *
     * @param dbConnection
     */
    public GiorniManager(DbConnection dbConnection) {
        super(dbConnection, Giorni.class);
    }

    /**
     *
     * @param data
     * @return
     */
    public Giorni getByDate(Date data) {

        List<Giorni> giorni = null;

        Criteria cr = getDbConnection().getSession().createCriteria(Giorni.class);
        cr.add(Restrictions.eq("data", data));
        try {
            giorni = cr.list();
        } catch (HibernateException e) {
            LOGGER.error(e);
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

        Criteria cr = getDbConnection().getSession().createCriteria(Giorni.class);
        cr.add(Restrictions.eq("flgAperto", true));
        cr.addOrder(Order.asc("data"));
        try {
            giorni = cr.list();
        } catch (HibernateException e) {
            LOGGER.error(e);
        }

        if (giorni == null || giorni.isEmpty()) {
            return null;
        } else {
            return giorni;
        }
    }
}
