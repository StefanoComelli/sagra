package Main;

import Manager.GiorniManager;
import java.util.Date;
import model.Giorni;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Cassa {

    private static final Logger LOGGER = Logger.getLogger(Cassa.class);
    private Date data;
    private Giorni giorno;
    private Listino listino;

    /**
     *
     * @param data
     */
    public Cassa(Date data) {
        this.data = data;

        GiorniManager giorniMgr = new GiorniManager();
        this.giorno = giorniMgr.getByDate(data);

        listino = new Listino(this.giorno);

    }

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
     * @return the giorno
     */
    public Giorni getGiorno() {
        return giorno;
    }

    /**
     * @param giorno the giorno to set
     */
    public void setGiorno(Giorni giorno) {
        this.giorno = giorno;
    }

    /**
     *
     * @return
     */
    private Integer getIdGiorno() {
        return getGiorno().getId();
    }

    /**
     * @return the listino
     */
    public Listino getListino() {
        return listino;
    }

    /**
     * @param listino the listino to set
     */
    public void setListino(Listino listino) {
        this.listino = listino;
    }
}
