package Manager;

import abstr.AbstractManager;
import model.CategorieProdotti;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class CategorieProdottiManager extends AbstractManager<CategorieProdotti, Integer> {

    private static final Logger LOGGER = Logger.getLogger(CategorieProdottiManager.class);

    /**
     * CategorieProdottiManager
     */
    public CategorieProdottiManager() {
        super(CategorieProdotti.class);
    }
}
