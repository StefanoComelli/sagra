package Manager;

import abstr.AbstractManager;
import model.Commesse;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class CommesseManager extends AbstractManager<Commesse, Integer> {

    private static final Logger LOGGER = Logger.getLogger(CommesseManager.class);

    /**
     * CategorieProdottiManager
     */
    public CommesseManager() {
        super(Commesse.class);
    }
}
