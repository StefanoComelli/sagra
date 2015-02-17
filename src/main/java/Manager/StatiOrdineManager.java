package Manager;

import abstr.AbstractManager;
import model.StatiOrdine;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class StatiOrdineManager extends AbstractManager<StatiOrdine, Integer> {

    private static final Logger LOGGER = Logger.getLogger(StatiOrdine.class);

    /**
     * CategorieProdottiManager
     */
    public StatiOrdineManager() {
        super(StatiOrdine.class);
    }
}
