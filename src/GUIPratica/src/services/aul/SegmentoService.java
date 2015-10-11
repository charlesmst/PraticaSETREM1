/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.aul;


import model.aula.Segmento;
import services.Service;


public class SegmentoService extends Service<Segmento> {


    
    public SegmentoService() {
        super(Segmento.class);
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
