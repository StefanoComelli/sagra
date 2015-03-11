package Manager;

import abstr.AbstractManager;
import static abstr.AbstractManager.getFactory;
import java.util.List;
import keys.ListinoRealeKey;
import model.ListinoReale;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
     */
    public ListinoRealeManager() {
        super(ListinoReale.class);
    }

    /**
     * getByDate
     *
     * @param idGiorno
     * @return
     */
    public List<ListinoReale> getByDate(Integer idGiorno) {

        List<ListinoReale> listinoReale = null;
        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(ListinoReale.class);
        cr.add(Restrictions.eq("id.idGiorno", idGiorno));

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            listinoReale = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }
        return listinoReale;
    }

}
