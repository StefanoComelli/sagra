package utils;

import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class IdDescr {

    private static final Logger LOGGER = Logger.getLogger(IdDescr.class);

    private final String idDescr;

    /**
     *
     * @param idDescr
     */
    public IdDescr(String idDescr) {
        this.idDescr = idDescr;
    }

    /**
     *
     * @return
     */
    private String[] getSplit() {
        return idDescr.split(" - ");
    }

    /**
     *
     * @return
     */
    public int getId() {
        int id;
        String idString;
        try {

            idString = getSplit()[0];
            id = Integer.parseInt(idString);
        } catch (Exception e) {
            id = 0;
        }

        return id;
    }

    /**
     *
     * @return
     */
    public String getDescr() {
        String descr;
        try {

            descr = getSplit()[1];
        } catch (Exception e) {
            descr = "";
        }

        return descr;
    }
}
