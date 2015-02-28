package Main;

import beans.Cassa;
import java.util.Calendar;
import java.util.GregorianCalendar;
import model.Operatori;

/**
 *
 * @author Stefano
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        GregorianCalendar gData = new GregorianCalendar(2015, Calendar.MAY, 29);
        gData.setLenient(false);

        Operatori operatore = new Operatori();

        Cassa cassa = new Cassa(gData.getTime(), operatore);
    }
}
