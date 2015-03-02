package Manager;

import abstr.AbstractManager;
import model.Casse;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class CasseManager extends AbstractManager<Casse, Integer> {

    private static final Logger LOGGER = Logger.getLogger(Casse.class);

    /**
     * CategorieProdottiManager
     */
    public CasseManager() {
        super(Casse.class);
    }
}
