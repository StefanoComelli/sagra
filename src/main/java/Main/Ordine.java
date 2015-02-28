package Main;

import Manager.CommesseManager;
import model.Commesse;

/**
 *
 * @author Stefano
 */
public class Ordine {

    private Integer idGiorno;
    private Integer idOperatore;
    Integer idCassa;

    CommesseManager commessaMgr;
    Commesse commessa;

    /**
     *
     * @param idCassa
     * @param idOperatore
     * @param idGiorno
     */
    public Ordine(Integer idCassa, Integer idOperatore, Integer idGiorno) {
        this.idOperatore = idOperatore;
        this.idGiorno = idGiorno;
        this.idCassa=idCassa;
        commessaMgr = new CommesseManager();
        this.commessa = new Commesse();
        ApriOrdine();
    }

    public void ApriOrdine() {
        this.commessa.setIdCassa(getIdCassa());
        this.commessa.setIdOperatore(getIdOperatore());
        this.commessa.setIdGiorno(getIdGiorno());
        getCommessaMgr().insert(commessa);
        
    }

    public void ChiudiOrdine() {
    }

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

}
