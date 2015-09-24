/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import java.util.Collection;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Charles
 */
public abstract class Repository<T> {
    public T findById(int id){
        throw new NotImplementedException();
    }
    public void insert(T obj){
        throw new NotImplementedException();
    }
    public void update(T obj){
        throw new NotImplementedException();
    }
    
    public void delete(int key){
        throw new NotImplementedException();
    }
    
    public Collection<T> getAll(){
        throw new NotImplementedException();
    }
    
    
    public Collection<T> getFilter(String filter){
        throw new NotImplementedException();
    }
}
