package model;

import abstr.AbstractData;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class OperazioniCassa extends AbstractData<String> {

    private static final Logger LOGGER = Logger.getLogger(OperazioniCassa.class);
    private String descrizione;

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

}
