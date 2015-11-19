/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.ordem;

import components.CellRenderer;
import components.JDialogController;
import components.JTableDataBinderListener;
import forms.frmMain;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import model.Pessoa;
import forms.FrmPessoaF2;
import forms.fluxo.FrmContaCadastro;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import model.estoque.EstoqueMovimentacao;
import model.fluxo.Conta;
import model.fluxo.ContaCategoria;
import model.ordem.Ordem;
import model.ordem.OrdemServico;
import model.ordem.OrdemStatus;
import model.ordem.Veiculo;
import model.queryresults.ItemFichaServico;
import model.queryresults.OrdemRelatorio;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Priority;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Converter;
import services.PessoaService;
import services.ServiceException;
import services.estoque.EstoqueMovimentacaoService;
import services.fluxo.ContaService;
import services.ordem.OrdemService;
import services.ordem.OrdemServicoService;
import services.ordem.OrdemStatusService;
import services.ordem.VeiculoService;
import utils.AlertaTipos;
import utils.Forms;
import utils.Mensagens;
import utils.Parametros;
import utils.Utils;

/**
 *
 * @author Charles
 */
public class FrmOrdemCadastro extends JDialogController {
    
    private int id;
    private final OrdemService service = new OrdemService();

    /**
     * Creates new form frmCadastroOrdem
     */
    public FrmOrdemCadastro() {
        this(0);
    }
    
    public FrmOrdemCadastro(int id) {
        super(frmMain.getInstance(), "Manutenção de Ordem de serviço");
        this.id = id;
        initComponents();
        table.setDefaultRenderer(Object.class, new CellRenderer());
        setupForm();
    }
    List lordenada;
    
    private void setupForm() {

        //Esconde a coluna id
        table.removeColumn(table.getColumn("Id"));
        if (id > 0) {
            ordem = service.findOrdem(id);
        } else {
            ordem = new Ordem();
            ordem.setPessoa(new Pessoa());
            ordem.setVeiculo(new Veiculo());
            setStatusPadrao();
        }
        // center the jframe on screen
        setLocationRelativeTo(null);
        setDefaultButton(btnSalvar);
        
        validator.validarObrigatorio(txtCliente);
        validator.validarObrigatorio(jcbStatus);
        validator.validarDeBanco(txtCliente, new PessoaService());
        validator.validarDeBanco(txtVeiculo, new VeiculoService());
        validator.validarCustom(jcbStatus, (valor) -> jcbStatus.getSelectedItem() != null, "Selecione uma status");
        
        jcbStatus.setModel(new DefaultComboBoxModel(new Vector(new OrdemStatusService().findAtivos())));
        
        table.setListener(new JTableDataBinderListener() {
            
            @Override
            public Collection lista(String busca) throws ServiceException {
                return listagem();
            }
            
            @Override
            public Object[] addRow(Object dado) {
                Object[] l = new Object[7];
                if (dado instanceof OrdemServico) {
                    OrdemServico s = ((OrdemServico) dado);
                    l[0] = s.getId() + "";
                    l[1] = "SERVIÇO";
                    if (s.getConta() != null) {
                        l[1] += " DE TERCEIRO";
                    }
                    l[2] = Utils.formataDinheiro(s.getValorEntrada());
                    l[3] = s.getQuantidade() + "";
                    l[4] = s.getTipoServico().getNome();
                    l[5] = Utils.formataDate(s.getDataRealizada());
                    l[6] = Utils.formataDinheiro(s.getValorEntrada() * s.getQuantidade());
                } else {
                    EstoqueMovimentacao m = (EstoqueMovimentacao) dado;
                    l[0] = m.getId() + "";
                    l[1] = "PEÇA";
                    l[2] = Utils.formataDinheiro(m.getValorUnitarioVenda());
                    l[3] = m.getQuantidade() + "";
                    l[4] = m.getEstoque().getItem().getDescricao();
                    l[5] = Utils.formataDate(m.getDataLancamento());
                    l[6] = Utils.formataDinheiro(m.getValorUnitarioVenda() * m.getQuantidade());
                }
                return l;
            }
        });
        initBinding();
    }
    
    private List listagem() {
        lordenada = new ArrayList();
        lordenada.addAll(ordem.getEstoqueMovimentacaos());
        lordenada.addAll(ordem.getOrdemServicos());
        lordenada.sort(new Comparator() {
            
            @Override
            public int compare(Object o1, Object o2) {
                Date d1;
                if (o1 instanceof OrdemServico) {
                    d1 = ((OrdemServico) o1).getDataRealizada();
                } else {
                    d1 = ((EstoqueMovimentacao) o1).getDataLancamento();
                }
                
                Date d2;
                if (o2 instanceof OrdemServico) {
                    d2 = ((OrdemServico) o2).getDataRealizada();
                } else {
                    d2 = ((EstoqueMovimentacao) o2).getDataLancamento();
                }
                return d1.compareTo(d2);
            }
        });
        return lordenada;
        
    }
    
    private void atualizaListagem() {
        table.atualizar();
        double valorTotal = service.valorTotal(ordem);
        lblValor.setText(Utils.formataDinheiro(valorTotal));
    }
    
    private void setStatusPadrao() {
        int inicial = Integer.parseInt(Parametros.getInstance().getValue("status_inicial"));
        ordem.setOrdemStatus(new OrdemStatusService().findById(inicial));
        
    }
    private boolean binded = false;
    
    private void initBinding() {
        if (!binded) {
            binded = true;
            AutoBinding autoVehicle = Utils.createBind(ordem, "veiculo", txtVeiculo, false);
            autoVehicle.setConverter(new Converter<Veiculo, String>() {
                
                @Override
                public Veiculo convertReverse(String value) {
                    try {
                        Veiculo p = new Veiculo();
                        p.setId(Integer.valueOf(value));
                        return p;
                    } catch (Exception e) {
                        return null;
                    }
                }
                
                @Override
                public String convertForward(Veiculo value) {
                    if (value != null && value.getId() != 0) {
                        return value.getId() + "";
                    }
                    return "";
                }
            });
            autoVehicle.bind();
            OrdemStatus st = ordem.getOrdemStatus();
            DefaultComboBoxModel m = ((DefaultComboBoxModel) jcbStatus.getModel());
            if (st != null) {
                for (int i = 0; i < m.getSize(); i++) {
                    OrdemStatus get = (OrdemStatus) m.getElementAt(i);
                    if (get.getId() == st.getId()) {
                        ordem.setOrdemStatus(get);
                        break;
                    }
                }
            }
            
            Utils.createBind(ordem, "ordemStatus", jcbStatus);
            AutoBinding a = Utils.createBind(ordem, "pessoa", txtCliente, false);
            a.setConverter(new Converter<Pessoa, String>() {
                
                @Override
                public Pessoa convertReverse(String value) {
                    try {
                        Pessoa p = new Pessoa();
                        p.setId(Integer.valueOf(value));
                        return p;
                    } catch (Exception e) {
                        return null;
                    }
                }
                
                @Override
                public String convertForward(Pessoa value) {
                    if (value != null && value.getId() != 0) {
                        return value.getId() + "";
                    }
                    return "";
                }
            });
            a.bind();
            if (ordem.getPrazo() != null) {
                txtPrazo.setValue(ordem.getPrazo());
            }
//            AutoBinding aPrazo = Utils.createBind(ordem, "prazo", txtPrazo, false);
//            aPrazo.setConverter(new Converter<Date,Object>() {
//
//                @Override
//                public Object convertForward(Date value) {
//                    return value;
//                }
//
//                @Override
//                public Date convertReverse(Object value) {
//                    if(value != null)
//                        return (Date) value;
//                    else
//                        return null;
//                }
//            });
//            aPrazo.bind();
            Utils.createBind(ordem, "descricao", txtPedido);
        }
        atualizaListagem();
    }
    
    private void salvar() {
        if (ordem.getOrdemStatus().isFinaliza() && ordem.getConta() == null) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Para a ordem de serviço ser finalizada, deve ser cadastrada uma conta para ela, deseja cadastrar agora?");
            if (dialogResult == JOptionPane.YES_OPTION) {
                btnFinalizar.doClick();
                return;
            } else {
                setStatusPadrao();
                JOptionPane.showMessageDialog(rootPane, "A ordem não foi finalizada");
            }
        }
        ordem.setPrazo(txtPrazo.getDate());
        if (ordem.getId() == 0) {
            service.insert(ordem);
        } else {
            service.update(ordem);
        }
        id = ordem.getId();
    }
    
    private void save() {
        if (!validator.isValido()) {
            return;
        }
        
        Utils.safeCode(() -> {
            salvar();
            utils.Forms.mensagem(utils.Mensagens.registroSalvo, AlertaTipos.sucesso);
//
            dispose();
        });
    }
    
    private void imprimirFicha() {
        
        OrdemRelatorio relatorio = new OrdemRelatorio();
        relatorio.setCliente(ordem.getPessoa().getNome());
        relatorio.setMarca(ordem.getVeiculo().getModelo().getMarca().getNome());
        relatorio.setModelo(ordem.getVeiculo().getModelo().getNome());
        relatorio.setPedido(ordem.getDescricao());
        relatorio.setTelefone(ordem.getPessoa().getTelefone());
        relatorio.setPlaca(ordem.getVeiculo().getPlaca());
        relatorio.setAno(ordem.getVeiculo().getAno() == 0 ? "" : ordem.getVeiculo().getAno() + "");
        relatorio.setValorTotal(Utils.formataDinheiro(service.valorTotal(ordem)));
        relatorio.setCor(ordem.getVeiculo().getCor().getNome());
        relatorio.setDesconto(Utils.formataDinheiro(ordem.getDesconto()));
        for (Object item : listagem()) {
            ItemFichaServico i = new ItemFichaServico();
            if (item instanceof OrdemServico) {
                OrdemServico s = ((OrdemServico) item);
                i.setTipoServico("SERVIÇO");
                if (s.getConta() != null) {
                    i.setTipoServico(i.getTipoServico() + " DE TERCEIRO");
                }
                i.setValorUnitario(Utils.formataDinheiro(s.getValorEntrada()));
                i.setQuantidade(s.getQuantidade());
                i.setDescricao(s.getTipoServico().getNome());
                i.setDataRealizado(Utils.formataDate(s.getDataRealizada()));
                i.setValorTotal(Utils.formataDinheiro(s.getValorEntrada() * s.getQuantidade()));
            } else {
                EstoqueMovimentacao m = (EstoqueMovimentacao) item;
                i.setTipoServico("PEÇA");
                i.setValorUnitario(Utils.formataDinheiro(m.getValorUnitarioVenda()));
                i.setQuantidade(m.getQuantidade());
                i.setDescricao(m.getEstoque().getItem().getDescricao());
                i.setDataRealizado(Utils.formataDate(m.getDataLancamento()));
                i.setValorTotal(Utils.formataDinheiro(m.getValorUnitarioVenda() * m.getQuantidade()));
            }
            relatorio.getItens().add(i);
        }
        
        List<OrdemRelatorio> fichas = new ArrayList<>();
        fichas.add(relatorio);
        JRBeanCollectionDataSource jrs = new JRBeanCollectionDataSource(fichas);
        
        Map parametros = new HashMap();
        try {
            JasperPrint jpr = JasperFillManager
                    .fillReport("src/relatorios/ordem_servico.jasper",
                            parametros, jrs);
            Forms.showJasperModal(jpr);
            
        } catch (JRException ex) {
            Forms.mensagem(Mensagens.erroRelatorio, AlertaTipos.erro);
            LogManager.getLogger(getClass()).log(Priority.ERROR, ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        ordem = new model.ordem.Ordem();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jcbStatus = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPedido = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        txtCliente = new components.F2(FrmPessoaF2.class,(id)->new PessoaService().findById(id).getNome());
        jLabel4 = new javax.swing.JLabel();
        txtVeiculo = new components.F2(FrmVeiculoF2.class,(id)->new VeiculoService().findById(id,true).toString());
        txtPrazo = new components.JDateField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblValor = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new components.JTableDataBinder();
        btnFinalizar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnAddServico = new javax.swing.JButton();
        btnAddPeca = new javax.swing.JButton();
        btnExcluiPeca = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jcbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Carregando..." }));

        txtPedido.setColumns(20);
        txtPedido.setRows(5);
        jScrollPane1.setViewportView(txtPedido);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Cliente:");

        txtCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Veículo:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Prazo:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Valor Atual:");

        lblValor.setText("R$0,00");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Descrição do Pedido:");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tipo", "Valor Unitário", "Qtde.", "Descrição", "Data", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(table);

        btnFinalizar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/save.png"))); // NOI18N
        btnFinalizar.setText("Finalizar ordem");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cash4.png"))); // NOI18N
        jButton1.setText("Conta da Ordem de Serviço");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnAddServico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAddServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/wrenches8.png"))); // NOI18N
        btnAddServico.setText("Serviço");
        btnAddServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddServicoActionPerformed(evt);
            }
        });

        btnAddPeca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAddPeca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/wrench105.png"))); // NOI18N
        btnAddPeca.setText("Peça");
        btnAddPeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPecaActionPerformed(evt);
            }
        });

        btnExcluiPeca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnExcluiPeca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/excluir.png"))); // NOI18N
        btnExcluiPeca.setText("Excluir");
        btnExcluiPeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluiPecaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddServico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddPeca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluiPeca))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFinalizar))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblValor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton1))
                                    .addComponent(txtVeiculo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(jcbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(lblValor)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddServico, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddPeca, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluiPeca, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        save();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        if (!validator.isValido()) {
            return;
        }
        //Cadastra conta
        if (ordem.getConta() == null) {
            
            double valorTotal = service.valorTotal(ordem);
            FrmDescontoCadastro frmDesconto = new FrmDescontoCadastro(valorTotal);
            frmDesconto.setListener((desconto) -> {
                //Aplicar desconto
//                ordem.set;
                FrmContaCadastro frmConta = new FrmContaCadastro(valorTotal - desconto, 1, "", Conta.ContaTipo.ordem, ContaCategoria.TipoCategoria.entrada);
                frmConta.setPessoa(ordem.getPessoa().getId(), true);
                frmConta.setDescricao("ORDEM DE SERVIÇO CÒDIGO " + ordem.getId());
                frmConta.setListenerOnSave((c) -> {
                    ordem.setConta(c);
                    
                    int codigoStatus = Integer.parseInt(Parametros.getInstance().getValue("status_finalizador"));
                    ordem.setOrdemStatus(new OrdemStatusService().findById(codigoStatus));
                    salvar();
                    imprimirFicha();
                    Forms.mensagem("Ordem de serviço finalizada", AlertaTipos.sucesso);
                });
                frmConta.setVisible(true);
            });
            frmDesconto.setVisible(true);
        } else {
            if (!ordem.getOrdemStatus().isFinaliza()) {
                int codigoStatus = Integer.parseInt(Parametros.getInstance().getValue("status_finalizador"));
                ordem.setOrdemStatus(new OrdemStatusService().findById(codigoStatus));
            }
            salvar();
            Forms.mensagem("Ordem de serviço já está finalizada", AlertaTipos.sucesso);

            //Se já foi gerada a conta, mas os valores foram alterados, deve ser exibida a conta
            if (ordem.getConta().getValorTotal() != service.valorTotal(ordem) - ordem.getDesconto()) {
                FrmContaCadastro frmConta = new FrmContaCadastro(ordem.getConta().getId());
                frmConta.setVisible(true);
            }
            imprimirFicha();
            
        }
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void btnAddServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddServicoActionPerformed
        if (!validator.isValido()) {
            return;
        }
        salvar();
        
        FrmOrdemServicoCadastro frm = new FrmOrdemServicoCadastro(ordem);
        frm.setVisible(true);
        service.refreshCollection(ordem);
        atualizaListagem();
    }//GEN-LAST:event_btnAddServicoActionPerformed

    private void btnAddPecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPecaActionPerformed
        if (!validator.isValido()) {
            return;
        }
        salvar();
        JDialog dialog = new FrmOrdemItemCadastro(ordem);
        dialog.setVisible(true);
        atualizaListagem();
    }//GEN-LAST:event_btnAddPecaActionPerformed

    private void btnExcluiPecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluiPecaActionPerformed
        
        int id = table.getSelectedRow();
        if (id >= 0 && Forms.dialogDelete()) {
            Object o = lordenada.get(id);
            if (o instanceof EstoqueMovimentacao) {
                new EstoqueMovimentacaoService().excluirEstoque(ordem, (EstoqueMovimentacao) o);
                utils.Forms.mensagem(Mensagens.excluidoSucesso, AlertaTipos.sucesso);
                
            } else {
                OrdemServico ordemServico = (OrdemServico) o;
                boolean deleteconta = false;
                if (ordemServico.getConta() != null) {
                    int sel = JOptionPane.showConfirmDialog(null, "O serviço possui uma conta a pagar relacionada, deseja excluir a conta?");
                    if (sel == JOptionPane.YES_OPTION) {
                        deleteconta = true;
                    } else if (sel == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                ordem.getOrdemServicos().remove(ordemServico);
                salvar();
                service.refreshCollection(ordem);
//                new OrdemServicoService().delete(ordemServico.getId());

                if (deleteconta) {
                    new ContaService().delete(ordemServico.getConta().getId());
                }
            }
        }
        
        atualizaListagem();

    }//GEN-LAST:event_btnExcluiPecaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (ordem.getConta() != null) {
            new FrmContaCadastro(ordem.getConta().getId()).setVisible(true);
        } else {
            Forms.mensagem("Não há conta relacionada", AlertaTipos.erro);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPeca;
    private javax.swing.JButton btnAddServico;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluiPeca;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox jcbStatus;
    private javax.swing.JLabel lblValor;
    private model.ordem.Ordem ordem;
    private components.JTableDataBinder table;
    private components.F2 txtCliente;
    private javax.swing.JTextArea txtPedido;
    private components.JDateField txtPrazo;
    private components.F2 txtVeiculo;
    // End of variables declaration//GEN-END:variables
}
