package model;

import abstr.AbstractData;
import java.util.Date;
import keys.LogOrdiniKey;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class LogOrdini extends AbstractData<LogOrdiniKey> {

    private static final Logger LOGGER = Logger.getLogger(LogOrdini.class);
    private StatiOrdine statoOrdine;
    private Date dataOra;

    /**
     * @return the statoOrdine
     */
    public StatiOrdine getStatoOrdine() {
        return statoOrdine;
    }

    /**
     * @param statoOrdine the statoOrdine to set
     */
    public void setStatoOrdine(StatiOrdine statoOrdine) {
        this.statoOrdine = statoOrdine;
    }

    /**
     * @return the dataOra
     */
    public Date getDataOra() {
        return dataOra;
    }

    /**
     * @param dataOra the dataOra to set
     */
    public void setDataOra(Date dataOra) {
        this.dataOra = dataOra;
    }
}
