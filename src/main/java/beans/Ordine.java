package beans;

import Manager.CommesseManager;
import Manager.StatiOrdineManager;
import java.util.List;
import model.Commesse;
import model.StatiOrdine;

/**
 *
 * @author Stefano
 */
public class Ordine {

    private Integer idGiorno;
    private Integer idOperatore;
    private Integer idCassa;

    private CommesseManager commessaMgr;
    private Commesse commessa;
    private List<StatiOrdine> statiOrdine;
    private StatiOrdine statoOrdine;

    /**
     *
     * @param idCassa
     * @param idOperatore
     * @param idGiorno
     */
    public Ordine(Integer idCassa, Integer idOperatore, Integer idGiorno) {
        this.idOperatore = idOperatore;
        this.idGiorno = idGiorno;
        this.idCassa = idCassa;

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
        this.commessa.setIdCassa(getIdCassa());
        this.commessa.setIdOperatore(getIdOperatore());
        this.commessa.setIdGiorno(getIdGiorno());
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
     * @return the idGiorno
     */
    public Integer getIdGiorno() {
        return idGiorno;
    }

    /**
     * @param idGiorno the idGiorno to set
     */
    public void setIdGiorno(Integer idGiorno) {
        this.idGiorno = idGiorno;
    }

    /**
     * @return the idOperatore
     */
    public Integer getIdOperatore() {
        return idOperatore;
    }

    /**
     * @param idOperatore the idOperatore to set
     */
    public void setIdOperatore(Integer idOperatore) {
        this.idOperatore = idOperatore;
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
     * @return the idCassa
     */
    public Integer getIdCassa() {
        return idCassa;
    }

    /**
     * @param idCassa the idCassa to set
     */
    public void setIdCassa(Integer idCassa) {
        this.idCassa = idCassa;
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

}
