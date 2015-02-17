package keys;

import java.io.Serializable;

/**
 *
 * @author Stefano
 */
public class ProdottiGiornalieraKey implements Serializable {

    private Integer idGiorno;
    private Integer idProdotto;

    /**
     * @return the idGiorno
     */
    public Integer getIdGiorno() {
        return idGiorno;
    }

    /**
     * @param idGiorno the idGiorno to set
     */
    public void setIdGiorno(Integer idGiorno) {
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
    public void setIdProdotto(Integer idProdotto) {
        this.idProdotto = idProdotto;
    }
}
