/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import utils.AlertaTipos;

/**
 *
 * @author Charles
 */

public interface ThrowingCommand {

    default void action() {

        try {
            actionThrows();
        } catch (final Exception e) {
            utils.Forms.mensagem(e.getMessage(), AlertaTipos.erro);
        }
    }

    void actionThrows() throws Exception;
}
