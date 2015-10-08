/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import forms.frmF2;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javafx.scene.input.KeyCode;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Charles
 */
public class F2 {

    private F2Listener listener;
    private final JLabel label;
    private JTextField txt;
    private Class<? extends forms.frmF2> classRef;

    public F2(JTextField txt, Class<? extends forms.frmF2> classRef) {
        this.txt = txt;
        this.classRef = classRef;
        new JCampoBusca(txt, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    listener.buscar(txt.getText());
                }
            }
        });

        label = new JLabel();
        setupLabel();

        if (classRef != null) {
            txt.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    //Apertou F2
                    if (e.getKeyCode() == 113) {
                        try {
                            frmF2 frm = classRef.newInstance();
                            int o = 0;
                            frm.setCallback(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    setSelected(e.getID(), e.getActionCommand());
                                }
                            });
                        } catch (Exception ex) {
                            
                        }

                    }
                }
            });
        }
    }
    private final void setSelected(int id, String desc){
    
    }

    private void setupLabel() {

        Container parent = txt.getParent();
        parent.add(label);
        posicionaLabel();

    }

    private void posicionaLabel() {

        int x = txt.getX() + txt.getWidth() + 6;
        int y = ((int) (txt.getHeight() + 16) / 2) + txt.getY() - 16;
//        label.setLocation(x, control.getY());
//        label.setLocation(0,0);
        label.setLabelFor(txt);
        // label.setBounds(x, y, 16, 16);
//            label.setLocation(Integer.parseInt(JOptionPane.showInputDialog("x")), Integer.parseInt(JOptionPane.showInputDialog("y")));
    }

    public void setBuscador(F2Listener a) {
        this.listener = a;
    }
}