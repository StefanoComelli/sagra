package model;

import abstr.AbstractData;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class ListinoProdotti extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(ListinoProdotti.class);
    private CategorieProdotti categoriaProdotto;
    private String nomeProdotto;
    private String descrizione;
    private float prezzoUnitario;

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
     *
     * @return
     */
    @Override
    public String toString() {
        return getId().toString() + TO_STRING_SEP + getDescrizione();
    }
}
