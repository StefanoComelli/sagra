package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import java.util.List;
import model.RigheCommesse;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
     *
     * @param dbConnection
     */
    public RigheCommesseManager(DbConnection dbConnection) {
        super(dbConnection, RigheCommesse.class);
    }

    /**
     *
     * @param idCommessa
     * @return
     */
    public List<RigheCommesse> getByCommessa(int idCommessa) {

        List<RigheCommesse> righe = null;

        Criteria cr = getDbConnection().getSession().createCriteria(RigheCommesse.class);
        cr.add(Restrictions.eq("idCommessa", idCommessa));

        try {
            righe = cr.list();
        } catch (HibernateException e) {
            LOGGER.error(e);
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
     * @param flgBar
     * @return
     */
    public List<RigheCommesse> getByCommessaStampa(int idCommessa, boolean flgBar) {

        List<RigheCommesse> righe = null;

        Criteria cr = getDbConnection().getSession().createCriteria(RigheCommesse.class);
        cr.add(Restrictions.eq("idCommessa", idCommessa));
        if (flgBar) {
            cr.add(Restrictions.lt("idProdotto", 1000));
        } else {
            cr.add(Restrictions.ge("idProdotto", 1000));
        }

        try {
            righe = cr.list();
        } catch (HibernateException e) {
            LOGGER.error(e);
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

        Criteria cr = getDbConnection().getSession().createCriteria(RigheCommesse.class);
        cr.add(Restrictions.eq("idCommessa", idCommessa));
        Transaction tx = null;
        try {
            tx = getDbConnection().getSession().beginTransaction();
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

        Criteria cr = getDbConnection().getSession().createCriteria(RigheCommesse.class);
        cr.add(Restrictions.eq("idCommessa", idCommessa));
        try {
            righe = cr.list();
            for (RigheCommesse riga : righe) {
                totale += riga.getPrezzoListino() * riga.getQuantita();
            }
        } catch (HibernateException e) {
            LOGGER.error(e);
        }

        if (righe == null || righe.isEmpty()) {
            return 0;
        } else {
            return totale;
        }
    }

}
