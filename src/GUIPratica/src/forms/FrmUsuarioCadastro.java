package forms;

import components.JDialogController;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import model.Cidade;
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
        txtPessoa.setText(u.getPessoa().getId()+"");
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
            if (id == 0) {
                try {
                    u.setSenha(converteSenha());
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(FrmUsuarioCadastro.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(FrmUsuarioCadastro.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (txtSenha.isEnabled()) {
                try {
                    u.setSenha(converteSenha());
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(FrmUsuarioCadastro.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(FrmUsuarioCadastro.class.getName()).log(Level.SEVERE, null, ex);
                }
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Código");

        labelSenha.setText("Senha");

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

        labelSenhaConf.setText("Confirmação da Senha");

        jLabel4.setText("Tipo de Usuário");

        txtTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GESTOR", "FUNCIONARIO" }));

        jLabel5.setText("Pessoa");

        txtAtivo.setSelected(true);
        txtAtivo.setText("Ativo");

        btnAlterarSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/chave.png"))); // NOI18N
        btnAlterarSenha.setText("Alterar Senha");
        btnAlterarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarSenhaActionPerformed(evt);
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
                        .addComponent(labelSenha)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                                .addComponent(txtAtivo))
                            .addComponent(txtSenha, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPessoa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSenhaConfirmacao, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelSenhaConf, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(btnSalvar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCancelar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAlterarSenha)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSenhaConf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSenhaConfirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAtivo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar)
                    .addComponent(btnAlterarSenha))
                .addContainerGap(20, Short.MAX_VALUE))
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
        JPasswordField password = new JPasswordField(10);
        password.setEchoChar('*');
        password.grabFocus();

        JLabel rotulo = new JLabel("Senha Atual:");

        JPanel entUsuario = new JPanel();
        entUsuario.add(rotulo);
        entUsuario.add(password);
        entUsuario.grabFocus();
        entUsuario.setFocusable(true);
        JOptionPane.showMessageDialog(null, entUsuario, "Autenticação!", JOptionPane.PLAIN_MESSAGE);
        String s = Arrays.toString(password.getPassword());
        try {
            if (u.autentica(s)) {
                txtSenha.setEnabled(true);
                txtSenhaConfirmacao.setEnabled(true);
            } else {
                utils.Forms.mensagem("Senha incorreta!", AlertaTipos.erro);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FrmUsuarioCadastro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FrmUsuarioCadastro.class.getName()).log(Level.SEVERE, null, ex);
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
    // End of variables declaration//GEN-END:variables
}
