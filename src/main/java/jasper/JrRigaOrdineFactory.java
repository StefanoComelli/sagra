package jasper;

import java.util.Collection;
import beans.Ordine;
import database.DbConnection;
import java.util.ArrayList;
import java.util.List;
import model.RigheCommesse;

/**
 *
 * @author Stefano
 */
public class JrRigaOrdineFactory {

    private final Collection<JrRigaOrdine> data;
    private final DbConnection dbConnection;

    private boolean empty;

    /**
     *
     * @param dbConnection
     * @param ordine
     * @param flgBar
     */
    public JrRigaOrdineFactory(DbConnection dbConnection, Ordine ordine, boolean flgBar) {
        this.dbConnection = dbConnection;
        data = new ArrayList<>();
        int idCommessa = ordine.getCommessa().getId();
        List<RigheCommesse> righe;
        righe = ordine.getRigheMgr().getByCommessaStampa(idCommessa, flgBar);
        if (righe != null) {
            for (RigheCommesse riga : righe) {
                empty = false;
                JrRigaOrdine jRiga = new JrRigaOrdine(dbConnection, riga);
                data.add(jRiga);
            }
        } else {
            empty = true;
        }
    }

    /**
     *
     * @return
     */
    public Object[] getBeanArray() {
        return data.toArray(new JrRigaOrdine[data.size()]);
    }

    /**
     *
     * @return
     */
    public Collection<JrRigaOrdine> getBeanCollection() {
        return (data);
    }

    /**
     * @return the empty
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * @param empty the empty to set
     */
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

}
