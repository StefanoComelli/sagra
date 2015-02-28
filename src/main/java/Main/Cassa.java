package Main;

import Manager.GiorniManager;
import Manager.ListinoRealeManager;
import java.util.Date;
import java.util.List;
import model.Giorni;
import model.ListinoReale;
import model.Operatori;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Cassa {

    private static final Logger LOGGER = Logger.getLogger(Cassa.class);
    private Date data;
    private Giorni giorno;
    private List<ListinoReale> listino;
    Operatori operatore;

    protected ListinoRealeManager listinoMgr;

    /**
     * 
     * @param data
     * @param operatore 
     */
    public Cassa(Date data, Operatori operatore) {
        this.data = data;
        this.operatore=operatore;
        
        GiorniManager giorniMgr = new GiorniManager();
        this.giorno = giorniMgr.getByDate(data);
        listinoMgr = new ListinoRealeManager();
        Refresh();

    }

    /**
     *
     */
    public void Refresh() {
        this.listino = listinoMgr.getByDate(giorno.getId());
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
    public  List<ListinoReale> getListino() {
        return listino;
    }

    /**
     * @param listino the listino to set
     */
    public void setListino( List<ListinoReale> listino) {
        this.listino = listino;
    }

    /**
     * @return the listinoMgr
     */
    public ListinoRealeManager getListinoMgr() {
        return listinoMgr;
    }

    /**
     * @param listinoMgr the listinoMgr to set
     */
    public void setListinoMgr(ListinoRealeManager listinoMgr) {
        this.listinoMgr = listinoMgr;
    }

    /**
     * @return the operatore
     */
    public Operatori getOperatore() {
        return operatore;
    }

    /**
     * @param operatore the operatore to set
     */
    public void setOperatore(Operatori operatore) {
        this.operatore = operatore;
    }
}
