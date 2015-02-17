package Manager;

import abstr.AbstractManager;
import keys.LogOrdiniKey;
import model.LogOrdini;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class LogOrdiniManager extends AbstractManager<LogOrdini, LogOrdiniKey> {
 
      private static final Logger LOGGER = Logger.getLogger(LogOrdiniManager.class);

    /**
     * CategorieProdottiManager
     */
    public LogOrdiniManager() {
        super(LogOrdini.class);
    }

}
