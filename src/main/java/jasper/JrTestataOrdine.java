package jasper;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Stefano
 */
public class JrTestataOrdine {

    private Date data;
    private String cliente;
    private int coperti;
    private String cassa;
    private String cassiere;
    private float scontoDaApplicare;
    private float totale;
    private float sconto;
    private float netto;
    private boolean asporto;
    private int id;
    private String tipoSconto;
    private String tavolo;
    private String destinazione;

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
     *
     * @param flgBar
     * @return
     */
    public HashMap getHashMap(boolean flgBar) {

        HashMap parameters = new HashMap();

        parameters.put("data", this.data);
        parameters.put("cliente", this.cliente);
        parameters.put("coperti", this.coperti);
        parameters.put("cassa", cassa);
        parameters.put("cassiere", cassiere);
        parameters.put("scontoDaApplicare", scontoDaApplicare);
        parameters.put("totale", totale);
        parameters.put("sconto", sconto);
        parameters.put("netto", netto);
        parameters.put("asporto", asporto);
        parameters.put("id", id);
        parameters.put("tavolo", tavolo);
        parameters.put("tipoSconto", tipoSconto);
        if (flgBar) {
            parameters.put("destinazione", "CONSEGNARE AL BAR");
        } else if (asporto) {
            parameters.put("destinazione", "ATTENDERE ALLA CASSA BAR");
        } else {
            parameters.put("destinazione", "ATTENDERE AL VOSTRO TAVOLO");
        }

        return parameters;
    }

    /**
     * @return the cassa
     */
    public String getCassa() {
        return cassa;
    }

    /**
     * @param cassa the cassa to set
     */
    public void setCassa(String cassa) {
        this.cassa = cassa;
    }

    /**
     * @return the cassiere
     */
    public String getCassiere() {
        return cassiere;
    }

    /**
     * @param cassiere the cassiere to set
     */
    public void setCassiere(String cassiere) {
        this.cassiere = cassiere;
    }

    /**
     * @return the scontoDaApplicare
     */
    public float getScontoDaApplicare() {
        return scontoDaApplicare;
    }

    /**
     * @param scontoDaApplicare the scontoDaApplicare to set
     */
    public void setScontoDaApplicare(float scontoDaApplicare) {
        this.scontoDaApplicare = scontoDaApplicare;
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
     * @return the sconto
     */
    public float getSconto() {
        return sconto;
    }

    /**
     * @param sconto the sconto to set
     */
    public void setSconto(float sconto) {
        this.sconto = sconto;
    }

    /**
     * @return the netto
     */
    public float getNetto() {
        return netto;
    }

    /**
     * @param netto the netto to set
     */
    public void setNetto(float netto) {
        this.netto = netto;
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
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the tipoSconto
     */
    public String getTipoSconto() {
        return tipoSconto;
    }

    /**
     * @param tipoSconto the tipoSconto to set
     */
    public void setTipoSconto(String tipoSconto) {
        this.tipoSconto = tipoSconto;
    }

    /**
     * @return the tavolo
     */
    public String getTavolo() {
        return tavolo;
    }

    /**
     * @param tavolo the tavolo to set
     */
    public void setTavolo(String tavolo) {
        this.tavolo = tavolo;
    }

    /**
     * @return the destinazione
     */
    public String getDestinazione() {
        return destinazione;
    }

    /**
     * @param destinazione the destinazione to set
     */
    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

}
