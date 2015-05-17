package jasper;

import beans.Ordine;
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
     * @return
     */
    public HashMap getHashMap() {
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

}
