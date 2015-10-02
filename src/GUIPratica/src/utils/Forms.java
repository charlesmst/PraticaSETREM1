/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import forms.frmMain;

/**
 *
 * @author Charles
 */
public class Forms {
    /**
     * Mostra mensagem na tela
     */
    public static void mensagem(String mensagem, AlertaTipos tipo){
        frmMain.getInstance().alert(mensagem);
        frmMain.getInstance().setIcone(tipo);
    }

    public static void iniciaProgress() {
        frmMain.getInstance().iniciaProgress();
    }
    
    public static void paraProgress() {
        frmMain.getInstance().paraProgress();
    }
}
