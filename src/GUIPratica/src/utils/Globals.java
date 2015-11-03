/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import model.Usuario;

/**
 *
 * @author Charles
 */
public class Globals {

    public static int idUsuarioOn = 1;
    public static Usuario.Tipo nivel = Usuario.Tipo.gestor;
    public static ImageIcon iconeSuccess;
    public static ImageIcon iconeError;
    public static ImageIcon iconeWarning;

    public static Image imageIcone;

    static {
        iconeSuccess = new ImageIcon(Globals.class.getClassLoader().getResource("resources/success.png"));
        iconeError = new ImageIcon(Globals.class.getClassLoader().getResource("resources/erro.png"));
        iconeWarning = new ImageIcon(Globals.class.getClassLoader().getResource("resources/warning.png"));
        try {
            imageIcone =  ImageIO.read(Globals.class.getClassLoader().getResource("resources/principal.png"));
        } catch (IOException ex) {
            Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
