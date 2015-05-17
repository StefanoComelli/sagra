package jasper;

import model.RigheCommesse;
import utils.IdDescr;

/**
 *
 * @author Stefano
 */
public class JrRigaOrdine {

    private String prodotto;
    private float prezzo;
    private int quantita;
    private String variante;

    /**
     *
     * @param riga
     */
    public JrRigaOrdine(RigheCommesse riga) {
        IdDescr idProdotto = riga.getIdDescrProdotto();
        this.prodotto = idProdotto.getDescr();
        this.prezzo = riga.getPrezzoListino();
        this.quantita = riga.getQuantita();
        this.variante = riga.getVarianti();
    }

    /**
     * @return the prodotto
     */
    public String getProdotto() {
        return prodotto;
    }

    /**
     * @param prodotto the prodotto to set
     */
    public void setProdotto(String prodotto) {
        this.prodotto = prodotto;
    }

    /**
     * @return the prezzo
     */
    public float getPrezzo() {
        return prezzo;
    }

    /**
     * @param prezzo the prezzo to set
     */
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
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
     * @return the variante
     */
    public String getVariante() {
        return variante;
    }

    /**
     * @param variante the variante to set
     */
    public void setVariante(String variante) {
        this.variante = variante;
    }

}
