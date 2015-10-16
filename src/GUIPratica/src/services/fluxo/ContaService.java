/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.fluxo;

import java.io.Serializable;
import model.fluxo.Conta;
import services.Service;
import services.ServiceException;

/**
 *
 * @author Charles
 */
public class ContaService extends Service<Conta> {

    

    public ContaService() {
        super(Conta.class);
    }

    @Override
    public void update(Conta obj) throws ServiceException {
        obj.setDescricao(obj.getDescricao().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Conta obj) throws ServiceException {
        obj.setDescricao(obj.getDescricao().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

}
