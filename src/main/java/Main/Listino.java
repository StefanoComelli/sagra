package Main;

import Manager.ListinoProdottiManager;
import Manager.ProdottiGiornalieraManager;
import java.util.List;
import model.Giorni;
import model.ListinoProdotti;
import model.ProdottiGiornaliera;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Listino {

    private static final Logger LOGGER = Logger.getLogger(Listino.class);
    private Giorni giorno;
    private List<ProdottiGiornaliera> prodottiGiornaliera;
    private List<ListinoProdotti> listinoProdotti;

    /**
     *
     * @param giorno
     */
    public Listino(Giorni giorno) {
        this.giorno = giorno;
        ProdottiGiornalieraManager prodottiGiornalieraMgr = new ProdottiGiornalieraManager();
        this.prodottiGiornaliera = prodottiGiornalieraMgr.getByDate(this.giorno.getId());
         ListinoProdottiManager listinoProdottiMgr = new ListinoProdottiManager();
    //    this.listinoProdotti = listinoProdottiMgr.getByDate(this.giorno.getId());
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
     * @return the listinoProdotti
     */
    public List<ListinoProdotti> getListinoProdotti() {
        return listinoProdotti;
    }

    /**
     * @param listinoProdotti the listinoProdotti to set
     */
    public void setListinoProdotti(List<ListinoProdotti> listinoProdotti) {
        this.listinoProdotti = listinoProdotti;
    }

}
