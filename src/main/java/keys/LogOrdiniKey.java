package keys;

import java.io.Serializable;

/**
 *
 * @author Stefano
 */
public class LogOrdiniKey implements Serializable {

    private int idRigaLog;
    private int idOrdine;

    /**
     * @return the idRigaLog
     */
    public int getIdRigaLog() {
        return idRigaLog;
    }

    /**
     * @param idRigaLog the idRigaLog to set
     */
    public void setIdRigaLog(int idRigaLog) {
        this.idRigaLog = idRigaLog;
    }

    /**
     * @return the idOrdine
     */
    public int getIdOrdine() {
        return idOrdine;
    }

    /**
     * @param idOrdine the idOrdine to set
     */
    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }
}
