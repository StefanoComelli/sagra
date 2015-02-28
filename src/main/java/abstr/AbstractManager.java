package abstr;

import java.io.Serializable;
import java.util.List;
import org.jboss.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Stefano
 * @param <Pojo>
 * @param <PrimaryKey>
 */
public abstract class AbstractManager<Pojo, PrimaryKey extends Serializable> {

    private static final Logger LOGGER = Logger.getLogger(AbstractManager.class);

    private static SessionFactory factory;

    /**
     * @return the factory
     */
    public static SessionFactory getFactory() {
        return factory;
    }
    protected Class<Pojo> pojoClass;

    /**
     * AbstractManager
     *
     * @param pojoClass
     */
    protected AbstractManager(Class<Pojo> pojoClass) {
        this.pojoClass = pojoClass;
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            LOGGER.error("Failed to create sessionFactory object.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * insert
     *
     * @param pojo
     * @return
     */
    public Integer insert(Pojo pojo) {
        Session session = getFactory().openSession();
        Transaction tx = null;
        Integer id = null;
        try {
            tx = session.beginTransaction();
            id = (Integer) session.save(pojo);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
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
        Session session = getFactory().openSession();
        Transaction tx = null;
        List<Pojo> pojos = null;
        try {
            tx = session.beginTransaction();
            pojos = session.createQuery(query).list();
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

    /**
     * get
     *
     * @param primaryKey
     * @return
     */
    public Pojo get(PrimaryKey primaryKey) {
        Pojo pojo = null;
        Session session = getFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            pojo = (Pojo) session.get(getPojoClass(), primaryKey);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error(e);
        } finally {
            session.close();
        }
        return pojo;
    }

    /**
     * update
     *
     * @param primaryKey
     * @param pojoNew
     */
    public void update(PrimaryKey primaryKey, Pojo pojoNew) {
        Session session = getFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Pojo pojo = (Pojo) get(primaryKey);
            session.update(pojoNew);
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
     * delete
     *
     * @param primaryKey
     */
    public void delete(PrimaryKey primaryKey) {
        Session session = getFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Pojo pojo = (Pojo) get(primaryKey);
            session.delete(pojo);
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
}
