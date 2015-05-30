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

    /**
     *
     * @param dbConnection
     * @param ordine
     */
    public JrRigaOrdineFactory(DbConnection dbConnection, Ordine ordine) {
        this.dbConnection = dbConnection;
        data = new ArrayList<>();
        int idCommessa = ordine.getCommessa().getId();
        List<RigheCommesse> righe = ordine.getRigheMgr().getByCommessa(idCommessa);

        if (righe != null) {
            for (RigheCommesse riga : righe) {
                JrRigaOrdine jRiga = new JrRigaOrdine(dbConnection, riga);
                data.add(jRiga);
            }
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

}
