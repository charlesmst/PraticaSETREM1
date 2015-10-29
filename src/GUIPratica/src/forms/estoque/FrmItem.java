
package forms.estoque;

import components.CellRenderer;
import java.awt.event.ActionEvent;
import components.JPanelControleButtons;
import components.JTableDataBinderListener;
import java.util.Collection;
import javax.swing.JDialog;
import model.estoque.Item;
import services.ServiceException;
import services.estoque.ItemService;

/**
 *
 * @author Gustavo
 */
public class FrmItem extends JPanelControleButtons {

    private final ItemService service;
//    JTableDataBinder table;

    public FrmItem() {
        initComponents();
        table.setDefaultRenderer(Object.class, new CellRenderer());
        setBtnAddEnable(true);
        setBtnAlterarEnable(true);
        setBtnExcluirEnable(true);
        setBtnAtualizarEnable(true);

        service = new ItemService();

        table.setListener(new JTableDataBinderListener<Item>() {

            @Override
            public Collection<Item> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "descricao", "itemTipo.nome", "prateleira.descricao");

            }

            @Override
            public Object[] addRow(Item dado) {
                return new Object[]{dado.getId(),
                    dado.getItemTipo().getNome(),
                    dado.getDescricao(),
                    dado.getEstoqueMinimo(),
                    dado.getPrateleira().getDescricao(),
                    dado.getUltimoValorVenda()
                };

            }
        });

        table.setBusca(txtBuscar, true);
        table.atualizar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        table = new components.JTableDataBinder();
        txtBuscar = new components.JTextFieldUpper(true);
        jLabel2 = new javax.swing.JLabel();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Tipo de Item", "Descrição", "Limite Mínimo", "Prateleira", "Último Valor de Venda"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setBusca(txtBuscar);
        jScrollPane2.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(60);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(460);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(150);
            table.getColumnModel().getColumn(5).setPreferredWidth(150);
        }

        jLabel2.setText(" Buscar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private components.JTableDataBinder table;
    private components.JTextFieldUpper txtBuscar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void btnAddActionPerformed(ActionEvent evt) {
        JDialog dialog = new FrmItemCadastro();
        dialog.setVisible(true);
        table.atualizar();
    }

    @Override
    public void btnAlterarActionPerformed(ActionEvent evt) {
        defaultUpdateOperation(table, (i) -> {
            JDialog dialog = new FrmItemCadastro(i);
            dialog.setVisible(true);
        });

    }

    @Override
    public void btnExcluirActionPerformed(ActionEvent evt) {
        defaultDeleteOperation(table, (i) -> service.delete(i));
    }

    @Override
    public void btnAtualizarActionPerformed(ActionEvent evt) {
        table.atualizar();
    }
}
