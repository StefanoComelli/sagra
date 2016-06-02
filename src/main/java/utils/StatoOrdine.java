package utils;

/**
 *
 * @author Stefano
 */
public class StatoOrdine {

    private int stato;

    /**
     *
     * @param stato
     */
    public StatoOrdine(int stato) {
        this.stato = stato;
    }

    /**
     * @return the stato
     */
    public int getStato() {
        return stato;
    }

    /**
     * @param stato the stato to set
     */
    public void setStato(int stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        String testo;
        switch (stato) {
            case 1:
                testo = "I";
                break;
            case 2:
                testo = "C";
                break;
            case 3:
                testo = "E";
                break;
            case 4:
                testo = "X";
                break;
            default:
                testo = " ";

        }
        return testo;
    }

}
