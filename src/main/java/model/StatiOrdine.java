package model;

import abstr.AbstractData;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class StatiOrdine extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(StatiOrdine.class);
    private String descrizione;
    private int ordineSequenziale;

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * @return the ordineSequenziale
     */
    public int getOrdineSequenziale() {
        return ordineSequenziale;
    }

    /**
     * @param ordineSequenziale the ordineSequenziale to set
     */
    public void setOrdineSequenziale(int ordineSequenziale) {
        this.ordineSequenziale = ordineSequenziale;
    }

}
