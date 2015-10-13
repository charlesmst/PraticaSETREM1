/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.aula;

import components.F2;
import components.JDialogController;
import components.JTextFieldIcone;
import components.JValidadorDeCampos;
import forms.frmMain;
import model.aula.Produto;
import services.aul.MarcaService;
import services.aul.ProdutoService;
import services.aul.SegmentoService;
import utils.AlertaTipos;
import utils.Utils;
import components.JTableDataBinder;
import components.JTableDataBinderListener;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.aula.Especificacao;
import model.aula.EspecificacaoProduto;
import model.aula.Marca;
import model.aula.Segmento;
import services.ServiceException;
import services.aul.EspecificacaoService;

/**
 *
 * @author Charles
 */
public class FrmProdutoCadastro extends JDialogController {

    private int id;
    private final ProdutoService service = new ProdutoService();
    protected List<EspecificacaoProduto> especificacoes;

    protected JTableDataBinder<EspecificacaoProduto> table;
    protected final JValidadorDeCampos validadorEspecificacao = new JValidadorDeCampos();

    /**
     * Creates new form frmCadastroProduto
     */
    public FrmProdutoCadastro() {
        this(0);
    }

    public FrmProdutoCadastro(int id) {
        super(frmMain.getInstance(), "Manutenção de Produtos");
        initComponents();
        this.id = id;
        setupForm();
    }

    private void setupForm() {
        // center the jframe on screen
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar1);
        txtCodigo.setEnabled(false);

        table = ((JTableDataBinder) jtbEspecificacoes);

        validator.validarObrigatorio(txtDescricao);
        validator.validarObrigatorio(txtMarca);
        validator.validarObrigatorio(txtSegmento);
        validator.validarDeBanco(txtMarca, new MarcaService());
        validator.validarDeBanco(txtSegmento, new SegmentoService());

        validator.validarCustom(table, (valor) -> table.getRowCount() > 0, "Insira no mínimo uma especificação");
        validator.validarCustom(table, (valor) -> {
            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, 1).equals("")) {
                    return false;
                }
            }
            return true;
        }, "Preencha as especificações");

        table.hideColumn("Id");

        //Validador de especificacoes
        validadorEspecificacao.validarObrigatorio(txtEspecificacao);
        validadorEspecificacao.validarDeBanco(txtEspecificacao, new EspecificacaoService());
        validadorEspecificacao.validarCustom(txtEspecificacao, (valor) -> {
            DefaultTableModel model = table.getDefaultTableModel();
            try {
                int v = Integer.parseInt(valor);
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (v == Integer.parseInt(model.getValueAt(i, 0).toString())) {
                        return false;
                    }
                }
            } catch (Exception ex) {
                return false;
            }

            return true;
        }, "Valor já existe nas especificações");
//        new F2(txtMarca, FrmMarcaF2.class).setBuscador((id) -> service.findById(id).getDescricao());
//        new F2(txtMarca, FrmMarcaF2.class).setBuscador((id) -> service.findById(id).getDescricao());

        table.setBusca(txtEspecificacao);

        txtDescricao.requestFocus();
        if (id > 0) {
            load();
        }
    }

    private void load() {
        Produto m = service.findById(id);
        txtCodigo.setText(String.valueOf(m.getId()));
        txtDescricao.setText(m.getDescricao());
        txtMarca.setText(m.getMarca().getId() + "");
        txtSegmento.setText(m.getSegmento().getId() + "");

        table.setListener(new JTableDataBinderListener<EspecificacaoProduto>() {

            @Override
            public Collection<EspecificacaoProduto> lista(String busca) throws ServiceException {
                return service.getEspecificacoes(m);
            }

            @Override
            public Object[] addRow(EspecificacaoProduto dado) {
                return new String[]{dado.getEspecificacao().getId() + "", dado.getEspecificacao().getTitulo(), dado.getDescricao()};
            }
        });
        table.atualizar();
//        especificacoes = m.get
    }

    private void save() {
        if (!validator.isValido()) {
            return;
        }
        Produto m;
        if (id > 0) {
            m = service.findById(id);
        } else {
            m = new Produto();
        }
        m.setDescricao(txtDescricao.getText());

        m.setMarca(new Marca(Integer.valueOf(txtMarca.getText())));

        m.setSegmento(new Segmento(Integer.valueOf(txtSegmento.getText())));

        List<EspecificacaoProduto> especificacoes = new ArrayList<>();
        for (int i = 0; i < table.getDefaultTableModel().getRowCount(); i++) {
            Especificacao e = new Especificacao();
            e.setId(Integer.valueOf(table.getDefaultTableModel().getValueAt(i, 0).toString()));
            EspecificacaoProduto esp = new EspecificacaoProduto();
            esp.setProduto(m);
            esp.setEspecificacao(e);
            esp.setDescricao(table.getDefaultTableModel().getValueAt(i, 2).toString());
            especificacoes.add(esp);
        }
        Utils.safeCode(() -> {
            service.insertOrUpdateProduto(m, especificacoes);
            utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);

            dispose();
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableDataBinder1 = new components.JTableDataBinder();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtDescricao = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnSalvar1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnNovoEspecificacao = new javax.swing.JButton();
        btnExcluirExpecificacao = new javax.swing.JButton();
        txtEspecificacao = new components.F2(FrmEspecificacaoF2.class, (a)-> new EspecificacaoService().findById(a).getTitulo()
        );
        jLabel5 = new javax.swing.JLabel();
        txtMarca = new F2(FrmMarcaF2.class,(id) -> new MarcaService().findById(id).getNome());
        txtSegmento = new F2(FrmSegmentoF2.class,(id) -> new SegmentoService().findById(id).getNome());
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbEspecificacoes = new JTableDataBinder<Especificacao>();

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

        jTableDataBinder1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTableDataBinder1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Código");

        jLabel2.setText("Descrição");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel3.setText("Segmento");

        jLabel4.setText("Marca");

        btnSalvar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/save.png"))); // NOI18N
        btnSalvar1.setText("Salvar");
        btnSalvar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvar1ActionPerformed(evt);
            }
        });

        btnNovoEspecificacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/new.png"))); // NOI18N
        btnNovoEspecificacao.setText("Novo");
        btnNovoEspecificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoEspecificacaoActionPerformed(evt);
            }
        });

        btnExcluirExpecificacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/delete.png"))); // NOI18N
        btnExcluirExpecificacao.setText("Excluir");
        btnExcluirExpecificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirExpecificacaoActionPerformed(evt);
            }
        });

        txtEspecificacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEspecificacaoKeyPressed(evt);
            }
        });

        jLabel5.setText("Especificações");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtEspecificacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnNovoEspecificacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluirExpecificacao))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovoEspecificacao)
                    .addComponent(btnExcluirExpecificacao)
                    .addComponent(txtEspecificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jtbEspecificacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Especificacao", "Descrição"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtbEspecificacoes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtbEspecificacoesKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jtbEspecificacoes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSalvar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                            .addComponent(txtSegmento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMarca, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDescricao, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(26, 26, 26))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(7, 7, 7)
                .addComponent(txtSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addGap(9, 9, 9)
                .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNovoEspecificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoEspecificacaoActionPerformed
        if (validadorEspecificacao.isValido()) {
            DefaultTableModel model = table.getDefaultTableModel();
            model.addRow(new Object[]{txtEspecificacao.getText(), ((F2) txtEspecificacao).getTextValue(), ""});
            txtEspecificacao.setText("");

            table.startEditing(model.getRowCount() - 1, 1);

        }
    }//GEN-LAST:event_btnNovoEspecificacaoActionPerformed

    private void btnSalvar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvar1ActionPerformed
        save();
    }//GEN-LAST:event_btnSalvar1ActionPerformed

    private void btnExcluirExpecificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirExpecificacaoActionPerformed
        if (table.getSelectedId() > 0) {
            table.getDefaultTableModel().removeRow(table.getSelectedRow());
        }
    }//GEN-LAST:event_btnExcluirExpecificacaoActionPerformed

    private void jtbEspecificacoesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtbEspecificacoesKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            btnExcluirExpecificacao.doClick();
        } else if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
            txtEspecificacao.requestFocus();
        }
    }//GEN-LAST:event_jtbEspecificacoesKeyPressed

    private void txtEspecificacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEspecificacaoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnNovoEspecificacao.doClick();
        }
    }//GEN-LAST:event_txtEspecificacaoKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluirExpecificacao;
    private javax.swing.JButton btnNovoEspecificacao;
    private javax.swing.JButton btnSalvar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private components.JTableDataBinder jTableDataBinder1;
    private javax.swing.JTable jtbEspecificacoes;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescricao;
    private components.F2 txtEspecificacao;
    private components.F2 txtMarca;
    private components.F2 txtSegmento;
    // End of variables declaration//GEN-END:variables
}
