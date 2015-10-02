/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author Charles
 */
public class ServiceException extends Exception{
    public ServiceException(String mensagem, Exception ex){
        super(mensagem);
    }
}
