/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import components.TreeMenuModel;
import components.TreeNodeMenu;
import components.JPanelControleButtons;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import jdk.nashorn.internal.objects.Global;
import model.Usuario;
import utils.AlertaTipos;
import utils.Globals;

/**
 *
 * @author Charles
 */
public class frmMain extends javax.swing.JFrame {

    private Timer timerAlert;
    private boolean startedTree = false;

    public JPanelControleButtons getCurrentComponent() {
        JPanelControleButtons comp = null;

        Component c = jtabs.getSelectedComponent();
        if (c != null && c instanceof JPanelControleButtons) {
            comp = (JPanelControleButtons) c;
        }

        return comp;
    }

    private static frmMain instance;

    public static frmMain getInstance() {
        return instance;
    }

    public void iniciaProgress() {
        progressGeral.setIndeterminate(true);
    }

    public void paraProgress() {
        progressGeral.setIndeterminate(false);
    }

    /**
     * Creates new form frmMain
     */
    public frmMain() {
        if (instance == null) {
            instance = this;
        }
        initComponents();
        setupFrame();
        setupTree();
        setupWelcome();
        setupToolbar();
        paraProgress();
        timerAlert = new Timer(5000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frmMain.getInstance().resetAlert();
                timerAlert.stop();
            }
        });
        timerAlert.stop();

    }

    public void alert(String alert) {
        lblStatus.setText(alert);
        lblStatus.setVisible(true);

        if (timerAlert != null) {
            timerAlert.stop();
            timerAlert.start();
        }
    }

    private void setupWelcome() {
        jtabs.addTab("Bem Vindo", new frmBemVindo());
    }

    private void setupFrame() {
        resetAlert();
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
    }

    private void resetAlert() {
        lblIcone.setVisible(false);
        lblStatus.setVisible(false);
    }

    public void setIcone(AlertaTipos tipo) {
        ImageIcon i;
        switch (tipo) {
            case sucesso:
                i = new ImageIcon(getClass().getClassLoader().getResource("resources/success.png"));
                break;
            case erro:
                i = new ImageIcon(getClass().getClassLoader().getResource("resources/erro.png"));
                break;
            default:
                i = null;
                break;
        }
        lblIcone.setIcon(i);
        lblIcone.setVisible(true);
        lblIcone.setText("");
    }

    private void setupToolbar() {
        jToolBar1.setLayout(new FlowLayout(FlowLayout.CENTER));
        Border emptyBorder = BorderFactory.createEmptyBorder();
        jToolBar1.setBorder(emptyBorder);
//        for (Component c : jToolBar1.getComponents()) {
//            if (c instanceof JButton) {
//                ((JButton) c).setBorder(emptyBorder);
//
//            }
//        }
    }

    private void openTab(DefaultMutableTreeNode tab) {
        if (tab instanceof TreeNodeMenu) {
            TreeNodeMenu treeNodeMenu = (TreeNodeMenu) tab;

            JPanel p = treeNodeMenu.getPanel();
            //Tenta encontrar nas tabs abertas
            for (int i = 0; i < jtabs.getTabCount(); i++) {
                //Já está aberto
                if (jtabs.getComponentAt(i) == p) {

                    jtabs.setSelectedComponent(p);
                    return;
                }
            }

            jtabs.add(treeNodeMenu.getUserObject().toString(), p);
            jtabs.setSelectedComponent(p);
        }
    }

    private void setupTree() {

        TreeMenuModel model = new TreeMenuModel(new DefaultMutableTreeNode());
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.setUserObject("ProCar");
        boolean master = Globals.nivel == Usuario.Tipo.gestor;
        jTreeMenu.setModel(model);

//        jTreeMenu.setRootVisible(false);
        jTreeMenu.setShowsRootHandles(true);

        jTreeMenu.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        if (master) {
            DefaultMutableTreeNode gerenciamento = new DefaultMutableTreeNode("Gerenciamento");
            gerenciamento.add(new TreeNodeMenu("Sumário de compras e vendas", "forms.fluxo.FrmRelatorioSumario"));
            gerenciamento.add(new TreeNodeMenu("Livro Caixa", "forms.fluxo.FrmRelatorioLivroCaixa"));
            gerenciamento.add(new TreeNodeMenu("Movimentações de caixa", "forms.fluxo.FrmRelatorioMovimentoCaixa"));

            gerenciamento.add(new TreeNodeMenu("Pessoas", "forms.frmPessoa"));
            gerenciamento.add(new TreeNodeMenu("Usuários", "forms.frmUsuarios"));
            root.add(gerenciamento);
        }
//----------------------------- Estoque -----------------------------//   
        DefaultMutableTreeNode estoque = new DefaultMutableTreeNode("Estoque");

        estoque.add(new TreeNodeMenu(("Prateleira"), "forms.estoque.FrmPrateleira"));
        estoque.add(new TreeNodeMenu(("Tipo de Item"), "forms.estoque.FrmItemTipo"));

        estoque.add(new TreeNodeMenu(("Item"), "forms.estoque.FrmItem"));
        estoque.add(new TreeNodeMenu(("Tipo de Movimentação"), "forms.estoque.FrmMovimentacaoTipo"));

        estoque.add(new TreeNodeMenu(("Movimentações"), "forms.estoque.FrmEstoque"));

        root.add(estoque);

//-------------------------------------------------------------------//    
        DefaultMutableTreeNode ordem = new DefaultMutableTreeNode("Ordem de Serviço");

        ordem.add(new TreeNodeMenu("Ordens de Serviço", "forms.ordem.FrmOrdem"));
        if (master) {
            ordem.add(new TreeNodeMenu("Tipos de Serviço", "forms.ordem.FrmOrdemTipoServico"));
            ordem.add(new TreeNodeMenu("Status de Serviço", "forms.ordem.FrmOrdemStatus"));
        }
        ordem.add(new TreeNodeMenu("Veiculos", "forms.ordem.FrmVeiculo"));

        ordem.add(new TreeNodeMenu("Marcas", "forms.ordem.FrmMarca"));
        ordem.add(new TreeNodeMenu("Cores", "forms.ordem.FrmCor"));

        ordem.add(new TreeNodeMenu("Modelos", "forms.ordem.FrmModelo"));

        root.add(ordem);

//-------------------------------------------------------------------//    
        DefaultMutableTreeNode caixa = new DefaultMutableTreeNode("Fluxo de Caixa");
        caixa.add(new TreeNodeMenu("Contas a pagar e receber", "forms.fluxo.FrmConta"));
        if (master) {
            caixa.add(new TreeNodeMenu("Contas Bancárias", "forms.fluxo.FrmContaBancaria"));

            caixa.add(new TreeNodeMenu("Categorias de Conta", "forms.fluxo.FrmContaCategoria"));
            caixa.add(new TreeNodeMenu("Formas de Pagamento", "forms.fluxo.FrmFormaPagamento"));
        }
//        caixa.add(new TreeNodeMenu("Usuários", "forms.frmUsuarios"));
        root.add(caixa);

        model.nodeChanged(root);

        int j = jTreeMenu.getRowCount();
        int i = 0;
        while (i < j) {
            jTreeMenu.expandRow(i);
            i += 1;
            j = jTreeMenu.getRowCount();
        }
        startedTree = true;
         jTreeMenu.setRootVisible(false) ;
         jTreeMenu.setShowsRootHandles(true);


    }

    private TreeNodeMenu findComponent(DefaultMutableTreeNode current, JPanel finding) {
        if (finding == null) {
            return null;
        }
        if (current instanceof TreeNodeMenu) {
            TreeNodeMenu m = (TreeNodeMenu) current;
            if (m.instantiated() && m.getPanel() == finding) {
                return m;
            }
        }
        if (current != null) {
            for (int i = 0; i < current.getChildCount(); i++) {
                TreeNode n = current.getChildAt(i);
                TreeNodeMenu menu = findComponent((DefaultMutableTreeNode) n, finding);
                if (menu != null) {
                    return menu;
                }
            }
        }
        return null;
    }

    private void refreshSelectionTree() {
        TreeMenuModel model = (TreeMenuModel) jTreeMenu.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        JPanel finding = (JPanel) jtabs.getSelectedComponent();
        TreeNodeMenu active = findComponent(root, finding);
        if (active != null) {
//            jTreeMenu
//            TreeSelectionModel modelSelection = (TreeMenuModel)jTreeMenu.getSelectionModel(); 
//            TreePath path = jTreeMenu.getPathForRow()
            TreeNode[] nodes = model.getPathToRoot(active);
            TreePath path = new TreePath(nodes);

//            m_tree.setExpandsSelectedPaths(true);
            jTreeMenu.setSelectionPath(new TreePath(nodes));
//            modelSelection.setSelectionPath(active.getPath());
        } else {
            jTreeMenu.setSelectionPath(null);
        }
    }

    public void refreshButtons() {
        JPanelControleButtons c = getCurrentComponent();
        if (c != null) {
            btnAdd.setEnabled(c.isBtnAddEnable());
            btnAlterar.setEnabled(c.isBtnAlterarEnable());
            btnExcluir.setEnabled(c.isBtnExcluirEnable());
//            btnSalvar.setEnabled(c.isBtnSalvarEnable());
//            btnCancelar.setEnabled(c.isBtnCancelarEnable());
            btnAtualizar.setEnabled(c.isBtnAtualizarEnable());

            menuNovo.setEnabled(c.isBtnAddEnable());
            menuAlterar.setEnabled(c.isBtnAlterarEnable());
            menuExcluir.setEnabled(c.isBtnExcluirEnable());
            menuAtualizar.setEnabled(c.isBtnAtualizarEnable());

        } else {
            btnAdd.setEnabled(false);
            btnAlterar.setEnabled(false);
            btnExcluir.setEnabled(false);
            btnAtualizar.setEnabled(false);

            menuNovo.setEnabled(false);
            menuAlterar.setEnabled(false);
            menuExcluir.setEnabled(false);
            menuAtualizar.setEnabled(false);

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

        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        jFrame1 = new javax.swing.JFrame();
        jMenu1 = new javax.swing.JMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeMenu = new javax.swing.JTree();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        progressGeral = new javax.swing.JProgressBar();
        lblIcone = new javax.swing.JLabel();
        jtabs = new components.JTabbedPaneCloseButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        menuNovo = new javax.swing.JMenuItem();
        menuAlterar = new javax.swing.JMenuItem();
        menuExcluir = new javax.swing.JMenuItem();
        menuAtualizar = new javax.swing.JMenuItem();

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu1.setText("Arquivo");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));
        setForeground(new java.awt.Color(102, 102, 102));
        setIconImage(utils.Globals.imageIcone);

        jTreeMenu.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeMenuValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeMenu);

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(false);
        jToolBar1.setFloatable(false);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/new.png"))); // NOI18N
        btnAdd.setToolTipText("Novo(INSERT)");
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/edit.png"))); // NOI18N
        btnAlterar.setToolTipText("Alterar(F3)");
        btnAlterar.setFocusable(false);
        btnAlterar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAlterar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAlterar);

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/delete.png"))); // NOI18N
        btnExcluir.setToolTipText("Excluir(DELETE)");
        btnExcluir.setFocusable(false);
        btnExcluir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExcluir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        jToolBar1.add(btnExcluir);

        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        btnAtualizar.setToolTipText("Atualizar(F5)");
        btnAtualizar.setFocusable(false);
        btnAtualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAtualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAtualizar);

        jPanel1.setBackground(new java.awt.Color(200, 200, 200));
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStatus.setText("Status");

        lblIcone.setText("Label");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcone, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(progressGeral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progressGeral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblStatus)
                        .addComponent(lblIcone)))
                .addContainerGap())
        );

        jtabs.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtabsStateChanged(evt);
            }
        });

        jMenuBar1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jMenu2.setText("Arquivo");
        jMenu2.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N

        jMenuItem1.setText("Preferências");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Sair");
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Editar");
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Ações");

        menuNovo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_INSERT, 0));
        menuNovo.setText("Novo");
        menuNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNovoActionPerformed(evt);
            }
        });
        jMenu4.add(menuNovo);

        menuAlterar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        menuAlterar.setText("Alterar");
        menuAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAlterarActionPerformed(evt);
            }
        });
        jMenu4.add(menuAlterar);

        menuExcluir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        menuExcluir.setText("Excluir");
        menuExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExcluirActionPerformed(evt);
            }
        });
        jMenu4.add(menuExcluir);

        menuAtualizar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        menuAtualizar.setText("Atualizar");
        menuAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAtualizarActionPerformed(evt);
            }
        });
        jMenu4.add(menuAtualizar);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtabs))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtabs)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTreeMenuValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeMenuValueChanged

        if (startedTree) {
            TreePath path = evt.getNewLeadSelectionPath();
            if (path != null) {
                Object o = path.getLastPathComponent();

                openTab((DefaultMutableTreeNode) o);
            }
        }
    }//GEN-LAST:event_jTreeMenuValueChanged

    private void jtabsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtabsStateChanged

        if (startedTree && isVisible()) {
            refreshSelectionTree();
            refreshButtons();
        }
        //Verifica se é pra mudar o menu selecionado

    }//GEN-LAST:event_jtabsStateChanged

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        JPanelControleButtons c = getCurrentComponent();
        if (c != null) {
            c.btnAtualizarActionPerformed(evt);
        }
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        JPanelControleButtons c = getCurrentComponent();
        if (c != null) {
            c.btnExcluirActionPerformed(evt);
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        JPanelControleButtons c = getCurrentComponent();
        if (c != null) {
            c.btnAlterarActionPerformed(evt);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        JPanelControleButtons c = getCurrentComponent();
        if (c != null) {
            c.btnAddActionPerformed(evt);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void menuNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNovoActionPerformed

        btnAdd.doClick();
    }//GEN-LAST:event_menuNovoActionPerformed

    private void menuAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAlterarActionPerformed
        btnAlterar.doClick();        // TODO add your handling code here:
    }//GEN-LAST:event_menuAlterarActionPerformed

    private void menuExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExcluirActionPerformed
        btnExcluir.doClick();        // TODO add your handling code here:
    }//GEN-LAST:event_menuExcluirActionPerformed

    private void menuAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAtualizarActionPerformed
        btnAtualizar.doClick();        // TODO add your handling code here:
    }//GEN-LAST:event_menuAtualizarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTreeMenu;
    private javax.swing.JTabbedPane jtabs;
    private javax.swing.JLabel lblIcone;
    private javax.swing.JLabel lblStatus;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private javax.swing.JMenuItem menuAlterar;
    private javax.swing.JMenuItem menuAtualizar;
    private java.awt.MenuBar menuBar1;
    private javax.swing.JMenuItem menuExcluir;
    private javax.swing.JMenuItem menuNovo;
    private javax.swing.JProgressBar progressGeral;
    // End of variables declaration//GEN-END:variables
}
