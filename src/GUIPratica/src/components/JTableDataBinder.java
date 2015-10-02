/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.util.Collection;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class JTableDataBinder<T> extends JTable {

    private JTextField busca;

    public void setBusca(JTextField componente) {
        busca = componente;
    }

    public void atualizar() {
        String textobusca = null;
        if (this.busca != null) {
            textobusca = this.busca.getText();
        }

        Collection<T> c = listener.lista(textobusca);

        DefaultTableModel model = ((DefaultTableModel) this.getModel());

        model.setRowCount(0);

        for (T linha : c) {
            model.addRow(listener.addRow(linha));
        }
    }
    private JTableDataBinderListener listener;

    public void setListener(JTableDataBinderListener listener) {
        this.listener = listener;
    }
}
