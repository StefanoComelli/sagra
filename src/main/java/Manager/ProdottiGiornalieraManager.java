package Manager;

import abstr.AbstractManager;
import java.util.List;
import keys.ProdottiGiornalieraKey;
import model.ProdottiGiornaliera;
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
public class ProdottiGiornalieraManager extends AbstractManager<ProdottiGiornaliera, ProdottiGiornalieraKey> {

    private static final Logger LOGGER = Logger.getLogger(ProdottiGiornalieraManager.class);

    /**
     * CategorieProdottiManager
     */
    public ProdottiGiornalieraManager() {
        super(ProdottiGiornaliera.class);
    }

    /**
     *
     * @param idGiorno
     * @return
     */
    public List<ProdottiGiornaliera> getByDate(Integer idGiorno) {

        List<ProdottiGiornaliera> prodottiGiornaliera = null;

        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(ProdottiGiornaliera.class);
        cr.add(Restrictions.eq("idGiorno", idGiorno));
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            prodottiGiornaliera = cr.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }

        return prodottiGiornaliera;
    }
}
