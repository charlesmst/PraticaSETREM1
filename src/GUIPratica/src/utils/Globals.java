/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.swing.ImageIcon;
import model.Usuario;

/**
 *
 * @author Charles
 */
public class Globals {

    public static Usuario.Tipo nivel = Usuario.Tipo.gestor;
    public static ImageIcon iconeSuccess;
    public static ImageIcon iconeError;
        public static ImageIcon iconeWarning;


    static {
        iconeSuccess = new ImageIcon(Globals.class.getClassLoader().getResource("resources/success.png"));
        iconeError = new ImageIcon(Globals.class.getClassLoader().getResource("resources/erro.png"));
        iconeWarning = new ImageIcon(Globals.class.getClassLoader().getResource("resources/warning.png"));

    }
}
