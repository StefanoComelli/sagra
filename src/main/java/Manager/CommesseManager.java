package Manager;

import abstr.AbstractManagerGiorno;
import beans.StatoCassa;
import database.DbConnection;
import java.util.List;
import model.Casse;
import model.Commesse;
import model.Giorni;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class CommesseManager extends AbstractManagerGiorno<Commesse, Integer> {

    private static final Logger LOGGER = Logger.getLogger(CommesseManager.class);

    /**
     * CategorieProdottiManager
     *
     * @param dbConnection
     */
    public CommesseManager(DbConnection dbConnection) {
        super(dbConnection, Commesse.class);
    }

    /**
     *
     * @param cassa
     * @param giorno
     * @param statoCassa
     */
    public void getStatoCassa(Casse cassa, Giorni giorno, StatoCassa statoCassa) {
        getTotali(cassa, giorno, statoCassa);
        getNumeroAsporto(cassa, giorno, statoCassa);
        getNumeroOmaggi(cassa, giorno, statoCassa);
        getNumeroOperatore(cassa, giorno, statoCassa);
    }

    /**
     *
     * @param cassa
     * @param giorno
     * @return
     */
    private Criteria getStatCriteria(Casse cassa, Giorni giorno) {
        Criteria cr = getDbConnection().getSession().createCriteria(Commesse.class);
        cr.add(Restrictions.eq("cassa", cassa));
        cr.add(Restrictions.eq("giorno", giorno));
        return cr;
    }

    /**
     *
     * @param cassa
     * @param giorno
     * @param statoCassa
     */
    private void getTotali(Casse cassa, Giorni giorno, StatoCassa statoCassa) {
        Criteria cr = getStatCriteria(cassa, giorno);

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.count("totale"));
        projectionList.add(Projections.sum("totale"));
        projectionList.add(Projections.sum("totalePagato"));
        projectionList.add(Projections.sum("scontoApplicato"));
        cr.setProjection(projectionList);

        try {
            List list = cr.list();

            if (list != null && list.size() > 0) {
                Object[] o = (Object[]) list.get(0);
                if (o[0] != null) {
                    statoCassa.setNumeroOrdini(((Long) o[0]));
                } else {
                    statoCassa.setNumeroOrdini(0);
                }
                if (o[1] != null) {
                    statoCassa.setTotaleOrdini(((Double) o[1]));
                } else {
                    statoCassa.setTotaleOrdini(0);
                }

                if (o[2] != null) {
                    statoCassa.setTotalePagato(((Double) o[2]));
                } else {
                    statoCassa.setTotalePagato(0);
                }

                if (o[3] != null) {
                    statoCassa.setTotaleSconti(((Double) o[3]));
                } else {
                    statoCassa.setTotaleSconti(0);
                }
            }
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
    }

    /**
     *
     * @param cassa
     * @param giorno
     * @param statoCassa
     */
    private void getNumeroAsporto(Casse cassa, Giorni giorno, StatoCassa statoCassa) {
        Criteria cr = getStatCriteria(cassa, giorno);
        cr.add(Restrictions.eq("asporto", true));

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.count("asporto"));
        cr.setProjection(projectionList);

        try {
            List list = cr.list();

            if (list != null && list.size() > 0) {
                if (list.get(0) != null) {
                    statoCassa.setNumeroAsporto(((Long) list.get(0)));
                } else {
                    statoCassa.setNumeroAsporto(0);
                }
            }
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
    }

    /**
     *
     * @param cassa
     * @param giorno
     * @param statoCassa
     */
    private void getNumeroOmaggi(Casse cassa, Giorni giorno, StatoCassa statoCassa) {
        Criteria cr = getStatCriteria(cassa, giorno);
        cr.add(Restrictions.eq("descSconto", "100 - Omaggio"));

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.count("descSconto"));
        cr.setProjection(projectionList);

        try {
            List list = cr.list();
            if (list != null && list.size() > 0) {
                if (list.get(0) != null) {
                    statoCassa.setNumeroScontiOmaggio((Long) list.get(0));
                } else {
                    statoCassa.setNumeroScontiOmaggio(0);
                }
            }
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
    }

    /**
     *
     * @param cassa
     * @param giorno
     * @param statoCassa
     */
    private void getNumeroOperatore(Casse cassa, Giorni giorno, StatoCassa statoCassa) {
        Criteria cr = getStatCriteria(cassa, giorno);
        cr.add(Restrictions.eq("descSconto", "100 - Sconto operatori sagra"));

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.count("descSconto"));
        cr.setProjection(projectionList);

        try {
            List list = cr.list();

            if (list != null && list.size() > 0) {
                if (list.get(0) != null) {
                    statoCassa.setNumeroScontiOperatore((Long) list.get(0));
                } else {
                    statoCassa.setNumeroScontiOperatore(0);
                }
            }
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
    }
}
