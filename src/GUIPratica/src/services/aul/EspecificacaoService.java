/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.aul;


import model.aula.Especificacao;
import services.Service;


public class EspecificacaoService extends Service<Especificacao> {


    
    public EspecificacaoService() {
        super(Especificacao.class);
    }
//    public Collection<Marca> marcasAtivas() {
//        
//        try {
//            return findFilter(Restrictions.eq("ativo", true));
//        } catch (services.ServiceException ex) {
//            Logger.getLogger(MarcaService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
}
