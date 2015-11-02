/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.ordem;

import model.fluxo.Conta;
import model.fluxo.Parcela;
import model.ordem.Ordem;
import services.Service;
import services.ServiceException;

/**
 *
 * @author charles
 */
public class OrdemService extends Service<Ordem> {

    public OrdemService() {
        super(Ordem.class);
    }

    public Ordem findOrdem(int id) {
        return (Ordem) selectOnSession((s) -> {
            Ordem c = (Ordem) s.createQuery(" select p"
                    + " from Ordem p  "
                    + " left outer join fetch p.ordemServicos e "
                    + " left outer join fetch p.estoqueMovimentacaos s "
                    //                    + " left outer join fetch e.pagamentos"
                    + " where p.id = :p"
            )
                    .setInteger("p", id)
                    .uniqueResult();
            return c;
        });
    }
}
