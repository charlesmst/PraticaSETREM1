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
import java.util.Optional;
import java.util.function.Consumer;
import model.fluxo.Conta;
import model.fluxo.Parcela;
import model.fluxo.ParcelaPagamento;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Converter;
import services.PessoaService;
import services.fluxo.ContaService;
import services.fluxo.ParcelaService;
import utils.AlertaTipos;
import utils.Utils;

/**
 *
 * @author Charles
 */
public class FrmParcelaCadastro extends JDialogController {

    private Parcela parcela;

    private Consumer<Conta> listener;

    public Consumer<Conta> getListener() {
        return listener;
    }

    public void setListener(Consumer<Conta> listener) {
        this.listener = listener;
    }
    private Date quitado;
    private Conta conta;

    /**
     * Creates new form frmCadastroParcela
     *
     * @param c
     */
    public FrmParcelaCadastro(Conta c) {
        this(c, null);
    }

    /**
     *
     * @param c
     * @param parcela Parcela que é pra vim aberta
     */
    public FrmParcelaCadastro(Conta c, Parcela parcela) {
        super(frmMain.getInstance(), "Manutenção de Parcelas");
        if (c == null) {
            throw new IllegalArgumentException("conta");
        }
//        if (parcela != null && !c.getParcelas().contains(parcela)) {
//            throw new IllegalArgumentException("conta não está relacionada a parcela");
//        }
        if (parcela == null) {
            c.getParcelas().sort((c1, c2)
                    -> Integer.compare(c1.getParcela(), c2.getParcela())
            );
            for (Parcela parcela1 : c.getParcelas()) {
                if (ParcelaService.valorTotalParcela(parcela1) < parcela1.getValor()) {
                    parcela = parcela1;
                    break;
                }
            }
            if (parcela == null) {
                parcela = c.getParcelas().get(0);
            }
        }
        initComponents();
        this.parcela = parcela;
        this.conta = c;
        panelPagamentos1.setConta(conta);

        setupForm();
    }
    private AutoBinding avalor;
    private AutoBinding aboleto;

    private AutoBinding adata;
    private boolean binded;

    double valorOriginal;

    private void setupParcela() {
        if (parcela == null) {
            parcela = new Parcela();
            parcela.setConta(conta);
        } else {
            jtbParcela.setValue(parcela.getParcela());
        }
        valorOriginal = parcela.getValor();
        quitado = parcela.getDataQuitado();

        jtbConta.setText(conta.toString());
        if (!binded) {
            binded = true;

            aboleto = Utils.createBind(parcela, "boleto", jcbBoleto, true);

            avalor = Utils.createBind(parcela, "valor", jffValor, true);

            adata = Utils.createBind(parcela, "dataLancamento", jdfData, true);
        } else {

            aboleto.unbind();
            aboleto.setSourceObject(parcela);
            aboleto.bind();
            avalor.unbind();
            avalor.setSourceObject(parcela);
            avalor.bind();
            adata.unbind();
            adata.setSourceObject(parcela);
            adata.bind();
        }
        panelPagamentos1.atualiza(parcela);
        atualizaValor();
    }

    private void atualizaValor() {
        parcela.setValor(jffValor.getValue());
        double soma = 0d;
        for (ParcelaPagamento pagamento : parcela.getPagamentos()) {
            soma += pagamento.getValor();
        }
        if (soma == parcela.getValor()) {
            if (quitado == null) {
                quitado = new Date();
            }

            parcela.setDataQuitado(quitado);
        } else {
            parcela.setDataQuitado(null);
        }
        jffSaldo.setValue(soma - parcela.getValor());

    }

    private void setupForm() {
        // center the jframe on screen
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);

        validator.validarObrigatorio(jffValor);
        validator.validarCustom(jffValor, (valor) -> {
            return jffValor.getValue() > 0;
        }, "Valor deve ser maior que zero");
        validator.validarObrigatorio(jdfData);

        validator.validarCustom(jdfData, (valor) -> {
            if (parcela.getParcela() <= 1) {
                return true;
            }
            int i = conta.getParcelas().indexOf(parcela);
            return conta.getParcelas().get(i - 1).getDataLancamento().before(jdfData.getDate());

        }, "A data da parcela deve ser maior que anterior");

        new JCampoBusca(jffValor, () -> atualizaValor());
            setupParcela();
    }

    private void save() {
        if (!validator.isValido()) {
            return;
        }

        if (!conta.getParcelas().contains(parcela)) {
            conta.getParcelas().add(parcela);
        }
        atualizaValor();
        parcela.setBoleto(jcbBoleto.getText());
        parcela.setDataLancamento(jdfData.getDate());
        if (listener != null) {
            listener.accept(conta);
        }
        dispose();
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
        jLabel2 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        jcbBoleto = new components.JTextFieldUpper();
        jLabel3 = new javax.swing.JLabel();
        jtbParcela = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jffValor = new components.JTextFieldMoney();
        jdfData = new components.JDateField();
        jLabel5 = new javax.swing.JLabel();
        panelPagamentos1 = new forms.fluxo.PanelPagamentos();
        jtbConta = new components.JTextFieldUpper();
        jLabel6 = new javax.swing.JLabel();
        jffSaldo = new components.JTextFieldMoney();
        jLabel7 = new javax.swing.JLabel();
        btnNovaParcela = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Boleto:");

        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/save.png"))); // NOI18N
        btnSalvar.setText("OK");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jcbBoleto.setMargin(new java.awt.Insets(2, 8, 2, 2));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Parcela:");

        jtbParcela.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(0), null, Integer.valueOf(1)));
        jtbParcela.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtbParcelaStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Valor da parcela:");

        jffValor.setMargin(new java.awt.Insets(2, 8, 2, 2));
        jffValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jffValorFocusLost(evt);
            }
        });

        jdfData.setDate(new java.util.Date(1446514089000L));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Data de Vencimento:");

        jtbConta.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Conta:");

        jffSaldo.setEnabled(false);
        jffSaldo.setMargin(new java.awt.Insets(2, 8, 2, 2));
        jffSaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jffSaldoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Saldo da Parcela:");

        btnNovaParcela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/new.png"))); // NOI18N
        btnNovaParcela.setToolTipText("");
        btnNovaParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaParcelaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPagamentos1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtbParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNovaParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jdfData, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jtbConta, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jcbBoleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jffValor, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jffSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(20, 20, 20))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalvar)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jtbParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNovaParcela))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jdfData, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtbConta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(11, 11, 11)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jffValor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jffSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPagamentos1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        save();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jffSaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jffSaldoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jffSaldoActionPerformed

    private void jtbParcelaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtbParcelaStateChanged
        if (validator.isValido()) {
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
        }

        setupParcela();
    }//GEN-LAST:event_jtbParcelaStateChanged

    private void btnNovaParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaParcelaActionPerformed
        if (!validator.isValido()) {
            return;
        }
        Parcela p = new Parcela();
        p.setConta(conta);
        p.setDataLancamento(new Date());
        Optional<Parcela> op = conta.getParcelas().stream().max(
                (o, o2) -> Integer.compare(o.getParcela(), o2.getParcela())
        );
        if (op.isPresent()) {
            p.setParcela(op.get().getParcela() + 1);
            p.setValor(op.get().getValor());

        } else {
            p.setParcela(1);
        }
        conta.getParcelas().add(p);
        parcela = p;
        setupParcela();
        jdfData.requestFocus();
    }//GEN-LAST:event_btnNovaParcelaActionPerformed

    private void jffValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jffValorFocusLost
        if (parcela.getId() == 0) {
            conta.setValorTotal(ContaService.valorConta(conta));
        } else if (valorOriginal != jffValor.getValue()) {
            FrmConfirmarValorParcela frm = new FrmConfirmarValorParcela(conta, parcela, valorOriginal);
            frm.setListenerOnCancelar((e) -> {
                parcela.setValor(valorOriginal);
                jffValor.setValue(valorOriginal);
            });
            frm.setVisible(true);
        }
    }//GEN-LAST:event_jffValorFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNovaParcela;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private components.JTextFieldUpper jcbBoleto;
    private components.JDateField jdfData;
    private components.JTextFieldMoney jffSaldo;
    private components.JTextFieldMoney jffValor;
    private components.JTextFieldUpper jtbConta;
    private javax.swing.JSpinner jtbParcela;
    private forms.fluxo.PanelPagamentos panelPagamentos1;
    // End of variables declaration//GEN-END:variables
}
