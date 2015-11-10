/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.estoque;

import java.util.List;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
import model.estoque.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import services.Service;

public class EstoqueMovimentacaoService extends Service<EstoqueMovimentacao> {

//    @Override
//    public void update(EstoqueMovimentacao obj) throws services.ServiceException {
//        obj.setDescricao(obj.getDescricao().toUpperCase());
//        super.update(obj); 
//    }
//
    public void insertPersonalizado(EstoqueMovimentacao estMov, Estoque est, Item i) throws services.ServiceException {
        Session s = getSession();
        synchronized (s) {
            Transaction t = s.beginTransaction();
            try {
                s.save(estMov);
                s.merge(est);
                s.merge(i);
                t.commit();

            } catch (Exception e) {
                t.rollback();
                throw new services.ServiceException("Erro ao inserir " + classRef.getSimpleName(), e, logger);
            } finally {
                s.close();
            }
        }
    }
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
