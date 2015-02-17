package Manager;

import abstr.AbstractManager;
import keys.RigheCommesseKey;
import model.RigheCommesse;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class RigheCommesseManager extends AbstractManager<RigheCommesse, RigheCommesseKey> {

    private static final Logger LOGGER = Logger.getLogger(RigheCommesseManager.class);

    /**
     * CategorieProdottiManager
     */
    public RigheCommesseManager() {
        super(RigheCommesse.class);
    }
}
