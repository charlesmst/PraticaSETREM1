package forms.estoque;

import components.CellRenderer;
import components.JDialogController;
import components.JTableDataBinderListener;
import forms.FrmPessoaF2;
import forms.fluxo.FrmContaCadastro;
import forms.frmMain;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Pessoa;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
import model.estoque.Item;
import model.estoque.MovimentacaoTipo;
import model.fluxo.Conta;
import model.fluxo.ContaCategoria;
import model.ordem.Ordem;
import services.PessoaService;
import services.ServiceException;
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
public class FrmEstoqueCadastro extends JDialogController {

    private int id;
    private List<Estoque> estoque = new ArrayList<>();
    private List<EstoqueMovimentacao> estoqueMovimentacoes = new ArrayList<>();

    private final EstoqueService serviceEst = new EstoqueService();
    private final EstoqueMovimentacaoService serviceEstMov = new EstoqueMovimentacaoService();
    List<MovimentacaoTipo> movTipo;
    Estoque est;
    EstoqueMovimentacao estMov;

    public FrmEstoqueCadastro() {
        this(0);
    }

    public FrmEstoqueCadastro(int id) {
        super(frmMain.getInstance(), "Manutenção de Estoque");
        initComponents();
        chkDataValidade.setSelected(false);
        this.id = id;
        setupForm();
        tableItem.setDefaultRenderer(Object.class, new CellRenderer());
        loadTiposMovimentação();
        zerarCampos();
    }

    private void setupForm() {
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);
        validator.validarObrigatorio(txtItem);
        validator.validarDeBanco(txtItem, new ItemService());
        validator.validarCustom(txtValorCompra, (valor) -> {
            return txtValorCompra.getValue() > 0;
        }, "Valor deve ser maior que zero");
        tableItem.setListener(new JTableDataBinderListener<Estoque>() {

            @Override
            public Collection<Estoque> lista(String busca) throws ServiceException {
                return estoque;
            }

            @Override
            public Object[] addRow(Estoque dado) {
                return new String[]{
                    "" + dado.getItem().getDescricao(),
                    "" + Utils.formataDinheiro(dado.getValorUnitario()),
                    "" + dado.getQuantidadeDisponivel(),
                    "" + Utils.formataDate(dado.getDataCompra()),
                    "" + dado.getLote()
                };
            }
        });
    }

    private void save() {

        Utils.safeCode(() -> {

            for (EstoqueMovimentacao estoqueMovimentacoe : estoqueMovimentacoes) {
                estoqueMovimentacoe.setPessoa(new Pessoa(txtPessoa.getValueSelected()));
            }
            FrmContaCadastro frmConta = new FrmContaCadastro(txtValorTotal.getValue(), 1, txtNotaFiscal.getText(), Conta.ContaTipo.estoque, ContaCategoria.TipoCategoria.saida);
            frmConta.setPessoa(txtPessoa.getValueSelected(), true);
            frmConta.setDescricao("ENTRADA DE ESTOQUE DO FORNECEDOR " + txtPessoa.getTextValue());
            frmConta.setListenerOnSave((c) -> {
                for (EstoqueMovimentacao estoqueMovimentacoe : estoqueMovimentacoes) {
                    estoqueMovimentacoe.setConta(c);
                }
                serviceEst.insert(estoque, estoqueMovimentacoes);
                utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
                dispose();
            });
            frmConta.setVisible(true);
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtItem = new components.F2(FrmItemF2.class, (id) -> new ItemService().findById(id).getDescricao());
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jcbTipoMovimentação = new javax.swing.JComboBox();
        txtDataValidade = new components.JDateField();
        txtValorCompra = new components.JTextFieldMoney();
        spinerQuantidade = new javax.swing.JSpinner();
        chkDataValidade = new javax.swing.JCheckBox();
        txtDataCompra = new components.JDateField();
        jLabel1 = new javax.swing.JLabel();
        txtNotaFiscal = new components.JTextFieldUpper();
        txtLote = new components.JTextFieldUpper();
        txtPessoa = new components.F2(FrmPessoaF2.class, (id)-> new PessoaService().findById(id).toString());
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtValorTotal = new components.JTextFieldMoney();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableItem = new components.JTableDataBinder();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro das entradas de Estoque");

        jLabel2.setText("Lote");

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

        txtItem.setMinimumSize(new java.awt.Dimension(6, 25));
        txtItem.setPreferredSize(new java.awt.Dimension(6, 25));

        jLabel3.setText("Item");

        jLabel4.setText("Quantidade");

        jLabel5.setText("Tipo de Movimentação");

        jLabel7.setText("Valor unit. de Compra");

        jLabel9.setText("Data de Compra");

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/new.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/delete.png"))); // NOI18N
        btnRemover.setText("Remover");

        jcbTipoMovimentação.setPreferredSize(new java.awt.Dimension(56, 25));

        txtValorCompra.setMinimumSize(new java.awt.Dimension(6, 30));

        spinerQuantidade.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        chkDataValidade.setText("Definir Data de Validade");
        chkDataValidade.setFocusable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtDataValidade, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), chkDataValidade, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        jLabel1.setText("Nota Fiscal");

        jLabel6.setText("Fornecedor");

        jLabel8.setText("Valor Total");

        tableItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Valor Unit.", "Quantidade", "Data de Compra", "Lote"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableItem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtItem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(50, 50, 50)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(spinerQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(46, 46, 46)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(txtDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chkDataValidade)
                                    .addComponent(txtDataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbTipoMovimentação, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)))
                            .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemover))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(20, 20, 20))
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(chkDataValidade)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcbTipoMovimentação, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinerQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnRemover))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar))
                .addGap(20, 20, 20))
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

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        if (!validator.isValido()) {
            return;
        }
        Estoque est = new Estoque();
        EstoqueMovimentacao estMov = new EstoqueMovimentacao();
        estMov.setEstoque(est);
        est.setItem(new ItemService().findById(txtItem.getValueSelected()));
        estMov.setMovimentacaoTipo((MovimentacaoTipo) jcbTipoMovimentação.getModel().getSelectedItem());
        estMov.setValorUnitario(txtValorCompra.getValue());
        estMov.setQuantidade(Integer.parseInt(spinerQuantidade.getValue().toString()));
        estMov.setDataLancamento(new Date());
        estMov.setDescricao("ENTRADA DE " + estMov.getQuantidade() + " -> " + est.getItem().getItemTipo().getNome()
                + " -> " + est.getItem().getDescricao());
        est.setDataCompra(txtDataCompra.getDate());
        est.setLote(txtLote.getText());
        if (chkDataValidade.isSelected()) {
            est.setDataValidade(txtDataValidade.getDate());
        } else {
            est.setDataValidade(null);
        }
        estMov.setNotaFiscal(txtNotaFiscal.getText());
        est.setValorUnitario(txtValorCompra.getValue());
        est.setQuantidadeDisponivel(Integer.parseInt(spinerQuantidade.getValue().toString()));
        estMov.setEstoque(est);
        estoque.add(est);
        estoqueMovimentacoes.add(estMov);
        zerarCampos();
        refreshTable();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void loadTiposMovimentação() {
        List<MovimentacaoTipo> movTipoTemp = new MovimentacaoTipoService().findAll();
        movTipo = new ArrayList<>();
        for (MovimentacaoTipo m : movTipoTemp) {
            if (m.isAtivo() && m.getTipo().equals(m.getTipo().entrada)) {
                movTipo.add(m);
            }
        }
        jcbTipoMovimentação.setModel(new DefaultComboBoxModel(new Vector(movTipo)));
    }

    private void zerarCampos() {

        txtItem.setText("");
        jcbTipoMovimentação.setSelectedIndex(0);
        txtValorCompra.setValue(0);
        spinerQuantidade.setValue(1);
        txtLote.setText("");
    }

    private void refreshTable() {
        tableItem.atualizar();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkDataValidade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox jcbTipoMovimentação;
    private javax.swing.JSpinner spinerQuantidade;
    private components.JTableDataBinder tableItem;
    private components.JDateField txtDataCompra;
    private components.JDateField txtDataValidade;
    private components.F2 txtItem;
    private components.JTextFieldUpper txtLote;
    private components.JTextFieldUpper txtNotaFiscal;
    private components.F2 txtPessoa;
    private components.JTextFieldMoney txtValorCompra;
    private components.JTextFieldMoney txtValorTotal;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
