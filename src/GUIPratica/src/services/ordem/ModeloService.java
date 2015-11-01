/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.ordem;

import java.io.Serializable;
import model.ordem.Modelo;
import services.Service;
import services.ServiceException;

/**
 *
 * @author charles
 */
public class ModeloService extends Service<Modelo> {

    public ModeloService() {
        super(Modelo.class);
    }

    @Override
    public void update(Modelo obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Modelo obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
}
