package forms.estoque;

import components.CellRenderer;
import java.awt.event.ActionEvent;
import components.JPanelControleButtons;
import components.JTableDataBinderListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.estoque.EstoqueMovimentacao;
import model.estoque.Item;
import model.estoque.MovimentacaoTipo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Priority;
import services.ServiceException;
import services.estoque.EstoqueMovimentacaoService;
import services.estoque.ItemService;
import utils.AlertaTipos;
import utils.Forms;
import utils.Mensagens;
import utils.Utils;

/**
 *
 * @author Gustavo
 */
public class FrmRelatorioMovimentoEstoque extends JPanelControleButtons {

    private final EstoqueMovimentacaoService service;
    private Item item;
    private List<EstoqueMovimentacao> lista = new ArrayList<>();
    private double custoMedio = 0d;
    private double vendaTotal = 0d;
    private List<EstoqueMovimentacao> listaImprimir = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("d/M");
    private int quantidadeTotal = 0;
    private double valotUniMed = 0d;

    public FrmRelatorioMovimentoEstoque() {
        initComponents();
        setBtnAtualizarEnable(true);
        table.setDefaultRenderer(Object.class, new CellRenderer());
        tableSaldo.setDefaultRenderer(Object.class, new CellRenderer());
        service = new EstoqueMovimentacaoService();
        setupForm();
    }

    private void setupForm() {
        txtData.setDateFormat("M/y");
        txtData.setValue(new Date());
        tableResumos.setTableHeader(null);

        table.setListener(new JTableDataBinderListener<EstoqueMovimentacao>() {

            @Override
            public Collection<EstoqueMovimentacao> lista(String busca) throws ServiceException {
                return lista;
            }

            @Override
            public Object[] addRow(EstoqueMovimentacao dado) {
                Object[] o = new Object[7];
                o[0] = dado.getId() + "";
                o[1] = format.format(dado.getDataLancamento());
                o[2] = dado.getDescricao();
                o[3] = dado.getQuantidade() + "";
                o[4] = valorEntrada(dado);
                o[5] = valorSaida(dado);
                o[6] = Utils.formataDinheiro(somaQuantidade(dado));
                return o;
            }
        });

        tableSaldo.setListener(new JTableDataBinderListener<Item>() {

            @Override
            public Collection<Item> lista(String busca) throws ServiceException {
                List<Item> itens = new ArrayList<>();
                Item i = new Item();
                i.setQtdDisponivel(quantidadeTotal);
                i.setCustoMedio(valotUniMed);
                i.setValotTotal(quantidadeTotal * valotUniMed);
                itens.add(i);
                return itens;
            }

            @Override
            public Object[] addRow(Item dado) {
                Object[] o = new Object[7];
                o[0] = dado.getQtdDisponivel() + "";
                o[1] = Utils.formataDinheiro(dado.getCustoMedio());
                o[2] = Utils.formataDinheiro(dado.getValotTotal());
                return o;
            }

        });

        tableResumos.setListener(new JTableDataBinderListener<String[]>() {

            @Override
            public Collection<String[]> lista(String busca) throws ServiceException {
                List<String[]> l = new ArrayList<>();
                l.add(new String[]{"VENDA BRUTA", Utils.formataDinheiro(getVendaBruta())});
                l.add(new String[]{"CUSTO (MÉDIA PONDERADA MÓVEL)", Utils.formataDinheiro(getCustoMPM())});
                l.add(new String[]{"LUCRO BRUTO", Utils.formataDinheiro(getLucroBruto())});
                return l;
            }

            @Override
            public Object[] addRow(String[] dado) {
                return dado;
            }
        });

    }

    private double getLucroBruto() {
        return vendaTotal - custoMedio;
    }

    private void gerarRelatorio() {
        listaImprimir = new ArrayList<>();
        for (EstoqueMovimentacao dado : lista) {
            EstoqueMovimentacao estMov = new EstoqueMovimentacao();
            estMov.setId(dado.getId());
            estMov.setData(format.format(dado.getDataLancamento()));
            estMov.setQuantidade(dado.getQuantidade());
            estMov.setValorEntrada(valorEntrada(dado));
            estMov.setValorSaida(valorSaida(dado));
            estMov.setValorTotal(Utils.formataDinheiro(somaQuantidade(dado)));
            estMov.setDescricao(dado.getDescricao());
            listaImprimir.add(estMov);
        }
    }

    private String valorEntrada(EstoqueMovimentacao estMov) {
        if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.entrada)) {
            return Utils.formataDinheiro(estMov.getValorUnitario());
        } else if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.saida)
                && estMov.getMovimentacaoTipo().getDescricao().contains("AJUSTE SAIDA")) {
            return Utils.formataDinheiro(-estMov.getValorUnitario());
        } else {
            return "-";
        }
    }

    private String valorSaida(EstoqueMovimentacao estMov) {
        if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.saida)
                && estMov.getMovimentacaoTipo().getDescricao().contains("AJUSTE SAIDA")) {
            return "-";
        } else if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.saida)) {
            return Utils.formataDinheiro(estMov.getValorUnitarioVenda());
        } else {
            return "-";
        }
    }

    private double somaQuantidade(EstoqueMovimentacao estMov) {
        if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.entrada)) {
            return estMov.getQuantidade() * estMov.getValorUnitario();
        } else if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.saida)
                && estMov.getMovimentacaoTipo().getDescricao().contains("AJUSTE SAIDA")) {
            return estMov.getQuantidade() * -estMov.getValorUnitario();
        } else {
            return estMov.getQuantidade() * estMov.getValorUnitarioVenda();
        }
    }

    private List<EstoqueMovimentacao> getEstoqueMovimentacao() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(txtData.getDate());
        aCalendar.set(Calendar.DATE, 1);

        Date start = aCalendar.getTime();

        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = aCalendar.getTime();
        lista = service.movimentosDeEstoque(start, end, item);
        return lista;
    }

    private void atualizar() {
        table.atualizar();
        tableSaldo.atualizar();
        tableResumos.atualizar();
    }

    private double getVendaBruta() {
        double venda = 0d;
        for (EstoqueMovimentacao estMov : lista) {
            if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.saida)) {
                venda += estMov.getQuantidade() * estMov.getValorUnitarioVenda();
            }
        }
        vendaTotal = venda;
        return venda;
    }

    private double getCustoMPM() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(txtData.getDate());
        aCalendar.set(Calendar.DATE, 1);
        Date start = aCalendar.getTime();
        //----------Saldo----------
        int qtdSaldo = 0;
        double valorUniSaldo = 0d;
        double valorTotal = 0d;
        //-------------------------
        double venda = 0d;
        int cont = 0;
        Date date = null;
        for (EstoqueMovimentacao estMov : lista) {
            if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.entrada)
                    || estMov.getMovimentacaoTipo().getDescricao().contains("AJUSTE ENTRADA")
                    || estMov.getMovimentacaoTipo().getDescricao().contains("AJUSTE SAIDA")) {
                if (estMov.getMovimentacaoTipo().getDescricao().contains("AJUSTE SAIDA")) {
                    valorTotal -= (estMov.getQuantidade() * estMov.getValorUnitario());
                    qtdSaldo -= estMov.getQuantidade();
                    valorUniSaldo = valorTotal / qtdSaldo;
                } else {
                    valorTotal += (estMov.getQuantidade() * estMov.getValorUnitario());
                    qtdSaldo += estMov.getQuantidade();
                    valorUniSaldo = valorTotal / qtdSaldo;
                }
            }
            if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.saida)
                    && !estMov.getMovimentacaoTipo().getDescricao().contains("AJUSTE SAIDA")) {
                valorTotal -= (estMov.getQuantidade() * valorUniSaldo);
                venda += estMov.getQuantidade() * valorUniSaldo;
                qtdSaldo -= estMov.getQuantidade();
            }
        }

        custoMedio = venda;
        //custoMedio = valorTotal;
        quantidadeTotal = qtdSaldo;
        valotUniMed = valorUniSaldo;
        return venda;
    }

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
        jScrollPane3 = new javax.swing.JScrollPane();
        tableResumos = new components.JTableDataBinder();
        btnImprimir = new javax.swing.JButton();
        txtItem = new components.F2(FrmItemF2.class, (id) -> new ItemService().findById(id).getDescricao());
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableSaldo = new components.JTableDataBinder();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

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

        txtData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Mês");

        btnBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/magnifying.png"))); // NOI18N
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
                "Código", "Data", "Descrição", "Quantidade", "Valor de Compra", "Valor de Venda", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMaxWidth(100);
            table.getColumnModel().getColumn(1).setMinWidth(100);
            table.getColumnModel().getColumn(1).setMaxWidth(100);
            table.getColumnModel().getColumn(3).setMinWidth(110);
            table.getColumnModel().getColumn(3).setMaxWidth(110);
            table.getColumnModel().getColumn(4).setMinWidth(130);
            table.getColumnModel().getColumn(4).setMaxWidth(130);
            table.getColumnModel().getColumn(5).setMinWidth(130);
            table.getColumnModel().getColumn(5).setMaxWidth(130);
            table.getColumnModel().getColumn(6).setMinWidth(100);
            table.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        tableResumos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descrição", "Valor"
            }
        ));
        tableResumos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane3.setViewportView(tableResumos);

        btnImprimir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/paper6.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText(" Item");

        tableSaldo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Quantidade Total", "Custo Médio", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSaldo.setMaximumSize(new java.awt.Dimension(2147483647, 18));
        tableSaldo.setMinimumSize(new java.awt.Dimension(45, 15));
        tableSaldo.setPreferredSize(new java.awt.Dimension(225, 18));
        tableSaldo.setRowHeight(19);
        jScrollPane4.setViewportView(tableSaldo);

        jLabel3.setText("Saldo do período");

        jLabel4.setText("Lucratividade com venda");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtItem, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnImprimir)
                        .addGap(334, 334, 334))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            item = new ItemService().findById(txtItem.getValueSelected());
            getEstoqueMovimentacao();
            getCustoMPM();
            atualizar();
        } catch (Exception e) {
            utils.Forms.mensagem("Campos obrigatórios vazios", AlertaTipos.erro);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        try {
            new ItemService().findById(txtItem.getValueSelected());
            gerarRelatorio();
            JRBeanCollectionDataSource jrs = new JRBeanCollectionDataSource(listaImprimir);
            Map parametros = new HashMap();
            parametros.put("saldoQT", quantidadeTotal + "");
            parametros.put("saldoVM", Utils.formataDinheiro(valotUniMed));
            parametros.put("saldoVT", Utils.formataDinheiro(quantidadeTotal * valotUniMed));
            parametros.put("vendaBruta", Utils.formataDinheiro(getVendaBruta()));
            parametros.put("custoMedio", Utils.formataDinheiro(getCustoMPM()));
            parametros.put("lucroBruto", Utils.formataDinheiro(getLucroBruto()));

            try {
                JasperPrint jpr = JasperFillManager
                        .fillReport("src/relatorios/movimentacao_estoque.jasper",
                                parametros,
                                jrs);

                Forms.showJasperModal(jpr);
            } catch (JRException ex) {
                Forms.mensagem(Mensagens.erroRelatorio, AlertaTipos.erro);
                LogManager.getLogger(getClass()).log(Priority.ERROR, ex);
            }
        } catch (Exception e) {
            utils.Forms.mensagem("Campos obrigatórios vazios", AlertaTipos.erro);
        }

    }//GEN-LAST:event_btnImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private components.JTableDataBinder jTableDataBinder1;
    private components.JTableDataBinder table;
    private components.JTableDataBinder tableResumos;
    private components.JTableDataBinder tableSaldo;
    private components.JDateField txtData;
    private components.F2 txtItem;
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
