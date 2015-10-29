/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author gustavo
 */
public class CellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    public CellRenderer() {
        super();
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        this.setHorizontalAlignment(CENTER);

        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }
}
