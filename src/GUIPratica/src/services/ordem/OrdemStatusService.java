/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.ordem;

import java.io.Serializable;
import java.util.List;
import model.ordem.OrdemStatus;
import services.Service;
import services.ServiceException;

/**
 *
 * @author charles
 */
public class OrdemStatusService extends Service<OrdemStatus> {

    public OrdemStatusService() {
        super(OrdemStatus.class);
    }

    public List<OrdemStatus> findAtivos(){
        return findBy("ativo", true);
    }
    @Override
    public void update(OrdemStatus obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(OrdemStatus obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void delete(Serializable key) throws ServiceException {
        //Faz update pra inativo
        OrdemStatus c = findById(key);
        c.setAtivo(false);

        update(c); //To change body of generated methods, choose Tools | Templates.
    }
}
