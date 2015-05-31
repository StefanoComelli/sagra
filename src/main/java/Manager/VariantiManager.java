package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import java.util.List;
import model.Varianti;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
     *
     * @param dbConnection
     */
    public VariantiManager(DbConnection dbConnection) {
        super(dbConnection, Varianti.class);
    }

    /**
     *
     * @return
     */
    public List<Varianti> getAllSorted() {
        Criteria cr = getDbConnection().getSession().createCriteria(Varianti.class);
        cr.addOrder(Order.asc("categoriaProdotto.id"));
        cr.addOrder(Order.asc("id"));

        List<Varianti> varianti = null;
        try {
            varianti = cr.list();
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return varianti;
    }
}
