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
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import model.Usuario;
import services.UsuarioService;
import utils.AlertaTipos;
import utils.Globals;

/**
 *
 * @author Gustavo
 */
public class frmUsuarios extends JPanelControleButtons {

    private final UsuarioService service;
    JTableDataBinder table;

    public frmUsuarios() {
        initComponents();
        setBtnAddEnable(true);
        setBtnAlterarEnable(true);
        setBtnExcluirEnable(true);
        setBtnAtualizarEnable(true);
        service = new UsuarioService();
        jtbDados.setDefaultRenderer(Object.class, new CellRenderer());
        new JCampoBusca(txtBuscar, () -> table.atualizar());

        ((JTableDataBinder) jtbDados).setListener(new JTableDataBinderListener<Usuario>() {

            @Override
            public Collection<Usuario> lista(String busca) {
                try {
                    return service.findByMultipleColumns(busca, "id", "id", "pessoa.nome");
                } catch (Exception e) {
                    utils.Forms.mensagem(e.getMessage(), AlertaTipos.erro);
                }
                return new ArrayList<Usuario>();
            }

            @Override
            public Object[] addRow(Usuario dado) {
                ImageIcon i;
                if (dado.isAtivo()) {
                    i = Globals.iconeSuccess;
                } else {
                    i = Globals.iconeError;
                }
                return new Object[]{dado.getId() + "", 
                    dado.getUsuario(),
                    dado.getPessoa().getNome(),
                    dado.getNivel().toString().toUpperCase(),
                    i
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDataBinder1 = new components.JTableDataBinder();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDados = new JTableDataBinder();
        txtBuscar = new components.JTextFieldUpper();

        jTableDataBinder1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableDataBinder1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Buscar:");

        jtbDados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Usuário", "Pessoa", "Tipo de Usuário", "Ativo?"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtbDados);
        if (jtbDados.getColumnModel().getColumnCount() > 0) {
            jtbDados.getColumnModel().getColumn(1).setPreferredWidth(250);
            jtbDados.getColumnModel().getColumn(2).setPreferredWidth(400);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private components.JTableDataBinder jTableDataBinder1;
    private javax.swing.JTable jtbDados;
    private components.JTextFieldUpper txtBuscar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void btnAddActionPerformed(ActionEvent evt) {
        JDialog dialog = new FrmUsuarioCadastro();
        dialog.setVisible(true);
        table.atualizar();
    }

    @Override
    public void btnAlterarActionPerformed(ActionEvent evt) {
        defaultUpdateOperation(table, (i) -> {
            JDialog dialog = new FrmUsuarioCadastro(i);
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
