/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.fluxo;

import components.JCampoBusca;
import components.JDialogController;
import forms.frmMain;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import model.fluxo.Conta;
import model.fluxo.ContaBancaria;
import model.fluxo.ContaCategoria;
import model.fluxo.Parcela;
import model.fluxo.ParcelaPagamento;
import services.fluxo.ContaBancariaService;
import services.fluxo.ContaCategoriaService;
import services.fluxo.ParcelaPagamentoService;
import services.fluxo.ParcelaService;
import utils.AlertaTipos;
import utils.Utils;

/**
 *
 * @author Charles
 */
public class FrmParcelaPagamentoCadastro extends JDialogController {

    private int id;
    private final ParcelaPagamentoService service = new ParcelaPagamentoService();

    private Conta conta;
    private Parcela parcela;

    /**
     * Creates new form frmCadastroParcelaPagamento Abre o formulário com o
     * primeiro pagamento
     *
     * @param c
     */
    public FrmParcelaPagamentoCadastro(Conta c) {
        this(c, c.getParcelas().stream().findFirst().get());

    }

    public FrmParcelaPagamentoCadastro(Conta c, Parcela parcela) {
        super(frmMain.getInstance(), "Manutenção de Contas Bancarias");
        if (!c.getParcelas().stream().findFirst().isPresent()) {
            throw new IllegalArgumentException("Conta sem parcelas");
        }

        initComponents();
        conta = c;
        this.parcela = parcela;
        setupForm();
    }

    private void ajustaValores() {
        double valorPago = 0d;
        valorPago = ParcelaService.valorTotalParcela(parcela);

        double valorParcela = jffValor.getValue();

        double diferenca = valorPago + valorParcela - parcela.getValor();
        //Se ultrapassa, ajusta o valor
        if (jcbImpostos.isSelected()) {
            if (diferenca > 0d) {
                jffValor1.setValue(diferenca);
            } else {
                jffValor1.setValue(0d);
            }

        }
        if (jcbDescontos.isSelected()) {
            if (diferenca < 0d) {
                jffValor2.setValue(Math.abs(diferenca));
            } else {
                jffValor2.setValue(0d);
            }
        }

    }

    private void setupForm() {
        // center the jframe on screen
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);
        txtConta.setEnabled(false);

        validator.validarObrigatorio(jtbParcela);
        validator.validarObrigatorio(jcbCategoria);
        validator.validarObrigatorio(jffValor);
        validator.validarCustom(jffValor, (value) -> jffValor.getValue() > 0d, "Valor deve ser maior que zero");
        validator.validarCustom(jcbCategoria1, (value) -> jffValor1.getValue() == 0d || jcbCategoria1.getSelectedItem() != null, "Valor obrigatório");
        validator.validarCustom(jcbCategoria2, (value) -> jffValor2.getValue() == 0d || jcbCategoria2.getSelectedItem() != null, "Valor obrigatório");

        List<ContaBancaria> contas = new ContaBancariaService().findAtivos();
        jcbContaBancaria.setModel(new DefaultComboBoxModel(new Vector(contas)));
        jcbContaBancaria1.setModel(new DefaultComboBoxModel(new Vector(contas)));
        jcbContaBancaria2.setModel(new DefaultComboBoxModel(new Vector(contas)));

        List<ContaCategoria> categorias = new ContaCategoriaService().findAtivos();
        jcbCategoria.setModel(new DefaultComboBoxModel(new Vector(categorias)));
        jcbCategoria1.setModel(new DefaultComboBoxModel(new Vector(categorias)));
        jcbCategoria2.setModel(new DefaultComboBoxModel(new Vector(categorias)));

        new JCampoBusca(jffValor, () -> ajustaValores());
        load();

    }

    private void load() {
        txtConta.setText(conta.toString());
        jtbParcela.setValue(parcela.getParcela());
        double valorPago = ParcelaService.valorTotalParcela(parcela);
        
        lblParcela.setText(Utils.formataDinheiro(parcela.getValor()));
        if (parcela.getValor() - valorPago > 0) {
            jffValor.setValue(parcela.getValor() - valorPago);
        } else {
            jffValor.setValue(0);
        }
        ajustaValores();
    }

    private void save() {
        if (!validator.isValido()) {
            return;
        }
        double valorPago = 0d;
        for (ParcelaPagamento pagamento : parcela.getPagamentos()) {
            valorPago += pagamento.getValor();
        }

        ParcelaPagamento p = new ParcelaPagamento();
        p.setData(new Date());
        p.setContaBancaria((ContaBancaria) jcbContaBancaria.getSelectedItem());
        p.setContaCategoria((ContaCategoria) jcbCategoria.getSelectedItem());
        p.setParcela(parcela);
        double valorParcela = jffValor.getValue();
        //Se ultrapassa, ajusta o valor
        if (jffValor1.getValue() > 0d) {
            ParcelaPagamento pImposto = new ParcelaPagamento();
            pImposto.setData(new Date());
            pImposto.setContaBancaria((ContaBancaria) jcbContaBancaria1.getSelectedItem());
            pImposto.setParcela(parcela);
            pImposto.setValor(jffValor1.getValue());
            pImposto.setContaCategoria((ContaCategoria) jcbCategoria1.getSelectedItem());
            if (jcbImpostos.isSelected() && valorParcela + valorPago > parcela.getValor()) {
                valorParcela -= jffValor1.getValue();
            }
            parcela.getPagamentos().add(pImposto);
        }
        if (jffValor2.getValue() > 0d) {
            ParcelaPagamento pDesconto = new ParcelaPagamento();
            pDesconto.setData(new Date());
            pDesconto.setContaBancaria((ContaBancaria) jcbContaBancaria2.getSelectedItem());
            pDesconto.setParcela(parcela);
            pDesconto.setValor(jffValor2.getValue());
            pDesconto.setContaCategoria((ContaCategoria) jcbCategoria2.getSelectedItem());

            parcela.getPagamentos().add(pDesconto);
        }

        p.setValor(valorParcela);

        parcela.getPagamentos().add(p);
        dispose();
//        ParcelaPagamento m;
//        if (id > 0) {
//            m = service.findById(id);
//        } else {
//            m = new ParcelaPagamento();
//        }
//        m.setNome(txtNome.getText());
//
//        if (jrbEntrada.isSelected()) {
//            m.setTipo(ParcelaPagamento.TipoCategoria.entrada);
//        } else {
//            m.setTipo(ParcelaPagamento.TipoCategoria.saida);
//
//        }
//        m.setAtivo(jcbAtivo.isSelected());
//        Utils.safeCode(() -> {
//            if (id == 0) {
//                service.insert(m);
//            } else {
//                service.update(m);
//            }
//            utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
//
//            dispose();
//        });
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
        txtConta = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jtbParcela = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jcbCategoria = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jffValor = new components.JTextFieldMoney();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jffValor1 = new components.JTextFieldMoney();
        jcbCategoria1 = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jffValor2 = new components.JTextFieldMoney();
        jcbCategoria2 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jcbDescontos = new javax.swing.JCheckBox();
        jcbImpostos = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jcbContaBancaria = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jcbContaBancaria1 = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jcbContaBancaria2 = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        lblParcela = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Conta");

        txtConta.setFocusable(false);

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/save.png"))); // NOI18N
        btnSalvar.setText("OK");
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

        jtbParcela.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(0), null, Integer.valueOf(1)));
        jtbParcela.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtbParcelaStateChanged(evt);
            }
        });

        jLabel3.setText("Parcela");

        jcbCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Carregando..." }));
        jcbCategoria.setMinimumSize(new java.awt.Dimension(94, 30));

        jLabel2.setText("Categoria");

        jffValor.setToolTipText("");
        jffValor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jffValor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jffValorPropertyChange(evt);
            }
        });
        jffValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jffValorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jffValorKeyTyped(evt);
            }
        });

        jLabel4.setText("Valor Total");

        jLabel5.setText("Imposto/Acréscimo");

        jffValor1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jcbCategoria1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Carregando..." }));
        jcbCategoria1.setMinimumSize(new java.awt.Dimension(94, 30));

        jLabel6.setText("Categoria");

        jLabel7.setText("Desconto/Perdas");

        jffValor2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jcbCategoria2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Carregando..." }));
        jcbCategoria2.setMinimumSize(new java.awt.Dimension(94, 30));

        jLabel8.setText("Categoria");

        jcbDescontos.setText("Lançar o que falta como desconto");
        jcbDescontos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jcbDescontosStateChanged(evt);
            }
        });

        jcbImpostos.setSelected(true);
        jcbImpostos.setText("Lançar excesso como acréscimo");
        jcbImpostos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jcbImpostosStateChanged(evt);
            }
        });

        jcbContaBancaria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Carregando..." }));
        jcbContaBancaria.setMinimumSize(new java.awt.Dimension(94, 30));

        jLabel9.setText("Conta");

        jcbContaBancaria1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Carregando..." }));
        jcbContaBancaria1.setMinimumSize(new java.awt.Dimension(94, 30));

        jLabel10.setText("Conta");

        jLabel11.setText("Conta");

        jcbContaBancaria2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Carregando..." }));
        jcbContaBancaria2.setMinimumSize(new java.awt.Dimension(94, 30));

        jLabel12.setText("Valor Parcela");

        lblParcela.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblParcela.setText("Label");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(187, 187, 187)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jcbCategoria1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jcbContaBancaria1, 0, 191, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jcbCategoria2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jcbContaBancaria2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnSalvar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCancelar))
                                    .addComponent(jffValor2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(jcbDescontos)
                                    .addComponent(jcbImpostos)
                                    .addComponent(jffValor1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jtbParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(41, 41, 41)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblParcela)
                                            .addComponent(jLabel12))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jffValor, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jcbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jcbContaBancaria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(20, 20, 20))))
                    .addComponent(txtConta)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtbParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblParcela))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jffValor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jcbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbContaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbImpostos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jffValor1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jcbCategoria1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbContaBancaria1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jcbDescontos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jffValor2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jcbCategoria2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbContaBancaria2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        save();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jtbParcelaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtbParcelaStateChanged

        int i = conta.getParcelas().indexOf(parcela);
        boolean desceu = parcela.getParcela() > (Integer) jtbParcela.getValue();
        int direcao = desceu ? -1 : 1;
        //navegando
        if (conta.getParcelas().size() > i + direcao && i + direcao >= 0) {
            Parcela p = conta.getParcelas().stream().filter((item) -> item.getParcela() == (Integer) jtbParcela.getValue()).findFirst().get();
            if (p != null) {
                this.parcela = p;
            }
        }

        load();
    }//GEN-LAST:event_jtbParcelaStateChanged

    private void jcbImpostosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jcbImpostosStateChanged
        jffValor1.setValue(0);
        ajustaValores();
    }//GEN-LAST:event_jcbImpostosStateChanged

    private void jcbDescontosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jcbDescontosStateChanged

        jffValor2.setValue(0);
        ajustaValores();
    }//GEN-LAST:event_jcbDescontosStateChanged

    private void jffValorPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jffValorPropertyChange

    }//GEN-LAST:event_jffValorPropertyChange

    private void jffValorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jffValorKeyTyped
        ajustaValores();
    }//GEN-LAST:event_jffValorKeyTyped

    private void jffValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jffValorKeyPressed
        ajustaValores();
    }//GEN-LAST:event_jffValorKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JComboBox jcbCategoria;
    private javax.swing.JComboBox jcbCategoria1;
    private javax.swing.JComboBox jcbCategoria2;
    private javax.swing.JComboBox jcbContaBancaria;
    private javax.swing.JComboBox jcbContaBancaria1;
    private javax.swing.JComboBox jcbContaBancaria2;
    private javax.swing.JCheckBox jcbDescontos;
    private javax.swing.JCheckBox jcbImpostos;
    private components.JTextFieldMoney jffValor;
    private components.JTextFieldMoney jffValor1;
    private components.JTextFieldMoney jffValor2;
    private javax.swing.JSpinner jtbParcela;
    private javax.swing.JLabel lblParcela;
    private javax.swing.JTextField txtConta;
    // End of variables declaration//GEN-END:variables
}
