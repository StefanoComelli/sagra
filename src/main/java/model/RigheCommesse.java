package model;

import abstr.AbstractData;
import keys.RigheCommesseKey;
import org.jboss.logging.Logger;
import utils.Valuta;

/**
 *
 * @author Stefano
 */
public class RigheCommesse extends AbstractData<RigheCommesseKey> {

    private static final Logger LOGGER = Logger.getLogger(RigheCommesse.class);
    private ListinoProdotti prodotto;
    int quantita;
    private String varianti;
    private float prezzoListino;
    private float scontoApplicato;

    /**
     * @return the prodotto
     */
    public ListinoProdotti getProdotto() {
        return prodotto;
    }

    /**
     * @param prodotto the prodotto to set
     */
    public void setProdotto(ListinoProdotti prodotto) {
        this.prodotto = prodotto;
    }

    /**
     * @return the varianti
     */
    public String getVarianti() {
        return varianti;
    }

    /**
     * @param varianti the varianti to set
     */
    public void setVarianti(String varianti) {
        this.varianti = varianti;
    }

    /**
     * @return the prezzoListino
     */
    public float getPrezzoListino() {
        return prezzoListino;
    }

    /**
     * @param prezzoListino the prezzoListino to set
     */
    public void setPrezzoListino(float prezzoListino) {
        this.prezzoListino = prezzoListino;
    }

    /**
     * @return the scontoApplicato
     */
    public float getScontoApplicato() {
        return scontoApplicato;
    }

    /**
     * @param scontoApplicato the scontoApplicato to set
     */
    public void setScontoApplicato(float scontoApplicato) {
        this.scontoApplicato = scontoApplicato;
    }

    /**
     * @return the quantita
     */
    public int getQuantita() {
        return quantita;
    }

    /**
     * @param quantita the quantita to set
     */
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    /**
     *
     * @return
     */
    public Object[] getRow() {
        Valuta prezzo = new Valuta(prezzoListino);
        return new Object[]{
            prodotto.getNomeProdotto(),
            prezzo.toString(),};
    }
}
