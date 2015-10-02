/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Charles
 */
public interface JRepositoryModelListener<T> {
    abstract void fieldChanged(int rowIndex, int columnIndex, T object);
    abstract void commit();
    abstract void roolback();
    abstract T requestNew();
    public Map<String,String> getColumnModel(String columnProp);
    public boolean isColumnModel(String columnProp);

}
