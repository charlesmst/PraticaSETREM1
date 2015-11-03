/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.estoque;

import model.estoque.Estoque;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.spi.ServiceException;
import services.Service;

public class EstoqueService extends Service<Estoque> {

//    @Override
//    public void update(Estoque obj) throws services.ServiceException {
//        obj.setDescricao(obj.getDescricao().toUpperCase());
//        super.update(obj); 
//    }
//
//    @Override
//    public void insert(Estoque obj) throws services.ServiceException {
//        obj.setDescricao(obj.getDescricao().toUpperCase());
//        super.insert(obj);
//    }
//
//    public boolean unico(int id, String descricao) throws ServiceException {
//        return findFilter(Restrictions.ne("id", id), Restrictions.eq("descricao", descricao)).isEmpty();
//    }

    public EstoqueService() {
        super(Estoque.class);
    }
}
