/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JTextField;

/**
 *
 * @author Charles
 */
public class JTextFieldIcone extends JTextField {
    private  String image ;
    public JTextFieldIcone(){
        this("resources/find.png");
    }
    public JTextFieldIcone(String image){
        this.image = image;
    }
    public void paint(Graphics g) {
        super.paint(g);

        try {
            Image image = ImageIO.read(getClass().getClassLoader().getResource(this.image));
            
            g.drawImage(image,getWidth() - image.getWidth(null) - 5, (getHeight() + image.getHeight(null)) / 2 - image.getHeight(null), null);
        } catch (Exception ex) {

        }
    }
}
