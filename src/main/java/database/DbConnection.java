package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class DbConnection {

    private SessionFactory factory;
    private Session session;
    private static final Logger LOGGER = Logger.getLogger(DbConnection.class);

    /**
     *
     * @return
     */
    public Session getSession() {
        return session;
    }

    /**
     *
     */
    public DbConnection() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
            session = factory.openSession();
        } catch (Throwable ex) {
            LOGGER.error("Failed to create object.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     *
     */
    public void Dispose() {
        try {
            session.close();
            session = null;
            factory.close();
            factory = null;
        } catch (Throwable ex) {
            LOGGER.error("Failed to close  object.", ex);
            throw new ExceptionInInitializerError(ex);
        }

    }
}
