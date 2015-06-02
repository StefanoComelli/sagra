package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import java.util.List;
import keys.ListinoRealeKey;
import model.ListinoReale;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class ListinoRealeManager extends AbstractManager<ListinoReale, ListinoRealeKey> {

    private static final Logger LOGGER = Logger.getLogger(ListinoRealeManager.class);

    /**
     * CategorieProdottiManager
     *
     * @param dbConnection
     */
    public ListinoRealeManager(DbConnection dbConnection) {
        super(dbConnection, ListinoReale.class);
    }

    /**
     * getByDate
     *
     * @param idGiorno
     * @return
     */
    public List<ListinoReale> getByDate(Integer idGiorno) {
        return getByDateAndCat(idGiorno, 0);

    }

    /**
     * getByDate
     *
     * @param idGiorno
     * @param idCategoriaProdotto
     * @return
     */
    public List<ListinoReale> getByDate(Integer idGiorno, Integer idCategoriaProdotto) {
        return getByDateAndCat(idGiorno, idCategoriaProdotto);
    }

    /**
     * getByDateAndCat
     *
     * @param idGiorno
     * @param idCategoriaProdotto
     * @return
     */
    private List<ListinoReale> getByDateAndCat(Integer idGiorno, Integer idCategoriaProdotto) {

        List<ListinoReale> listinoReale = null;
        Criteria cr = getDbConnection().getSession().createCriteria(ListinoReale.class);
        cr.add(Restrictions.eq("id.idGiorno", idGiorno));
        cr.addOrder(Order.asc("categoriaProdotto.id"));
        cr.addOrder(Order.asc("nomeProdotto"));

        if (idCategoriaProdotto != 0) {
            cr.add(Restrictions.eq("categoriaProdotto.id", idCategoriaProdotto));
        }

        try {
            listinoReale = cr.list();
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return listinoReale;
    }
}
