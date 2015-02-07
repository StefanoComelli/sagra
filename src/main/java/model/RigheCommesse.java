package model;

import abstr.AbstractData;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class RigheCommesse extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(RigheCommesse.class);
    private Commesse commessa;
    private ListinoProdotti prodotto;   
    private String varianti;
    private float prezzoListino;
    private float scontoApplicato;

    /**
     * @return the commessa
     */
    public Commesse getCommessa() {
        return commessa;
    }

    /**
     * @param commessa the commessa to set
     */
    public void setCommessa(Commesse commessa) {
        this.commessa = commessa;
    }

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
}
