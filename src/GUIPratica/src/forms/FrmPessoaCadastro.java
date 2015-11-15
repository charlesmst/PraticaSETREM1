package forms;

import components.JDialogController;
import model.Pessoa;
import model.PessoaFisica;
import model.PessoaJuridica;
import services.PessoaService;
import services.CidadeService;
import services.PessoaFisicaService;
import services.PessoaJuridicaService;
import utils.AlertaTipos;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmPessoaCadastro extends JDialogController {

    private int id;
    private final PessoaService service = new PessoaService();
    Pessoa p = new Pessoa();
    PessoaFisica pF = new PessoaFisica();
    PessoaJuridica pJ = new PessoaJuridica();

    public FrmPessoaCadastro() {
        this(0);
    }

    public FrmPessoaCadastro(int id) {
        super(frmMain.getInstance(), "Manutenção de Pessoas");
        initComponents();
        this.id = id;
        setupForm();
    }

    private void setupForm() {
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);
        txtCodigo.setEnabled(false);
        txtCheckDefDataNasc.setSelected(false);
        buttonGroup1.add(txtRadioFisica);
        buttonGroup1.add(txtRadioJuridica);
        buttonGroup1.clearSelection();
        desabilitaFisica();
        desabilitaJuridico();
        validator.validarObrigatorio(txtNome);
        validator.validarObrigatorio(txtCidade);
        validator.validarDeBanco(txtCidade, new CidadeService());

        if (id > 0) {
            load();
        }
    }

    private void desabilitaFisica() {
        panelFisica.setEnabled(false);
        txtSexo.setEnabled(false);
        txtRg.setEnabled(false);
        txtCpf.setEnabled(false);
    }

    private void desabilitaJuridico() {
        panelJuridica.setEnabled(false);
        txtNomeFantasia.setEnabled(false);
        txtRazaoSocial.setEnabled(false);
        txtCnpj.setEnabled(false);
    }

    private void habilitaFisica() {
        panelFisica.setEnabled(true);
        txtSexo.setEnabled(true);
        txtRg.setEnabled(true);
        txtCpf.setEnabled(true);
    }

    private void habilitaJuridico() {
        panelJuridica.setEnabled(true);
        txtNomeFantasia.setEnabled(true);
        txtRazaoSocial.setEnabled(true);
        txtCnpj.setEnabled(true);
    }

    private void load() {
        p = service.findById(id);
        pF = new PessoaFisica();
        pJ = new PessoaJuridica();

        if (p.getTipo().equals(Pessoa.TipoPessoa.fisica)) {
            habilitaFisica();
            desabilitaJuridico();
            txtRadioFisica.setEnabled(false);
            txtRadioJuridica.setEnabled(false);
            pF = new PessoaFisicaService().findById(p.getId());
            if (pF != null) {
                if (pF.getSexo() != null) {
                    txtSexo.setSelectedItem(pF.getSexo().toString().toUpperCase());
                }
                txtRg.setText(pF.getRg());
                txtCpf.setText(pF.getCpf());
            }

        } else if (p.getTipo().equals(Pessoa.TipoPessoa.juridica)) {
            desabilitaFisica();
            habilitaJuridico();
            txtRadioFisica.setEnabled(false);
            txtRadioJuridica.setEnabled(false);
            pJ = new PessoaJuridicaService().findById(p.getId());
            txtNomeFantasia.setText(pJ.getNomeFantasia());
            txtRazaoSocial.setText(pJ.getRazaoSocial());
            txtCnpj.setText(pJ.getCnpj());
        }

        txtCodigo.setText(p.getId() + "");
        txtNome.setText(p.getNome());
        txtTelefone.setText(p.getTelefone());
        txtTelefone2.setText(p.getTelefoneSecundario());
        txtEmail.setText(p.getEmail());
        if (p.getDataNascimento() != null) {
            txtCheckDefDataNasc.setSelected(true);
            txtDataNasc.setDate(p.getDataNascimento());
        }
        if (p.getCidade() != null) {
            txtCidade.setText(p.getCidade().getId() + "");
        }
        txtEndereco.setText(p.getEndereco());
    }

    private void save() {
        if (!txtRadioFisica.isSelected() && !txtRadioJuridica.isSelected()) {
            utils.Forms.mensagem("Selecione o tipo de Pessoa", AlertaTipos.erro);
        }
        p.setNome(txtNome.getText());
        p.setTelefone(txtTelefone.getText());
        p.setTelefoneSecundario(txtTelefone2.getText());
        p.setEmail(txtEmail.getText());
        if (txtCheckDefDataNasc.isSelected()) {
            p.setDataNascimento(txtDataNasc.getDate());
        } else {
            p.setDataNascimento(null);
        }
        p.setCidade(new CidadeService().findById(Integer.parseInt(txtCidade.getText())));
        p.setEndereco(txtEndereco.getText());
        if (txtRadioFisica.isSelected()) {
            if (txtSexo.getSelectedIndex() == 0) {
                pF.setSexo(PessoaFisica.SexoPessoa.masculino);
            } else if (txtSexo.getSelectedIndex() == 1) {
                pF.setSexo(PessoaFisica.SexoPessoa.feminino);
            }
            pF.setPessoa(p);
            pF.setCpf(txtCpf.getText());
            pF.setRg(txtRg.getText());
            p.setTipo(Pessoa.TipoPessoa.fisica);
            Utils.safeCode(() -> {

                new PessoaFisicaService().insert(pF);

                utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
                dispose();
            });
        } else if (txtRadioJuridica.isSelected()) {
            pJ.setPessoa(p);
            pJ.setNomeFantasia(txtNomeFantasia.getText());
            pJ.setRazaoSocial(txtRazaoSocial.getText());
            pJ.setCnpj(txtCnpj.getText());
            p.setTipo(Pessoa.TipoPessoa.juridica);
            Utils.safeCode(() -> {

                new PessoaJuridicaService().insert(pJ);

                utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
                dispose();
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCidade = new components.F2(FrmCidadeF2.class, (id)-> new CidadeService().findById(id).toString());
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDataNasc = new components.JDateField();
        jLabel2 = new javax.swing.JLabel();
        txtRadioJuridica = new javax.swing.JRadioButton();
        panelFisica = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtSexo = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        txtRg = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCpf = new javax.swing.JTextField();
        panelJuridica = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtNomeFantasia = new components.JTextFieldUpper();
        txtRazaoSocial = new components.JTextFieldUpper();
        txtCnpj = new components.JTextFieldUpper();
        txtRadioFisica = new javax.swing.JRadioButton();
        txtCheckDefDataNasc = new javax.swing.JCheckBox();
        txtEndereco = new components.JTextFieldUpper();
        txtNome = new components.JTextFieldUpper();
        txtTelefone = new javax.swing.JFormattedTextField();
        txtTelefone2 = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Código:");

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

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Endereço:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Telefone:");

        txtCidade.setMargin(new java.awt.Insets(2, 8, 2, 2));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Nome:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Cidade:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Telefone: (9 dígitos)");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("E-mail:");

        txtEmail.setMargin(new java.awt.Insets(2, 8, 2, 2));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Data de Nascimento/Fundação:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Tipo de Pessoa:");

        txtRadioJuridica.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtRadioJuridica.setText("Jurídica");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, panelJuridica, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), txtRadioJuridica, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        txtRadioJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRadioJuridicaActionPerformed(evt);
            }
        });

        panelFisica.setBorder(javax.swing.BorderFactory.createTitledBorder("Pessoa Física"));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Sexo:");

        txtSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MASCULINO", "FEMININO" }));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("RG:");

        txtRg.setMargin(new java.awt.Insets(2, 8, 2, 2));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("CPF:");

        txtCpf.setMargin(new java.awt.Insets(2, 8, 2, 2));

        javax.swing.GroupLayout panelFisicaLayout = new javax.swing.GroupLayout(panelFisica);
        panelFisica.setLayout(panelFisicaLayout);
        panelFisicaLayout.setHorizontalGroup(
            panelFisicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFisicaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelFisicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel6)
                    .addComponent(txtRg, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addComponent(txtCpf)
                    .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        panelFisicaLayout.setVerticalGroup(
            panelFisicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFisicaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelJuridica.setBorder(javax.swing.BorderFactory.createTitledBorder("Pessoa Jurídica"));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Nome Fantasia:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Razão Social:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("CNPJ:");

        txtNomeFantasia.setMargin(new java.awt.Insets(2, 8, 2, 2));

        txtRazaoSocial.setMargin(new java.awt.Insets(2, 8, 2, 2));

        txtCnpj.setMargin(new java.awt.Insets(2, 8, 2, 2));

        javax.swing.GroupLayout panelJuridicaLayout = new javax.swing.GroupLayout(panelJuridica);
        panelJuridica.setLayout(panelJuridicaLayout);
        panelJuridicaLayout.setHorizontalGroup(
            panelJuridicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJuridicaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelJuridicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtRazaoSocial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCnpj, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNomeFantasia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelJuridicaLayout.setVerticalGroup(
            panelJuridicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJuridicaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtRadioFisica.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtRadioFisica.setText("Física");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, panelFisica, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), txtRadioFisica, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        txtRadioFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRadioFisicaActionPerformed(evt);
            }
        });

        txtCheckDefDataNasc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCheckDefDataNasc.setText("Definir");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtDataNasc, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), txtCheckDefDataNasc, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        txtEndereco.setMargin(new java.awt.Insets(2, 8, 2, 2));

        txtNome.setMargin(new java.awt.Insets(2, 8, 2, 2));

        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefone.setMargin(new java.awt.Insets(2, 8, 2, 2));

        try {
            txtTelefone2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefone2.setMargin(new java.awt.Insets(2, 8, 2, 2));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtRadioFisica)
                        .addGap(12, 12, 12)
                        .addComponent(txtRadioJuridica)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelFisica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(panelJuridica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTelefone2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtCheckDefDataNasc)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtDataNasc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel10)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCheckDefDataNasc)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(7, 7, 7)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(txtTelefone2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtRadioFisica)
                    .addComponent(txtRadioJuridica))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelJuridica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFisica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txtRadioFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRadioFisicaActionPerformed
        habilitaFisica();
        desabilitaJuridico();
    }//GEN-LAST:event_txtRadioFisicaActionPerformed

    private void txtRadioJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRadioJuridicaActionPerformed
        habilitaJuridico();
        desabilitaFisica();
    }//GEN-LAST:event_txtRadioJuridicaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelFisica;
    private javax.swing.JPanel panelJuridica;
    private javax.swing.JCheckBox txtCheckDefDataNasc;
    private components.F2 txtCidade;
    private components.JTextFieldUpper txtCnpj;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCpf;
    private components.JDateField txtDataNasc;
    private javax.swing.JTextField txtEmail;
    private components.JTextFieldUpper txtEndereco;
    private components.JTextFieldUpper txtNome;
    private components.JTextFieldUpper txtNomeFantasia;
    private javax.swing.JRadioButton txtRadioFisica;
    private javax.swing.JRadioButton txtRadioJuridica;
    private components.JTextFieldUpper txtRazaoSocial;
    private javax.swing.JTextField txtRg;
    private javax.swing.JComboBox txtSexo;
    private javax.swing.JFormattedTextField txtTelefone;
    private javax.swing.JFormattedTextField txtTelefone2;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
