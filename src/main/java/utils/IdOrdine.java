package utils;

/**
 *
 * @author Stefano
 */
public class IdOrdine {

    private final int PREFIX = 201600000;

    private int id = 0;
    private int barcode = 0;

    /**
     *
     * @return
     */
    public boolean isOk() {
        return (getId() > 0);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
        barcode = id + PREFIX;
    }

    /**
     * @return the barcode
     */
    public int getBarcode() {
        return barcode;
    }

    /**
     * @param barcode the barcode to set
     */
    public void setBarcode(int barcode) {
        this.barcode = barcode;
        id = barcode - PREFIX;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        if (id == 0) {
            return "";
        } else {
            return " - Ultimo Ordine: " + String.valueOf(id + PREFIX);
        }
    }
}
