/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.estoque;

import services.aul.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.estoque.Prateleira;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.spi.ServiceException;
import services.Service;

public class PrateleiraService extends Service<Prateleira> {

    @Override
    public void update(Prateleira obj) throws services.ServiceException {
        obj.setDescricao(obj.getDescricao().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Prateleira obj) throws services.ServiceException {
        obj.setDescricao(obj.getDescricao().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean unico(int id, String descricao) throws ServiceException {
        return findFilter(Restrictions.ne("id", id), Restrictions.eq("descricao", descricao)).isEmpty();
    }

    public PrateleiraService() {
        super(Prateleira.class);
    }
//   
}
