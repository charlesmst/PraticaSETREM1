/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.util.function.Function;
import utils.AlertaTipos;

/**
 *
 * @author Charles
 */
public interface ThrowingFunction<T, U> extends Function<T, U> {

    @Override
    default U apply(T t) {
        try {
            return acceptThrows(t);
        } catch (final Exception e) {
            utils.Forms.mensagem(e.getMessage(), AlertaTipos.erro);
        }
        return null;
    }

    U acceptThrows(T elem) throws Exception;

}
