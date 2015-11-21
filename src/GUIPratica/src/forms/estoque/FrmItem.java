package forms.estoque;

import components.CellRenderer;
import java.awt.event.ActionEvent;
import components.JPanelControleButtons;
import components.JTableDataBinderListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import model.estoque.Item;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import services.ServiceException;
import services.estoque.ItemService;
import utils.AlertaTipos;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmItem extends JPanelControleButtons {

    private final ItemService service;
    private double totalDoEstoque;

    public FrmItem() {
        initComponents();
        CellRenderer cr = new CellRenderer();
        cr.setAlign(4, SwingConstants.RIGHT);
        cr.setAlign(4, SwingConstants.CENTER);
        cr.setAlign(6, SwingConstants.RIGHT);
        cr.setAlign(7, SwingConstants.RIGHT);
        table.setDefaultRenderer(Object.class, cr);
        setBtnAddEnable(true);
        setBtnAlterarEnable(true);
        setBtnExcluirEnable(true);
        setBtnAtualizarEnable(true);

        service = new ItemService();

        table.setListener(new JTableDataBinderListener<Item>() {

            @Override
            public Collection<Item> lista(String busca) throws ServiceException {
                totalDoEstoque = 0d;
                return service.findByMultipleColumns(busca, "id", "id", "descricao", "itemTipo.nome", "prateleira.descricao");
            }

            @Override
            public Object[] addRow(Item dado) {
                Item item = service.MPM(dado);
                totalDoEstoque += item.getValotTotal();
                txtCapitalEstoque.setValue(totalDoEstoque);
                return new Object[]{dado.getId() + "",
                    dado.getDescricao(),
                    dado.getEstoqueMinimo() + "",
                    dado.getPrateleira().getDescricao(),
                    Utils.formataDinheiro(dado.getUltimoValorVenda()),
                    item.getQtdDisponivel(),
                    Utils.formataDinheiro(item.getCustoMedio()),
                    Utils.formataDinheiro(item.getValotTotal())
                };
            }

        });
        table.setBusca(txtBuscar, true);
        table.atualizar();
        atualizaCapitalTotal();
    }

    public void atualizaCapitalTotal() {
        txtCapitalEstoque.setValue(totalDoEstoque);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        table = new components.JTableDataBinder();
        txtBuscar = new components.JTextFieldUpper(true);
        jLabel2 = new javax.swing.JLabel();
        btnEntrada = new javax.swing.JButton();
        btnRelatorio = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtCapitalEstoque = new components.JTextFieldMoney();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Descrição", "Limite Mínimo", "Prateleira", "R$ Venda", "Qtd. Disponível", "Custo Médio", "Valor Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setBusca(txtBuscar);
        table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(350);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(100);
            table.getColumnModel().getColumn(5).setPreferredWidth(110);
            table.getColumnModel().getColumn(6).setPreferredWidth(100);
            table.getColumnModel().getColumn(7).setPreferredWidth(100);
        }

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText(" Buscar:");

        btnEntrada.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/work11.png"))); // NOI18N
        btnEntrada.setText("Entrada de Estoque");
        btnEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntradaActionPerformed(evt);
            }
        });

        btnRelatorio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/file148.png"))); // NOI18N
        btnRelatorio.setText("Relatório");
        btnRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioActionPerformed(evt);
            }
        });

        jLabel1.setText("Capital em estoque:");

        txtCapitalEstoque.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEntrada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCapitalEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtCapitalEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntradaActionPerformed
        JDialog dialog = new FrmEstoqueCadastro();
        dialog.setVisible(true);
        table.atualizar();
    }//GEN-LAST:event_btnEntradaActionPerformed

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed
        List<Item> itemAll = service.findAll();
        double capital = 0d;
        List<Item> item = new ArrayList<>();
        for (Item i : itemAll) {
            i = service.MPM(i);
            i.setQtdDisponivelRel(i.getQtdDisponivel() + "");
            i.setCustoMedioRel(Utils.formataDinheiro(i.getCustoMedio()));
            i.setValotTotalRel(Utils.formataDinheiro(i.getValotTotal()));
            capital += i.getValotTotal();
            item.add(i);
        }
        JRBeanCollectionDataSource jrs = new JRBeanCollectionDataSource(item);
        Map parametros = new HashMap();
        parametros.put("capitalEstoque", Utils.formataDinheiro(capital));
        try {
            JasperPrint jpr = JasperFillManager
                    .fillReport("src/relatorios/relatorio_de_item.jasper", parametros, jrs);
            JasperViewer.viewReport(jpr, false);
        } catch (JRException ex) {
            utils.Forms.mensagem(ex.getMessage(), AlertaTipos.erro);
        }
    }//GEN-LAST:event_btnRelatorioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEntrada;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private components.JTableDataBinder table;
    private components.JTextFieldUpper txtBuscar;
    private components.JTextFieldMoney txtCapitalEstoque;
    // End of variables declaration//GEN-END:variables

    @Override
    public void btnAddActionPerformed(ActionEvent evt) {
        JDialog dialog = new FrmItemCadastro();
        dialog.setVisible(true);
    }

    @Override
    public void btnAlterarActionPerformed(ActionEvent evt) {
        defaultUpdateOperation(table, (i) -> {
            JDialog dialog = new FrmItemCadastro(i);
            dialog.setVisible(true);
        });
    }

    @Override
    public void btnExcluirActionPerformed(ActionEvent evt) {
        defaultDeleteOperation(table, (i) -> service.delete(i));
    }

    @Override
    public void btnAtualizarActionPerformed(ActionEvent evt) {
        table.atualizar();
    }
}
