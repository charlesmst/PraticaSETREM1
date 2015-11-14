/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.ordem;

import java.io.Serializable;
import model.ordem.Marca;
import org.hibernate.criterion.Restrictions;
import services.Service;
import services.ServiceException;

/**
 *
 * @author charles
 */
public class MarcaService extends Service<Marca> {

    public MarcaService() {
        super(Marca.class);
    }

    @Override
    public void update(Marca obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Marca obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean unico(int id, String nome) throws ServiceException {
        return findFilter(Restrictions.ne("id", id), Restrictions.eq("nome", nome)).isEmpty();
    }

}
