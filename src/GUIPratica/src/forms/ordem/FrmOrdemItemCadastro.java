
package forms.ordem;

import components.JDialogController;
import forms.estoque.FrmItemF2;
import forms.frmMain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
import model.estoque.Item;
import model.estoque.MovimentacaoTipo;
import model.ordem.Ordem;
import services.estoque.EstoqueMovimentacaoService;
import services.estoque.EstoqueService;
import services.estoque.ItemService;
import services.estoque.MovimentacaoTipoService;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmOrdemItemCadastro extends JDialogController {

    private Ordem ordem;
    private final EstoqueMovimentacaoService service = new EstoqueMovimentacaoService();
    Item item = new Item();
    private List<EstoqueMovimentacao> estoqueMovimentacoes = new ArrayList<>();
    List<MovimentacaoTipo> movTipo;
    private EstoqueMovimentacao eM = new EstoqueMovimentacao();

    public FrmOrdemItemCadastro(Ordem ordem) {
        super(frmMain.getInstance(), "Saída de Estoque");
        initComponents();
        this.ordem = ordem;
        setupForm();
    }

    private void setupForm() {
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);
        loadTiposMovimentação();
        validator.validarObrigatorio(txtItem);
        validator.validarDeBanco(txtItem, new ItemService());
        
        //atualizaValor();
    }

    private void atualizaValor() {
        item = new ItemService().findById(txtItem.getValueSelected());
        if(item.getUltimoValorVenda() > 0){
            txtValor.setValue(item.getUltimoValorVenda());
        } else {
            txtValor.setValue(0);
        }
        atualizaValorTotal();
    }

    private double getValorTotal() {
        double v = txtValor.getValue() * ((Integer) txtQuantidade.getValue() + 0d);
        return v;
    }

    private void atualizaValorTotal() {
        lblValorTotal.setText(Utils.formataDinheiro(getValorTotal()));

    }

    private void save() {
        atualizaValor();
        if (!validator.isValido()) {
            return;
        }
        int qtd = Integer.parseInt(new ItemService().verificaQuantidadeDisp(item));
        if (qtd > 0) {
            Estoque est = new EstoqueService().verificaMaisAntigo(item);
            List<EstoqueMovimentacao> lista = new EstoqueMovimentacaoService().buscaMovimentacoes(est);
            EstoqueMovimentacao estMov = new EstoqueMovimentacao();
            int cont = 0;
            for (EstoqueMovimentacao eM : lista) {
                if (cont > 0) {
                    JOptionPane.showMessageDialog(null, "Possui mais de uma movimentação-estoque relacionada ao Estoque");
                } else {
                    estMov = eM;
                }
                cont++;
            }
            estMov.setDataLancamento(new Date());
            estMov.setPessoa(ordem.getPessoa());
            estMov.setDescricao("SAIDA DE -> " + txtQuantidade.getValue() + " -> " + item.toString());
            estMov.setMovimentacaoTipo((MovimentacaoTipo) txtOrigem.getModel().getSelectedItem());
            estMov.setQuantidade(qtd * -1);
            est.getItem().setUltimoValorVenda(getValorTotal());
            est.setQuantidadeDisponivel(est.getQuantidadeDisponivel() + estMov.getQuantidade());
            estMov.setEstoque(est);
            estMov.setValorUnitario(txtValor.getValue());
            estMov.getOrdem().add(ordem);
            ordem.getEstoqueMovimentacaos().add(estMov);
            eM = estMov;

            Utils.safeCode(() -> {
                service.insertPersonalizado(eM, eM.getEstoque(), eM.getEstoque().getItem());
                dispose();
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        txtValor = new components.JTextFieldMoney();
        jLabel4 = new javax.swing.JLabel();
        lblValorTotal = new javax.swing.JLabel();
        txtData = new components.JDateField();
        txtItem = new components.F2(FrmItemF2.class, (id)-> new ItemService().findById(id).toString());
        jLabel5 = new javax.swing.JLabel();
        txtOrigem = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Item");

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

        jLabel2.setText("Quantidade");

        txtQuantidade.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        txtQuantidade.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtQuantidadeStateChanged(evt);
            }
        });

        jLabel3.setText("Valor Unitário de Venda");

        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorKeyReleased(evt);
            }
        });

        jLabel4.setText("Valor Total");

        lblValorTotal.setText("R$0,00");

        jLabel5.setText("Origem");

        jLabel6.setText("Data");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblValorTotal)
                            .addComponent(jLabel4))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtData, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtOrigem, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar))
                            .addComponent(jLabel1)
                            .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValorTotal)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar))
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

    private void txtValorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyReleased
        atualizaValorTotal();
    }//GEN-LAST:event_txtValorKeyReleased

    private void txtQuantidadeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtQuantidadeStateChanged
        atualizaValorTotal();
    }//GEN-LAST:event_txtQuantidadeStateChanged
    private void loadTiposMovimentação() {
        List<MovimentacaoTipo> movTipoTemp = new MovimentacaoTipoService().findAll();
        movTipo = new ArrayList<>();
        for (MovimentacaoTipo m : movTipoTemp) {
            if (m.isAtivo() && m.getTipo().equals(m.getTipo().saida)) {
                movTipo.add(m);
            }
        }
        txtOrigem.setModel(new DefaultComboBoxModel(new Vector(movTipo)));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblValorTotal;
    private components.JDateField txtData;
    private components.F2 txtItem;
    private javax.swing.JComboBox txtOrigem;
    private javax.swing.JSpinner txtQuantidade;
    private components.JTextFieldMoney txtValor;
    // End of variables declaration//GEN-END:variables
}
