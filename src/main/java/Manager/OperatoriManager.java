package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import java.util.List;
import model.Operatori;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
     *
     * @param dbConnection
     */
    public OperatoriManager(DbConnection dbConnection) {
        super(dbConnection, Operatori.class);
    }

    /**
     *
     * @return
     */
    public List<Operatori> getAllSorted() {
        Criteria cr = getDbConnection().getSession().createCriteria(Operatori.class);
        cr.addOrder(Order.asc("operatore"));

        List<Operatori> operatori = null;
        try {
            operatori = cr.list();
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return operatori;
    }
}
