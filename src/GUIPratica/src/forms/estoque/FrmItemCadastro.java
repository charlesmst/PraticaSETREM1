package forms.estoque;

import components.JDialogController;
import forms.frmMain;
import model.estoque.Item;
import model.estoque.ItemTipo;
import model.estoque.Prateleira;
import services.estoque.ItemService;
import services.estoque.ItemTipoService;
import services.estoque.PrateleiraService;
import utils.AlertaTipos;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmItemCadastro extends JDialogController {

    private int id;
    private final ItemService service = new ItemService();

    public FrmItemCadastro() {
        this(0);
    }

    public FrmItemCadastro(int id) {
        super(frmMain.getInstance(), "Manutenção de Itens de Estoque");
        initComponents();
        this.id = id;
        setupForm();
    }

    private void setupForm() {
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);
        txtCodigo.setEnabled(false);

        validator.validarObrigatorio(txtItemTipo);
        validator.validarDeBanco(txtItemTipo, new ItemTipoService());

        validator.validarObrigatorio(txtPrateleira);
        validator.validarDeBanco(txtPrateleira, new PrateleiraService());

        //int quantidade = spinEstoqueMin.getComponentCount();
        //validator.validarObrigatorio(spinEstoqueMin);
//        validator.validarCustom(spinEstoqueMin, (valor) -> {
//            if (quantidade <= 1) {
//                return true;
//            } else {
//                return false;
//            }
//        }, "Informe a quantidade mínima do estoque!");
        validator.validarObrigatorio(txtDescricao);
        validator.validarCustom(txtDescricao,
                (valor) -> service.unico(id, valor), "Descrição já existe");
        if (id > 0) {
            load();
        }
    }

    private void load() {
        Item i = service.findById(id);
        txtCodigo.setText(String.valueOf(i.getId()));
        txtItemTipo.setText(String.valueOf(i.getItemTipo().getId()));
        txtDescricao.setText(i.getDescricao());
        spinEstoqueMin.setValue(i.getEstoqueMinimo());
        txtPrateleira.setText(String.valueOf(i.getPrateleira().getId()));
    }

    private void save() {
        if (!validator.isValido()) {
            return;
        }
        Item i;
        if (id > 0) {
            i = service.findById(id);
        } else {
            i = new Item();
        }

        ItemTipo itemTipo = new ItemTipoService().findById(Integer.parseInt(txtItemTipo.getText()));
        i.setItemTipo(itemTipo);
        i.setDescricao(txtDescricao.getText());
        i.setEstoqueMinimo(Integer.parseInt(spinEstoqueMin.getValue().toString()));
        Prateleira prateleira = new PrateleiraService().findById(Integer.parseInt(txtPrateleira.getText()));
        i.setPrateleira(prateleira);

        Utils.safeCode(() -> {
            if (id == 0) {
                service.insert(i);
            } else {
                service.update(i);
            }
            utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
            dispose();
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtDescricao = new components.JTextFieldUpper();
        txtItemTipo = new components.F2(FrmItemTipoF2.class, (id) -> new ItemTipoService().findById(id).getNome());
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spinEstoqueMin = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        txtPrateleira = new components.F2(FrmPrateleiraF2.class, (id) -> new PrateleiraService().findById(id).getDescricao());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Itens de Estoque");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Código:");

        txtCodigo.setEditable(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Descrição:");

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

        txtDescricao.setMargin(new java.awt.Insets(2, 8, 2, 2));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Tipo de Item:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Estoque mínimo padrão:");

        spinEstoqueMin.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Prateleira:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar))
                            .addComponent(txtDescricao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtItemTipo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinEstoqueMin, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrateleira, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtItemTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(spinEstoqueMin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        save();
    }//GEN-LAST:event_btnSalvarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSpinner spinEstoqueMin;
    private javax.swing.JTextField txtCodigo;
    private components.JTextFieldUpper txtDescricao;
    private components.F2 txtItemTipo;
    private components.F2 txtPrateleira;
    // End of variables declaration//GEN-END:variables
}
