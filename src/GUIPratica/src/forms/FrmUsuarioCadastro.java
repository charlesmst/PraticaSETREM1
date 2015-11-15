package forms;

import components.JDialogController;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import model.Cidade;
import model.Pessoa;
import model.Usuario;
import services.CidadeService;
import services.PessoaService;
import services.UsuarioService;
import utils.AlertaTipos;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmUsuarioCadastro extends JDialogController {

    private int id;
    private final UsuarioService service = new UsuarioService();
    Usuario u;

    public FrmUsuarioCadastro() {
        this(0);
    }

    public FrmUsuarioCadastro(int id) {
        super(frmMain.getInstance(), "Manutenção de Usuários");
        initComponents();
        this.id = id;
        setupForm();
    }

    private void setupForm() {
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);
        txtCodigo.setEnabled(false);
        validator.validarObrigatorio(txtPessoa);
        validator.validarObrigatorio(txtUsuario);
        validator.validarDeBanco(txtPessoa, new PessoaService());
        btnAlterarSenha.setVisible(false);
        if (id > 0) {
            load();
        } else {
            validator.validarObrigatorio(txtSenha);
            validator.validarObrigatorio(txtSenhaConfirmacao);
        }
    }

    private void load() {
        btnAlterarSenha.setVisible(true);
        txtSenha.setEnabled(false);
        txtSenhaConfirmacao.setEnabled(false);
        u = service.findById(id);
        txtCodigo.setText(String.valueOf(u.getId()));
        txtPessoa.setText(u.getPessoa().getId() + "");
        txtUsuario.setText(u.getUsuario());
        if (u.getNivel().equals(Usuario.Tipo.gestor)) {
            txtTipoUsuario.setSelectedIndex(0);
        } else if (u.getNivel().equals(Usuario.Tipo.funcionario)) {
            txtTipoUsuario.setSelectedIndex(1);
        }
        if (u.isAtivo()) {
            txtAtivo.setSelected(true);
        } else {
            txtAtivo.setSelected(false);
        }
    }

    private void save() {
        if (!validator.isValido()) {
            return;
        } else if (Arrays.equals(txtSenha.getPassword(), txtSenhaConfirmacao.getPassword())) {
            Usuario u;
            if (id > 0) {
                u = service.findById(id);
            } else {
                u = new Usuario();
            }
            u.setPessoa(new PessoaService().findById(txtPessoa.getValueSelected()));
            u.setUsuario(txtUsuario.getText());
            if (id == 0 || txtSenha.isEnabled()) {
               
                    u.setSenha(Utils.criptografa(new String(txtSenha.getPassword())));
               
            }
            if (txtTipoUsuario.getSelectedIndex() == 0) {
                u.setNivel(Usuario.Tipo.gestor);
            } else if (txtTipoUsuario.getSelectedIndex() == 1) {
                u.setNivel(Usuario.Tipo.funcionario);
            }
            if (txtAtivo.isSelected()) {
                u.setAtivo(true);
            } else {
                u.setAtivo(false);
            }

            Utils.safeCode(() -> {
                if (id == 0) {
                    service.insert(u);
                } else {
                    service.update(u);
                }
                utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);

                dispose();
            });

        } else {
            utils.Forms.mensagem("As senhas não são iguais", AlertaTipos.erro);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        labelSenha = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        labelSenhaConf = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTipoUsuario = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        txtPessoa = new components.F2(FrmPessoaF2.class, (id)-> new PessoaService().findById(id).toString());
        txtSenhaConfirmacao = new javax.swing.JPasswordField();
        txtAtivo = new javax.swing.JCheckBox();
        btnAlterarSenha = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtUsuario = new components.JTextFieldUpper();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Código:");

        txtCodigo.setMargin(new java.awt.Insets(2, 8, 2, 2));

        labelSenha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelSenha.setText("Senha:");

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

        labelSenhaConf.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelSenhaConf.setText("Confirmação Senha:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Tipo de Usuário: ");

        txtTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GESTOR", "FUNCIONARIO" }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Pessoa:");

        txtSenha.setMargin(new java.awt.Insets(2, 8, 2, 2));

        txtPessoa.setMargin(new java.awt.Insets(2, 8, 2, 2));

        txtSenhaConfirmacao.setMargin(new java.awt.Insets(2, 8, 2, 2));

        txtAtivo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtAtivo.setSelected(true);
        txtAtivo.setText("Ativo");

        btnAlterarSenha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAlterarSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/chave.png"))); // NOI18N
        btnAlterarSenha.setText("Alterar Senha");
        btnAlterarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarSenhaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Usuário:");

        txtUsuario.setMargin(new java.awt.Insets(2, 8, 2, 2));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtTipoUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAtivo)
                        .addGap(190, 190, 190))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(266, 266, 266))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAlterarSenha))
                            .addComponent(txtUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPessoa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelSenha)
                                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelSenhaConf)
                                    .addComponent(txtSenhaConfirmacao, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))))
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
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelSenha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelSenhaConf)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSenhaConfirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAtivo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlterarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        save();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnAlterarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarSenhaActionPerformed
        JPasswordField jpf = new JPasswordField();
        JOptionPane jop = new JOptionPane(jpf,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = jop.createDialog("Senha atual:");
        dialog.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                jpf.requestFocusInWindow();
            }
        });
        dialog.setVisible(true);
        int result = (Integer) jop.getValue();
        dialog.dispose();
        char[] password = null;
        if (result == JOptionPane.OK_OPTION) {
            password = jpf.getPassword();
        }
        String s = String.valueOf(password);
        String cr = Utils.criptografa(s);
        if (cr.equals(u.getSenha())) {
            txtSenha.setEnabled(true);
            txtSenhaConfirmacao.setEnabled(true);
            validator.validarObrigatorio(txtSenha);
            validator.validarObrigatorio(txtSenhaConfirmacao);
        } else {
            utils.Forms.mensagem("Senha incorreta!", AlertaTipos.erro);
        }

    }//GEN-LAST:event_btnAlterarSenhaActionPerformed

    private String converteSenha() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String senha = Arrays.toString(txtSenha.getPassword());
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        String senhahex = hexString.toString();
        return senhahex;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterarSenha;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel labelSenhaConf;
    private javax.swing.JCheckBox txtAtivo;
    private javax.swing.JTextField txtCodigo;
    private components.F2 txtPessoa;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JPasswordField txtSenhaConfirmacao;
    private javax.swing.JComboBox txtTipoUsuario;
    private components.JTextFieldUpper txtUsuario;
    // End of variables declaration//GEN-END:variables
}
