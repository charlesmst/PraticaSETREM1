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
public class FrmEstoqueCadastro extends JDialogController {

    private int id;
    private List<Estoque> estoque = new ArrayList<>();
    private final EstoqueService serviceEst = new EstoqueService();
    private final EstoqueMovimentacaoService serviceEstMov = new EstoqueMovimentacaoService();
    List<MovimentacaoTipo> movTipo;

    public FrmEstoqueCadastro() {
        this(0);
    }

    public FrmEstoqueCadastro(int id) {
        super(frmMain.getInstance(), "Manutenção de Estoque");
        initComponents();
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

        //int quantidade = spinEstoqueMin.getComponentCount();
        //validator.validarObrigatorio(spinEstoqueMin);
//        validator.validarCustom(spinEstoqueMin, (valor) -> {
//            if (quantidade <= 1) {
//                return true;
//            } else {
//                return false;
//            }
//        }, "Informe a quantidade mínima do estoque!");
//        validator.validarObrigatorio(txtDescricao);
//        validator.validarCustom(txtDescricao,
//                (valor) -> service.unico(id, valor), "Descrição já existe");
//        if (id > 0) {
//            load();
//        }
    }

    private void load() {
//        Estoque i = service.findById(id);
//        txtCodigo.setText(String.valueOf(i.getId()));
//        txtEstoqueTipo.setText(String.valueOf(i.getEstoqueTipo().getId()));
//        txtDescricao.setText(i.getDescricao());
//        spinQuantidade.setValue(i.getEstoqueMinimo());
//        txtTipoMovimentacao.setText(String.valueOf(i.getPrateleira().getId()));
    }

    private void save() {
        for (int x = 0; x < estoque.size(); x++) {
            Estoque est;
            EstoqueMovimentacao estMov;
//        if (id > 0) {
//            est = serviceEst.findById(id);
//            estMov = serviceEstMov.findById(id);
//        } else {
//            est = new Estoque();
//            estMov = new EstoqueMovimentacao();
//        }
            est = estoque.get(x);
            estMov = estoque.get(x).getMovimentacoes().get(0);
            int w = 0;
            for (EstoqueMovimentacao eM : estoque.get(x).getMovimentacoes()) {
                estMov = estoque.get(x).getMovimentacoes().get(w);
                w++;
            }
            //estMov = estoque.get(x).getMovimentacoes().get(0);
            serviceEst.insert(est);
            serviceEstMov.insert(estMov);
            utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
            dispose();
//EstoqueTipo itemTipo = new EstoqueTipoService().findById(Integer.parseInt(txtEstoqueTipo.getText()));
//        i.setEstoqueTipo(itemTipo);
//        i.setDescricao(txtDescricao.getText());
//        i.setEstoqueMinimo(Integer.parseInt(spinQuantidade.getValue().toString()));
//        Prateleira prateleira = new PrateleiraService().findById(Integer.parseInt(txtTipoMovimentacao.getText()));
//        i.setPrateleira(prateleira);
//            Utils.safeCode(() -> {
//                if (id == 0) {
//                    serviceEst.insert(est);
//                    serviceEstMov.insert(estMov);
//
//                } else {
//                    serviceEst.update(est);
//                    serviceEstMov.update(estMov);
//                }
//                utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
//                dispose();
//            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tableItem = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jcbTipoMovimentação = new javax.swing.JComboBox();
        jDateCompra = new components.JDateField();
        txtDataValidade = new components.JDateField();
        txtValorCompra = new components.JTextFieldMoney();
        spinerQuantidade = new javax.swing.JSpinner();
        chkDataValidade = new javax.swing.JCheckBox();

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

        tableItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Item", "Movimentação", "Valor", "Quantidade", "Data", "Lote"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableItem);

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

        txtDataValidade.setModel(new javax.swing.SpinnerDateModel());
        txtDataValidade.setEnabled(false);

        txtValorCompra.setMinimumSize(new java.awt.Dimension(6, 30));

        spinerQuantidade.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        chkDataValidade.setText("Definir Data de Validade");
        chkDataValidade.setFocusable(false);
        chkDataValidade.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkDataValidadeStateChanged(evt);
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
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAdicionar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRemover))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtValorCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGap(46, 46, 46)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel4)
                                                .addComponent(spinerQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(50, 50, 50)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel9)
                                                .addComponent(jDateCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(txtDataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(chkDataValidade))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jcbTipoMovimentação, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(chkDataValidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbTipoMovimentação, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinerQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdicionar)
                            .addComponent(btnRemover))))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        Estoque est = new Estoque();
        EstoqueMovimentacao estMov = new EstoqueMovimentacao();

        est.setItem(new ItemService().findById(txtItem.getValueSelected()));
        estMov.setMovimentacaoTipo((MovimentacaoTipo) jcbTipoMovimentação.getModel().getSelectedItem());
        estMov.setValorUnitario(txtValorCompra.getValue());
        estMov.setQuantidade(Integer.parseInt(spinerQuantidade.getValue().toString()));
        estMov.setDataLancamento(new Date());
        estMov.setDescricao("Movimentação de " + estMov.getMovimentacaoTipo().getDescricao()
                + " de " + estMov.getQuantidade() + " Item: " + est.getItem().getDescricao());
        est.setDataCompra(jDateCompra.getDate());
        est.setLote(txtLote.getText());
        if (chkDataValidade.isSelected()) {
            est.setDataValidade(txtDataValidade.getDate());
        } else {
            est.setDataValidade(null);
        }
        est.setValorUnitario(txtValorCompra.getValue());
        est.setQuantidadeDisponivel(Integer.parseInt(spinerQuantidade.getValue().toString()));
        List<EstoqueMovimentacao> movimentacoes = new ArrayList<>();
        movimentacoes.add(estMov);
        estMov.setEstoque(est);
        est.setMovimentacoes(movimentacoes);
        estoque.add(est);
        zerarCampos();
        refreshTable();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void chkDataValidadeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkDataValidadeStateChanged
        if (chkDataValidade.isSelected()) {
            txtDataValidade.setEnabled(true);
        } else {
            txtDataValidade.setEnabled(false);
        }
    }//GEN-LAST:event_chkDataValidadeStateChanged

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
        DefaultTableModel table = (DefaultTableModel) tableItem.getModel();
        table.setNumRows(0);
        txtItem.setText("");
        jcbTipoMovimentação.setSelectedIndex(0);
        txtValorCompra.setValue(0);
        spinerQuantidade.setValue(1);
        txtLote.setText("");
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tableItem.getModel();
        model.setNumRows(0);
        for (Estoque est : estoque) {
            EstoqueMovimentacao estMov = new EstoqueMovimentacao();
            for (int x = 0; x < est.getMovimentacoes().size(); x++) {
                if (est.getMovimentacoes().get(x).getEstoque().hashCode() == est.hashCode()) {
                    estMov = est.getMovimentacoes().get(x);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro! Contate o administrador");
                }
                model.addRow(new String[]{
                    "" + est.getItem().getDescricao(),
                    "" + estMov.getMovimentacaoTipo().getDescricao(),
                    "" + est.getValorUnitario(),
                    "" + estMov.getQuantidade(),
                    "" + est.getDataCompra().getDate(),
                    "" + est.getLote()
                });
            }

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkDataValidade;
    private components.JDateField jDateCompra;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox jcbTipoMovimentação;
    private javax.swing.JSpinner spinerQuantidade;
    private javax.swing.JTable tableItem;
    private components.JDateField txtDataValidade;
    private components.F2 txtItem;
    private components.JTextFieldUpper txtLote;
    private components.JTextFieldMoney txtValorCompra;
    // End of variables declaration//GEN-END:variables
}
