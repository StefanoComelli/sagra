package Manager;

import abstr.AbstractManager;
import model.Varianti;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class OperazioniCassaiManager extends AbstractManager<Varianti, Integer> {

    private static final Logger LOGGER = Logger.getLogger(OperazioniCassaiManager.class);

    /**
     * CategorieProdottiManager
     */
    public OperazioniCassaiManager() {
        super(Varianti.class);
    }
}
