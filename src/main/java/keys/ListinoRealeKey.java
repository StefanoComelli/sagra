package keys;

import java.io.Serializable;

/**
 *
 * @author Stefano
 */
public class ListinoRealeKey implements Serializable {

    private int idGiorno;
    private int idProdotto;

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
     * @return the idProdotto
     */
    public Integer getIdProdotto() {
        return idProdotto;
    }

    /**
     * @param idProdotto the idProdotto to set
     */
    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.idGiorno;
        hash = 41 * hash + this.idProdotto;
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
        final ListinoRealeKey other = (ListinoRealeKey) obj;
        if (this.idGiorno != other.idGiorno) {
            return false;
        }
        if (this.idProdotto != other.idProdotto) {
            return false;
        }
        return true;
    }

}
