package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Stefano
 */
public class DateUtils {

    /**
     *
     * @param year
     * @param month
     * @param date
     * @return
     */
    public static Date date(int year, int month, int date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(year, month, date, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     *
     * @param date
     * @return
     */
    public static String getStringFromDate(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return dateFormat.format(date);
    }
}
