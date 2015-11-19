/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.fluxo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import model.fluxo.Conta;
import model.fluxo.ContaBancaria;
import model.fluxo.ContaCategoria;
import model.fluxo.FormaPagamento;
import model.fluxo.Parcela;
import model.fluxo.ParcelaPagamento;
import model.queryresults.ComprasVendas;
import model.queryresults.LivroCaixa;
import model.queryresults.MovimentoBancario;
import model.queryresults.SomaCategoria;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import services.Service;
import services.ServiceException;
import utils.Parametros;
import utils.Utils;

/**
 *
 * @author Charles
 */
public class ContaService extends Service<Conta> {

    public ContaService() {
        super(Conta.class);
    }

    public Conta findByNota(String nota) throws ServiceException {
        List<Conta> c = findBy("notaFiscal", nota);

        if (c.size() == 0 || c.get(0).getCategoria().getTipo() == ContaCategoria.TipoCategoria.saida) {
            return null;
        } else {
            return c.get(0);
        }
    }

    public static double valorConta(Conta c) {
        double valor = 0d;
        valor = c.getParcelas().stream()
                .map((parcela) -> parcela.getValor())
                .reduce(valor, (accumulator, _item) -> accumulator + _item);
        return valor;
    }

    public static double saldoConta(Conta c) {
        double valor = 0d;

        valor = c.getParcelas().stream()
                .map((parcela) -> parcela.getValor())
                .reduce(valor, (accumulator, _item) -> accumulator + _item);
        return valor - c.getValorPago();
//        return  c.getParcelas().stream()
//                .map((pagamento) -> pagamento.getValor())
//                .reduce(0, (accumulator, _item) -> accumulator + _item);
    }

    @Override
    public void update(Conta obj) throws ServiceException {
        this.insert(obj);
    }

    public Optional<Parcela> proximaParcela(Conta conta) {
        return conta.getParcelas().stream().filter((p) -> !p.isFechado()).min((e1, e2) -> e1.getDataLancamento().compareTo(e2.getDataLancamento()));
    }

    private double calculaValorPago(Conta conta) {
        double valorPago = 0;
        for (Parcela parcela : conta.getParcelas()) {
            valorPago += ParcelaService.valorTotalParcela(parcela);
        }
        return valorPago;
    }

    @Override
    public void insert(Conta obj) throws ServiceException {
        insert(new Conta[]{obj});

    }

    public void insert(Conta... objs) {

        executeOnTransaction((s, t) -> {
            for (Conta obj : objs) {

                if (obj.getDescricao() != null) {
                    obj.setDescricao(obj.getDescricao().toUpperCase());
                }
                //calcula saldo
                obj.setValorPago(calculaValorPago(obj));

                double valorTotal = 0d;
                for (Parcela parcela : obj.getParcelas()) {
                    valorTotal += parcela.getValor();
                    parcela.setConta(obj);
                    for (ParcelaPagamento pagamento : parcela.getPagamentos()) {
                        pagamento.setParcela(parcela);
                    }
                    parcela.setFechado(ParcelaService.valorTotalParcela(parcela) >= parcela.getValor());
                }
                obj.setValorTotal(valorTotal);
                if (obj.getId() > 0) {
                    s.merge(obj);
                } else {
                    obj.setDataLancamento(new Date());
                    s.save(obj);
                }

            }
            t.commit();
        });
    }

    public Conta findConta(int id) throws ServiceException {
        return (Conta) selectOnSession((s) -> {
            Conta c = (Conta) s.createQuery(" select p"
                    + " from Conta p  "
                    + " left outer join fetch p.parcelas e"
                    //                    + " left outer join fetch e.pagamentos"
                    + " where p.id = :p"
            )
                    .setInteger("p", id)
                    .uniqueResult();
//            Conta c = (Conta)s.load(Conta.class,id);
            for (Parcela parcela : c.getParcelas()) {
                List<ParcelaPagamento> p = parcela.getPagamentos();
                for (ParcelaPagamento p1 : p) {
                    break;
                }

            }
            return c;
        });
    }

    public List<Conta> findContas(String filtro, boolean aPagar, boolean aReceber) throws ServiceException {
        return (List<Conta>) selectOnSession((Session s) -> {
            boolean isNumber = Utils.isNumber(filtro);

            String hql = " select p"
                    + " from Conta p  "
                    + " left outer join fetch p.parcelas e";
            List<String> w = new ArrayList<>();

            List<ContaCategoria.TipoCategoria> in = new ArrayList<>();
            if (aPagar) {
                in.add(ContaCategoria.TipoCategoria.saida);
            }
            if (aReceber) {
                in.add(ContaCategoria.TipoCategoria.entrada);
            }

            if (isNumber) {
                w.add(" p.id = :n");
            }
            w.add("p.descricao like :d");
            w.add("p.pessoa.nome like :d");
            if (w.size() > 0) {
                hql += " where (" + String.join(" OR ", w) + ") and p.categoria.tipo" + (in.size() > 0 ? " in (:t)" : "");
            }
            Query q = s.createQuery(hql + " order by p.id");
            if (isNumber) {
                q.setInteger("n", Integer.parseInt(filtro));
            }
            if (in.size() > 0) {
                q.setParameterList("t", in);
            }
            q.setString("d", "%" + filtro + "%");
            q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); // Yes, really!  

            return q.list();

        });
    }

    public String statusParcela(Conta conta) {
        Date agora = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, +7);
        Date venceEssaSemana = c.getTime();
        for (Parcela parcela : conta.getParcelas()) {
            if (!parcela.isFechado() && parcela.getDataLancamento().before(agora)) {
                return "PARCELA ATRASADA";
            } else if (!parcela.isFechado() && parcela.getDataLancamento().before(venceEssaSemana)) {
                return "VENCE ESTÁ SEMANA";
            } else if (!parcela.isFechado()) {
                return "EM DIA";
            }

        }
        return "FINALIZADA";
    }

    /**
     * Seleciona as compras e vendas a prazo e a vista do periodo passado
     *
     * @param monthFirstDate
     * @param monthEndDate
     * @return
     */
    public List<ComprasVendas> comprasMes(Date monthFirstDate, Date monthEndDate) {
        return (List<ComprasVendas>) selectOnSession((s) -> {
            List<ComprasVendas> l = s.createQuery("select "
                    + "new model.queryresults.ComprasVendas("
                    + " cast(dataLancamento as date), "
                    + "sum(case when (conta.tipo =  :contacompra) then valorTotal else 0 end),"
                    //+ "sum(case when (conta.tipo =  :contacompra and conta.formaPagamento.id = :formaavista) then valorTotal else 0 end),"
                    + "sum(case when (conta.tipo =  :contavenda ) then valorTotal else 0 end)"
                    //+ "sum(case when (conta.tipo =  :contavenda and conta.formaPagamento.id = :formaavista) then valorTotal else 0 end)"
                    + ")"
                    + " from Conta conta"
                    + " where dataLancamento  between :inicio and :fim "
                    + " group by cast(dataLancamento as date) order by cast(dataLancamento as date)")
                    //                    .setParameter("tiposaida", ContaCategoria.TipoCategoria.saida)
                    .setDate("inicio", monthFirstDate)
                    .setDate("fim", monthEndDate)
                    .setParameter("contavenda", Conta.ContaTipo.ordem)
                    .setParameter("contacompra", Conta.ContaTipo.estoque)
                    //                    .setParameter("formaavista", formaAvista)
                    .list();
            //Seleciona os pagamentos a vista
            List<ParcelaPagamento> pagamento = s.createQuery("from ParcelaPagamento where aVista = true and data  between :inicio and :fim and (parcela.conta.tipo = :contacompra or parcela.conta.tipo = :contavenda) ")
                    .setDate("inicio", monthFirstDate)
                    .setDate("fim", monthEndDate)
                    
                    .setParameter("contavenda", Conta.ContaTipo.ordem)
                    .setParameter("contacompra", Conta.ContaTipo.estoque)
                    .list();
            SimpleDateFormat f1 = new SimpleDateFormat("dMy");

            Calendar ci = Calendar.getInstance();
            ci.setTime(monthFirstDate);
            //Adiciona as datas sem valores
            SimpleDateFormat f = new SimpleDateFormat("dM");
            for (; ci.getTime().compareTo(monthEndDate) <= 0; ci.add(Calendar.DATE, 1)) {
                if (!l.stream().anyMatch((c) -> f.format(c.getData()).equals(f.format(ci.getTime())))) {
                    l.add(new ComprasVendas(ci.getTime(), 0d, 0d));
                }
            }
            //Reordena
            l.sort((c1, c2) -> c1.getData().compareTo(c2.getData()));

            for (ParcelaPagamento pagamento1 : pagamento) {
                for (ComprasVendas cv : l) {
                    if (f1.format(pagamento1.getParcela().getDataLancamento()).equals(f1.format(cv.getData()))) {
                        if (pagamento1.getContaCategoria().getTipo() == ContaCategoria.TipoCategoria.saida) {
                            cv.setComprasPrazo(cv.getComprasPrazo() - pagamento1.getValor());
                            for (ComprasVendas cv2 : l) {
                                if (f1.format(cv2.getData()).equals(f1.format(pagamento1.getData()))) {
                                    cv2.setComprasAVista(cv2.getComprasAVista() + pagamento1.getValor());
                                    break;
                                }

                            }

                        } else {
                            cv.setVendasPrazo(cv.getVendasPrazo() - pagamento1.getValor());
                            for (ComprasVendas cv2 : l) {
                                if (f1.format(cv2.getData()).equals(f1.format(pagamento1.getData()))) {
                                    cv2.setVendasAVista(cv2.getVendasAVista() + pagamento1.getValor());
                                    break;
                                }

                            }

                        }
                        break;
                    }

                }
            }
            return l;

        });
        /*
         select 
         data_lancamento, 
         sum(case when (fc_conta_categoria.tipo = 1) then valortotal else 0 end) as compravista,
         sum(case when (fc_conta_categoria.tipo = 0) then valortotal else 0 end) as vendavista
         from fc_conta
         inner join fc_conta_categoria on fc_conta.conta_categoria_id = fc_conta_categoria.id
         group by data_lancamento
         */
    }

    /**
     * Consulta a categoria e valores do período
     *
     * @param monthFirstDate
     * @param monthEndDate
     * @param tipo
     * @return
     */
    public List<SomaCategoria> valorCategoria(Date monthFirstDate, Date monthEndDate, ContaCategoria.TipoCategoria tipo) {
        return (List<SomaCategoria>) selectOnSession((s) -> {
            return s.createQuery("select "
                    + "new model.queryresults.SomaCategoria(sum(conta.valorTotal),conta.categoria.nome) "
                    + "from Conta conta "
                    + " where dataLancamento  between :inicio and :fim"
                    + " and conta.categoria.tipo = :tipo"
                    + " group by conta.categoria.nome")
                    .setDate("inicio", monthFirstDate)
                    .setDate("fim", monthEndDate)
                    .setParameter("tipo", tipo)
                    .list();
        });
    }

    private double valorPagamentosPeriodo(Session s, ContaCategoria.TipoCategoria tipo, Date monthFirstDate, Date monthEndDate) {
        Object d = s.createQuery("select "
                + " sum(valor) "
                + "from ParcelaPagamento parcelaPagamento "
                + " where data  between :inicio and :fim"
                + " and parcelaPagamento.contaCategoria.tipo = :tipo "
        )
                .setDate("inicio", monthFirstDate)
                .setDate("fim", monthEndDate)
                .setParameter("tipo", tipo)
                .uniqueResult();
        if (d != null) {
            return (Double) d;
        } else {
            return 0D;
        }
    }

    private double valorPagamentosPeriodo(Session s, ContaCategoria.TipoCategoria tipo, Date monthFirstDate, Date monthEndDate, Conta.ContaTipo tipoDeConta) {
        Object d = s.createQuery("select "
                + " sum(valor) "
                + "from ParcelaPagamento parcelaPagamento "
                + " where data  between :inicio and :fim"
                + " and parcelaPagamento.contaCategoria.tipo = :tipo "
                + " and parcelaPagamento.parcela.conta.tipo = :tipoConta "
        )
                .setDate("inicio", monthFirstDate)
                .setDate("fim", monthEndDate)
                .setParameter("tipo", tipo)
                .setParameter("tipoConta", tipoDeConta)
                .uniqueResult();
        if (d != null) {
            return (Double) d;
        } else {
            return 0D;
        }
    }

    private double valorPagamentosPeriodoAPrazo(Session s, ContaCategoria.TipoCategoria tipo, Date monthFirstDate, Date monthEndDate, Conta.ContaTipo tipoConta) {
        return valorPagamentosPeriodoAPrazoAVista(s, tipo, monthFirstDate, monthEndDate, tipoConta, false);
    }

    private double valorPagamentosPeriodoAPrazoAVista(Session s, ContaCategoria.TipoCategoria tipo, Date monthFirstDate, Date monthEndDate, Conta.ContaTipo tipoConta, boolean avista) {
        //int formaAvista = Integer.valueOf(Parametros.getInstance().getValue("forma_pagamento_a_vista"));

        Object o = s.createQuery("select "
                + " sum(valor) "
                + "from ParcelaPagamento parcelaPagamento "
                + " where data  between :inicio and :fim"
                + " and parcelaPagamento.contaCategoria.tipo = :tipo "
                //   + " and parcelaPagamento.parcela.conta.formaPagamento.id " + (avista ? "=" : "<>") + " :avista "
                + " and parcelaPagamento.parcela.conta.tipo = :tipoConta"
                + " and  parcelaPagamento.aVista = :avista"
        )
                .setDate("inicio", monthFirstDate)
                .setDate("fim", monthEndDate)
                .setParameter("tipo", tipo)
                .setParameter("avista", avista)
                .setParameter("tipoConta", tipoConta)
                .uniqueResult();
        if (o != null) {
            return (Double) o;
        } else {
            return 0d;
        }
    }

    private double valorPagamentosPeriodoAVista(Session s, ContaCategoria.TipoCategoria tipo, Date monthFirstDate, Date monthEndDate, Conta.ContaTipo tipoConta) {
        return valorPagamentosPeriodoAPrazoAVista(s, tipo, monthFirstDate, monthEndDate, tipoConta, true);

    }

    /**
     * Consulta os resultados do período, utilizados nos relatórios
     *
     * @param monthFirstDate
     * @param monthEndDate
     * @return
     */
    public List<SomaCategoria> resultadoPeriodo(Date monthFirstDate, Date monthEndDate) {
        return (List<SomaCategoria>) selectOnSession((s) -> {
            List<SomaCategoria> l = new ArrayList<>();

            l.add(new SomaCategoria(valorPagamentosPeriodoAVista(s, ContaCategoria.TipoCategoria.entrada, monthFirstDate, monthEndDate, Conta.ContaTipo.ordem), "RECEBIMENTO DE VENDAS À VISTA"));

            l.add(new SomaCategoria(valorPagamentosPeriodoAPrazo(s, ContaCategoria.TipoCategoria.entrada, monthFirstDate, monthEndDate, Conta.ContaTipo.ordem), "RECEBIMENTOS DE VENDA A PRAZO"));

            l.add(new SomaCategoria(valorPagamentosPeriodo(s, ContaCategoria.TipoCategoria.entrada, monthFirstDate, monthEndDate, Conta.ContaTipo.conta), "OUTRAS RECEITAS"));

            l.add(new SomaCategoria(valorPagamentosPeriodo(s, ContaCategoria.TipoCategoria.entrada, monthFirstDate, monthEndDate), "TOTAL DE RECEBIMENTOS"));

            //Separador
            l.add(new SomaCategoria(0d, ""));

            l.add(new SomaCategoria(valorPagamentosPeriodoAVista(s, ContaCategoria.TipoCategoria.saida, monthFirstDate, monthEndDate, Conta.ContaTipo.estoque), "PAGAMENTOS DE COMPRAS À VISTA"));

            l.add(new SomaCategoria(valorPagamentosPeriodoAPrazo(s, ContaCategoria.TipoCategoria.saida, monthFirstDate, monthEndDate, Conta.ContaTipo.estoque), "PAGAMENTO EFETUADOS A FORNECEDORES"));

            l.add(new SomaCategoria(valorPagamentosPeriodo(s, ContaCategoria.TipoCategoria.saida, monthFirstDate, monthEndDate, Conta.ContaTipo.conta), "OUTRAS DESPESAS"));

            l.add(new SomaCategoria(valorPagamentosPeriodo(s, ContaCategoria.TipoCategoria.saida, monthFirstDate, monthEndDate), "TOTAL DE CUSTOS/DESPESAS"));

            //Separador
            l.add(new SomaCategoria(0d, ""));

            //Saldo anterior
            l.add(new SomaCategoria(new ContaBancariaService().saldoGeral(monthFirstDate), "SALDO ANTERIOR"));

            //Saldo Período
            l.add(new SomaCategoria(new ContaBancariaService().saldoGeral(monthEndDate), "SALDO PERÍODO"));

            return l;

        });
    }

    public List<LivroCaixa> livroCaixa(Date start, Date end, ContaBancaria caixa) {

        return (List<LivroCaixa>) selectOnSession((s) -> {
            List<LivroCaixa> l = s.createQuery("select "
                    + "new model.queryresults.LivroCaixa("
                    + " data,"
                    + " parcelaPagamento.contaCategoria.nome,"
                    + " parcela.conta.descricao,"
                    + " case when contaCategoria.tipo = :tipoentrada then valor else 0.0 end, "
                    + " case when contaCategoria.tipo <> :tipoentrada then valor else 0.0 end, "
                    + " parcela.parcela "
                    + ") "
                    + " from ParcelaPagamento parcelaPagamento "
                    + " where data  between :inicio and :fim"
                    + " and parcelaPagamento.contaBancaria.id = :contaid"
                    + " order by data")
                    .setDate("inicio", start)
                    .setDate("fim", end)
                    .setParameter("contaid", caixa.getId())
                    .setParameter("tipoentrada", ContaCategoria.TipoCategoria.entrada)
                    .list();
            int i = 0;
            for (LivroCaixa li : l) {
                li.setNumero(++i);
//                li.setDescricao("PARCELA " + li.getParcela() + " " + li.getDescricao());
            }
            return l;

        });
    }

    /**
     * Selecionar movimentos de bancarios
     *
     * @param start
     * @param end
     * @return
     */
    public List<MovimentoBancario> movimentosBancarios(Date start, Date end) {
        List<MovimentoBancario> r = (List<MovimentoBancario>) selectOnSession((s) -> {
            Query q = s.createQuery("select "
                    + "new model.queryresults.MovimentoBancario("
                    + "parcelaPagamento.data,"
                    + "parcelaPagamento.contaBancaria.nome,"
                    + "case when parcelaPagamento.contaCategoria.tipo = :tipoentrada then valor else 0 end,"
                    + "case when parcelaPagamento.contaCategoria.tipo <> :tipoentrada then valor else 0 end,"
                    + "parcelaPagamento.parcela.conta.descricao"
                    + ") from ParcelaPagamento parcelaPagamento where parcelaPagamento.data between :start and :end")
                    .setDate("start", start)
                    .setDate("end", end)
                    .setParameter("tipoentrada", ContaCategoria.TipoCategoria.entrada);
            return q.list();
        });
        int i = 0;
        for (MovimentoBancario r1 : r) {
            r1.setNumero(++i);
        }
        return r;

    }
}
