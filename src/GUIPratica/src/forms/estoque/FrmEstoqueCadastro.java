package forms.estoque;

import components.CellRenderer;
import components.JDialogController;
import components.JTableDataBinderListener;
import forms.FrmPessoaF2;
import forms.fluxo.FrmContaCadastro;
import forms.frmMain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.Pessoa;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
import model.estoque.Item;
import model.estoque.MovimentacaoTipo;
import model.fluxo.Conta;
import model.fluxo.ContaCategoria;
import services.PessoaService;
import services.ServiceException;
import services.estoque.EstoqueMovimentacaoService;
import services.estoque.EstoqueService;
import services.estoque.ItemService;
import services.estoque.MovimentacaoTipoService;
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
    private double valorTotal;
    private final EstoqueService serviceEst = new EstoqueService();
    private final EstoqueMovimentacaoService serviceEstMov = new EstoqueMovimentacaoService();
    List<MovimentacaoTipo> movTipo;
    Estoque est;
    EstoqueMovimentacao estMov;
    int seq = 0;

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
        setDefaultButton(btnAdicionar);
        validator.validarObrigatorio(txtItem);
        validator.validarDeBanco(txtItem, new ItemService());
        validator.validarObrigatorio(txtPessoa);
        validator.validarDeBanco(txtPessoa, new PessoaService());
        validator.validarCustom(txtValorCompra, (valor) -> {
            return txtValorCompra.getValue() > 0;
        }, "Valor unitário deve ser maior que zero");
        tableItem.setListener(new JTableDataBinderListener<Estoque>() {

            @Override
            public Collection<Estoque> lista(String busca) throws ServiceException {
                return estoque;
            }

            @Override
            public Object[] addRow(Estoque dado) {
                return new String[]{
                    "" + dado.getId(),
                    "" + dado.getItem().getDescricao(),
                    "" + dado.getQuantidadeDisponivel(),
                    Utils.formataDinheiro(dado.getValorUnitario()),
                    Utils.formataDinheiro(dado.getQuantidadeDisponivel() * dado.getValorUnitario())
                };
            }
        });
    }

    private void save() {
        if (estoque.size() < 1 || txtPessoa.getValueSelected() == 0) {
            utils.Forms.mensagem("Verifique os campos obrigatórios", AlertaTipos.erro);
        } else {
            Utils.safeCode(() -> {
                for (EstoqueMovimentacao estoqueMovimentacao : estoqueMovimentacoes) {
                    estoqueMovimentacao.setPessoa(new Pessoa(txtPessoa.getValueSelected()));
                    estoqueMovimentacao.setId(0);
                    estoqueMovimentacao.getEstoque().setId(0);
                    estoqueMovimentacao.setDataLancamento(txtDataCompra.getDate());
                    estoqueMovimentacao.getEstoque().setDataCompra(txtDataCompra.getDate());
                    estoqueMovimentacao.setNotaFiscal(txtNotaFiscal.getText());
                    try {
                        estoqueMovimentacao.setMovimentacaoTipo((MovimentacaoTipo) jcbTipoMovimentacao.getModel().getSelectedItem());
                    } catch (Exception e) {
                        utils.Forms.mensagem("Nenhum tipo de movimentação cadastrado!", AlertaTipos.erro);
                    }
                }
                FrmContaCadastro frmConta = new FrmContaCadastro(valorTotal, 1, txtNotaFiscal.getText(), Conta.ContaTipo.estoque, ContaCategoria.TipoCategoria.saida);
                frmConta.setPessoa(txtPessoa.getValueSelected(), true);
                frmConta.setDescricao("ENTRADA DE ESTOQUE DO FORNECEDOR " + txtPessoa.getTextValue());
                frmConta.setListenerOnSave((c) -> {
                    for (EstoqueMovimentacao estoqueMovimentacao : estoqueMovimentacoes) {
                        estoqueMovimentacao.setConta(c);
                    }
                    serviceEst.insert(estoque, estoqueMovimentacoes);
                    utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
                    dispose();
                });
                frmConta.setVisible(true);
            });
        }
    }

    private void atualizaValotTotal() {
        valorTotal = 0;
        for (EstoqueMovimentacao eM : estoqueMovimentacoes) {
            valorTotal += (eM.getQuantidade() * eM.getValorUnitario());
        }
        txtValorTotal.setValue(valorTotal);
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
        jcbTipoMovimentacao = new javax.swing.JComboBox();
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
        jLabel10 = new javax.swing.JLabel();
        txtValorVenda = new components.JTextFieldMoney();

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
        setResizable(false);

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
        txtItem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtItemFocusLost(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Item:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Quantidade:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Tipo de Movimentação:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Valor unit. de Compra:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Data de Compra:");

        btnAdicionar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/new.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnRemover.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/delete.png"))); // NOI18N
        btnRemover.setText("Remover");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        jcbTipoMovimentacao.setPreferredSize(new java.awt.Dimension(56, 25));

        txtValorCompra.setMinimumSize(new java.awt.Dimension(6, 30));

        spinerQuantidade.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        chkDataValidade.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkDataValidade.setText("Definir Data de Validade");
        chkDataValidade.setFocusable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtDataValidade, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), chkDataValidade, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        chkDataValidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDataValidadeActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Nota Fiscal:");

        txtNotaFiscal.setMargin(new java.awt.Insets(2, 8, 2, 2));

        txtLote.setMargin(new java.awt.Insets(2, 8, 2, 2));
        txtLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoteActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Fornecedor:");

        txtValorTotal.setEditable(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Total:");

        tableItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Item", "Quantidade", "Valor Unit.", "Valor Total"
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
        if (tableItem.getColumnModel().getColumnCount() > 0) {
            tableItem.getColumnModel().getColumn(0).setMinWidth(30);
            tableItem.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableItem.getColumnModel().getColumn(0).setMaxWidth(30);
            tableItem.getColumnModel().getColumn(1).setPreferredWidth(300);
            tableItem.getColumnModel().getColumn(2).setPreferredWidth(50);
        }

        jLabel10.setText("Preço de Venda Sugerido");

        txtValorVenda.setMinimumSize(new java.awt.Dimension(6, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chkDataValidade)
                                    .addComponent(txtDataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane3)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAdicionar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemover))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(spinerQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(txtValorVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtLote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jcbTipoMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(20, Short.MAX_VALUE))))
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel5))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jcbTipoMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(chkDataValidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinerQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
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
        seq++;
        Estoque est = new Estoque();
        EstoqueMovimentacao estMov = new EstoqueMovimentacao();
        est.setItem(new ItemService().findById(txtItem.getValueSelected()));
        estMov.setValorUnitario(txtValorCompra.getValue());
        if (txtValorVenda.getValue() > 0.01) {
            est.getItem().setUltimoValorVenda(txtValorVenda.getValue());
        }
        estMov.setQuantidade(Integer.parseInt(spinerQuantidade.getValue().toString()));
        estMov.setDescricao("ENTRADA DE " + est.getItem().getDescricao());
        est.setLote(txtLote.getText());
        if (chkDataValidade.isSelected()) {
            est.setDataValidade(txtDataValidade.getDate());
        } else {
            est.setDataValidade(null);
        }
        estMov.setId(seq);
        est.setId(estMov.getId());
        est.setValorUnitario(txtValorCompra.getValue());
        est.setQuantidadeDisponivel(Integer.parseInt(spinerQuantidade.getValue().toString()));
        estMov.setEstoque(est);
        estoque.add(est);
        estoqueMovimentacoes.add(estMov);
        zerarCampos();
        atualizaValotTotal();
        refreshTable();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        int linha = tableItem.getSelectedId();
        if (linha < 1) {
            utils.Forms.mensagem("Selecione um item da tabela", AlertaTipos.erro);
        } else {
            for (int x = 0; x < estoque.size(); x++) {
                if (estoque.get(x).getId() == linha) {
                    estoque.remove(estoque.get(x));
                    estoqueMovimentacoes.remove(estoqueMovimentacoes.get(x));
                    atualizaValotTotal();
                    refreshTable();
                }
            }
        }
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void chkDataValidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDataValidadeActionPerformed

    }//GEN-LAST:event_chkDataValidadeActionPerformed

    private void txtLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoteActionPerformed

    }//GEN-LAST:event_txtLoteActionPerformed

    private void txtItemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemFocusLost
        try {
            Item i = new ItemService().findById(txtItem.getValueSelected());
            if (i.getUltimoValorVenda() > 0.01) {
                txtValorVenda.setValue(i.getUltimoValorVenda());
            } else {
                txtValorVenda.setValue(0d);
            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_txtItemFocusLost

    private void loadTiposMovimentação() {
        List<MovimentacaoTipo> movTipoTemp = new MovimentacaoTipoService().findAll();
        if (movTipoTemp.size() < 1) {
            utils.Forms.mensagem("É necessário cadastrar um tipo de movimentação de entrada de estoque para prosseguir!", AlertaTipos.erro);
            this.dispose();
        } else {
            movTipo = new ArrayList<>();
            for (MovimentacaoTipo m : movTipoTemp) {
                if (m.isAtivo() && m.getTipo().equals(MovimentacaoTipo.TipoMovimentacao.entrada)
                        && !m.getDescricao().contains("AJUSTE ENTRADA")) {
                    movTipo.add(m);
                }
            }
            jcbTipoMovimentacao.setModel(new DefaultComboBoxModel(new Vector(movTipo)));
        }
    }

    private void zerarCampos() {
        txtItem.setText("");
        try {
            jcbTipoMovimentacao.setSelectedIndex(0);
        } catch (Exception e) {
            utils.Forms.mensagem("Nenhum tipo de movimentação cadastrado!", AlertaTipos.erro);
        }
        txtValorCompra.setValue(0);
        txtValorVenda.setValue(0);
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
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JComboBox jcbTipoMovimentacao;
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
    private components.JTextFieldMoney txtValorVenda;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
