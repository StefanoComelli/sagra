package model;

import abstr.AbstractData;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Sconti extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(Sconti.class);
    private String descrizione;
    private float sconto;

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
     * @return the sconto
     */
    public float getSconto() {
        return sconto;
    }

    /**
     * @param sconto the sconto to set
     */
    public void setSconto(float sconto) {
        this.sconto = sconto;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getId().toString() + TO_STRING_SEP + getDescrizione() + TO_STRING_SUB + getSconto();
    }
}
