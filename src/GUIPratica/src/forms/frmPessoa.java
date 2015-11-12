/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import components.CellRenderer;
import components.JCampoBusca;
import java.awt.event.ActionEvent;
import components.JPanelControleButtons;
import components.JTableDataBinder;
import components.JTableDataBinderListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JDialog;
import model.Pessoa;
import model.Usuario;
import services.PessoaService;
import services.UsuarioService;
import utils.AlertaTipos;

/**
 *
 * @author Gustavo
 */
public class frmPessoa extends JPanelControleButtons {

    private final PessoaService service;
    JTableDataBinder table;

    public frmPessoa() {
        initComponents();
        setBtnAddEnable(true);
        setBtnAlterarEnable(true);
        setBtnExcluirEnable(true);
        setBtnAtualizarEnable(true);
        jtbDados.setDefaultRenderer(Object.class, new CellRenderer());

        service = new PessoaService();

        new JCampoBusca(txtBuscar, () -> table.atualizar());

        ((JTableDataBinder) jtbDados).setListener(new JTableDataBinderListener<Pessoa>() {

            @Override
            public Collection<Pessoa> lista(String busca) {
                try {
                    return service.findByMultipleColumns(busca, "id", "nome");
                } catch (Exception e) {
                    utils.Forms.mensagem(e.getMessage(), AlertaTipos.erro);
                }
                return new ArrayList<Pessoa>();
            }

            @Override
            public Object[] addRow(Pessoa dado) {
                return new Object[]{
                    "" + dado.getId(),
                    dado.getNome(),
                    dado.getEmail(),
                    dado.getEndereco(),
                    (dado.getCidade()!= null? dado.getCidade().getNome():""),
                     dado.getTipo().toString().toUpperCase()
                };
            }
        });

        table = ((JTableDataBinder) jtbDados);

        table.setBusca(txtBuscar);
        table.atualizar();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDados = new JTableDataBinder();
        jLabel2 = new javax.swing.JLabel();
        txtBuscar = new components.JTextFieldUpper();

        jtbDados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "E-mail", "Endereço", "Cidade", "Tipo de Pessoa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtbDados);
        if (jtbDados.getColumnModel().getColumnCount() > 0) {
            jtbDados.getColumnModel().getColumn(0).setPreferredWidth(20);
            jtbDados.getColumnModel().getColumn(1).setPreferredWidth(150);
            jtbDados.getColumnModel().getColumn(5).setPreferredWidth(20);
        }

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Buscar:");

        txtBuscar.setMargin(new java.awt.Insets(2, 8, 2, 2));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtbDados;
    private components.JTextFieldUpper txtBuscar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void btnAddActionPerformed(ActionEvent evt) {
        JDialog dialog = new FrmPessoaCadastro();
        dialog.setVisible(true);
        table.atualizar();
    }

    @Override
    public void btnAlterarActionPerformed(ActionEvent evt) {
        defaultUpdateOperation(table, (i) -> {
            JDialog dialog = new FrmPessoaCadastro(i);
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
