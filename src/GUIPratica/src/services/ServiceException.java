/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.HibernateUtil;

/**
 *
 * @author Charles
 */
public class ServiceException extends RuntimeException{
    public ServiceException(String mensagem, Exception ex){
        super(mensagem);
        if(ex != null){
            HibernateUtil.defaultLogger.error(ex.getMessage());
        }
    }
    
    public ServiceException(String mensagem, Exception ex, Logger logger){
        super(mensagem);
        if(ex != null){
            logger.error(ex.getMessage());
        }
    }
    
    public ServiceException(String mensagem, Exception ex, Class classLogger){
        super(mensagem);
         if(ex != null){
             LogManager.getLogger(classLogger).error(ex.getMessage());
        }
    }
}
