package forms.estoque;

import components.CellRenderer;
import java.awt.event.ActionEvent;
import components.JPanelControleButtons;
import components.JTableDataBinderListener;
import java.util.Collection;
import javax.swing.JDialog;
import model.estoque.Estoque;
import services.ServiceException;
import services.estoque.EstoqueService;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmEstoque extends JPanelControleButtons {

    private final EstoqueService service;

    public FrmEstoque() {
        initComponents();
        table.setDefaultRenderer(Object.class, new CellRenderer());
        setBtnAddEnable(true);
        setBtnAlterarEnable(true);
        setBtnExcluirEnable(true);
        setBtnAtualizarEnable(true);

        service = new EstoqueService();

        table.setListener(new JTableDataBinderListener<Estoque>() {

            @Override
            public Collection<Estoque> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "item.descricao", "lote");
            }

            @Override
            public Object[] addRow(Estoque dado) {
                return new Object[]{dado.getId(),
                    dado.getItem().getDescricao(),
                    dado.getDataCompra(),
                    dado.getLote(),
                    dado.getQuantidadeDisponivel(),
                    Utils.formataDinheiro(dado.getValorUnitario()),
                    dado.getDataValidade()
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
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Item", "Data de Compra", "Lote", "Quantidade Disponível", "Valor Unitário", "Data de Validade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setBusca(txtBuscar);
        jScrollPane2.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(60);
            table.getColumnModel().getColumn(1).setPreferredWidth(450);
            table.getColumnModel().getColumn(2).setPreferredWidth(150);
            table.getColumnModel().getColumn(3).setPreferredWidth(150);
            table.getColumnModel().getColumn(4).setPreferredWidth(150);
            table.getColumnModel().getColumn(5).setPreferredWidth(150);
            table.getColumnModel().getColumn(6).setPreferredWidth(150);
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
        JDialog dialog = new FrmEstoqueCadastro();
        dialog.setVisible(true);
        table.atualizar();
    }

    @Override
    public void btnAlterarActionPerformed(ActionEvent evt) {
        defaultUpdateOperation(table, (i) -> {
            JDialog dialog = new FrmEstoqueCadastro(i);
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
