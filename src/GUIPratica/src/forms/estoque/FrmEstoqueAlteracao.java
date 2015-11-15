package forms.estoque;

import components.CellRenderer;
import components.JDialogController;
import forms.frmMain;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
import model.estoque.Item;
import model.estoque.MovimentacaoTipo;
import services.PessoaService;
import services.estoque.EstoqueMovimentacaoService;
import services.estoque.EstoqueService;
import services.estoque.ItemService;
import services.estoque.MovimentacaoTipoService;
import services.fluxo.ContaService;
import sun.awt.datatransfer.DataTransferer;
import utils.AlertaTipos;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmEstoqueAlteracao extends JDialogController {

    private int id;
    private EstoqueService service = new EstoqueService();
    private EstoqueMovimentacaoService serviceMov = new EstoqueMovimentacaoService();
    List<MovimentacaoTipo> movTipo;
    Estoque est;
    EstoqueMovimentacao estMov;

    public FrmEstoqueAlteracao() {
        this(0);
    }

    public FrmEstoqueAlteracao(int id) {
        super(frmMain.getInstance(), "Manutenção de Estoque");
        initComponents();
        chkDataValidade.setSelected(false);
        this.id = id;
        loadTiposMovimentação();
        setupForm();
    }

    private void setupForm() {
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);
        validator.validarObrigatorio(txtItem);
        validator.validarDeBanco(txtItem, new ItemService());
        validator.validarCustom(txtValorCompra, (valor) -> {
            return txtValorCompra.getValue() > 0;
        }, "Valor deve ser maior que zero");

        if (id > 0) {
            load();
        }
    }

    private void load() {
        est = service.findById(id);
        estMov = new EstoqueMovimentacao();
        List<EstoqueMovimentacao> iM = service.todasMovimentacaos();
        for (int x = 0; x < iM.size(); x++) {
            if (iM.get(x).getEstoque().equals(est)) {
                estMov = iM.get(x);
            }
        }
        txtCodigo.setText(String.valueOf(est.getId()));
        txtFornecedor.setText(estMov.getPessoa().getId() + "");
        txtNotaFiscal1.setText(estMov.getNotaFiscal());
        txtValorTotal.setValue(estMov.getConta().getValorTotal()); //Ver com o Charles
        txtItem.setText(est.getItem().getId() + "");
        if (est.getDataValidade() != null) {
            chkDataValidade.setSelected(true);
            txtDataValidade.setDate(est.getDataValidade());
        }
        jcbTipoMovimentacao.getModel().setSelectedItem(estMov.getMovimentacaoTipo());
        txtValorCompra.setValue(est.getValorUnitario());
        spinerQuantidade.setValue(estMov.getQuantidade());
        txtDataCompra.setDate(est.getDataCompra());
        txtLote.setText(est.getLote());
    }

    private void save() {
        estMov.setPessoa(new PessoaService().findById(txtFornecedor.getValueSelected()));
        estMov.setNotaFiscal(txtNotaFiscal1.getText());
        estMov.getConta().setValorTotal(txtValorTotal.getValue()); //VER COM O CHARLES ******
        estMov.getConta().setNotaFiscal(estMov.getNotaFiscal()); //VER COM O CHARLES ******
        estMov.getConta().setPessoa(estMov.getPessoa()); //VER COM O CHARLES *******
        est.setItem(new ItemService().findById(txtItem.getValueSelected()));
        if (chkDataValidade.isSelected()) {
            est.setDataValidade(txtDataValidade.getDate());
        } else {
            est.setDataValidade(null);
        }
        estMov.setMovimentacaoTipo((MovimentacaoTipo) jcbTipoMovimentacao.getSelectedItem());
        est.setValorUnitario(txtValorCompra.getValue());
        estMov.setValorUnitario(txtValorCompra.getValue());
        est.setQuantidadeDisponivel(Integer.parseInt(spinerQuantidade.getValue().toString()));
        estMov.setQuantidade(Integer.parseInt(spinerQuantidade.getValue().toString()));
        est.setDataCompra(txtDataCompra.getDate());
        estMov.setDataLancamento(new Date());
        est.setLote(txtLote.getText());
        estMov.setDescricao(estMov.getMovimentacaoTipo().getDescricao()
                + " DE " + estMov.getQuantidade() + " " + est.getItem().getDescricao());
        estMov.setEstoque(est);
        Utils.safeCode(() -> {
            new EstoqueService().update(est, estMov);
            utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
            dispose();
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        f21 = new components.F2();
        jLabel8 = new javax.swing.JLabel();
        txtNotaFiscal = new components.JTextFieldUpper();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldMoney1 = new components.JTextFieldMoney();
        jLabel2 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtLote = new components.JTextFieldUpper();
        txtItem = new components.F2(FrmItemF2.class, (id) -> new ItemService().findById(id).getDescricao());
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jcbTipoMovimentacao = new javax.swing.JComboBox();
        txtDataCompra = new components.JDateField();
        txtValorCompra = new components.JTextFieldMoney();
        spinerQuantidade = new javax.swing.JSpinner();
        chkDataValidade = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtDataValidade = new components.JDateField();
        jLabel11 = new javax.swing.JLabel();
        txtFornecedor = new components.F2();
        jLabel12 = new javax.swing.JLabel();
        txtNotaFiscal1 = new components.JTextFieldUpper();
        jLabel13 = new javax.swing.JLabel();
        txtValorTotal = new components.JTextFieldMoney();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jLabel6.setText("Fornecedor");

        jLabel8.setText("Nota Fiscal");

        jLabel10.setText("Valor Total");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro das entradas de Estoque");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Lote:");

        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/save.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtItem.setMinimumSize(new java.awt.Dimension(6, 25));
        txtItem.setPreferredSize(new java.awt.Dimension(6, 25));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Item");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Quantidade:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Tipo de Movimentação");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Valor unit. de Compra:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Data de Compra:");

        jcbTipoMovimentacao.setPreferredSize(new java.awt.Dimension(56, 25));

        txtValorCompra.setMinimumSize(new java.awt.Dimension(6, 30));

        spinerQuantidade.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        chkDataValidade.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkDataValidade.setText("Definir Data de Validade");
        chkDataValidade.setFocusable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtDataValidade, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), chkDataValidade, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Código");

        txtCodigo.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Fornecedor");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Nota Fiscal");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Valor Total");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigo)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel7)
                                                .addComponent(txtValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel4)
                                                .addComponent(spinerQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel9)))
                                        .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(chkDataValidade)
                                                    .addGap(0, 0, Short.MAX_VALUE))
                                                .addComponent(txtDataValidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jcbTipoMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel5)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addComponent(btnSalvar)
                                            .addGap(9, 9, 9)
                                            .addComponent(btnCancelar))
                                        .addComponent(txtLote, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(0, 0, Short.MAX_VALUE))))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(txtFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel11)
                                            .addGap(288, 288, 288)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNotaFiscal1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel13)
                                        .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jLabel12)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNotaFiscal1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbTipoMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel3)
                                .addGap(6, 6, 6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkDataValidade)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(jLabel4))
                            .addGap(6, 6, 6)
                            .addComponent(txtValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinerQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        save();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void loadTiposMovimentação() {
        List<MovimentacaoTipo> movTipoTemp = new MovimentacaoTipoService().findAll();
        movTipo = new ArrayList<>();
        for (MovimentacaoTipo m : movTipoTemp) {
            if (m.isAtivo() && m.getTipo().equals(m.getTipo().entrada)) {
                movTipo.add(m);
            }
        }
        jcbTipoMovimentacao.setModel(new DefaultComboBoxModel(new Vector(movTipo)));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkDataValidade;
    private components.F2 f21;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private components.JTextFieldMoney jTextFieldMoney1;
    private javax.swing.JComboBox jcbTipoMovimentacao;
    private javax.swing.JSpinner spinerQuantidade;
    private javax.swing.JTextField txtCodigo;
    private components.JDateField txtDataCompra;
    private components.JDateField txtDataValidade;
    private components.F2 txtFornecedor;
    private components.F2 txtItem;
    private components.JTextFieldUpper txtLote;
    private components.JTextFieldUpper txtNotaFiscal;
    private components.JTextFieldUpper txtNotaFiscal1;
    private components.JTextFieldMoney txtValorCompra;
    private components.JTextFieldMoney txtValorTotal;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
