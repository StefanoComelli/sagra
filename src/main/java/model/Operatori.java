package model;

import abstr.AbstractData;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Operatori extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(Operatori.class);
    private String operatore;

    /**
     * @return the operatore
     */
    public String getOperatore() {
        return operatore;
    }

    /**
     * @param operatore the operatore to set
     */
    public void setOperatore(String operatore) {
        this.operatore = operatore;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getId().toString() + TO_STRING_SEP + getOperatore();
    }
}
