/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import utils.AlertaTipos;
import utils.Forms;

public class JTableDataBinder<T> extends JTable {

    private JTextField busca;
    private RefreshWorker<T> worker;

    public JTableDataBinder() {
        super();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    public void setBusca(JTextField componente) {
        busca = componente;
        addKeyDownNavigation();
        busca.requestFocus();
    }
    List<Class> columnClasses;

    public Class getColumnClass(int column) {
        if (columnClasses != null && columnClasses.size() > column) {
            return columnClasses.get(column);
        }
        return super.getColumnClass(column);
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

    public void hideColumn(String nome) {
        getColumnModel().removeColumn(getColumn(nome));
    }

    public void atualizar() {
        atualizar(true);
    }

    public void scrollTo(int row) {
        scrollRectToVisible(new Rectangle(getCellRect(row, 0, true)));
    }

    public void scrollToSelected() {
        scrollTo(getSelectedRow());
    }

    public void atualizar(boolean keepSelected) {
        String textobusca = null;
        if (this.busca != null) {
            textobusca = this.busca.getText();
        }
        columnClasses = new ArrayList<>(getColumnCount());

        DefaultTableModel model = ((DefaultTableModel) this.getModel());
        if (worker != null) {
            worker.cancel(true);
        }
        worker = new RefreshWorker<>(listener, model, textobusca, this, columnClasses);
        final RefreshWorker<T> workerN = worker;
        EventQueue.invokeLater(() -> {
            workerN.runStart();

        });

//
//        int wasSelected = getSelectedRow();
//        DefaultTableModel model = ((DefaultTableModel) this.getModel());
//        Collection<T> c = null;
//        try {
//            c = listener.lista(textobusca);
//
//        } catch (Exception e) {
//            utils.Forms.mensagem(e.getMessage(), AlertaTipos.erro);
//            model.setRowCount(0);
//
//            return;
//        }
//
//        model.setRowCount(0);
//
//        try {
//            for (T linha : c) {
//                model.addRow(listener.addRow(linha));
//            }
//        } catch (Exception e) {
//            utils.Forms.log(e);
//        }
//
//        if (model.getRowCount() > wasSelected && wasSelected >= 0) {
//            setRowSelectionInterval(wasSelected, wasSelected);
//        } else if ((wasSelected - 1) == model.getRowCount() - 1) {
//            setRowSelectionInterval(wasSelected - 1, wasSelected - 1);
//
//        }
    }
    private JTableDataBinderListener listener;

    public void setListener(JTableDataBinderListener listener) {
        this.listener = listener;
    }

    public DefaultTableModel getDefaultTableModel() {
        return (DefaultTableModel) getModel();
    }

    public void startEditing(int row, int column) {
        editCellAt(row, column);
        setRowSelectionInterval(row, row);
        requestFocus();
//        DefaultCellEditor ed = (DefaultCellEditor) getCellEditor(row, column);
//
//        ed.shouldSelectCell(new ListSelectionEvent(this, row, row, true));
    }
}

class RefreshWorker<T> extends SwingWorker<DefaultTableModel, Object[]> {

    @Override
    protected void done() {
        if (isCancelled()) {
            return;
        }
        Forms.paraProgress();
        if (model.getRowCount() > wasSelected && wasSelected >= 0) {
            table.setRowSelectionInterval(wasSelected, wasSelected);
        } else if ((wasSelected - 1) == model.getRowCount() - 1 && model.getRowCount() > 0) {
            table.setRowSelectionInterval(wasSelected - 1, wasSelected - 1);

        }

        table.scrollToSelected();
    }
    private JTableDataBinderListener listener;
    private DefaultTableModel model;
    private String textoBusca;
    private int wasSelected = 0;
    private JTableDataBinder<T> table;
    private List<Class> columnClasses;

    public RefreshWorker(JTableDataBinderListener listener, DefaultTableModel model, String textoBusca, JTableDataBinder<T> table, List<Class> columnClasses) {
        this.listener = listener;
        this.model = model;
        this.textoBusca = textoBusca;
        this.wasSelected = table.getSelectedRow();
        model.setRowCount(0);
        this.table = table;
        this.columnClasses = columnClasses;
    }

    public void runStart() {
        if (isCancelled()) {
            return;
        }
        Forms.iniciaProgress();
        execute();
    }

    @Override
    protected DefaultTableModel doInBackground() throws Exception {

        Collection<T> c = null;
        try {
            c = listener.lista(textoBusca);

        } catch (Exception e) {
            utils.Forms.mensagem(e.getMessage(), AlertaTipos.erro);
            model.setRowCount(0);

            return null;
        }

        try {
            for (T linha : c) {

                Object[] o = listener.addRow(linha);
                if (columnClasses.size() == 0) {
                    for (int i = 0; i < o.length; i++) {
                        columnClasses.add(o[i] != null ? o[i].getClass() : Object.class);
                    }
                }
                publish(o);
                if (isCancelled()) {
                    break;
                }

            }
        } catch (Exception e) {
            utils.Forms.log(e);
        }
        return model;
    }

//        private final File file;
//        private final DefaultTableModel model;
//
//        private LogWorker(File file, DefaultTableModel model) {
//            this.file = file;
//            this.model = model;
//            model.setColumnIdentifiers(new Object[]{file.getAbsolutePath()});
//        }
//
//        @Override
//        protected TableModel doInBackground() throws Exception {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String s;
//            while ((s = br.readLine()) != null) {
//                publish(s);
//            }
//            return model;
//        }
//
    @Override
    protected void process(List<Object[]> chunks) {
        if (!isCancelled()) {
            chunks.stream().forEach((s) -> {
                model.addRow(s);
            });
        }
    }
}
