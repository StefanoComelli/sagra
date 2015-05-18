package model;

import abstr.AbstractData;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Casse extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(Casse.class);
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getId().toString() + TO_STRING_SEP + getDescrizione();
    }
}
