package forms.estoque;

import components.CellRenderer;
import java.awt.event.ActionEvent;
import components.JPanelControleButtons;
import components.JTableDataBinderListener;
import java.util.Collection;
import javax.swing.JDialog;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
import model.estoque.MovimentacaoTipo;
import services.ServiceException;
import services.estoque.EstoqueMovimentacaoService;
import services.estoque.EstoqueService;
import utils.AlertaTipos;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmEstoque extends JPanelControleButtons {

    private final EstoqueMovimentacaoService service;

    @Override
    public void OnShow() {
        table.atualizar();
    }

    public FrmEstoque() {
        initComponents();
        table.setDefaultRenderer(Object.class, new CellRenderer());
        setBtnAddEnable(true);
        setBtnAlterarEnable(true);
        setBtnExcluirEnable(false);
        setBtnAtualizarEnable(true);

        service = new EstoqueMovimentacaoService();

        table.setListener(new JTableDataBinderListener<EstoqueMovimentacao>() {

            @Override
            public Collection<EstoqueMovimentacao> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "descricao");
            }

            @Override
            public Object[] addRow(EstoqueMovimentacao dado) {
                return new Object[]{dado.getId() + "",
                    dado.getDescricao(),
                    Utils.formataDate(dado.getDataLancamento()),
                    dado.getQuantidade() + "",
                    Utils.formataDinheiro(dado.getValorUnitario()),
                    Utils.formataDinheiro(dado.getValorUnitarioVenda())
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
                "Código", "Descrição", "Data", "Quantidade", "Custo Unitário", "Valor de Venda"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(550);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(100);
            table.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
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
        try {
            int id = table.getSelectedId();
            EstoqueMovimentacao estMov = service.findById(id);
            if (id < 1) {
                utils.Forms.mensagem("Selecione uma movimentação", AlertaTipos.erro);
            } else if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.entrada)
                    && !estMov.getMovimentacaoTipo().getDescricao().equals("AJUSTE ENTRADA")) {
                JDialog dialog = new FrmEstoqueAjuste(id);
                dialog.setVisible(true);
                table.atualizar();
            } else {
                utils.Forms.mensagem("Não é possível alterar essa movimentação", AlertaTipos.erro);
            }
        } catch (Exception e) {
            utils.Forms.mensagem(e.getMessage(), AlertaTipos.erro);
        }
    }

    @Override
    public void btnExcluirActionPerformed(ActionEvent evt) {

    }

    @Override
    public void btnAtualizarActionPerformed(ActionEvent evt) {
        table.atualizar();
    }
}
