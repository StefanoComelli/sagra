package model;

import abstr.AbstractData;
import keys.ProdottiGiornalieraKey;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class ProdottiGiornaliera extends AbstractData<ProdottiGiornalieraKey> {

    private static final Logger LOGGER = Logger.getLogger(ProdottiGiornaliera.class);

    private ListinoProdotti prodotto;
    private int disponibilita;
    private int quantitaVenduta;
    private int quantitaWarning;
    private double scontoGiorno;
    private boolean sospensione;
    private String motivoSospensione;

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
     * @return the disponibilita
     */
    public int getDisponibilita() {
        return disponibilita;
    }

    /**
     * @param disponibilita the disponibilita to set
     */
    public void setDisponibilita(int disponibilita) {
        this.disponibilita = disponibilita;
    }

    /**
     * @return the quantitaVenduta
     */
    public int getQuantitaVenduta() {
        return quantitaVenduta;
    }

    /**
     * @param quantitaVenduta the quantitaVenduta to set
     */
    public void setQuantitaVenduta(int quantitaVenduta) {
        this.quantitaVenduta = quantitaVenduta;
    }

    /**
     * @return the quantitaWarning
     */
    public int getQuantitaWarning() {
        return quantitaWarning;
    }

    /**
     * @param quantitaWarning the quantitaWarning to set
     */
    public void setQuantitaWarning(int quantitaWarning) {
        this.quantitaWarning = quantitaWarning;
    }

    /**
     * @return the scontoGiorno
     */
    public double getScontoGiorno() {
        return scontoGiorno;
    }

    /**
     * @param scontoGiorno the scontoGiorno to set
     */
    public void setScontoGiorno(double scontoGiorno) {
        this.scontoGiorno = scontoGiorno;
    }

    /**
     * @return the sospensione
     */
    public boolean isSospensione() {
        return sospensione;
    }

    /**
     * @param sospensione the sospensione to set
     */
    public void setSospensione(boolean sospensione) {
        this.sospensione = sospensione;
    }

    /**
     * @return the motivoSospensione
     */
    public String getMotivoSospensione() {
        return motivoSospensione;
    }

    /**
     * @param motivoSospensione the motivoSospensione to set
     */
    public void setMotivoSospensione(String motivoSospensione) {
        this.motivoSospensione = motivoSospensione;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getId().toString() + TO_STRING_SEP + getProdotto().getDescrizione();
    }
}
