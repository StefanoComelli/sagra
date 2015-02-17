package model;

import abstr.AbstractData;
import java.util.Date;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Giorni extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(Giorni.class);
    private Date data;
    private double scontoData;

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the scontoData
     */
    public double getScontoData() {
        return scontoData;
    }

    /**
     * @param scontoData the scontoData to set
     */
    public void setScontoData(double scontoData) {
        this.scontoData = scontoData;
    }

}
