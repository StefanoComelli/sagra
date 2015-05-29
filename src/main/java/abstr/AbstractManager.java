package abstr;

import database.DbConnection;
import java.io.Serializable;
import java.util.List;
import org.jboss.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

/**
 *
 * @author Stefano
 * @param <Pojo>
 * @param <PrimaryKey>
 */
public abstract class AbstractManager<Pojo, PrimaryKey extends Serializable> {

    private static final Logger LOGGER = Logger.getLogger(AbstractManager.class);

    private final DbConnection dbConnection;

    protected Class<Pojo> pojoClass;

    /**
     * AbstractManager
     *
     * @param dbConnection
     * @param pojoClass
     */
    protected AbstractManager(DbConnection dbConnection, Class<Pojo> pojoClass) {
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
            tx = getDbConnection().getSession().beginTransaction();
            id = (PrimaryKey) getDbConnection().getSession().save(pojo);
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
            tx = getDbConnection().getSession().beginTransaction();
            pojos = getDbConnection().getSession().createQuery(query).list();
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
            tx = getDbConnection().getSession().beginTransaction();
            pojo = (Pojo) getDbConnection().getSession().get(getPojoClass(), primaryKey);
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
            tx = getDbConnection().getSession().beginTransaction();
            pojos = getDbConnection().getSession().createCriteria(getPojoClass()).list();
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
            tx = getDbConnection().getSession().beginTransaction();
            getDbConnection().getSession().update(pojoNew);
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
            tx = getDbConnection().getSession().beginTransaction();
            Pojo pojo = (Pojo) get(primaryKey);
            getDbConnection().getSession().delete(pojo);
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
     * @return the dbConnection
     */
    public DbConnection getDbConnection() {
        return dbConnection;
    }
}
