package forms.estoque;

import forms.fluxo.*;
import components.JDialogController;
import forms.frmMain;
import java.util.function.Function;
import model.estoque.ItemTipo;
import services.estoque.ItemTipoService;
import utils.AlertaTipos;
import utils.Utils;

public class FrmItemTipoCadastro extends JDialogController {

    private int id;
    private final ItemTipoService service = new ItemTipoService();

    /**
     * Creates new form frmCadastroItemTipo
     */
    public FrmItemTipoCadastro() {
        this(0);
    }

    public FrmItemTipoCadastro(int id) {
        super(frmMain.getInstance(), "Manutenção dos Tipos de Itens do Estoque");
        initComponents();
        this.id = id;
        setupForm();
    }

    private void setupForm() {
     
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);
        txtCodigo.setEnabled(false);
        validator.validarObrigatorio(txtNome);
        validator.validarCustom(txtNome, (valor) -> service.unico(id, valor), "Nome já existe");
        if (id > 0) {
            load();
        }
    }

    private void load() {
        ItemTipo it = service.findById(id);
        txtCodigo.setText(String.valueOf(it.getId()));
        txtNome.setText(it.getNome());
        jcbAtivo.setSelected(it.isAtivo());
    }

    private void save() {
        if (!validator.isValido()) {
            return;
        }
        ItemTipo it;
        if (id > 0) {
            it = service.findById(id);
        } else {
            it = new ItemTipo();
        }
        it.setNome(txtNome.getText());
        it.setAtivo(jcbAtivo.isSelected());

        Utils.safeCode(() -> {
            if (id == 0) {
                service.insert(it);
            } else {
                service.update(it);
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
        txtNome = new components.JTextFieldUpper();
        jcbAtivo = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Código:");

        txtCodigo.setMargin(new java.awt.Insets(2, 8, 2, 2));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Nome:");

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

        txtNome.setMargin(new java.awt.Insets(2, 8, 2, 2));

        jcbAtivo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbAtivo.setSelected(true);
        jcbAtivo.setText("Ativo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcbAtivo)
                        .addContainerGap(594, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar))
                            .addComponent(txtNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 586, Short.MAX_VALUE))
                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jcbAtivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
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
    private javax.swing.JCheckBox jcbAtivo;
    private javax.swing.JTextField txtCodigo;
    private components.JTextFieldUpper txtNome;
    // End of variables declaration//GEN-END:variables
}
