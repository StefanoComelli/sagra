package Main;

import Manager.ListinoProdottiManager;
import model.ListinoProdotti;

/**
 *
 * @author Stefano
 */
public class Main {

    public static void main(String[] args) {

//        CategorieProdottiManager cpm = new CategorieProdottiManager();
// cpm.get(1);
//        List<CategorieProdotti> cps = cpm.select("FROM CategorieProdotti");
//        for (CategorieProdotti cp : cps) {
//            System.out.println(cp.getId() + " -" + cp.getDescrizione());
//        }
//
//        StatiOrdineManager som = new StatiOrdineManager();
//
//        List<StatiOrdine> sos = som.select("FROM StatiOrdine");
//        for (StatiOrdine so : sos) {
//            System.out.println(so.getId() + " - " + so.getDescrizione());
//        }
        ListinoProdottiManager lpm = new ListinoProdottiManager();
        ListinoProdotti lp = lpm.get(1000);

        if (lp != null) {
            System.out.println(lp.getCategoriaProdotto().getDescrizione());
        }

    }
}
