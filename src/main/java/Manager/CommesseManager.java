package Manager;

import abstr.AbstractManagerGiorno;
import model.Commesse;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class CommesseManager extends AbstractManagerGiorno<Commesse, Integer> {

    private static final Logger LOGGER = Logger.getLogger(CommesseManager.class);

    /**
     * CategorieProdottiManager
     */
    public CommesseManager() {
        super(Commesse.class);
    }
}
