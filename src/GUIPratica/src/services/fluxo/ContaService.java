/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.fluxo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.fluxo.Conta;
import model.fluxo.ContaCategoria;
import model.fluxo.Parcela;
import model.fluxo.ParcelaPagamento;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import services.Service;
import services.ServiceException;
import utils.Utils;

/**
 *
 * @author Charles
 */
public class ContaService extends Service<Conta> {

    public ContaService() {
        super(Conta.class);
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

                for (Parcela parcela : obj.getParcelas()) {
                    parcela.setConta(obj);
                    for (ParcelaPagamento pagamento : parcela.getPagamentos()) {
                        pagamento.setParcela(parcela);
                    }
                    parcela.setFechado(ParcelaService.valorTotalParcela(parcela) >= parcela.getValor());
                }
                if (obj.getId() > 0) {
                    s.merge(obj);
                } else {
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

            for (Parcela parcela : c.getParcelas()) {
                parcela.getPagamentos();
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

}
