package abstr;

import java.io.Serializable;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 * @param <PrimaryKey>
 */
public abstract class AbstractData<PrimaryKey extends Serializable> extends AbstractPrimaryKey<PrimaryKey> {

    private static final Logger LOGGER = Logger.getLogger(AbstractData.class);

    /**
     * AbstractData
     */
    public AbstractData() {
    }

    /**
     * AbstractData
     *
     * @param primaryKey
     */
    public AbstractData(PrimaryKey primaryKey) {
        super.setId(primaryKey);
    }

    /**
     *
     * @param As
     */
    public void copyValue(AbstractData As) {
    }
}
