package abstr;

import database.DbConnection;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 * @param <Pojo>
 * @param <PrimaryKey>
 */
public abstract class AbstractManagerGiorno<Pojo, PrimaryKey extends Serializable> {

    private static final Logger LOGGER = Logger.getLogger(AbstractManagerGiorno.class);

    private final DbConnection dbConnection;

    protected Class<Pojo> pojoClass;

    /**
     * AbstractManager
     *
     * @param dbConnection
     * @param pojoClass
     */
    protected AbstractManagerGiorno(DbConnection dbConnection, Class<Pojo> pojoClass) {
        this.dbConnection = dbConnection;
        this.pojoClass = pojoClass;
    }

    /**
     * insert
     *
     * @param pojo
     * @return
     */
    public PrimaryKey insert(Pojo pojo) {
        Transaction tx = null;
        PrimaryKey id = null;
        try {
            tx = dbConnection.getSession().beginTransaction();
            id = (PrimaryKey) dbConnection.getSession().save(pojo);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        }
        return id;
    }

    /**
     * select
     *
     * @param query
     * @return
     */
    public List<Pojo> select(String query) {

        Transaction tx = null;
        List<Pojo> pojos = null;

        try {
            tx = dbConnection.getSession().beginTransaction();
            pojos = dbConnection.getSession().createQuery(query).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        }
        return pojos;
    }

    /**
     * get
     *
     * @param primaryKey
     * @return
     */
    public Pojo get(PrimaryKey primaryKey) {

        Pojo pojo = null;
        Transaction tx = null;

        try {
            tx = dbConnection.getSession().beginTransaction();
            pojo = (Pojo) dbConnection.getSession().get(getPojoClass(), primaryKey);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        }
        return pojo;
    }

    /**
     *
     * @return
     */
    public List<Pojo> getAll() {

        List<Pojo> pojos = null;
        Transaction tx = null;

        try {
            tx = dbConnection.getSession().beginTransaction();
            pojos = dbConnection.getSession().createCriteria(getPojoClass()).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        }
        return pojos;
    }

    /**
     * update
     *
     * @param primaryKey
     * @param pojoNew
     */
    public void update(PrimaryKey primaryKey, Pojo pojoNew) {

        Transaction tx = null;

        try {
            tx = dbConnection.getSession().beginTransaction();
            dbConnection.getSession().update(pojoNew);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        }
    }

    /**
     * delete
     *
     * @param primaryKey
     */
    public void delete(PrimaryKey primaryKey) {
        Transaction tx = null;
        try {
            tx = dbConnection.getSession().beginTransaction();
            Pojo pojo = (Pojo) get(primaryKey);
            dbConnection.getSession().delete(pojo);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        }
    }

    /**
     * @return the pojoClass
     */
    public Class<Pojo> getPojoClass() {
        return pojoClass;
    }

    /**
     * @param pojoClass the pojoClass to set
     */
    public void setPojoClass(Class<Pojo> pojoClass) {
        this.pojoClass = pojoClass;
    }

    /**
     * getByDate
     *
     * @param idGiorno
     * @return
     */
    public List<Pojo> getByDate(Integer idGiorno) {

        List<Pojo> pojos = null;
        Criteria cr = dbConnection.getSession().createCriteria(getPojoClass());
        cr.add(Restrictions.eq("idGiorno", idGiorno));

        Transaction tx = null;

        try {
            tx = dbConnection.getSession().beginTransaction();
            pojos = cr.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        }
        return pojos;
    }
}
