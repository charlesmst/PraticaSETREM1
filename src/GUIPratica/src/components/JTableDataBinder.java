/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collection;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import utils.AlertaTipos;

public class JTableDataBinder<T> extends JTable {

    private JTextField busca;

    public JTableDataBinder() {
        super();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    public void setBusca(JTextField componente) {
        busca = componente;
        addKeyDownNavigation();
        busca.requestFocus();
    }

    private void addKeyDownNavigation() {
        final JTable j = this;
        busca.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    j.requestFocus();
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP && getSelectedRow() <= 0) {

                    busca.requestFocus();
                }
            }
        });
    }

    public void runOnSelected(ThrowingConsumer<Integer> callback) {
        if (getSelectedId() > 0) {
            callback.accept(getSelectedId());
            atualizar();
        } else {
            utils.Forms.mensagem(utils.Mensagens.selecioneUmItem, AlertaTipos.erro);
        }
    }

    public int getSelectedId() {
        int selected = getSelectedRow();
        if (selected >= 0) {

            DefaultTableModel model = ((DefaultTableModel) this.getModel());
            return Integer.parseInt(model.getValueAt(selected, 0).toString());
        } else {
            return 0;
        }
    }

    public void atualizar() {
        atualizar(true);
    }

    public void atualizar(boolean keepSelected) {
        String textobusca = null;
        if (this.busca != null) {
            textobusca = this.busca.getText();
        }

        int wasSelected = getSelectedRow();
        DefaultTableModel model = ((DefaultTableModel) this.getModel());
        Collection<T> c = null;
        try {
            c = listener.lista(textobusca);

        } catch (Exception e) {
            utils.Forms.mensagem(e.getMessage(), AlertaTipos.erro);
            model.setRowCount(0);

            return;
        }

        model.setRowCount(0);

        for (T linha : c) {
            model.addRow(listener.addRow(linha));
        }

        if (model.getRowCount() > wasSelected && wasSelected >= 0) {
            setRowSelectionInterval(wasSelected, wasSelected);
        } else if ((wasSelected - 1) == model.getRowCount() - 1) {
            setRowSelectionInterval(wasSelected - 1, wasSelected - 1);

        }
    }
    private JTableDataBinderListener listener;

    public void setListener(JTableDataBinderListener listener) {
        this.listener = listener;
    }
    public DefaultTableModel getDefaultTableModel(){
        return (DefaultTableModel)getModel();
    }
}
