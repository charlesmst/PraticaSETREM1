/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.fluxo;

import java.io.Serializable;
import model.fluxo.FormaPagamento;
import org.hibernate.criterion.Restrictions;
import services.Service;
import services.ServiceException;

/**
 *
 * @author Charles
 */
public class FormaPagamentoService extends Service<FormaPagamento> {

    @Override
    public void delete(Serializable key) throws ServiceException {
        //Faz update pra inativo
        FormaPagamento c = findById(key);
        c.setAtivo(false);

        update(c); //To change body of generated methods, choose Tools | Templates.
    }

    public FormaPagamentoService() {
        super(FormaPagamento.class);
    }

    @Override
    public void update(FormaPagamento obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(FormaPagamento obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean unico(int id, String nome) throws ServiceException {
        return findFilter(Restrictions.ne("id", id), Restrictions.eq("nome", nome)).isEmpty();
    }
}
