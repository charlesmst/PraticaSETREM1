/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.ordem;

import model.fluxo.Conta;
import model.fluxo.Parcela;
import model.ordem.OrdemServico;
import services.Service;
import services.ServiceException;

/**
 *
 * @author charles
 */
public class OrdemServicoService extends Service<OrdemServico> {

    public OrdemServicoService() {
        super(OrdemServico.class);
    }

}
