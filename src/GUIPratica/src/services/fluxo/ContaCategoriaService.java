/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.fluxo;

import java.io.Serializable;
import java.util.List;
import model.fluxo.ContaBancaria;
import model.fluxo.ContaCategoria;
import org.hibernate.criterion.Restrictions;
import services.Service;
import services.ServiceException;

/**
 *
 * @author Charles
 */
public class ContaCategoriaService extends Service<ContaCategoria> {

    @Override
    public void delete(Serializable key) throws ServiceException {
        //Faz update pra inativo
        ContaCategoria c = findById(key);
        c.setAtivo(false);

        update(c); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ContaCategoria> findAtivos() {
        return findBy("ativo", true);
    }

    public ContaCategoriaService() {
        super(ContaCategoria.class);
    }

    @Override
    public void update(ContaCategoria obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(ContaCategoria obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean unico(int id, String nome,ContaCategoria.TipoCategoria tipo) throws ServiceException {
        return findFilter(Restrictions.ne("id", id), Restrictions.eq("nome", nome), Restrictions.eq("tipo", tipo)).isEmpty();
    }

}
