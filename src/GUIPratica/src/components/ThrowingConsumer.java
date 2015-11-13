/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.util.function.Consumer;
import utils.AlertaTipos;

@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {
    @Override
    default void accept(final T elem) {
        try {
            acceptThrows(elem);
        } catch (Exception e) {
            utils.Forms.mensagem(e.getMessage(), AlertaTipos.erro);
        }
    }

    void acceptThrows(T elem) throws Exception;

}
