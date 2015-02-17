package model;

import abstr.AbstractData;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Varianti extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(Varianti.class);
    private CategorieProdotti categoriaProdotto;
    private String variante;

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
}
