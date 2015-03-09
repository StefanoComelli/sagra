package keys;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Stefano
 */
public class LogCasseKey implements Serializable {

    private int idCassa;
    private int idGiorno;
    private Date dataOra;

    /**
     * @return the idCassa
     */
    public int getIdCassa() {
        return idCassa;
    }

    /**
     * @param idCassa the idCassa to set
     */
    public void setIdCassa(int idCassa) {
        this.idCassa = idCassa;
    }

    /**
     * @return the idGiorno
     */
    public int getIdGiorno() {
        return idGiorno;
    }

    /**
     * @param idGiorno the idGiorno to set
     */
    public void setIdGiorno(int idGiorno) {
        this.idGiorno = idGiorno;
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
