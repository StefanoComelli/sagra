package Manager;

import abstr.AbstractManagerGiorno;
import keys.ProdottiGiornalieraKey;
import model.ProdottiGiornaliera;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class ProdottiGiornalieraManager extends AbstractManagerGiorno<ProdottiGiornaliera, ProdottiGiornalieraKey> {

    private static final Logger LOGGER = Logger.getLogger(ProdottiGiornalieraManager.class);

    /**
     * CategorieProdottiManager
     */
    public ProdottiGiornalieraManager() {
        super(ProdottiGiornaliera.class);
    }

}
