package beans;

import Manager.CasseManager;
import Manager.GiorniManager;
import Manager.OperatoriManager;
import java.util.List;
import model.Casse;
import model.Giorni;
import model.Operatori;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Login {

    private static final Logger LOGGER = Logger.getLogger(Login.class);
    private List<Casse> casse;
    private List<Operatori> operatori;
    private List<Giorni> giorni;

    /**
     *
     */
    public Login() {
        CasseManager casseMgr = new CasseManager();
        this.casse = casseMgr.getAll();
        OperatoriManager operatoriMgr = new OperatoriManager();
        this.operatori = operatoriMgr.getAll();
        GiorniManager giorniMgr = new GiorniManager();
        //this.giorni = giorniMgr.getAll();
        this.giorni = giorniMgr.getOggi();
    }

    /**
     * @return the casse
     */
    public List<Casse> getCasse() {
        return casse;
    }

    /**
     * @param casse the casse to set
     */
    public void setCasse(List<Casse> casse) {
        this.casse = casse;
    }

    /**
     * @return the operatori
     */
    public List<Operatori> getOperatori() {
        return operatori;
    }

    /**
     * @param operatori the operatori to set
     */
    public void setOperatori(List<Operatori> operatori) {
        this.operatori = operatori;
    }

    /**
     * @return the giorni
     */
    public List<Giorni> getGiorni() {
        return giorni;
    }

    /**
     * @param giorni the giorni to set
     */
    public void setGiorni(List<Giorni> giorni) {
        this.giorni = giorni;
    }
}
