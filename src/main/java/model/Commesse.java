package model;

import abstr.AbstractData;
import java.text.SimpleDateFormat;
import java.util.Set;
import org.jboss.logging.Logger;
import java.util.Calendar;
import java.util.Date;
import utils.StatoOrdine;

/**
 *
 * @author Stefano
 */
public class Commesse extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(Commesse.class);
    private Casse cassa;
    private Operatori operatore;
    private float totale;
    private float totalePagato;
    private float totaleContanti;
    private float totaleResto;
    private float scontoApplicato;
    private String nomeCliente;
    private String tavoloCliente;
    private String note;
    private Set<LogOrdini> logOrdine;
    private Giorni giorno;
    private int statoOrdine;
    private int coperti;
    private String descSconto;
    private boolean asporto;
    private Calendar orario;

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
     * @return the statoOrdine
     */
    public int getStatoOrdine() {
        return statoOrdine;
    }

    /**
     * @param statoOrdine the statoOrdine to set
     */
    public void setStatoOrdine(int statoOrdine) {
        this.statoOrdine = statoOrdine;
    }

    /**
     * @return the coperti
     */
    public int getCoperti() {
        return coperti;
    }

    /**
     * @param coperti the coperti to set
     */
    public void setCoperti(int coperti) {
        this.coperti = coperti;
    }

    /**
     * @return the descSconto
     */
    public String getDescSconto() {
        return descSconto;
    }

    /**
     * @param descSconto the descSconto to set
     */
    public void setDescSconto(String descSconto) {
        this.descSconto = descSconto;
    }

    /**
     * @return the asporto
     */
    public boolean isAsporto() {
        return asporto;
    }

    /**
     * @param asporto the asporto to set
     */
    public void setAsporto(boolean asporto) {
        this.asporto = asporto;
    }

    /**
     * @return the totale
     */
    public float getTotale() {
        return totale;
    }

    /**
     * @param totale the totale to set
     */
    public void setTotale(float totale) {
        this.totale = totale;
    }

    /**
     * @return the orario
     */
    public Calendar getOrario() {
        return orario;
    }

    /**
     * @param orario the orario to set
     */
    public void setOrario(Calendar orario) {
        this.orario = orario;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        String testo;
        StatoOrdine stOrdine = new StatoOrdine(getStatoOrdine());
        Date date = getOrario().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM");
        testo = dateFormat.format(date) + " " + stOrdine.toString();
        return testo;

    }
}
