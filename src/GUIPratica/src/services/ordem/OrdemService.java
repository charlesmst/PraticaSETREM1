/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.ordem;

import java.util.List;
import java.util.Set;
import model.estoque.EstoqueMovimentacao;
import model.fluxo.Conta;
import model.fluxo.Parcela;
import model.ordem.Ordem;
import model.ordem.OrdemServico;
import services.Service;
import services.ServiceException;

/**
 *
 * @author charles
 */
public class OrdemService extends Service<Ordem> {

    @Override
    public void update(Ordem obj) throws ServiceException {
        insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Ordem obj) throws ServiceException {
        executeOnTransaction((s, t) -> {

            if (obj.getDescricao() != null) {
                obj.setDescricao(obj.getDescricao().toUpperCase());
            }
            for (EstoqueMovimentacao estMov : obj.getEstoqueMovimentacaos()) {
                estMov.setConta(obj.getConta());
            }
            if (obj.getId() == 0) {
                s.save(obj);
            } else {
                s.merge(obj);
            }
//            obj.setEstoqueMovimentacaos(estoques);
            t.commit();
        });

    }

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

    public double valorTotal(Ordem obj) {
        double v = 0;
        for (EstoqueMovimentacao estoqueMovimentacao : obj.getEstoqueMovimentacaos()) {
            v += estoqueMovimentacao.getValorUnitarioVenda() * estoqueMovimentacao.getQuantidade();//@Todo Pegar valor de estoque;
        }
        for (OrdemServico ordemServico : obj.getOrdemServicos()) {
            v += ordemServico.getValorEntrada() * ordemServico.getQuantidade();
        }
        return v;
    }

    public void refreshCollection(Ordem ordem) {
        Ordem o = findOrdem(ordem.getId());
        ordem.setEstoqueMovimentacaos(o.getEstoqueMovimentacaos());
        ordem.setOrdemServicos(o.getOrdemServicos());
    }

    public List<Ordem> findAbertos() {
        return (List<Ordem>) selectOnSession((s) -> s.createQuery("from Ordem o where o.ordemStatus.finaliza = :f").setBoolean("f", false).list());
    }
}
