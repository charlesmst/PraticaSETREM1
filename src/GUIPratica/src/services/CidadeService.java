/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import model.Cidade;

/**
 *
 * @author Charles
 */
public class CidadeService extends Service<Cidade> {

    public CidadeService() {
        super(Cidade.class);
    }

    @Override
    public void update(Cidade obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        obj.setUf(obj.getUf().substring(0, 2).toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Cidade obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        obj.setUf(obj.getUf().substring(0, 2).toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }
}
