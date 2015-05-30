package utils;

/**
 *
 * @author Stefano
 */
public class MathUtils {

    /**
     *
     * @param valore
     * @return
     */
    public static float Arrotonda(float valore) {
        return (float) Math.round((double) valore * 10) / 10;
    }
}
