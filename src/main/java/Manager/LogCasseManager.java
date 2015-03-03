package Manager;

import abstr.AbstractManagerGiorno;
import keys.LogCasseKey;
import model.LogCasse;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class LogCasseManager extends AbstractManagerGiorno<LogCasse, LogCasseKey> {

    private static final Logger LOGGER = Logger.getLogger(LogCasseManager.class);

    /**
     * CategorieProdottiManager
     */
    public LogCasseManager() {
        super(LogCasse.class);
    }

}
