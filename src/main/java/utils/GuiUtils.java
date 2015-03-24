package utils;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Stefano
 */
public class GuiUtils {

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
