/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.fluxo;

import components.BeanTableModel;
import components.JTableDataBinderListener;
import components.RowTableModel;
import components.ThrowingCommand;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import model.fluxo.Conta;
import model.fluxo.Parcela;
import model.fluxo.ParcelaPagamento;
import org.apache.log4j.LogManager;
import services.ServiceException;
import services.fluxo.ParcelaService;
import utils.AlertaTipos;
import utils.Forms;
import utils.Globals;
import utils.Utils;

/**
 *
 * @author Charles
 */
public class PanelParcelas extends javax.swing.JPanel {

    private ParcelaService service = new ParcelaService();

    private Conta conta;

    public Conta getConta() {
        return conta;
    }

    public void setValor(double valor){
        jffValor.setValue(valor);
    }

    public void bindValor(){
        Utils.createBind(conta, "valorTotal",jffValor);
    }
    public void setParcelas(int parcelas){
        jtbParcelas.setValue(parcelas);
    }
    public void setParcelasEnable(boolean enable){
        jtbParcelas.setEnabled(enable);
    }
    public List<Parcela> getParcelas() {
        return conta.getParcelas();

    }

    public void setConta(Conta conta) {
        this.conta = conta;
        table.setListener(new JTableDataBinderListener<Parcela>() {

            @Override
            public Collection<Parcela> lista(String busca) throws ServiceException {
                getParcelas().sort((p1, p2) -> Integer.compare(p1.getParcela(), p2.getParcela()));
                EventQueue.invokeLater(() -> {
                    double valorTotal = 0d;
                    double valorPago = 0d;
                    for (Parcela parcela : getParcelas()) {
                        valorTotal += parcela.getValor();
                        if (parcela.isFechado()) {
                            valorPago += parcela.getValor();
                        } else {
                            for (ParcelaPagamento pagamento : parcela.getPagamentos()) {

                                valorPago += pagamento.getValor();
                            }
                        }
                    }
                    int max = (int) Math.round(valorTotal * 100);
                    int maxPago = (int) Math.round(valorPago * 100);
                    jProgressBar1.setMaximum(max);
                    jProgressBar1.setValue(maxPago);
                    jProgressBar1.setString("Pago " + Utils.formataDinheiro(valorPago) + "/" + Utils.formataDinheiro(valorTotal));
                });
                return getParcelas();
            }

            @Override
            public Object[] addRow(Parcela dado) {
                Object[] o = new Object[5];
                o[0] = dado.getParcela();
                o[1] = dado.getDataLancamento();
                o[2] = Utils.formataDinheiro(dado.getValor());

                double somaPagamento = 0D;
                if (dado.getPagamentos() != null) {
                    for (ParcelaPagamento pagamento : dado.getPagamentos()) {
                        somaPagamento += pagamento.getValor();
                    }
                }
                double saldo = somaPagamento - dado.getValor();

                ImageIcon status;
                if (saldo * -1 == dado.getValor()) {
                    status = Globals.iconeError;
                } else if (saldo >= 0) {
                    status = Globals.iconeSuccess;
                } else {
                    status = Globals.iconeWarning;
                }
                o[3] = status;
                o[4] = Utils.formataDinheiro(saldo);

                return o;
            }

            @Override
            public void setValueAt(int row, int column, Object value) {
                Parcela p = getParcelas().get(row);
                switch (column) {
                    case 1:
                        p.setDataLancamento((Date) value);
                        break;
                    case 2:

                        p.setValor(Utils.parseDinheiro(value + ""));
                        getTable().atualizar();
                        break;

                }
                LogManager.getLogger(getClass()).info("Chamou com " + value);
                System.out.println("Chamou com " + value);
            }
        });
        table.atualizar();

    }

    public void atualizar() {
        table.atualizar();
    }

    private void gerarParcelas() {
        int fieldIncrementar;
        int quantidade;
        /*
         Anual
         Semestral
         Trimestral
         Mensal
         Semanal
         Diário
         */
        switch (jcbFrequencia.getSelectedIndex()) {
            case 0:
                fieldIncrementar = Calendar.YEAR;
                quantidade = 1;

                break;
            case 1:
                fieldIncrementar = Calendar.MONTH;
                quantidade = 6;
                break;
            case 2:
                fieldIncrementar = Calendar.MONTH;
                quantidade = 3;

                break;
            default:
            case 3:
                fieldIncrementar = Calendar.MONTH;
                quantidade = 1;

                break;
            case 4:
                fieldIncrementar = Calendar.DATE;
                quantidade = 7;

                break;

            case 5:
                fieldIncrementar = Calendar.DATE;
                quantidade = 1;

                break;
        }
        Utils.safeCode(() -> {
            try {
                service.gerarParcelas(conta, (Integer) jtbParcelas.getValue(), jffValor.getValue(), jdfData.getDate(), fieldIncrementar, quantidade);
                table.atualizar();

            } catch (IllegalArgumentException e) {
                Forms.mensagem("Informe a " + e.getMessage(), AlertaTipos.erro);
            }
        });
    }

    /**
     * Creates new form PanelParcelas
     */
    public PanelParcelas() {
        initComponents();
    }

    private void editarParcelas() {

        int id = table.getSelectedId();
        Parcela parc = conta.getParcelas().stream().filter((p) -> p.getParcela() == id).findFirst().get();
        new FrmParcelaCadastro(conta, parc).setVisible(true);
        table.atualizar();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcbFrequencia = new javax.swing.JComboBox();
        jtbParcelas = new javax.swing.JSpinner();
        jdfData = new components.JDateField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new components.JTableDataBinder();
        jButton1 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel6 = new javax.swing.JLabel();
        jffValor = new components.JTextFieldMoney();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        jcbFrequencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Anual", "Semestral", "Trimestral", "Mensal", "Semanal", "Diário" }));
        jcbFrequencia.setSelectedItem("Mensal");

        jtbParcelas.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Data Primeiro Pagamento:");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Parcela", "Data", "Valor", "Status", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(table);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("Gerar Parcelas");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jProgressBar1.setMaximum(0);
        jProgressBar1.setToolTipText("Valor pago");
        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Valor:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Frequência:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Parcelas:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                                .addComponent(jdfData, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(50, 50, 50)
                                .addComponent(jffValor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtbParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jcbFrequencia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jffValor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jcbFrequencia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtbParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdfData, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jffValor.getValue() <= 0) {
            return;
        }
        gerarParcelas();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        if (evt.getClickCount() == 2) {
            editarParcelas();
        }
    }//GEN-LAST:event_tableMouseClicked

    private void tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            editarParcelas();
        }
    }//GEN-LAST:event_tableKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox jcbFrequencia;
    private components.JDateField jdfData;
    private components.JTextFieldMoney jffValor;
    private javax.swing.JSpinner jtbParcelas;
    private components.JTableDataBinder table;
    // End of variables declaration//GEN-END:variables

    public components.JTableDataBinder getTable() {
        return table;
    }
}
