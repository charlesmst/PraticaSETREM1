/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Charles
 */
public class JDataTableModel extends AbstractTableModel {

    String[] columnNames;

    private JDataTable dataTable;
    public JDataTableModel(JDataTable dt, String[] columnNames) {

        dataTable = dt;
        this.columnNames = new String[dt.getColumnCount()];

        for (int i = 0; i < dt.getColumnCount(); i++) {
            if (columnNames != null && columnNames.length > i) {
                this.columnNames[i] = columnNames[i];
            } else {
                this.columnNames[i] = dt.getColumnName(i + 1);
            }
        }
    }
    public JDataTableModel(JDataTable dt){
        this(dt,null);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return dataTable.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return  columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return dataTable.getValueAt(rowIndex,columnIndex);
    }

}
