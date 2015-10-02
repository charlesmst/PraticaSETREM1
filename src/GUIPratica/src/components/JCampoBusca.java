/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Charles
 */
public class JCampoBusca {

    private final Timer timer;

    public JCampoBusca(JTextField component, final ActionListener listener) {
        timer = new Timer(300, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                listener.actionPerformed(e);
            }
        });

        component.addKeyListener(new KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                timer.stop();
                timer.start();
            }
        });
    }
}
