package utils;

import java.text.NumberFormat;
import java.util.Locale;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Sconto {

    private static final Logger LOGGER = Logger.getLogger(Sconto.class);
    private float valore;

    /**
     *
     * @param valore
     */
    public Sconto(float valore) {
        this.valore = valore;
    }

    /**
     *
     * @param valore
     */
    public Sconto(String valore) {
        try {
            this.valore = Float.valueOf(valore);
        } catch (Exception e) {
            this.valore = 0;
        }

    }

    /**
     *
     * @param valore
     */
    public Sconto(Object valore) {
        try {
            String tV = (String) valore;
            String tmp = (String) tV.substring(2).replace(",", ".");
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
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ITALY);
        return numberFormat.format(valore);
    }
}
