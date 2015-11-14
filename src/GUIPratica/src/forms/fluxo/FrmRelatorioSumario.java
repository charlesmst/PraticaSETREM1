/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.fluxo;

import components.JCampoBusca;
import java.awt.event.ActionEvent;
import components.JPanelControleButtons;

import components.JTableDataBinderListener;
import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import model.fluxo.Conta;
import model.fluxo.ContaBancaria;
import model.fluxo.ContaCategoria;
import model.queryresults.ComprasVendas;
import model.queryresults.SomaCategoria;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Priority;
import services.ServiceException;
import services.fluxo.ContaService;
import utils.AlertaTipos;
import utils.Forms;
import utils.Globals;
import utils.Mensagens;
import utils.Utils;

/**
 *
 * @author Charles
 */
public class FrmRelatorioSumario extends JPanelControleButtons {

    private final ContaService service;
//    JTableDataBinder table;

    public FrmRelatorioSumario() {
        initComponents();
        setBtnAtualizarEnable(true);
        service = new ContaService();
        setupForm();
    }
    List<ComprasVendas> dados;

    private String porcentagem(double valor1, double valor2) {
        return Math.round(valor1 * 100 / (valor1 + valor2) * 100d) / 100d + "%";
    }

    List<SomaCategoria> totalCategorias;

    private void setupForm() {
        txtData.setDateFormat("M/y");
        txtData.setValue(new Date());
        SimpleDateFormat format = new SimpleDateFormat("d/M");
        table.setListener(new JTableDataBinderListener<ComprasVendas>() {

            @Override
            public Collection<ComprasVendas> lista(String busca) throws ServiceException {
                dados = getComprasEVendas();
                return dados;
            }

            @Override
            public Object[] addRow(ComprasVendas dado) {
                Object[] o = new Object[5];
                o[0] = format.format(dado.getData());
                o[1] = dado.getComprasAVista() == 0d ? "-" : Utils.formataDinheiro(dado.getComprasAVista());
                o[2] = dado.getComprasPrazo() == 0d ? "-" : Utils.formataDinheiro(dado.getComprasPrazo());
                o[3] = dado.getVendasAVista() == 0d ? "-" : Utils.formataDinheiro(dado.getVendasAVista());
                o[4] = dado.getVendasPrazo() == 0d ? "-" : Utils.formataDinheiro(dado.getVendasPrazo());
                return o;
            }

        });
        table.setListenerFinalizacao((e) -> {
            double totalcv = 0d, totalcp = 0d, totalvv = 0d, totalvp = 0d;
            for (ComprasVendas dado : dados) {
                totalcv += dado.getComprasAVista();
                totalcp += dado.getComprasPrazo();
                totalvv += dado.getVendasAVista();
                totalvp += dado.getVendasPrazo();
            }
            table.getDefaultTableModel().addRow(new Object[]{
                "TOTAL",
                Utils.formataDinheiro(totalcv),
                Utils.formataDinheiro(totalcp),
                Utils.formataDinheiro(totalvv),
                Utils.formataDinheiro(totalvp)
            });

            table.getDefaultTableModel().addRow(new Object[]{
                "",
                porcentagem(totalcv, totalcp),
                porcentagem(totalcp, totalcv),
                porcentagem(totalvv, totalvp),
                porcentagem(totalvp, totalvv)});

        });
        tableCategoria.setListener(new JTableDataBinderListener<SomaCategoria>() {

            @Override
            public Collection<SomaCategoria> lista(String busca) throws ServiceException {
                totalCategorias = getValorCategorias();
                return totalCategorias;
            }

            @Override
            public Object[] addRow(SomaCategoria dado) {
                return new Object[]{dado.getCategoria(), Utils.formataDinheiro(dado.getValor())};
            }
        });
        tableCategoria.setListenerFinalizacao((e) -> {
            double total = 0d;
            for (SomaCategoria totalCategoria : totalCategorias) {
                total += totalCategoria.getValor();
            }
            tableCategoria.getDefaultTableModel().addRow(new Object[]{"TOTAL DE CUSTOS E DESPESAS", Utils.formataDinheiro(total)});
        });
        tableResultados.setTableHeader(null);
        tableResultados.setListener(new JTableDataBinderListener<SomaCategoria>() {

            @Override
            public Collection<SomaCategoria> lista(String busca) throws ServiceException {
                return getResultados();

            }

            @Override
            public Object[] addRow(SomaCategoria dado) {
                return new Object[]{dado.getCategoria(), dado.getValor() == 0d ? "-" : Utils.formataDinheiro(dado.getValor())};
            }
        });
        atualizar();
    }

    private List<ComprasVendas> getComprasEVendas() {
        Calendar aCalendar = Calendar.getInstance();
// add -1 month to current month
        aCalendar.setTime(txtData.getDate());
// set DATE to 1, so first date of previous month
        aCalendar.set(Calendar.DATE, 1);

        Date start = aCalendar.getTime();

// set actual maximum date of previous month
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//read it
        Date end = aCalendar.getTime();
        return service.comprasMes(start, end);
    }

    private List<SomaCategoria> getValorCategorias() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(txtData.getDate());
        aCalendar.set(Calendar.DATE, 1);

        Date start = aCalendar.getTime();

        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = aCalendar.getTime();
        return service.valorCategoria(start, end, ContaCategoria.TipoCategoria.saida);
    }

    private List<SomaCategoria> getResultados() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(txtData.getDate());
        aCalendar.set(Calendar.DATE, 1);

        Date start = aCalendar.getTime();

        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = aCalendar.getTime();
        return service.resultadoPeriodo(start, end);
    }

    private void atualizar() {
        table.atualizar();
        tableCategoria.atualizar();
        tableResultados.atualizar();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtData = new components.JDateField();
        jLabel1 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new components.JTableDataBinder();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCategoria = new components.JTableDataBinder();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableResultados = new components.JTableDataBinder();
        btnImprimir = new javax.swing.JButton();

        jLabel1.setText("Mês");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dia", "Compras à Vista", "Compras a Prazo", "Vendas à Vista", "Vendas a Prazo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(1).setMinWidth(150);
            table.getColumnModel().getColumn(1).setMaxWidth(200);
            table.getColumnModel().getColumn(2).setHeaderValue("Compras a Prazo");
            table.getColumnModel().getColumn(3).setHeaderValue("Vendas à Vista");
            table.getColumnModel().getColumn(4).setHeaderValue("Vendas a Prazo");
        }

        tableCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Categoria", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableCategoria);
        if (tableCategoria.getColumnModel().getColumnCount() > 0) {
            tableCategoria.getColumnModel().getColumn(1).setMinWidth(150);
            tableCategoria.getColumnModel().getColumn(1).setMaxWidth(200);
        }

        jLabel2.setText("Demonstrativo de resultados do período");

        tableResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Categoria", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableResultados);
        if (tableResultados.getColumnModel().getColumnCount() > 0) {
            tableResultados.getColumnModel().getColumn(1).setMinWidth(150);
            tableResultados.getColumnModel().getColumn(1).setMaxWidth(200);
        }

        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar)
                        .addGap(9, 9, 9)
                        .addComponent(btnImprimir)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnBuscar)
                    .addComponent(btnImprimir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        atualizar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        List<Compras> l = new ArrayList<>();
        Compras c = new Compras();
        dados = getComprasEVendas();
        c.setCompras(dados);

        double totalcv = 0d, totalcp = 0d, totalvv = 0d, totalvp = 0d;
        for (ComprasVendas dado : dados) {
            totalcv += dado.getComprasAVista();
            totalcp += dado.getComprasPrazo();
            totalvv += dado.getVendasAVista();
            totalvp += dado.getVendasPrazo();
        }
        ComprasVendas cv = new ComprasVendas(null, 0, 0, 0, 0);
        cv.setDataFormatada("TOTAL");
        cv.setComprasAVistaD(Utils.formataDinheiro(totalcv));
        cv.setComprasPrazoD(Utils.formataDinheiro(totalcp));
        cv.setVendasAVistaD(Utils.formataDinheiro(totalvv));
        cv.setVendasPrazoD(Utils.formataDinheiro(totalvp));
        dados.add(cv);
        cv = new ComprasVendas(null, 0, 0, 0, 0);
        cv.setDataFormatada("");
        cv.setComprasAVistaD(porcentagem(totalcv, totalcp));
        cv.setComprasPrazoD(porcentagem(totalcp, totalcv));
        cv.setVendasAVistaD(porcentagem(totalvv, totalvp));
        cv.setVendasPrazoD(porcentagem(totalvp, totalvv));
        dados.add(cv);

        c.setCategorias(getValorCategorias());
        c.setResultados(getResultados());
        l.add(c);
        JRBeanCollectionDataSource jrs = new JRBeanCollectionDataSource(l);

        Map parametros = new HashMap();
//        parametros.put("comprasvendas", jrs);
        try {
            JasperPrint jpr = JasperFillManager
                    .fillReport("src/relatorios/registro_de_operacoes.jasper",
                            parametros, jrs);
            JasperViewer.viewReport(jpr, false);
        } catch (JRException ex) {
            Forms.mensagem(Mensagens.erroRelatorio, AlertaTipos.erro);
            LogManager.getLogger(getClass()).log(Priority.ERROR, ex);
        }
    }//GEN-LAST:event_btnImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private components.JTableDataBinder table;
    private components.JTableDataBinder tableCategoria;
    private components.JTableDataBinder tableResultados;
    private components.JDateField txtData;
    // End of variables declaration//GEN-END:variables

    @Override
    public void btnAddActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void btnAlterarActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void btnExcluirActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void btnAtualizarActionPerformed(ActionEvent evt) {
        atualizar();
    }

    public class Compras {

        private List<ComprasVendas> compras;

        private List<SomaCategoria> categorias;

        public List<SomaCategoria> getCategorias() {
            return categorias;
        }

        public void setCategorias(List<SomaCategoria> categorias) {
            this.categorias = categorias;
        }

        public List<SomaCategoria> getResultados() {
            return resultados;
        }

        public void setResultados(List<SomaCategoria> resultados) {
            this.resultados = resultados;
        }

        private List<SomaCategoria> resultados;

        public List<ComprasVendas> getCompras() {
            return compras;
        }

        public void setCompras(List<ComprasVendas> compras) {
            this.compras = compras;
        }

    }

}
