/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import components.JCampoBusca;
import components.JDialogController;
import components.JTableDataBinder;
import components.JTableDataBinderListener;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.util.function.BiConsumer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.Forms;

/**
 *
 * @author Charles
 */
public class frmF2 extends JDialogController {

    protected boolean allowNew = true;
    JTableDataBinder table;

    JCampoBusca campo;

    public frmF2(java.awt.Frame parent, String title) {
        super(parent, title);
        initComponents();

        table = ((JTableDataBinder) jtbDados);

        table.setBusca(txtBuscar);
        campo = new JCampoBusca(txtBuscar, () -> {
            table.atualizar();
            if (table.getRowCount() > 0 && table.getSelectedId() <= 0) {
                table.setRowSelectionInterval(0, 0);
            }
        });
        setDefaultButton(btnSelecionar);
        setResizable(false);

    }

    public void setBusca(String texto) {
        txtBuscar.setText(texto);
//        EventQueue.invokeLater(()->campo.go());

    }

    protected JTableDataBinderListener getListener() {
        throw new NotImplementedException();
    }
    BiConsumer<Integer, String> callback;

    public void setCallback(BiConsumer<Integer, String> callback) {
        this.callback = callback;
    }

    protected void requestNew() {
        throw new NotImplementedException();
    }

    protected String getSelectedText(){
        if(table.getSelectedId() > 0){
            return table.getDefaultTableModel().getValueAt(table.getSelectedRow(), 1).toString();
        }
        return "";
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDados = new JTableDataBinder();
        btnSelecionar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel2.setText("Buscar:");

        jtbDados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Código", "Nome"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtbDados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtbDadosMousePressed(evt);
            }
        });
        jtbDados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtbDadosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtbDadosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtbDadosKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jtbDados);

        btnSelecionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/success.png"))); // NOI18N
        btnSelecionar.setText("Selecionar");
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSelecionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNovo)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSelecionar)
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(btnNovo))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        table.setListener(getListener());
        table.atualizar();
        setLocationRelativeTo(null);

        btnNovo.setVisible(allowNew);
        txtBuscar.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        requestNew();
        table.atualizar();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void jtbDadosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtbDadosKeyReleased
        
    }//GEN-LAST:event_jtbDadosKeyReleased

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
            btnNovo.doClick();
        }
    }//GEN-LAST:event_formKeyReleased

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
            btnNovo.doClick();
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        if (table.getSelectedId() > 0) {
            if (callback != null) {
                
                callback.accept(table.getSelectedId(), getSelectedText());
            }
            dispose();
        } else {
            Forms.beep();
        }
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jtbDadosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtbDadosKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtbDadosKeyTyped

    private void jtbDadosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtbDadosKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSelecionar.doClick();
        } else if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
            btnNovo.doClick();
        }
    }//GEN-LAST:event_jtbDadosKeyPressed

    private void jtbDadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbDadosMousePressed
        if(evt.getClickCount() == 2){
            btnSelecionar.doClick();
        }
    }//GEN-LAST:event_jtbDadosMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtbDados;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
