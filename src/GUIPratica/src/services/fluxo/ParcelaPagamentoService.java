/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.fluxo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.fluxo.Conta;
import model.fluxo.FormaPagamento;
import model.fluxo.ParcelaPagamento;
import services.Service;
import services.ServiceException;

/**
 *
 * @author Charles
 */
public class ParcelaPagamentoService extends Service<ParcelaPagamento> {


    public ParcelaPagamentoService() {
        super(ParcelaPagamento.class);
    }

    private void changeSituation(ParcelaPagamento p ){
    
    }
    @Override
    public void insert(ParcelaPagamento obj) throws ServiceException {
        
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

}
