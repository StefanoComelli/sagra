package abstr;

import static abstr.AbstractManager.getFactory;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 * @param <Pojo>
 * @param <PrimaryKey>
 */
public abstract class AbstractManagerGiorno<Pojo, PrimaryKey extends Serializable> extends AbstractManager {

    private static final Logger LOGGER = Logger.getLogger(AbstractManager.class);

    /**
     *
     * @param pojoClass
     */
    protected AbstractManagerGiorno(Class<Pojo> pojoClass) {
        super(pojoClass);
    }

    /**
     * getByDate
     *
     * @param idGiorno
     * @return
     */
    public List<Pojo> getByDate(Integer idGiorno) {
        
        List<Pojo> pojos = null;
        Session session = getFactory().openSession();
        Criteria cr = session.createCriteria(super.getPojoClass());
        cr.add(Restrictions.eq("idGiorno", idGiorno));

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            pojos = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }
        return pojos;
    }

}
