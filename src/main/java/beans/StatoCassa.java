package beans;

import Manager.CommesseManager;
import database.DbConnection;
import model.Casse;
import model.Giorni;
import utils.Valuta;

/**
 *
 * @author Stefano
 */
public class StatoCassa {

    private final DbConnection dbConnection;
    private final CommesseManager commesseManager;
    private final Casse cassa;
    private final Giorni giorno;

    private long numeroOrdini;
    private double totaleOrdini;
    private double totaleSconti;
    private double totalePagato;
    private long numeroAsporto;
    private long numeroScontiOmaggio;
    private long numeroScontiOperatore;

    /**
     *
     * @param dbConnection
     * @param cassa
     * @param giorno
     */
    public StatoCassa(DbConnection dbConnection, Casse cassa, Giorni giorno) {
        this.dbConnection = dbConnection;
        this.cassa = cassa;
        this.giorno = giorno;
        commesseManager = new CommesseManager(dbConnection);
    }

    /**
     *
     */
    public void Refresh() {
        commesseManager.getStatoCassa(cassa, giorno, this);
    }

    /**
     * @return the numeroOrdini
     */
    public long getNumeroOrdini() {
        return numeroOrdini;
    }

    /**
     * @param numeroOrdini the numeroOrdini to set
     */
    public void setNumeroOrdini(long numeroOrdini) {
        this.numeroOrdini = numeroOrdini;
    }

    /**
     * @return the totaleOrdini
     */
    public double getTotaleOrdini() {
        return totaleOrdini;
    }

    /**
     * @param totaleOrdini the totaleOrdini to set
     */
    public void setTotaleOrdini(double totaleOrdini) {
        this.totaleOrdini = totaleOrdini;
    }

    /**
     * @return the totaleSconti
     */
    public double getTotaleSconti() {
        return totaleSconti;
    }

    /**
     * @param totaleSconti the totaleSconti to set
     */
    public void setTotaleSconti(double totaleSconti) {
        this.totaleSconti = totaleSconti;
    }

    /**
     * @return the totalePagato
     */
    public double getTotalePagato() {
        return totalePagato;
    }

    /**
     * @param totalePagato the totalePagato to set
     */
    public void setTotalePagato(double totalePagato) {
        this.totalePagato = totalePagato;
    }

    /**
     * @return the numeroAsporto
     */
    public long getNumeroAsporto() {
        return numeroAsporto;
    }

    /**
     * @param numeroAsporto the numeroAsporto to set
     */
    public void setNumeroAsporto(long numeroAsporto) {
        this.numeroAsporto = numeroAsporto;
    }

    /**
     * @return the numeroScontiOmaggio
     */
    public long getNumeroScontiOmaggio() {
        return numeroScontiOmaggio;
    }

    /**
     * @param numeroScontiOmaggio the numeroScontiOmaggio to set
     */
    public void setNumeroScontiOmaggio(long numeroScontiOmaggio) {
        this.numeroScontiOmaggio = numeroScontiOmaggio;
    }

    /**
     * @return the numeroScontiOperatore
     */
    public long getNumeroScontiOperatore() {
        return numeroScontiOperatore;
    }

    /**
     * @param numeroScontiOperatore the numeroScontiOperatore to set
     */
    public void setNumeroScontiOperatore(long numeroScontiOperatore) {
        this.numeroScontiOperatore = numeroScontiOperatore;
    }

    @Override
    /**
     *
     */
    public String toString() {
        String out;
        Valuta vTotaleOrdini = new Valuta(totaleOrdini);
        Valuta vTotaleSconti = new Valuta(totaleSconti);
        Valuta vTotalePagato = new Valuta(totalePagato);

        out = "Ordini:" + Long.toString(numeroOrdini);
        out = out + " - Totale:" + vTotaleOrdini.toString()
                + " Sconti:" + vTotaleSconti.toString()
                + " Pagato:" + vTotalePagato.toString();
        out = out + " - Asporto:" + Long.toString(numeroAsporto)
                + " Omaggi:" + Long.toString(numeroScontiOmaggio)
                + " Operatori:" + Long.toString(numeroScontiOperatore);
        return out;
    }

}
