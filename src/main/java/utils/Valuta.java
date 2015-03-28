package utils;

import java.text.NumberFormat;
import java.util.Locale;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Valuta {

    private static final Logger LOGGER = Logger.getLogger(Valuta.class);
    private float valore;

    /**
     *
     * @param valore
     */
    public Valuta(float valore) {
        this.valore = valore;
    }

    /**
     *
     * @param valore
     */
    public Valuta(String valore) {
        try {
            String tmp = valore.substring(2);
            this.valore = Float.valueOf(tmp);
        } catch (Exception e) {
            this.valore = 0;
        }

    }

    /**
     *
     * @param valore
     */
    public Valuta(Object valore) {
        try {
            String tV = (String) valore;
            String tmp = (String) tV.substring(2);
            this.valore = Float.valueOf(tmp);
        } catch (Exception e) {
            this.valore = 0;
        }

    }

    /**
     * @return the valore
     */
    public float getValore() {
        return valore;
    }

    /**
     * @param valore the valore to set
     */
    public void setValore(float valore) {
        this.valore = valore;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.ITALY);
        return numberFormat.format(valore);
    }
}
