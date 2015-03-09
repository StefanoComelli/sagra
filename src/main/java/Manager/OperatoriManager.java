package Manager;

import abstr.AbstractManager;
import model.Operatori;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class OperatoriManager extends AbstractManager<Operatori, Integer> {

    private static final Logger LOGGER = Logger.getLogger(Operatori.class);

    /**
     * CategorieProdottiManager
     */
    public OperatoriManager() {
        super(Operatori.class);
    }
 }
