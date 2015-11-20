/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Component;
import java.util.HashMap;
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
        defaultAll=CENTER;
    }
    public CellRenderer(int all) {
        super();
        defaultAll=all;
    }
    private int defaultAll;
    private HashMap<Integer, Integer> columns =  new HashMap<>();

    public void setAlign(int column, int align) {
        columns.put(column, align);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (columns.containsKey(column)) {
            this.setHorizontalAlignment(columns.get(column));

        } else {
            this.setHorizontalAlignment(defaultAll);
        }

        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }
}
