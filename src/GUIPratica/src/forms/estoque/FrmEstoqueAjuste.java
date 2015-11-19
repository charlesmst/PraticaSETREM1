package forms.estoque;

import components.JDialogController;
import forms.fluxo.FrmContaCadastro;
import forms.frmMain;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
import model.estoque.MovimentacaoTipo;
import services.estoque.EstoqueMovimentacaoService;
import services.estoque.EstoqueService;
import services.estoque.ItemService;
import utils.AlertaTipos;
import utils.Parametros;

/**
 *
 * @author gustavo
 */
public class FrmEstoqueAjuste extends JDialogController {

    int id;
    EstoqueMovimentacaoService service = new EstoqueMovimentacaoService();
    EstoqueMovimentacao estMov;
    Estoque est;

    public FrmEstoqueAjuste(int id) {
        super(frmMain.getInstance(), "Manutenção de Estoque");
        initComponents();
        this.id = id;
        setupForm();
        if (id > 0) {
            load();
        } else {

        }

    }

    private void setupForm() {

    }

    private void load() {
        estMov = service.findById(id);
        est = estMov.getEstoque();
        txtCodigo.setText(estMov.getId() + "");
        txtItem.setText(est.getItem().getDescricao() + "");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtQuantidade = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTipoAjuste = new javax.swing.JComboBox();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtItem = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        txtQuantidade.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jLabel1.setText("Quantidade");

        jLabel2.setText("Código");

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Item");

        jLabel4.setText("Tipo de Ajuste");

        txtTipoAjuste.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ENTRADA", "SAÍDA" }));

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtItem.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtTipoAjuste, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtCodigo)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addComponent(txtItem))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoAjuste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSalvar)
                            .addComponent(btnCancelar))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        estMov.setQuantidade((Integer) txtQuantidade.getValue());
        if (txtTipoAjuste.getSelectedIndex() == 0) {
            int id = Integer.parseInt(Parametros.getInstance().getValue("ajuste_entrada"));
            MovimentacaoTipo t = new MovimentacaoTipo();
            t.setId(id);
            estMov.setMovimentacaoTipo(t);
            est.setQuantidadeDisponivel(est.getQuantidadeDisponivel() + estMov.getQuantidade());
            estMov.setDescricao("AJUSTE DE (+) " + estMov.getQuantidade() + " " + est.getItem().getDescricao());
        } else if (txtTipoAjuste.getSelectedIndex() == 1) {
            int id = Integer.parseInt(Parametros.getInstance().getValue("ajuste_saida"));
            MovimentacaoTipo t = new MovimentacaoTipo();
            t.setId(id);
            estMov.setMovimentacaoTipo(t);
            if (est.getQuantidadeDisponivel() < (Integer) txtQuantidade.getValue()) {
                JOptionPane.showMessageDialog(null, "Atenção! Foram descontados "
                        + est.getQuantidadeDisponivel()
                        + " item(ns) desse estoque. Favor, desconte o restante de outra movimentação!");
                estMov.setDescricao("AJUSTE DE (-) " + est.getQuantidadeDisponivel() + " " + est.getItem().getDescricao());
                estMov.setQuantidade(est.getQuantidadeDisponivel());
                est.setQuantidadeDisponivel(0);
            } else {
                est.setQuantidadeDisponivel(est.getQuantidadeDisponivel() - (Integer) txtQuantidade.getValue());
                estMov.setDescricao("AJUSTE DE (-) " + (Integer) txtQuantidade.getValue() + " " + est.getItem().getDescricao());
            }
        }
        FrmContaCadastro frmConta = new FrmContaCadastro(estMov.getConta().getId());
        frmConta.setListenerOnSave((c) -> {
            new EstoqueService().update(est, estMov);
            utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
            dispose();
        });
        frmConta.setVisible(true);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtItem;
    private javax.swing.JSpinner txtQuantidade;
    private javax.swing.JComboBox txtTipoAjuste;
    // End of variables declaration//GEN-END:variables
}
