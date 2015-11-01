/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.ordem;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import model.ordem.Veiculo;
import services.Service;
import services.ServiceException;

/**
 *
 * @author charles
 */
public class VeiculoService extends Service<Veiculo> {

    public VeiculoService() {
        super(Veiculo.class);
    }

    public void validaPlaca(Veiculo v) throws ServiceException {
        if (v.getPlaca() == null || v.getPlaca().equals("")) {
            return;
        }
        v.setPlaca(v.getPlaca().toUpperCase());
        //Valida se tem 3 letras e 4 números
        if (!v.getPlaca().matches("^\\w{3}\\d{4}$")) {
            throw new ServiceException("Placa está em formato incorreto", null);
        }
    }

    public void validaUnico(Veiculo v) throws ServiceException {

        v.setPlaca(v.getPlaca().toUpperCase());
        List<Veiculo> l = findBy("placa", v.getPlaca());
        for (Veiculo veiculo : l) {
            if (veiculo.getId() != v.getId() && veiculo.getPlaca().equals(v.getPlaca())) {
                throw  new ServiceException("Placa deve ser única", null);
            }
        }
    }

    @Override
    public void update(Veiculo obj) throws ServiceException {
        validaPlaca(obj);
        obj.setPlaca(obj.getPlaca().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Veiculo obj) throws ServiceException {
        validaPlaca(obj);
        obj.setPlaca(obj.getPlaca().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

}
