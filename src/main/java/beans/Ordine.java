package beans;

import Manager.CommesseManager;
import Manager.RigheCommesseManager;
import Manager.StatiOrdineManager;
import java.util.List;
import model.Casse;
import model.Commesse;
import model.Giorni;
import model.ListinoReale;
import model.Operatori;
import model.Sconti;
import model.StatiOrdine;
import model.Varianti;

/**
 *
 * @author Stefano
 */
public class Ordine {

    private Giorni giorno;
    private Operatori operatore;
    private Casse cassa;

    private List<ListinoReale> listino;
    private List<Varianti> varianti;
    private List<Sconti> sconti;

    private CommesseManager commessaMgr;
    private RigheCommesseManager righeMgr;
    private Commesse commessa;
    private List<StatiOrdine> statiOrdine;
    private StatiOrdine statoOrdine;

    private String cliente;

    /**
     *
     * @param cassa
     * @param cliente
     */
    public Ordine(Cassa cassa, String cliente) {
        this.cassa = cassa.getCassa();
        this.operatore = cassa.getOperatore();
        this.giorno = cassa.getGiorno();
        this.listino = cassa.getListino();
        this.varianti = cassa.getVarianti();
        this.sconti = cassa.getSconti();

        commessaMgr = new CommesseManager();
        this.commessa = new Commesse();

        StatiOrdineManager statiOrdineMgr = new StatiOrdineManager();
        righeMgr = new RigheCommesseManager();
        statiOrdine = statiOrdineMgr.getElencoStati();
        statoOrdine = statiOrdineMgr.getDefault();
        this.commessa.setStatoOrdine(statoOrdine);
        this.commessa.setNomeCliente(cliente);
        this.cliente = cliente;
        this.commessa.setTavoloCliente("a1");
        this.commessa.setCassa(this.cassa);
        this.commessa.setGiorno(giorno);
        this.commessa.setOperatore(operatore);
        this.commessa.setCassa(getCassa());
        this.commessa.setOperatore(getOperatore());
        this.commessa.setGiorno(getGiorno());
        getCommessaMgr().insert(commessa);
    }

    /**
     *
     * @param idOrdine
     */
    public Ordine(int idOrdine) {
        commessaMgr = new CommesseManager();
        this.commessa = (Commesse) commessaMgr.get(idOrdine);
        righeMgr = new RigheCommesseManager();
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
        int idCommessa = commessa.getId();
        righeMgr.deleteByCommessa(idCommessa);
        commessaMgr.delete(idCommessa);
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

    /**
     * @return the listino
     */
    public List<ListinoReale> getListino() {
        return listino;
    }

    /**
     * @param listino the listino to set
     */
    public void setListino(List<ListinoReale> listino) {
        this.listino = listino;
    }

    /**
     * @return the varianti
     */
    public List<Varianti> getVarianti() {
        return varianti;
    }

    /**
     * @param varianti the varianti to set
     */
    public void setVarianti(List<Varianti> varianti) {
        this.varianti = varianti;
    }

    /**
     * @return the sconti
     */
    public List<Sconti> getSconti() {
        return sconti;
    }

    /**
     * @param sconti the sconti to set
     */
    public void setSconti(List<Sconti> sconti) {
        this.sconti = sconti;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the righeMgr
     */
    public RigheCommesseManager getRigheMgr() {
        return righeMgr;
    }

    /**
     * @param righeMgr the righeMgr to set
     */
    public void setRigheMgr(RigheCommesseManager righeMgr) {
        this.righeMgr = righeMgr;
    }

}
