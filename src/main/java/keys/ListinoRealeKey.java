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
}
