package utils;

import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class IdDescr {

    private static final Logger LOGGER = Logger.getLogger(IdDescr.class);

    private final String SEP = " - ";
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
     * @param idDescr
     */
    public IdDescr(Object idDescr) {
        this.idDescr = (String) idDescr;
    }

    /**
     *
     * @param id
     * @param descr
     */
    public IdDescr(String id, String descr) {
        this.idDescr = id + SEP + descr;
    }

    /**
     *
     * @param id
     * @param descr
     */
    public IdDescr(Integer id, String descr) {
        this.idDescr = id.toString() + SEP + descr;
    }

    /**
     *
     * @return
     */
    private String[] getSplit() {
        return idDescr.split(SEP);
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
            //LOGGER.warn(e);
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

    /**
     *
     * @return
     */
    public String getIdDescr() {
        return idDescr;
    }
}
