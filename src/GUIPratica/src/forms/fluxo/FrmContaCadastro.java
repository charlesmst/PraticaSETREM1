/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.fluxo;

import components.BeanTableModel;
import components.JDialogController;
import components.ValidacoesTipos;
import forms.frmMain;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import model.Pessoa;
import model.fluxo.Conta;
import model.fluxo.ContaCategoria;
import model.fluxo.FormaPagamento;
import model.fluxo.Parcela;
import forms.FrmPessoaF2;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import model.Parametro;
import org.jdesktop.beansbinding.AutoBinding;
import services.PessoaService;
import org.jdesktop.beansbinding.Converter;
import services.fluxo.ContaCategoriaService;
import services.fluxo.ContaService;
import services.fluxo.FormaPagamentoService;
import utils.AlertaTipos;
import utils.Parametros;
import utils.Utils;

/**
 *
 * @author Charles
 */
public class FrmContaCadastro extends JDialogController {

    private int id;
    private final ContaService service = new ContaService();

    private Consumer<Conta> listenerOnSave;

    public Consumer<Conta> getListenerOnSave() {
        return listenerOnSave;
    }

    public void setDescricao(String descricao) {
        conta.setDescricao(descricao);
    }

    public void setPessoa(int pessoa, boolean disable) {
        jtbPessoa.setEditable(!disable);
        conta.setPessoa(new Pessoa(pessoa));
    }

    public void setListenerOnSave(Consumer<Conta> listenerOnSave) {
        this.listenerOnSave = listenerOnSave;
    }

    private DefaultComboBoxModel<ContaCategoria> modelCategoria;
    private DefaultComboBoxModel<FormaPagamento> modelFormaPagamento;

    private Conta conta;

    /**
     * Creates new form frmCadastroConta
     */
    public FrmContaCadastro() {
        this(0);
        conta.setTipo(Conta.ContaTipo.conta);
    }

    public FrmContaCadastro(double valor, int parcelas, String notaFiscal, Conta.ContaTipo tipo, ContaCategoria.TipoCategoria tipoEntradaSaida) {
        this(0);

        conta.setValorTotal(valor);
        panelParcelas1.setValor(valor);
        panelParcelas1.setParcelas(parcelas);
        jtfNotaFiscal.setText(notaFiscal);
        jrbAPagar.setEnabled(false);
        jrbAReceber.setEnabled(false);
        conta.setTipo(tipo);
        if (tipoEntradaSaida == ContaCategoria.TipoCategoria.entrada) {
            jrbAReceber.setSelected(true);
        } else {
            jrbAPagar.setSelected(true);
        }
    }

    public FrmContaCadastro(int id) {
        super(frmMain.getInstance(), "Manutenção de Contas");
        initComponents();
        this.id = id;
        setupForm();
//        jffValor.setValue(100);
    }

    private void setupForm() {
        // center the jframe on screen
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);

//        jcbFrequencia.setSelectedIndex(3);
        bgPagarReceber.add(jrbAPagar);
        bgPagarReceber.add(jrbAReceber);

        validator.validarObrigatorio(jcbCategoria);
        validator.validarObrigatorio(jcbFormaPagamento);
        validator.validarObrigatorio(jtbPessoa);

        validator.validarCustom(panelParcelas1.getTable(), (v) -> !conta.getParcelas().isEmpty(), "A conta deve ter no mínimo uma parcela");
        //Validar a vista uma parcela
        validator.validarCustom(panelParcelas1.getTable(), (v) -> conta.getFormaPagamento() != null && 
                (conta.getFormaPagamento().getId() != Integer.parseInt(Parametros.getInstance().getValue("forma_pagamento_a_vista")) || conta.getParcelas().size() == 1), "A conta deve ter uma parcela quando é a vísta");

        validator.validarDeBanco(jtbPessoa, new PessoaService());

        conta = new Conta();
//        conta.setDescricao("cascasd");
        if (id > 0) {
            load();
        }
        ajustaPagarReceber();

//        initBindings();
    }

    public void setCategoria(ContaCategoria cc){
        conta.setCategoria(cc);
    }
    private boolean binded = false;

    private void initBindings() {
        if (!binded) {
            binded = true;
            Utils.createBind(conta, "descricao", jtaDescricao);
            Utils.createBind(conta, "formaPagamento", jcbFormaPagamento);
            Utils.createBind(conta, "categoria", jcbCategoria);

            if (jcbCategoria.getSelectedIndex() == -1 && jcbCategoria.getItemCount() > 0) {
                jcbCategoria.setSelectedIndex(0);
            }
            panelParcelas1.bindValor();

            if (jcbFormaPagamento.getSelectedIndex() == -1 && jcbFormaPagamento.getItemCount() > 0) {
                jcbFormaPagamento.setSelectedIndex(0);
                conta.setFormaPagamento((FormaPagamento)jcbFormaPagamento.getModel().getSelectedItem());
            }
        }

        AutoBinding a = Utils.createBind(conta, "pessoa", jtbPessoa, false);
        a.setConverter(new Converter<Pessoa, String>() {

            @Override
            public Pessoa convertReverse(String value) {
                try {
                    Pessoa p = new Pessoa();
                    p.setId(Integer.valueOf(value));
                    return p;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public String convertForward(Pessoa value) {
                if (value != null) {
                    return value.getId() + "";
                }
                return "";
            }
        });
        a.bind();
        verificaMensagem();
//        if (conta.getPessoa() != null) {
//            jtbPessoa.setText(conta.getPessoa().getId() + "");
//        }
//        if (conta.getParcelas() == null) {
//            conta.setParcelas(new ArrayList<>());
//        }

//        panelParcelas1.setParcelas(conta.getParcelas());
//        org.jdesktop.beansbinding.Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, service, rootPane, null)
    }

    private void ajustaPagarReceber() {
        Utils.safeCode(() -> {

            ContaCategoriaService serviceCategoria = new ContaCategoriaService();
            FormaPagamentoService serviceFormaPagamento = new FormaPagamentoService();

            List<ContaCategoria> categorias;
            List<FormaPagamento> formas = serviceFormaPagamento.findBy("ativo", true);

            if (jrbAPagar.isSelected()) {
                categorias = serviceCategoria.findBy("tipo", ContaCategoria.TipoCategoria.saida);
            } else {
                categorias = serviceCategoria.findBy("tipo", ContaCategoria.TipoCategoria.entrada);
            }
            modelFormaPagamento = new DefaultComboBoxModel<>(new Vector<>(formas));
            jcbFormaPagamento.setModel(modelFormaPagamento);

            modelCategoria = new DefaultComboBoxModel<>(new Vector<>(categorias));
            jcbCategoria.setModel(modelCategoria);

            panelParcelas1.setConta(conta);
            initBindings();

        }, true);

    }

    private void load() {
        conta = service.findConta(id);
        jrbAPagar.setEnabled(false);
        jrbAReceber.setEnabled(false);
        jcbFormaPagamento.setEnabled(false);
        if(conta.getCategoria().getTipo() ==ContaCategoria.TipoCategoria.entrada)
            jrbAReceber.setSelected(true);
        else
            jrbAPagar.setSelected(true);
    }

    private void save() {
        if (!validator.isValido()) {
            return;
        }
//        Pessoa p = new Pessoa();
//        p.setId(jtbPessoa.getValueSelected());
//        conta.setPessoa(p);

        Utils.safeCode(() -> {
            if (id == 0) {
                service.insert(conta);
            } else {
                service.update(conta);
            }
            utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
            if (getListenerOnSave() != null) {
                getListenerOnSave().accept(conta);
            }
            dispose();
        },true);
    }

    private void verificaMensagem() {
        FormaPagamento f = conta.getFormaPagamento();
        if (f == null) {
            return;
        }
        boolean avista = f.getId() == Integer.parseInt(Parametros.getInstance().getValue("forma_pagamento_a_vista"));
        lblAviso.setVisible(avista);
        panelParcelas1.setParcelasEnable(!avista);
        if (avista) {
            panelParcelas1.setParcelas(1);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgPagarReceber = new javax.swing.ButtonGroup();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jrbAPagar = new javax.swing.JRadioButton();
        jrbAReceber = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jcbCategoria = new javax.swing.JComboBox();
        jcbFormaPagamento = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtbPessoa = new components.F2(FrmPessoaF2.class,(id)->new PessoaService().findById(id).getNome());
        jLabel4 = new javax.swing.JLabel();
        jtfNotaFiscal = new components.JTextFieldUpper();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaDescricao = new javax.swing.JTextArea();
        panelParcelas1 = new forms.fluxo.PanelParcelas();
        jSeparator1 = new javax.swing.JSeparator();
        lblAviso = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jrbAPagar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jrbAPagar.setSelected(true);
        jrbAPagar.setText("A Pagar");
        jrbAPagar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jrbAPagarItemStateChanged(evt);
            }
        });

        jrbAReceber.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jrbAReceber.setText("A Receber");
        jrbAReceber.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jrbAReceberItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Categoria:");

        jcbCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Carregando..." }));
        jcbCategoria.setMinimumSize(new java.awt.Dimension(94, 30));
        jcbCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCategoriaActionPerformed(evt);
            }
        });

        jcbFormaPagamento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Carregando..." }));
        jcbFormaPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbFormaPagamentoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Forma de Pagamento:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Pessoa Relacionada:");

        jtbPessoa.setMargin(new java.awt.Insets(2, 8, 2, 2));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Nota Fiscal:");

        jtfNotaFiscal.setMargin(new java.awt.Insets(2, 8, 2, 2));
        jtfNotaFiscal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfNotaFiscalMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Descrição:");

        jtaDescricao.setColumns(20);
        jtaDescricao.setRows(5);
        jtaDescricao.setMargin(new java.awt.Insets(2, 8, 2, 2));
        jtaDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtaDescricaoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jtaDescricao);

        panelParcelas1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblAviso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/warning.png"))); // NOI18N
        lblAviso.setText("Contas á vista podem ter somente uma parcela");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelParcelas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addGap(19, 19, 19))
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jrbAPagar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jrbAReceber)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtbPessoa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtfNotaFiscal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAviso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbFormaPagamento, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbAPagar)
                    .addComponent(jrbAReceber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtbPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelParcelas1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jrbAReceberItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jrbAReceberItemStateChanged
        ajustaPagarReceber();
    }//GEN-LAST:event_jrbAReceberItemStateChanged

    private void jrbAPagarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jrbAPagarItemStateChanged
        ajustaPagarReceber();
    }//GEN-LAST:event_jrbAPagarItemStateChanged

    private void jtaDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtaDescricaoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            panelParcelas1.requestFocus();
        }
    }//GEN-LAST:event_jtaDescricaoKeyPressed

    private void jtfNotaFiscalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfNotaFiscalMouseClicked
        System.out.println(conta.getDescricao());
        System.out.println(conta.getCategoria());
        System.out.println(conta.getFormaPagamento());

    }//GEN-LAST:event_jtfNotaFiscalMouseClicked

    private void jcbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbCategoriaActionPerformed

    private void jcbFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbFormaPagamentoActionPerformed
        verificaMensagem();
    }//GEN-LAST:event_jcbFormaPagamentoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgPagarReceber;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox jcbCategoria;
    private javax.swing.JComboBox jcbFormaPagamento;
    private javax.swing.JRadioButton jrbAPagar;
    private javax.swing.JRadioButton jrbAReceber;
    private javax.swing.JTextArea jtaDescricao;
    private components.F2 jtbPessoa;
    private components.JTextFieldUpper jtfNotaFiscal;
    private javax.swing.JLabel lblAviso;
    private forms.fluxo.PanelParcelas panelParcelas1;
    // End of variables declaration//GEN-END:variables
}
