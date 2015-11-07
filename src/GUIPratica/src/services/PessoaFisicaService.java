/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import model.PessoaFisica;

/**
 *
 * @author Gustavo
 */
public class PessoaFisicaService extends Service<PessoaFisica> {

    public void insert(PessoaFisica pF) throws services.ServiceException {
        executeOnTransaction((s, t) -> {
                if (pF.getPessoa().getId() > 0) {
                    new PessoaService().insert(pF.getPessoa());
                    s.merge(pF);
                } else {
                    new PessoaService().insert(pF.getPessoa());
                    s.save(pF);
                }
            t.commit();
        });
    }
    
    public PessoaFisicaService() {
        super(PessoaFisica.class);
    }
}
