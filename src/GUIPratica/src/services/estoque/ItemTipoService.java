package services.estoque;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.estoque.ItemTipo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.spi.ServiceException;
import services.Service;

public class ItemTipoService extends Service<ItemTipo> {

    @Override
    public void update(ItemTipo obj) throws services.ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(ItemTipo obj) throws services.ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean unico(int id, String nome) throws ServiceException {
        return findFilter(Restrictions.ne("id", id), Restrictions.eq("nome", nome)).isEmpty();
    }

    @Override
    public void delete(Serializable key) throws ServiceException {
        //Faz update pra inativo
        ItemTipo it = findById(key);
        it.setAtivo(false);

        update(it); //To change body of generated methods, choose Tools | Templates.
    }

    public ItemTipoService() {
        super(ItemTipo.class);
    }
}
