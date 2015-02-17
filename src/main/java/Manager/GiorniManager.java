package Manager;

import abstr.AbstractManager;
import model.Giorni;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class GiorniManager extends AbstractManager<Giorni, Integer> {

    private static final Logger LOGGER = Logger.getLogger(GiorniManager.class);

    /**
     * CategorieProdottiManager
     */
    public GiorniManager() {
        super(Giorni.class);
    }
}
