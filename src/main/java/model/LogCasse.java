package model;

import abstr.AbstractData;
import keys.LogCasseKey;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class LogCasse extends AbstractData<LogCasseKey> {

    private static final Logger LOGGER = Logger.getLogger(LogCasse.class);
    private OperazioniCassa operazione;
    private Operatori operatore;
    private String descrizione;

    /**
     * @return the operazione
     */
    public OperazioniCassa getOperazione() {
        return operazione;
    }

    /**
     * @param operazione the operazione to set
     */
    public void setOperazione(OperazioniCassa operazione) {
        this.operazione = operazione;
    }

    /**
     * @return the operatore
     */
    public Operatori getOperatore() {
        return operatore;
    }

    /**
     * @param operatore the operatore to set
     */
    public void setOperatore(Operatori operatore) {
        this.operatore = operatore;
    }

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
