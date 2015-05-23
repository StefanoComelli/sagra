package beans;

import Manager.CasseManager;
import Manager.CategorieProdottiManager;
import Manager.GiorniManager;
import Manager.ListinoRealeManager;
import Manager.OperatoriManager;
import Manager.ScontiManager;
import Manager.VariantiManager;
import java.util.Date;
import java.util.List;
import model.Casse;
import model.CategorieProdotti;
import model.Giorni;
import model.ListinoReale;
import model.Operatori;
import model.Sconti;
import model.Varianti;
import org.jboss.logging.Logger;
import utils.IdDescr;

/**
 *
 * @author Stefano
 */
public class Cassa {

    private static final Logger LOGGER = Logger.getLogger(Cassa.class);
    private Casse cassa;
    private Date data;
    private Giorni giorno;
    private List<ListinoReale> listino;
    private Operatori operatore;
    private List<Varianti> varianti;
    private ListinoRealeManager listinoMgr;
    private List<Sconti> sconti;
    private Ordine ordine;
    private List<CategorieProdotti> categorie;

    /**
     *
     * @param giorno
     * @param cassa
     * @param operatore
     */
    public Cassa(String giorno, String cassa, String operatore) {

        IdDescr dGiorno = new IdDescr(giorno);
        IdDescr dCassa = new IdDescr(cassa);
        IdDescr dOperatore = new IdDescr(operatore);

        GiorniManager giorniMgr = new GiorniManager();
        this.giorno = giorniMgr.get(dGiorno.getId());
        this.data = this.giorno.getData();

        CasseManager casseMgr = new CasseManager();
        this.cassa = casseMgr.get(dCassa.getId());

        OperatoriManager operatoriMgr = new OperatoriManager();
        this.operatore = operatoriMgr.get(dOperatore.getId());

        listinoMgr = new ListinoRealeManager();

        VariantiManager variantiMgr = new VariantiManager();
        varianti = variantiMgr.getAllSorted();
        ScontiManager scontiMgr = new ScontiManager();
        sconti = scontiMgr.getAll();

        CategorieProdottiManager categorieProdottiManager = new CategorieProdottiManager();
        categorie = categorieProdottiManager.getAllSorted();

        RefreshListino();

    }

    /**
     *
     */
    public final void RefreshListino() {
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
     * @return the ordine
     */
    public Ordine getOrdine() {
        return ordine;
    }

    /**
     * @param ordine the ordine to set
     */
    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
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
     * @return the categorie
     */
    public List<CategorieProdotti> getCategorie() {
        return categorie;
    }

    /**
     * @param categorie the categorie to set
     */
    public void setCategorie(List<CategorieProdotti> categorie) {
        this.categorie = categorie;
    }
}
