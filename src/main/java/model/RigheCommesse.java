package model;

import Manager.ListinoProdottiManager;
import abstr.AbstractData;
import database.DbConnection;
import org.jboss.logging.Logger;
import utils.IdDescr;
import utils.Valuta;

/**
 *
 * @author Stefano
 */
public class RigheCommesse extends AbstractData<Integer> {

    private static final Logger LOGGER = Logger.getLogger(RigheCommesse.class);
    private int idProdotto;
    private int quantita;
    private String varianti;
    private float prezzoListino;
    private int idCommessa;
    private final DbConnection dbConnection;

    /**
     *
     */
    public RigheCommesse() {
        dbConnection = null;
    }

    /**
     *
     * @param dbConnection
     */
    public RigheCommesse(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * @return the varianti
     */
    public String getVarianti() {
        if (varianti != null) {
            return varianti;
        } else {
            return "";
        }
    }

    /**
     *
     * @return
     */
    private ListinoProdotti getProdotto() {
        if (dbConnection != null) {
            ListinoProdottiManager mgrProdotto = new ListinoProdottiManager(dbConnection);
            return mgrProdotto.get(idProdotto);
        } else {
            return null;
        }
    }

    /**
     * @param varianti the varianti to set
     */
    public void setVarianti(String varianti) {
        this.varianti = varianti;
    }

    /**
     * @return the prezzoListino
     */
    public float getPrezzoListino() {
        return prezzoListino;
    }

    /**
     * @param prezzoListino the prezzoListino to set
     */
    public void setPrezzoListino(float prezzoListino) {
        this.prezzoListino = prezzoListino;
    }

    /**
     * @return the quantita
     */
    public int getQuantita() {
        return quantita;
    }

    /**
     * @param quantita the quantita to set
     */
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    /**
     *
     * @return
     */
    public IdDescr getIdDescrProdotto() {
        return new IdDescr(this.getProdotto().getId(), this.getProdotto().getNomeProdotto());
    }

    /**
     *
     * @return
     */
    public Object[] getRow() {
        Valuta prezzo = new Valuta(prezzoListino);
        return new Object[]{
            this.getProdotto().getNomeProdotto(),
            prezzo.toString(),
            getQuantita(),
            getVarianti(),
            this.getProdotto().getId(),
            getId()
        };
    }

    /**
     * @return the idProdotto
     */
    public int getIdProdotto() {
        return idProdotto;
    }

    /**
     * @param idProdotto the idProdotto to set
     */
    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    /**
     * @return the idCommessa
     */
    public int getIdCommessa() {
        return idCommessa;
    }

    /**
     * @param idCommessa the idCommessa to set
     */
    public void setIdCommessa(int idCommessa) {
        this.idCommessa = idCommessa;
    }

    /**
     *
     * @param deltaQuantita
     */
    public void DeltaQuantita(int deltaQuantita) {
        this.quantita = this.quantita + deltaQuantita;
    }
}
