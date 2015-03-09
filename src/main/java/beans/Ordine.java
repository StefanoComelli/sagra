package beans;

import Manager.CommesseManager;
import Manager.StatiOrdineManager;
import java.util.List;
import model.Casse;
import model.Commesse;
import model.Giorni;
import model.Operatori;
import model.StatiOrdine;

/**
 *
 * @author Stefano
 */
public class Ordine {

    private Giorni giorno;
    private Operatori operatore;
    private Casse cassa;

    private CommesseManager commessaMgr;
    private Commesse commessa;
    private List<StatiOrdine> statiOrdine;
    private StatiOrdine statoOrdine;

    /**
     *
     * @param cassa
     * @param operatore
     * @param giorno
     */
    public Ordine(Casse cassa, Operatori operatore, Giorni giorno) {
        this.cassa = cassa;
        this.operatore = operatore;
        this.giorno = giorno;

        commessaMgr = new CommesseManager();
        this.commessa = new Commesse();

        StatiOrdineManager statiOrdineMgr = new StatiOrdineManager();
        statiOrdine = statiOrdineMgr.getElencoStati();
        statoOrdine = statiOrdineMgr.getDefault();
        ApriOrdine();
    }

    /**
     *
     */
    public void ApriOrdine() {
        this.commessa.setCassa(getCassa());
        this.commessa.setOperatore(getOperatore());
        this.commessa.setGiorno(getGiorno());
        getCommessaMgr().insert(commessa);

    }

    /**
     *
     */
    public void ChiudiOrdine() {
    }

    /**
     *
     */
    public void AnnullaOrdine() {
    }

    /**
     * @return the commessa
     */
    public Commesse getCommessa() {
        return commessa;
    }

    /**
     * @param commessa the commessa to set
     */
    public void setCommessa(Commesse commessa) {
        this.commessa = commessa;
    }

    /**
     * @return the commessaMgr
     */
    public CommesseManager getCommessaMgr() {
        return commessaMgr;
    }

    /**
     * @param commessaMgr the commessaMgr to set
     */
    public void setCommessaMgr(CommesseManager commessaMgr) {
        this.commessaMgr = commessaMgr;
    }

    /**
     * @return the statiOrdine
     */
    public List<StatiOrdine> getStatiOrdine() {
        return statiOrdine;
    }

    /**
     * @param statiOrdine the statiOrdine to set
     */
    public void setStatiOrdine(List<StatiOrdine> statiOrdine) {
        this.statiOrdine = statiOrdine;
    }

    /**
     * @return the statoOrdine
     */
    public StatiOrdine getStatoOrdine() {
        return statoOrdine;
    }

    /**
     * @param statoOrdine the statoOrdine to set
     */
    public void setStatoOrdine(StatiOrdine statoOrdine) {
        this.statoOrdine = statoOrdine;
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

    /**
     * @return the cassa
     */
    public Casse getCassa() {
        return cassa;
    }

    /**
     * @param cassa the cassa to set
     */
    public void setCassa(Casse cassa) {
        this.cassa = cassa;
    }

}
