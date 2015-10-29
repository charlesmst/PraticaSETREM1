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
import model.fluxo.Parcela;
import model.fluxo.ParcelaPagamento;
import org.hibernate.Query;
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
                .map((parcela) -> parcela.getValor() - ParcelaService.valorTotalParcela(parcela))
                .reduce(valor, (accumulator, _item) -> accumulator + _item);
        return valor;
//        return  c.getParcelas().stream()
//                .map((pagamento) -> pagamento.getValor())
//                .reduce(0, (accumulator, _item) -> accumulator + _item);
    }

    @Override
    public void update(Conta obj) throws ServiceException {
        this.insert(obj);
    }

    @Override
    public void insert(Conta obj) throws ServiceException {
        if (obj.getDescricao() != null) {
            obj.setDescricao(obj.getDescricao().toUpperCase());
        }
        executeOnTransaction((s, t) -> {

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
//
//            for (Parcela parcela : obj.getParcelas()) {
//                parcela.setConta(obj);
//
//                    s.saveOrUpdate(parcela);
//
//                for (ParcelaPagamento pagamento : parcela.getPagamentos()) {
//                    pagamento.setParcela(parcela);
//                    
//                    
//                    s.saveOrUpdate(pagamento);
//                }
//            }
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

    public List<Conta> findContas(String filtro) throws ServiceException {
        return (List<Conta>) selectOnSession((s) -> {
            boolean isNumber = Utils.isNumber(filtro);

            String hql = " select p"
                    + " from Conta p  "
                    + " left outer join fetch p.parcelas e";
            List<String> w = new ArrayList<>();

            if (isNumber) {
                w.add(" p.id = :n");
            }
            w.add("p.descricao like :d");
            if (w.size() > 0) {
                hql += " where " + String.join(" OR ", w);
            }
            Query q = s.createQuery(hql +" order by conta.id");
            if (isNumber) {
                q.setInteger("n", Integer.parseInt(filtro));
            }

            q.setString("d", "%" + filtro + "%");
            return q.list();

        });
    }

}
