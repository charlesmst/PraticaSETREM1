/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.estoque;

import components.JDialogController;
import forms.frmMain;
import model.estoque.MovimentacaoTipo;
import services.estoque.MovimentacaoTipoService;
import utils.AlertaTipos;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmMovimentacaoTipoCadastro extends JDialogController {

    private int id;
    private final MovimentacaoTipoService service = new MovimentacaoTipoService();

    public FrmMovimentacaoTipoCadastro() {
        this(0);
    }

    public FrmMovimentacaoTipoCadastro(int id) {
        super(frmMain.getInstance(), "Manutenção de Tipos de Movimentação de Estoque");
        initComponents();
        this.id = id;
        setupForm();
    }

    private void setupForm() {
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);
        txtCodigo.setEnabled(false);

        validator.validarObrigatorio(txtNome);
        validator.validarCustom(txtNome, (valor) -> service.unico(id, valor), "Tipo de movimentação já existe");

        validator.validarCustom(jrbSaida, (v) -> {
            return jrbSaida.isSelected() || jrbEntrada.isSelected();
        }, "Preencha um dos checkboxes");

        buttonGroup1.add(jrbEntrada);
        buttonGroup1.add(jrbSaida);

        jrbEntrada.setSelected(true);
        if (id > 0) {
            load();
        }
    }

    private void load() {
        MovimentacaoTipo m = service.findById(id);
        txtCodigo.setText(String.valueOf(m.getId()));
        txtNome.setText(m.getDescricao());
        jcbAtivo.setSelected(m.isAtivo());

        if (m.getTipo() == MovimentacaoTipo.TipoMovimentacao.saida) {
            jrbSaida.setSelected(true);
        } else {
            jrbEntrada.setSelected(true);
        }
    }

    private void save() {
        if (!validator.isValido()) {
            return;
        }
        MovimentacaoTipo m;
        if (id > 0) {
            m = service.findById(id);
        } else {
            m = new MovimentacaoTipo();
        }
        m.setDescricao(txtNome.getText());

        if (jrbEntrada.isSelected()) {
            m.setTipo(MovimentacaoTipo.TipoMovimentacao.entrada);
        } else {
            m.setTipo(MovimentacaoTipo.TipoMovimentacao.saida);

        }
        m.setAtivo(jcbAtivo.isSelected());
        Utils.safeCode(() -> {
            if (id == 0) {
                service.insert(m);
            } else {
                service.update(m);
            }
            utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);

            dispose();
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jrbEntrada = new javax.swing.JRadioButton();
        jrbSaida = new javax.swing.JRadioButton();
        txtNome = new components.JTextFieldUpper();
        jcbAtivo = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Código");

        jLabel2.setText("Nome");

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/save.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jrbEntrada.setText("ENTRADA");
        jrbEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbEntradaActionPerformed(evt);
            }
        });

        jrbSaida.setText("SAIDA");

        jcbAtivo.setSelected(true);
        jcbAtivo.setText("Ativo");
        jcbAtivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAtivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcbAtivo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 594, Short.MAX_VALUE)))
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jrbEntrada)
                                .addGap(18, 18, 18)
                                .addComponent(jrbSaida)))
                        .addContainerGap(465, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbEntrada)
                    .addComponent(jrbSaida))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jcbAtivo)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        save();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jrbEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbEntradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrbEntradaActionPerformed

    private void jcbAtivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAtivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbAtivoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JCheckBox jcbAtivo;
    private javax.swing.JRadioButton jrbEntrada;
    private javax.swing.JRadioButton jrbSaida;
    private javax.swing.JTextField txtCodigo;
    private components.JTextFieldUpper txtNome;
    // End of variables declaration//GEN-END:variables
}
