/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.ordem;

import java.io.Serializable;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import model.ordem.Cor;
import services.Service;
import services.ServiceException;

/**
 *
 * @author charles
 */
public class CorService extends Service<Cor> {

    public CorService() {
        super(Cor.class);
    }


    @Override
    public void update(Cor obj) throws ServiceException {
   
        obj.setNome(obj.getNome().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Cor obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

}
