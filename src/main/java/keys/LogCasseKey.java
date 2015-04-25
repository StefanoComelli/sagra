package keys;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.idCassa;
        hash = 47 * hash + this.idGiorno;
        hash = 47 * hash + Objects.hashCode(this.dataOra);
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
        final LogCasseKey other = (LogCasseKey) obj;
        if (this.idCassa != other.idCassa) {
            return false;
        }
        if (this.idGiorno != other.idGiorno) {
            return false;
        }
        if (!Objects.equals(this.dataOra, other.dataOra)) {
            return false;
        }
        return true;
    }

}
