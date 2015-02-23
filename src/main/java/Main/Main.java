package Main;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

        Cassa cassa = new Cassa(gData.getTime());
    }
}
