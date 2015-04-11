package model;

import Manager.ListinoProdottiManager;
import abstr.AbstractData;
import org.jboss.logging.Logger;
import utils.IdDescr;
import utils.Valuta;

/**
 *
 * @author Stefano
 */
public class RigheCommesse extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(RigheCommesse.class);
    private int idProdotto;
    private int quantita;
    private String varianti;
    private float prezzoListino;
    private float scontoApplicato;
    private int idCommessa;

    /**
     * @return the varianti
     */
    public String getVarianti() {
        if (varianti != null) {
            return varianti;
        } else {
            return "";
        }
    }

    private ListinoProdotti getProdotto() {
        ListinoProdottiManager mgrProdotto = new ListinoProdottiManager();
        return mgrProdotto.get(idProdotto);

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
    public IdDescr getIdDescrProdotto() {
        return new IdDescr(this.getProdotto().getId(), this.getProdotto().getNomeProdotto());
    }

    /**
     *
     * @return
     */
    public Object[] getRow() {
        Valuta prezzo = new Valuta(prezzoListino);
        Valuta sconto = new Valuta(getScontoApplicato());
        return new Object[]{
           // getIdDescrProdotto().getIdDescr(),
            this.getProdotto().getNomeProdotto(),
            prezzo.toString(),
            sconto.toString(),
            getQuantita(),
            getVarianti(),
            this.getProdotto().getId()
            
        };
    }

    /**
     * @return the idProdotto
     */
    public int getIdProdotto() {
        return idProdotto;
    }

    /**
     * @param idProdotto the idProdotto to set
     */
    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    /**
     * @return the idCommessa
     */
    public int getIdCommessa() {
        return idCommessa;
    }

    /**
     * @param idCommessa the idCommessa to set
     */
    public void setIdCommessa(int idCommessa) {
        this.idCommessa = idCommessa;
    }
    /**
     *
     * @return
     */
//    public IdDescr getIdDescrProdotto() {
//        return new IdDescr(this.getIdProdotto(), nomeProdotto);
//    }

}
