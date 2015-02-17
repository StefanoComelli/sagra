package model;

import abstr.AbstractData;
import java.util.Set;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class CategorieProdotti extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(CategorieProdotti.class);
    private String descrizione;
    private Set<ListinoProdotti> prodotti;
    private int ordineSequenziale;

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * @return the prodotti
     */
    public Set<ListinoProdotti> getProdotti() {
        return prodotti;
    }

    /**
     * @param prodotti the prodotti to set
     */
    public void setProdotti(Set<ListinoProdotti> prodotti) {
        this.prodotti = prodotti;
    }

    /**
     * @return the ordineSequenziale
     */
    public int getOrdineSequenziale() {
        return ordineSequenziale;
    }

    /**
     * @param ordineSequenziale the ordineSequenziale to set
     */
    public void setOrdineSequenziale(int ordineSequenziale) {
        this.ordineSequenziale = ordineSequenziale;
    }

   

}
