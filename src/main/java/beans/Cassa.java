package beans;

import Manager.CasseManager;
import Manager.CategorieProdottiManager;
import Manager.GiorniManager;
import Manager.ListinoRealeManager;
import Manager.OperatoriManager;
import Manager.ScontiManager;
import Manager.StatiOrdineManager;
import Manager.VariantiManager;
import database.DbConnection;
import java.util.Date;
import java.util.List;
import model.Casse;
import model.CategorieProdotti;
import model.Giorni;
import model.ListinoReale;
import model.Operatori;
import model.Sconti;
import model.StatiOrdine;
import model.Varianti;
import org.jboss.logging.Logger;
import utils.IdDescr;

/**
 *
 * @author Stefano
 */
public class Cassa {

    private static final Logger LOGGER = Logger.getLogger(Cassa.class);
    private final Casse cassa;
    private final Date data;
    private final Giorni giorno;
    private List<ListinoReale> listino;
    private final Operatori operatore;
    private final List<Varianti> varianti;
    private final ListinoRealeManager listinoMgr;
    private final List<Sconti> sconti;
    private Ordine ordine;
    private final List<CategorieProdotti> categorie;
    private final List<StatiOrdine> statiOrdine;
    private final DbConnection dbConnection;

    /**
     *
     * @param dbConnection
     * @param giorno
     * @param cassa
     * @param operatore
     */
    public Cassa(DbConnection dbConnection, String giorno, String cassa, String operatore) {
        this.dbConnection = dbConnection;
        IdDescr dGiorno = new IdDescr(giorno);
        IdDescr dCassa = new IdDescr(cassa);
        IdDescr dOperatore = new IdDescr(operatore);

        GiorniManager giorniMgr = new GiorniManager(dbConnection);
        this.giorno = giorniMgr.get(dGiorno.getId());
        this.data = this.giorno.getData();

        CasseManager casseMgr = new CasseManager(dbConnection);
        this.cassa = casseMgr.get(dCassa.getId());

        OperatoriManager operatoriMgr = new OperatoriManager(dbConnection);
        this.operatore = operatoriMgr.get(dOperatore.getId());

        listinoMgr = new ListinoRealeManager(dbConnection);

        VariantiManager variantiMgr = new VariantiManager(dbConnection);
        varianti = variantiMgr.getAllSorted();
        ScontiManager scontiMgr = new ScontiManager(dbConnection);
        sconti = scontiMgr.getAll();

        CategorieProdottiManager categorieProdottiManager = new CategorieProdottiManager(dbConnection);
        categorie = categorieProdottiManager.getAllSorted();

        StatiOrdineManager statiOrdineMgr = new StatiOrdineManager(dbConnection);
        statiOrdine = statiOrdineMgr.getElencoStati();
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
     * @return the giorno
     */
    public Giorni getGiorno() {
        return giorno;
    }

    /**
     * @return the listino
     */
    public List<ListinoReale> getListino() {
        return listino;
    }

    /**
     * @return the listinoMgr
     */
    public ListinoRealeManager getListinoMgr() {
        return listinoMgr;
    }

    /**
     * @return the operatore
     */
    public Operatori getOperatore() {
        return operatore;
    }

    /**
     * @return the varianti
     */
    public List<Varianti> getVarianti() {
        return varianti;
    }

    /**
     * @return the sconti
     */
    public List<Sconti> getSconti() {
        return sconti;
    }

    /**
     * @return the ordine
     */
    public Ordine getOrdine() {
        return ordine;
    }

    /**
     * @return the cassa
     */
    public Casse getCassa() {
        return cassa;
    }

    /**
     * @return the categorie
     */
    public List<CategorieProdotti> getCategorie() {
        return categorie;
    }

    /**
     * @return the statiOrdine
     */
    public List<StatiOrdine> getStatiOrdine() {
        return statiOrdine;
    }

}
