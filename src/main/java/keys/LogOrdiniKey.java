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

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.idRigaLog;
        hash = 89 * hash + this.idOrdine;
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogOrdiniKey other = (LogOrdiniKey) obj;
        if (this.idRigaLog != other.idRigaLog) {
            return false;
        }
        if (this.idOrdine != other.idOrdine) {
            return false;
        }
        return true;
    }

}
