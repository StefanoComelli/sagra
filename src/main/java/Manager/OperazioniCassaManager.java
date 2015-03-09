package Manager;

import abstr.AbstractManager;
import model.Varianti;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class OperazioniCassaManager extends AbstractManager<Varianti, Integer> {

    private static final Logger LOGGER = Logger.getLogger(OperazioniCassaManager.class);

    /**
     * CategorieProdottiManager
     */
    public OperazioniCassaManager() {
        super(Varianti.class);
    }
}
