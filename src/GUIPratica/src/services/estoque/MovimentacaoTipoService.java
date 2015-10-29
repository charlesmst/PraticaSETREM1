/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.estoque;

import java.io.Serializable;
import model.estoque.MovimentacaoTipo;
import services.Service;
import services.ServiceException;

/**
 *
 * @author Gustavo
 */
public class MovimentacaoTipoService extends Service<MovimentacaoTipo> {

    @Override
    public void delete(Serializable key) throws ServiceException {
        //Faz update pra inativo
        MovimentacaoTipo mT = findById(key);
        mT.setAtivo(false);

        update(mT); //To change body of generated methods, choose Tools | Templates.
    }

    public MovimentacaoTipoService() {
        super(MovimentacaoTipo.class);
    }

    @Override
    public void update(MovimentacaoTipo obj) throws ServiceException {
        obj.setDescricao(obj.getDescricao().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(MovimentacaoTipo obj) throws ServiceException {
        obj.setDescricao(obj.getDescricao().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

}
