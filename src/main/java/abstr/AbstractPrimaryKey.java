package abstr;

import java.io.Serializable;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 * @param <PrimaryKey>
 */
public abstract class AbstractPrimaryKey<PrimaryKey> implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(AbstractPrimaryKey.class);

    private PrimaryKey id;

    /**
     * AbstractData
     */
    public AbstractPrimaryKey() {
    }

    /**
     * AbstractData
     *
     * @param primaryKey
     */
    public AbstractPrimaryKey(PrimaryKey primaryKey) {
        this.id = primaryKey;
    }

    /**
     * getPrimaryKey
     *
     * @return
     */
    public final PrimaryKey getId() {
        return id;
    }

    /**
     * setPrimaryKey
     *
     * @param id
     */
    public final void setId(PrimaryKey id) {
        this.id = id;
    }
}
