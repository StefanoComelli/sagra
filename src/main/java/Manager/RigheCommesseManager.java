package Manager;

import abstr.AbstractManager;
import static abstr.AbstractManager.getFactory;
import java.util.List;
import model.RigheCommesse;
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
        cr.addOrder(Order.asc("idProdotto"));
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

    /**
     *
     * @param idCommessa
     */
    public void deleteByCommessa(int idCommessa) {

        List<RigheCommesse> righe;

        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(RigheCommesse.class);
        cr.add(Restrictions.eq("idCommessa", idCommessa));
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            righe = cr.list();
            if (righe != null) {
                for (RigheCommesse riga : righe) {
                    delete(riga.getId());
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }

    }

    /**
     *
     * @param id
     * @param deltaQuantita
     */
    public void DeltaQuantita(int id, int deltaQuantita) {
        RigheCommesse riga = get(id);
        riga.DeltaQuantita(deltaQuantita);
        update(id, riga);
    }

    /**
     *
     * @param id
     * @param qta
     */
    public void SettaQuantita(int id, int qta) {
        RigheCommesse riga = get(id);
        riga.setQuantita(qta);
        update(id, riga);
    }

    /**
     *
     * @param id
     */
    public void incQuantita(int id) {
        DeltaQuantita(id, 1);
    }

    /**
     *
     * @param id
     * @param qta
     */
    public void incQuantita(int id, int qta) {
        DeltaQuantita(id, qta);
    }

    /**
     *
     * @param id
     */
    public void decQuantita(int id) {
        DeltaQuantita(id, -1);
    }

    /**
     *
     * @param id
     * @param qta
     */
    public void decQuantita(int id, int qta) {
        DeltaQuantita(id, -qta);
    }

    /**
     *
     * @param id
     * @param variante
     */
    public void CambiaVariante(int id, String variante) {
        RigheCommesse riga = get(id);
        riga.setVarianti(variante);
        update(id, riga);
    }

    /**
     *
     * @param idCommessa
     * @return
     */
    public float getTotale(int idCommessa) {

        List<RigheCommesse> righe = null;
        float totale = 0;

        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(RigheCommesse.class);
        cr.add(Restrictions.eq("idCommessa", idCommessa));
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            righe = cr.list();
            for (RigheCommesse riga : righe) {
                totale += riga.getPrezzoListino() * riga.getQuantita();
            }
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
            return 0;
        } else {
            return totale;
        }
    }

}
