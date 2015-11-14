/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.fluxo;

import com.alee.managers.log.Log;
import components.JCampoBusca;
import java.awt.event.ActionEvent;
import components.JPanelControleButtons;

import components.JTableDataBinderListener;
import components.JValidadorDeCampos;
import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import model.fluxo.Conta;
import model.fluxo.ContaBancaria;
import model.fluxo.ContaCategoria;
import model.queryresults.ComprasVendas;
import model.queryresults.LivroCaixa;
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
import services.fluxo.ContaBancariaService;
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
public class FrmRelatorioLivroCaixa extends JPanelControleButtons {

    private final ContaService service;
    private final ContaBancariaService serviceBanco;

//    JTableDataBinder table;
    public FrmRelatorioLivroCaixa() {
        initComponents();
        setBtnAtualizarEnable(true);
        service = new ContaService();
        serviceBanco = new ContaBancariaService();
        setupForm();

    }
    Collection<ComprasVendas> dados;

    Collection<SomaCategoria> totalCategorias;
    private JValidadorDeCampos validador = new JValidadorDeCampos();

    private void setupForm() {
        txtData.setDateFormat("M/y");
        txtData.setValue(new Date());
        tableResumos.setTableHeader(null);
        SimpleDateFormat format = new SimpleDateFormat("d/M");
        validador.validarObrigatorio(jcbCaixa);
        jcbCaixa.setModel(new DefaultComboBoxModel(new Vector(new ContaBancariaService().findAtivos())));
        table.setListener(new JTableDataBinderListener<LivroCaixa>() {

            @Override
            public Collection<LivroCaixa> lista(String busca) throws ServiceException {
                return getLivroCaixa();
            }

            @Override
            public Object[] addRow(LivroCaixa dado) {
                Object[] r = new Object[7];
                r[0] = dado.getNumero();
                r[1] = dado.getData();
                r[2] = dado.getCategoria();
                r[3] = dado.getParcela();
                r[4] = dado.getDescricao();
                r[5] = dado.getEntrada() == 0d ? "" : Utils.formataDinheiro(dado.getEntrada());
                r[6] = dado.getSaida() == 0d ? "" : Utils.formataDinheiro(dado.getSaida());
                return r;
            }
        });
        tableResumos.setListener(new JTableDataBinderListener<String[]>() {

            @Override
            public Collection<String[]> lista(String busca) throws ServiceException {
                List<String[]> l = new ArrayList<>();
                l.add(new String[]{"SALDO ANTERIOR", Utils.formataDinheiro(getValorAnterior((ContaBancaria) jcbCaixa.getSelectedItem()))});
                l.add(new String[]{"SALDO PERÍODO", Utils.formataDinheiro(getValorPeriodo((ContaBancaria) jcbCaixa.getSelectedItem()))});
                return l;
            }

            @Override
            public Object[] addRow(String[] dado) {
                return dado;
            }
        });
        atualizar();

    }

    private List<LivroCaixa> getLivroCaixa() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(txtData.getDate());
        aCalendar.set(Calendar.DATE, 1);

        Date start = aCalendar.getTime();

        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = aCalendar.getTime();
        return service.livroCaixa(start, end, (ContaBancaria) jcbCaixa.getSelectedItem());
    }

    private void atualizar() {
        table.atualizar();
        tableResumos.atualizar();
    }

    private double getValorAnterior(ContaBancaria conta) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(txtData.getDate());
        aCalendar.set(Calendar.DATE, 1);

        Date start = aCalendar.getTime();

        return serviceBanco.saldoCaixa(conta, start);
    }

    private double getValorPeriodo(ContaBancaria conta) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(txtData.getDate());
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = aCalendar.getTime();
        return serviceBanco.saldoCaixa(conta, end);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDataBinder1 = new components.JTableDataBinder();
        txtData = new components.JDateField();
        jLabel1 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new components.JTableDataBinder();
        jLabel2 = new javax.swing.JLabel();
        jcbCaixa = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableResumos = new components.JTableDataBinder();
        btnImprimir = new javax.swing.JButton();

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
        jScrollPane2.setViewportView(jTableDataBinder1);

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
                "Nº Ordem", "Data", "Categoria", "Parcela", "Descrição", "Entrada", "Saída"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMaxWidth(100);
            table.getColumnModel().getColumn(1).setMinWidth(150);
            table.getColumnModel().getColumn(1).setMaxWidth(200);
            table.getColumnModel().getColumn(3).setMaxWidth(50);
            table.getColumnModel().getColumn(5).setMinWidth(150);
            table.getColumnModel().getColumn(5).setMaxWidth(200);
            table.getColumnModel().getColumn(6).setMinWidth(150);
            table.getColumnModel().getColumn(6).setMaxWidth(200);
        }

        jLabel2.setText("Conta");

        jcbCaixa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Carregando..." }));

        tableResumos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descrição", "Valor"
            }
        ));
        jScrollPane3.setViewportView(tableResumos);

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
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcbCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImprimir)
                        .addGap(0, 540, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
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
                    .addComponent(jLabel2)
                    .addComponent(jcbCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (!validador.isValido()) {
            return;
        }
        atualizar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

        if (!validador.isValido()) {
            return;
        }
        JRBeanCollectionDataSource jrs = new JRBeanCollectionDataSource(getLivroCaixa());
        JRProperties.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
        Map parametros = new HashMap();
        parametros.put("saldoAnterior", Utils.formataDinheiro(getValorAnterior((ContaBancaria) jcbCaixa.getSelectedItem())));

        parametros.put("saldoPeriodo", Utils.formataDinheiro(getValorPeriodo((ContaBancaria) jcbCaixa.getSelectedItem())));
        try {
            JasperPrint jpr = JasperFillManager
                    .fillReport("src/relatorios/livro_caixa.jasper",
                            parametros,
                            jrs);
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
    private components.JTableDataBinder jTableDataBinder1;
    private javax.swing.JComboBox jcbCaixa;
    private components.JTableDataBinder table;
    private components.JTableDataBinder tableResumos;
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

}
