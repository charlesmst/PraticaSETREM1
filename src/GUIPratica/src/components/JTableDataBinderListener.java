/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.util.Collection;

/**
 *
 * @author Charles
 */
public interface JTableDataBinderListener<T> {
    Collection<T> lista(String busca);
    Object[] addRow(T dado );
}
