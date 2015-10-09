/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.aul;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.aula.Marca;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.spi.ServiceException;
import services.Service;


public class MarcaService extends Service<Marca> {


    
    public MarcaService() {
        super(Marca.class);
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
