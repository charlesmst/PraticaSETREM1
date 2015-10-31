/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.fluxo;

import components.JDialogController;
import forms.frmMain;
import javax.swing.ButtonGroup;
import model.fluxo.ContaBancaria;
import services.fluxo.ContaBancariaService;
import utils.AlertaTipos;
import utils.Utils;

/**
 *
 * @author Charles
 */
public class FrmContaBancariaCadastro extends JDialogController {

    private int id;
    private final ContaBancariaService service = new ContaBancariaService();

    /**
     * Creates new form frmCadastroContaBancaria
     */
    public FrmContaBancariaCadastro() {
        this(0);
    }

    public FrmContaBancariaCadastro(int id) {
        super(frmMain.getInstance(), "Manutenção de Contas Bancarias");
        initComponents();
        this.id = id;
        setupForm();
    }

    private void setupForm() {
        // center the jframe on screen
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);
        txtCodigo.setEnabled(false);

        validator.validarObrigatorio(txtNome);

        validator.validarCustom(jrbContaBancaria, (v)->{
            return jrbContaBancaria.isSelected() || jrbCaixa.isSelected();
        }, "Preencha um dos checkboxes");
        
//        ButtonGroup b
        buttonGroup1.add(jrbCaixa);
        buttonGroup1.add(jrbContaBancaria);
        
        jrbContaBancaria.setSelected(true);
        if (id > 0) {
            load();
        }
    }

    private void load() {
        ContaBancaria m = service.findById(id);
        txtCodigo.setText(String.valueOf(m.getId()));
        txtNome.setText(m.getNome());
        jcbAtivo.setSelected(m.isAtivo());
        
        if(m.getTipo() == ContaBancaria.TipoContaBancaria.banco)
            jrbContaBancaria.setSelected(true);
        else
            jrbCaixa.setSelected(true);
    }

    private void save() {
        if (!validator.isValido()) {
            return;
        }
        ContaBancaria m;
        if (id > 0) {
            m = service.findById(id);
        } else {
            m = new ContaBancaria();
        }
        m.setNome(txtNome.getText());

        if (jrbCaixa.isSelected()) {
            m.setTipo(ContaBancaria.TipoContaBancaria.caixa);
        } else {
            m.setTipo(ContaBancaria.TipoContaBancaria.banco);

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jrbCaixa = new javax.swing.JRadioButton();
        jrbContaBancaria = new javax.swing.JRadioButton();
        txtNome = new components.JTextFieldUpper();
        jLabel3 = new javax.swing.JLabel();
        jtbSaldoAtual = new javax.swing.JFormattedTextField();
        jcbAtivo = new javax.swing.JCheckBox();
        btnMovimentar = new javax.swing.JButton();
        btnVisualizarMovimentacoes = new javax.swing.JButton();

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

        jrbCaixa.setText("CAIXA");

        jrbContaBancaria.setText("CONTA BANCARIA");

        jLabel3.setText("Saldo atual");

        jtbSaldoAtual.setEnabled(false);

        jcbAtivo.setSelected(true);
        jcbAtivo.setText("Ativo");

        btnMovimentar.setText("Fazer Movimentação");
        btnMovimentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovimentarActionPerformed(evt);
            }
        });

        btnVisualizarMovimentacoes.setText("Visualizar Movimentações");
        btnVisualizarMovimentacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarMovimentacoesActionPerformed(evt);
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
                        .addComponent(jrbCaixa)
                        .addGap(18, 18, 18)
                        .addComponent(jrbContaBancaria)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtbSaldoAtual, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(btnSalvar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCancelar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnMovimentar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnVisualizarMovimentacoes)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbAtivo)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))))
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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbCaixa)
                    .addComponent(jrbContaBancaria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtbSaldoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbAtivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMovimentar)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalvar)
                        .addComponent(btnCancelar)
                        .addComponent(btnVisualizarMovimentacoes)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        save();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnMovimentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovimentarActionPerformed
        new FrmMovimentacaoCadastro().setVisible(true);
        load();
    }//GEN-LAST:event_btnMovimentarActionPerformed

    private void btnVisualizarMovimentacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarMovimentacoesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVisualizarMovimentacoesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnMovimentar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnVisualizarMovimentacoes;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JCheckBox jcbAtivo;
    private javax.swing.JRadioButton jrbCaixa;
    private javax.swing.JRadioButton jrbContaBancaria;
    private javax.swing.JFormattedTextField jtbSaldoAtual;
    private javax.swing.JTextField txtCodigo;
    private components.JTextFieldUpper txtNome;
    // End of variables declaration//GEN-END:variables
}
