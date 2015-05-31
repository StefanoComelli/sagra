package Manager;

import abstr.AbstractManager;
import database.DbConnection;
import java.util.List;
import model.CategorieProdotti;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class CategorieProdottiManager extends AbstractManager<CategorieProdotti, Integer> {

    private static final Logger LOGGER = Logger.getLogger(CategorieProdottiManager.class);

    /**
     * CategorieProdottiManager
     *
     * @param dbConnection
     */
    public CategorieProdottiManager(DbConnection dbConnection) {
        super(dbConnection, CategorieProdotti.class);
    }

    /**
     *
     * @return
     */
    public List<CategorieProdotti> getAllSorted() {
        Criteria cr = getDbConnection().getSession().createCriteria(CategorieProdotti.class);
        cr.addOrder(Order.asc("ordineSequenziale"));
        List<CategorieProdotti> categorie = null;
        try {
            categorie = cr.list();
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return categorie;
    }

    /**
     *
     * @param statoOra
     * @param successivo
     * @return
     */
    private CategorieProdotti getCategoria(CategorieProdotti categoriaOra, boolean successivo) {
        CategorieProdotti categoria = null;
        Criteria cr = getDbConnection().getSession().createCriteria(CategorieProdotti.class);
        if (successivo) {
            cr.add(Restrictions.gt("ordineSequenziale", categoriaOra.getOrdineSequenziale()));
            cr.addOrder(Order.asc("ordineSequenziale"));
        } else {
            cr.add(Restrictions.lt("ordineSequenziale", categoriaOra.getOrdineSequenziale()));
            cr.addOrder(Order.desc("ordineSequenziale"));
        }

        try {
            categoria = (CategorieProdotti) cr.list().get(0);
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return categoria;
    }

    /**
     *
     * @return
     */
    private CategorieProdotti getFirst() {
        try {
            return getAllSorted().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    private CategorieProdotti getLast() {
        try {
            List<CategorieProdotti> lista = getAllSorted();
            return lista.get(lista.size() - 1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param categoriaOra
     * @return
     */
    public CategorieProdotti getNext(CategorieProdotti categoriaOra) {
        CategorieProdotti categoria = getCategoria(categoriaOra, true);
        if (categoria != null) {
            return categoria;
        } else {
            return getFirst();
        }
    }

    /**
     *
     * @param categoriaOra
     * @return
     */
    public CategorieProdotti getPrev(CategorieProdotti categoriaOra) {
        CategorieProdotti categoria = getCategoria(categoriaOra, false);
        if (categoria != null) {
            return categoria;
        } else {
            return getLast();
        }
    }
}
