package keys;

import java.io.Serializable;

/**
 *
 * @author Stefano
 */
public class RigheCommesseKey implements Serializable {

    private int idCommessa;
    private int idRiga;

    /**
     * @return the idCommessa
     */
    public int getIdCommessa() {
        return idCommessa;
    }

    /**
     * @param idCommessa the idCommessa to set
     */
    public void setIdCommessa(int idCommessa) {
        this.idCommessa = idCommessa;
    }

    /**
     * @return the idRiga
     */
    public int getIdRiga() {
        return idRiga;
    }

    /**
     * @param idRiga the idRiga to set
     */
    public void setIdRiga(int idRiga) {
        this.idRiga = idRiga;
    }
}
