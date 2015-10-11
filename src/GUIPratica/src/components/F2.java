/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import forms.frmF2;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javafx.scene.input.KeyCode;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import utils.HibernateUtil;

/**
 *
 * @author Charles
 */
public class F2 {

    private ThrowingFunction<Integer, String> listener;
    private final JLabel label;
    private JTextField txt;
    private Class<? extends forms.frmF2> classRef;

    public F2(JTextField txt, Class<? extends forms.frmF2> classRef) {
        this.txt = txt;
        this.classRef = classRef;

        label = new JLabel();

        setupLabel();

        if (classRef != null) {
            KeyAdapter keyAdapter = new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    //Apertou F2 ou digitou letras
                    if (e.getKeyCode() == 113 || Character.isLetter(e.getKeyChar())) {
                        openDialog();
                    }

                }
            };
            txt.addKeyListener(keyAdapter);
            txt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            txt.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    openDialog();
                }
            });
//            JLabel lbl = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/find.png")));
//            
//            lbl.setBounds(0,0, 16, 16);
//            JLayeredPane panel = new JLayeredPane();
//            
//            panel.add(lbl,2);
//            panel.setBackground(Color.red);
//            JRootPane pPai = txt.getRootPane();
//            int x = txt.getX();
//            int y = txt.getY();
//            txt.getLayout().addLayoutComponent("LEADING", panel);
//            pPai.add(panel);
//            pPai.remove(txt);
//            panel.add(txt,1);
//            panel.setBounds(x,y,txt.getWidth(),txt.getHeight());

        }

    }

    private void openDialog() {
        try {
            frmF2 frm = classRef.newInstance();
            frm.setBusca(txt.getText());
            frm.setCallback((id, text) -> setSelected(id, text));
            frm.setVisible(true);
        } catch (Exception ex) {

        }

    }

    private final void setSelected(int id, String desc) {
        txt.setText(String.valueOf(id));
        label.setText(desc);
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

    public void setBuscador(ThrowingFunction<Integer, String> a) {

        boolean wasOne = listener != null;
        this.listener = a;

        if (!wasOne) {
            new JCampoBusca(txt, () -> {
                if (listener != null) {
                    String v = "";
                    if (utils.Utils.isNumber(txt.getText())) {
                        v = listener.apply(Integer.parseInt(txt.getText()));
                    }
                    label.setText(v);
                }
            }
            ).go();
        }
    }
}
