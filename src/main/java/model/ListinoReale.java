package model;

import abstr.AbstractData;
import keys.ListinoRealeKey;
import org.jboss.logging.Logger;
import utils.IdDescr;
import utils.Valuta;

/**
 *
 * @author Stefano
 */
public class ListinoReale extends AbstractData<ListinoRealeKey> {

    private static final Logger LOGGER = Logger.getLogger(ListinoProdotti.class);
    private CategorieProdotti categoriaProdotto;
    private String nomeProdotto;
    private String descrizione;
    private float prezzoUnitario;
    private int disponibilita;
    private int quantitaVenduta;
    private int quantitaWarning;

    /**
     * @return the categoriaProdotto
     */
    public CategorieProdotti getCategoriaProdotto() {
        return categoriaProdotto;
    }

    /**
     * @param categoriaProdotto the categoriaProdotto to set
     */
    public void setCategoriaProdotto(CategorieProdotti categoriaProdotto) {
        this.categoriaProdotto = categoriaProdotto;
    }

    /**
     * @return the nomeProdotto
     */
    public String getNomeProdotto() {
        return nomeProdotto;
    }

    /**
     * @param nomeProdotto the nomeProdotto to set
     */
    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

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
     * @return the prezzoUnitario
     */
    public float getPrezzoUnitario() {
        return prezzoUnitario;
    }

    /**
     * @param prezzoUnitario the prezzoUnitario to set
     */
    public void setPrezzoUnitario(float prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
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
     *
     * @return
     */
    public IdDescr getIdDescrCategoria() {
        return new IdDescr(categoriaProdotto.getId(), categoriaProdotto.getDescrizione());
    }

    /**
     *
     * @return
     */
    public IdDescr getIdDescrProdotto() {
        return new IdDescr(this.getId().getIdProdotto(), nomeProdotto);
    }

    /**
     *
     * @return
     */
    public Object[] getRow() {
        Valuta prezzo = new Valuta(prezzoUnitario);
        return new Object[]{
            categoriaProdotto.getDescrizione(),
            nomeProdotto,
            prezzo.toString(),
            descrizione,
            categoriaProdotto.getId(),
            this.getId().getIdProdotto()
        };
    }
}
