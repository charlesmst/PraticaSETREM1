/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import forms.FrmMain;

/**
 *
 * @author Charles
 */
public class Forms {
    /**
     * Mostra mensagem na tela
     */
    public static void mensagem(String mensagem, AlertaTipos tipo){
        FrmMain.getInstance().alert(mensagem);
        FrmMain.getInstance().setIcone(tipo);
    }

    public static void iniciaProgress() {
        FrmMain.getInstance().iniciaProgress();
    }
    
    public static void paraProgress() {
        FrmMain.getInstance().paraProgress();
    }
}
