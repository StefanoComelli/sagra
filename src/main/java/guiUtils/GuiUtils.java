package guiUtils;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class GuiUtils {

    private static final Logger LOGGER = Logger.getLogger(GuiUtils.class);

    /**
     *
     * @param jTable
     */
    public static void EmptyJtable(JTable jTable) {
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();

        int rows = model.getRowCount();
        for (int i = rows - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
}
