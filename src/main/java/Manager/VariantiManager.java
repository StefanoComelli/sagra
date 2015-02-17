package Manager;

import abstr.AbstractManager;
import model.Varianti;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class VariantiManager extends AbstractManager<Varianti, Integer> {

    private static final Logger LOGGER = Logger.getLogger(VariantiManager.class);

    /**
     * CategorieProdottiManager
     */
    public VariantiManager() {
        super(Varianti.class);
    }
}
