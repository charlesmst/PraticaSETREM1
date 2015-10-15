/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.aul;

import java.io.Serializable;
import java.util.List;
import model.aula.EspecificacaoProduto;
import model.aula.Produto;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import services.Service;
import services.ServiceException;

public class ProdutoService extends Service<Produto> {

//    @Override
//    public void delete(Serializable key) throws ServiceException {
//        this.executeOnTransaction((s, t) -> {
//
//            Query q = s.createQuery("delete from EspecificacaoProduto where produto.id = ?");
//            q.setInteger(0, Integer.valueOf(key.toString()));
//            q.executeUpdate();
//            Object persistentInstance = s.load(classRef, key);
//            s.delete(persistentInstance);
//            t.commit();
//        });
//
//    }
    public ProdutoService() {
        super(Produto.class);
    }

    public Produto findProduto(int id) throws ServiceException {
        return (Produto) selectOnSession((s) -> {
            return s.createQuery(" select p"
                    + " from Produto p  "
                    + " left outer join fetch p.especificacoes e"
                    + " where p.id = :p"
            )
                    .setInteger("p", id)
                    .uniqueResult();
        });
    }
//
//    public void insertOrUpdateProduto(Produto p, List<EspecificacaoProduto> especificacoes) throws ServiceException {
//
//        this.executeOnTransaction((s, t) -> {
//            s.saveOrUpdate(p);
//
//            Query q = s.createQuery("delete from EspecificacaoProduto where produto.id = ?");
//            q.setInteger(0, p.getId());
//            q.executeUpdate();
//            for (EspecificacaoProduto especificacoe : especificacoes) {
//                especificacoe.setProduto(p);
//                s.save(especificacoe);
//            }
//
//            t.commit();
//        });
//
//    }
//
    public List<EspecificacaoProduto> getEspecificacoes(Produto p) throws ServiceException {
        return (List<EspecificacaoProduto>) selectOnSession((s) -> {
            Query query = s.createQuery("from EspecificacaoProduto where produto.id = ?");
            query.setInteger(0, p.getId());
            return query.list();
        });
    }
//    public Collection<Marca> marcasAtivas() {
//        
//        try {
//            return findFilter(Restrictions.eq("ativo", true));
//        } catch (services.ServiceException ex) {
//            Logger.getLogger(MarcaService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
}
