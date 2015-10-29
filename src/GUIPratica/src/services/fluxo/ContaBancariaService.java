/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.fluxo;

import java.io.Serializable;
import java.util.List;
import model.fluxo.ContaBancaria;
import services.Service;
import services.ServiceException;

/**
 *
 * @author Charles
 */
public class ContaBancariaService extends Service<ContaBancaria> {

    @Override
    public void delete(Serializable key) throws ServiceException {
        //Faz update pra inativo
        ContaBancaria c = findById(key);
        c.setAtivo(false);
        
        update(c); //To change body of generated methods, choose Tools | Templates.
    }

    public ContaBancariaService() {
        super(ContaBancaria.class);
    }

    @Override
    public void update(ContaBancaria obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(ContaBancaria obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ContaBancaria> findAtivos(){
        return findBy("ativo", true);
    }
}
