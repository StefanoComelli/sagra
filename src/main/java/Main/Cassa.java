package Main;

import Manager.GiorniManager;
import Manager.ProdottiGiornalieraManager;
import java.util.Date;
import java.util.List;
import model.Giorni;
import model.ProdottiGiornaliera;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Cassa {

    private static final Logger LOGGER = Logger.getLogger(Cassa.class);
    private Date data;
    private Giorni giorno;
    private List<ProdottiGiornaliera> prodottiGiornaliera;

    /**
     *
     * @param data
     */
    public Cassa(Date data) {
        this.data = data;

        GiorniManager giorniMgr = new GiorniManager();
        this.giorno = giorniMgr.getByDate(data);

        ProdottiGiornalieraManager prodottiGiornalieraMgr = new ProdottiGiornalieraManager();
        this.prodottiGiornaliera = prodottiGiornalieraMgr.getByDate(getIdGiorno());
    }

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
     * @return the giorno
     */
    public Giorni getGiorno() {
        return giorno;
    }

    /**
     * @param giorno the giorno to set
     */
    public void setGiorno(Giorni giorno) {
        this.giorno = giorno;
    }

    /**
     * @return the prodottiGiornaliera
     */
    public List<ProdottiGiornaliera> getProdottiGiornaliera() {
        return prodottiGiornaliera;
    }

    /**
     * @param prodottiGiornaliera the prodottiGiornaliera to set
     */
    public void setProdottiGiornaliera(List<ProdottiGiornaliera> prodottiGiornaliera) {
        this.prodottiGiornaliera = prodottiGiornaliera;
    }

    /**
     *
     * @return
     */
    private Integer getIdGiorno() {
        return getGiorno().getId();
    }
}
