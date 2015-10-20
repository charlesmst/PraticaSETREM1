/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import model.Pessoa;

/**
 *
 * @author Charles
 */
public class PessoaService extends Service<Pessoa> {

    @Override
    public void update(Pessoa obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());

        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Pessoa obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public PessoaService() {
        super(Pessoa.class);
    }
}
