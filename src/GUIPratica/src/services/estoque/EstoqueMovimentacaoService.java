/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.estoque;

import java.util.List;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.spi.ServiceException;
import services.Service;

public class EstoqueMovimentacaoService extends Service<EstoqueMovimentacao> {

//    @Override
//    public void update(EstoqueMovimentacao obj) throws services.ServiceException {
//        obj.setDescricao(obj.getDescricao().toUpperCase());
//        super.update(obj); 
//    }
//
//    @Override
//    public void insert(EstoqueMovimentacao obj) throws services.ServiceException {
//        obj.setDescricao(obj.getDescricao().toUpperCase());
//        super.insert(obj);
//    }
//
//    public boolean unico(int id, String descricao) throws ServiceException {
//        return findFilter(Restrictions.ne("id", id), Restrictions.eq("descricao", descricao)).isEmpty();
//    }
    public List<EstoqueMovimentacao> buscaMovimentacoes(Estoque e) {
        List<EstoqueMovimentacao> estMov = findBy("estoque.id", e.getId());
        return estMov;
    }

    public EstoqueMovimentacaoService() {
        super(EstoqueMovimentacao.class);
    }
}
