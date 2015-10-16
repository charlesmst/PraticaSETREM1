/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.estoque;

import services.aul.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.estoque.Prateleira;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.spi.ServiceException;
import services.Service;


public class PrateleiraService extends Service<Prateleira> {


    
    public PrateleiraService() {
        super(Prateleira.class);
    }
//    public Collection<Prateleira> marcasAtivas() {
//        
//        try {
//            return findFilter(Restrictions.eq("ativo", true));
//        } catch (services.ServiceException ex) {
//            Logger.getLogger(PrateleiraService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
}
