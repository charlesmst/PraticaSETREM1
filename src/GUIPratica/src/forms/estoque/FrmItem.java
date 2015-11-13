package forms.estoque;

import components.CellRenderer;
import java.awt.event.ActionEvent;
import components.JPanelControleButtons;
import components.JTableDataBinderListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import model.estoque.Item;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import services.ServiceException;
import services.estoque.EstoqueService;
import services.estoque.ItemService;
import utils.AlertaTipos;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmItem extends JPanelControleButtons {

    private final ItemService service;

    public FrmItem() {
        initComponents();
        table.setDefaultRenderer(Object.class, new CellRenderer());
        setBtnAddEnable(true);
        setBtnAlterarEnable(true);
        setBtnExcluirEnable(true);
        setBtnAtualizarEnable(true);

        service = new ItemService();

        table.setListener(new JTableDataBinderListener<Item>() {

            @Override
            public Collection<Item> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "descricao", "itemTipo.nome", "prateleira.descricao");

            }

            @Override
            public Object[] addRow(Item dado) {
                return new Object[]{dado.getId() + "",
                    dado.getItemTipo().getNome(),
                    dado.getDescricao(),
                    dado.getEstoqueMinimo() + "",
                    dado.getPrateleira().getDescricao(),
                    Utils.formataDinheiro(dado.getUltimoValorVenda()),
                    new ItemService().verificaQuantidadeDisp(dado)
                };
            }
        });

        table.setBusca(txtBuscar, true);
        table.atualizar();
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

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Tipo de Item", "Descrição", "Limite Mínimo", "Prateleira", "Valor de Venda", "Qtd. Disponível"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setBusca(txtBuscar);
        jScrollPane2.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(460);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(100);
            table.getColumnModel().getColumn(5).setPreferredWidth(110);
            table.getColumnModel().getColumn(6).setPreferredWidth(110);
        }

        jLabel2.setText(" Buscar:");

        btnEntrada.setText("Entrada de Estoque");
        btnEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntradaActionPerformed(evt);
            }
        });

        btnRelatorio.setText("Relatório");
        btnRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioActionPerformed(evt);
            }
        });

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
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEntrada)
                        .addGap(18, 18, 18)
                        .addComponent(btnRelatorio))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnEntrada)
                    .addComponent(btnRelatorio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntradaActionPerformed
        JDialog dialog = new FrmEstoqueCadastro();
        dialog.setVisible(true);
    }//GEN-LAST:event_btnEntradaActionPerformed

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed
        JRBeanCollectionDataSource jrs = new JRBeanCollectionDataSource(service.findAllWithDisp());
        Map parametros = new HashMap();
         try {
             JasperPrint jpr = JasperFillManager.fillReport("C:\\Users\\gusta\\Documents\\NetBeansProjects\\trunk\\src\\GUIPratica\\src\\relatorios\\relatorio_item.jasper", parametros, jrs);
             JasperViewer.viewReport(jpr, false);
             JasperExportManager.exportReportToPdfFile(jpr, "relatorios/relatorio_item.pdf");
         } catch(JRException ex){
             utils.Forms.mensagem(ex.getMessage(), AlertaTipos.erro);
         }
    }//GEN-LAST:event_btnRelatorioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEntrada;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private components.JTableDataBinder table;
    private components.JTextFieldUpper txtBuscar;
    // End of variables declaration//GEN-END:variables

    @Override
        public void btnAddActionPerformed(ActionEvent evt) {
        JDialog dialog = new FrmItemCadastro();
        dialog.setVisible(true);
        table.atualizar();
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
