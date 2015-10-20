/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.fluxo;

import java.io.Serializable;
import model.fluxo.Conta;
import model.fluxo.Parcela;
import model.fluxo.ParcelaPagamento;
import services.Service;
import services.ServiceException;

/**
 *
 * @author Charles
 */
public class ContaService extends Service<Conta> {

    public ContaService() {
        super(Conta.class);
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

}
