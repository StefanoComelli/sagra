package guiUtils;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Stefano
 */
public class BetterTableCellRenderer extends DefaultTableCellRenderer {

    /**
     * 
     * @param table
     * @param value
     * @param selected
     * @param focused
     * @param row
     * @param column
     * @return 
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        setEnabled(table == null || table.isEnabled());
        return super.getTableCellRendererComponent(table, value, selected, focused, row, column);
    }
}
