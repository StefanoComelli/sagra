package model;

import abstr.AbstractData;
import java.util.Set;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Commesse extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(Commesse.class);
    private int idCassa;
    private int idOperatore;
    private float totalePagato;
    private float totaleContanti;
    private float totaleResto;
    private float scontoApplicato;
    private String nomeCliente;
    private String tavoloCliente;
    private String note;
    private Set<RigheCommesse> righeCommesse;
    private Set<LogOrdini> logOrdine;
    private int idGiorno;
    int idStatoOrdine;

    /**
     * @return the idCassa
     */
    public int getIdCassa() {
        return idCassa;
    }

    /**
     * @param idCassa the idCassa to set
     */
    public void setIdCassa(int idCassa) {
        this.idCassa = idCassa;
    }

    /**
     * @return the idOperatore
     */
    public int getIdOperatore() {
        return idOperatore;
    }

    /**
     * @param idOperatore the idOperatore to set
     */
    public void setIdOperatore(int idOperatore) {
        this.idOperatore = idOperatore;
    }

    /**
     * @return the totalePagato
     */
    public float getTotalePagato() {
        return totalePagato;
    }

    /**
     * @param totalePagato the totalePagato to set
     */
    public void setTotalePagato(float totalePagato) {
        this.totalePagato = totalePagato;
    }

    /**
     * @return the totaleContanti
     */
    public float getTotaleContanti() {
        return totaleContanti;
    }

    /**
     * @param totaleContanti the totaleContanti to set
     */
    public void setTotaleContanti(float totaleContanti) {
        this.totaleContanti = totaleContanti;
    }

    /**
     * @return the totaleResto
     */
    public float getTotaleResto() {
        return totaleResto;
    }

    /**
     * @param totaleResto the totaleResto to set
     */
    public void setTotaleResto(float totaleResto) {
        this.totaleResto = totaleResto;
    }

    /**
     * @return the scontoApplicato
     */
    public float getScontoApplicato() {
        return scontoApplicato;
    }

    /**
     * @param scontoApplicato the scontoApplicato to set
     */
    public void setScontoApplicato(float scontoApplicato) {
        this.scontoApplicato = scontoApplicato;
    }

    /**
     * @return the nomeCliente
     */
    public String getNomeCliente() {
        return nomeCliente;
    }

    /**
     * @param nomeCliente the nomeCliente to set
     */
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    /**
     * @return the tavoloCliente
     */
    public String getTavoloCliente() {
        return tavoloCliente;
    }

    /**
     * @param tavoloCliente the tavoloCliente to set
     */
    public void setTavoloCliente(String tavoloCliente) {
        this.tavoloCliente = tavoloCliente;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the righeCommesse
     */
    public Set<RigheCommesse> getRigheCommesse() {
        return righeCommesse;
    }

    /**
     * @param righeCommesse the righeCommesse to set
     */
    public void setRigheCommesse(Set<RigheCommesse> righeCommesse) {
        this.righeCommesse = righeCommesse;
    }

    /**
     * @return the logOrdine
     */
    public Set<LogOrdini> getLogOrdine() {
        return logOrdine;
    }

    /**
     * @param logOrdine the logOrdine to set
     */
    public void setLogOrdine(Set<LogOrdini> logOrdine) {
        this.logOrdine = logOrdine;
    }

    /**
     * @return the idGiorno
     */
    public int getIdGiorno() {
        return idGiorno;
    }

    /**
     * @param idGiorno the idGiorno to set
     */
    public void setIdGiorno(int idGiorno) {
        this.idGiorno = idGiorno;
    }

    /**
     * @return the idStato
     */
    public int getIdStatoOrdine() {
        return idStatoOrdine;
    }

    /**
     * @param idStatoOrdine
     */
    public void setIdStatoOrdine(int idStatoOrdine) {
        this.idStatoOrdine = idStatoOrdine;
    }

}
