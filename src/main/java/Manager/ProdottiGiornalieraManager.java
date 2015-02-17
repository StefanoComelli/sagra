package Manager;

import abstr.AbstractManager;
import keys.ProdottiGiornalieraKey;
import model.ProdottiGiornaliera;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class ProdottiGiornalieraManager extends AbstractManager<ProdottiGiornaliera, ProdottiGiornalieraKey> {

    private static final Logger LOGGER = Logger.getLogger(ProdottiGiornalieraManager.class);

    /**
     * CategorieProdottiManager
     */
    public ProdottiGiornalieraManager() {
        super(ProdottiGiornaliera.class);
    }
}
