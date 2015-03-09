package Manager;

import abstr.AbstractManagerGiorno;
import keys.ListinoRealeKey;
import model.ListinoReale;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class ListinoRealeManager extends AbstractManagerGiorno<ListinoReale, ListinoRealeKey> {

    private static final Logger LOGGER = Logger.getLogger(ListinoRealeManager.class);

    /**
     * CategorieProdottiManager
     */
    public ListinoRealeManager() {
        super(ListinoReale.class);
    }  
}
