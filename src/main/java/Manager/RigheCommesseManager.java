package Manager;

import abstr.AbstractManager;
import static abstr.AbstractManager.getFactory;
import java.util.List;
import model.RigheCommesse;
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
public class RigheCommesseManager extends AbstractManager<RigheCommesse, Integer> {

    private static final Logger LOGGER = Logger.getLogger(RigheCommesseManager.class);

    /**
     * CategorieProdottiManager
     */
    public RigheCommesseManager() {
        super(RigheCommesse.class);
    }

    /**
     *
     * @param idCommessa
     * @return
     */
    public List<RigheCommesse> getByCommessa(int idCommessa) {

        List<RigheCommesse> righe = null;

        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(RigheCommesse.class);
        cr.add(Restrictions.eq("idCommessa", idCommessa));
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            righe = cr.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }

        if (righe == null || righe.isEmpty()) {
            return null;
        } else {
            return righe;
        }
    }
}
