/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import model.Pessoa;
import model.PessoaJuridica;

/**
 *
 * @author Gustavo
 */
public class PessoaService extends Service<Pessoa> {

    @Override
    public void update(Pessoa obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.update(obj);
    }

    public void insert(Pessoa p) throws services.ServiceException {
        executeOnTransaction((s, t) -> {
            if (p.getId() > 0) {
                s.merge(p);
            } else {
                s.save(p);
            }
            t.commit();
        });
    }

    public PessoaService() {
        super(Pessoa.class);
    }
}
