/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import model.PessoaJuridica;

/**
 *
 * @author Gustavo
 */
public class PessoaJuridicaService extends Service<PessoaJuridica> {

    public PessoaJuridicaService() {
        super(PessoaJuridica.class);
    }
    
    public void insert(PessoaJuridica pJ) throws services.ServiceException {
        executeOnTransaction((s, t) -> {
                if (pJ.getPessoa().getId() > 0) {
                    new PessoaService().insert(pJ.getPessoa());
                    s.merge(pJ);
                } else {
                    new PessoaService().insert(pJ.getPessoa());
                    s.save(pJ);
                }
            t.commit();
        });
    }
}
