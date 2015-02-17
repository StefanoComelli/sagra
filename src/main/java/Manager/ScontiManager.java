package Manager;

import abstr.AbstractManager;
import model.Sconti;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class ScontiManager extends AbstractManager<Sconti, Integer> {

    private static final Logger LOGGER = Logger.getLogger(Sconti.class);

    /**
     * CategorieProdottiManager
     */
    public ScontiManager() {
        super(Sconti.class);
    }
}
