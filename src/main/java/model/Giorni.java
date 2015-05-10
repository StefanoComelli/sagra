package model;

import abstr.AbstractData;
import java.util.Date;
import java.util.Set;
import org.jboss.logging.Logger;
import utils.Sconto;

/**
 *
 * @author Stefano
 */
public class Giorni extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(Giorni.class);
    private Date data;
    private float scontoGiorno;
    private boolean flgAperto;
    private Set<ProdottiGiornaliera> prodottiGiornaliera;

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the scontoGiorno
     */
    public float getScontoGiorno() {
        return scontoGiorno;
    }

    /**
     * @param scontoGiorno the scontoGiorno to set
     */
    public void setScontoGiorno(float scontoGiorno) {
        this.scontoGiorno = scontoGiorno;
    }

    /**
     * @return the prodottiGiornaliera
     */
    public Set<ProdottiGiornaliera> getProdottiGiornaliera() {
        return prodottiGiornaliera;
    }

    /**
     * @param prodottiGiornaliera the prodottiGiornaliera to set
     */
    public void setProdottiGiornaliera(Set<ProdottiGiornaliera> prodottiGiornaliera) {
        this.prodottiGiornaliera = prodottiGiornaliera;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getId().toString() + TO_STRING_SEP + getData().toString();
    }

    /**
     *
     * @return
     */
    public String getTitolo() {
        if (scontoGiorno == 0) {
            return data.toString();
        } else {
            Sconto sGiorno = new Sconto(getScontoGiorno());
            return data.toString() + " Sconto " + sGiorno.toString();
        }
    }

    /**
     * @return the flgAperto
     */
    public boolean isFlgAperto() {
        return flgAperto;
    }

    /**
     * @param flgAperto the flgAperto to set
     */
    public void setFlgAperto(boolean flgAperto) {
        this.flgAperto = flgAperto;
    }

}
