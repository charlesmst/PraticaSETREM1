/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.ordem;

import components.CellRenderer;
import java.awt.event.ActionEvent;
import components.JPanelControleButtons;

import components.JTableDataBinderListener;
import java.util.Collection;
import javax.swing.JDialog;
import model.ordem.Ordem;
import services.ServiceException;
import services.ordem.OrdemService;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmOrdem extends JPanelControleButtons {

    private final OrdemService service;
//    JTableDataBinder table;

    public FrmOrdem() {
        initComponents();
        table.setDefaultRenderer(Object.class, new CellRenderer());
        setBtnAddEnable(true);
        setBtnAlterarEnable(true);
        setBtnExcluirEnable(true);
        setBtnAtualizarEnable(true);

        service = new OrdemService();

        table.setListener(new JTableDataBinderListener<Ordem>() {

            @Override
            public Collection<Ordem> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id","descricao","veiculo.placa","pessoa.nome","ordemStatus.nome");

            }

            @Override
            public Object[] addRow(Ordem dado) {
                return new Object[]{
                    dado.getId()+"", 
                    dado.getDescricao(),
                    Utils.formataDate(dado.getPrazo()),
                    dado.getPessoa().getNome(),
                    dado.getVeiculo() != null? dado.getVeiculo().toString():"",
                    dado.getOrdemStatus().getNome()
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

            },
            new String [] {
                "Código", "Descrição", "Prazo", "Cliente", "Veículo", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
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
            table.getColumnModel().getColumn(0).setMinWidth(10);
            table.getColumnModel().getColumn(0).setPreferredWidth(60);
            table.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Buscar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
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

        JDialog dialog = new FrmOrdemCadastro();
        dialog.setVisible(true);
        table.atualizar();
    }

    @Override
    public void btnAlterarActionPerformed(ActionEvent evt) {
        defaultUpdateOperation(table, (i) -> {
            JDialog dialog = new FrmOrdemCadastro(i);
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
