package Manager;

import abstr.AbstractManager;
import model.RigheCommesse;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class RigheCommesseManager extends AbstractManager<RigheCommesse, Integer> {

    private static final Logger LOGGER = Logger.getLogger(RigheCommesseManager.class);

    /**
     * CategorieProdottiManager
     */
    public RigheCommesseManager() {
        super(RigheCommesse.class);
    }
}
