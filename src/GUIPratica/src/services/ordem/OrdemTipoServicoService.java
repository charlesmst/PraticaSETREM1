/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.ordem;

import java.io.Serializable;
import model.ordem.OrdemTipoServico;
import services.Service;
import services.ServiceException;

/**
 *
 * @author charles
 */
public class OrdemTipoServicoService extends Service<OrdemTipoServico> {

    public OrdemTipoServicoService() {
        super(OrdemTipoServico.class);
    }

    @Override
    public void update(OrdemTipoServico obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(OrdemTipoServico obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Serializable key) throws ServiceException {
        //Faz update pra inativo
        OrdemTipoServico c = findById(key);
        c.setAtivo(false);

        update(c); //To change body of generated methods, choose Tools | Templates.
    }
}
