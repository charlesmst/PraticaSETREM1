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
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.BiConsumer;
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
public class F2 extends JTextFieldIcone {

    private String textValue;

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        if (textValue != null) {
            FontMetrics metrics = g.getFontMetrics(getFont());

            int widthUsed = metrics.stringWidth(getText());
            int widthRequired = metrics.stringWidth(textValue) + 30;

            String textDraw = textValue;
            int x = 0;
            int y = (int) Math.ceil(getHeight() + getFont().getSize2D() / 2 - (getFont().getSize2D())) - 2;
            if (getWidth() >= (widthUsed + widthRequired)) {
                x = getWidth() - metrics.stringWidth(textValue) - 25;
            } else {
                //Calcula quanto do texto pode colocar
                int usado = metrics.stringWidth("...");
                int l = 0;
                int available = getWidth() - 60 - metrics.stringWidth(getText());
                for (int i = 0; i < textValue.length() && (usado + metrics.charWidth(textValue.charAt(i))) <= available; i++) {
                    usado += metrics.charWidth(textValue.charAt(i));
                    l++;
                }

                textDraw = textValue.substring(0, l) + "...";
                x = getWidth() - usado - 25;
            }
            g.drawString(textDraw, x, y);
        }
    }

    private ThrowingFunction<Integer, String> listener;
//    private final JLabel label;
    private Class<? extends forms.frmF2> classRef;

    private BiConsumer<Integer, String> valueSelectedListener;

    public void setValueSelectedListener(BiConsumer<Integer, String> valueSelectedListener) {
        this.valueSelectedListener = valueSelectedListener;
    }

    public void setForm(Class<? extends forms.frmF2> classRef) {
        this.classRef = classRef;

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
            this.addKeyListener(keyAdapter);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.addMouseListener(new MouseAdapter() {
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
//            JRootPane pPai = this.getRootPane();
//            int x = this.getX();
//            int y = this.getY();
//            this.getLayout().addLayoutComponent("LEADING", panel);
//            pPai.add(panel);
//            pPai.remove(this);
//            panel.add(this,1);
//            panel.setBounds(x,y,this.getWidth(),this.getHeight());

        }

    }

    public F2() {
        super();
    }

    public F2(Class<? extends forms.frmF2> classRef, ThrowingFunction<Integer, String> a) {
        super();
        setBuscador(a);
        setForm(classRef);
    }

    private void openDialog() {
        try {
            frmF2 frm = classRef.newInstance();
            frm.setBusca(this.getText());
            frm.setCallback((id, text) -> {
                setSelected(id, text);
                if (valueSelectedListener != null) {
                    valueSelectedListener.accept(id, text);
                }
            });
            frm.setVisible(true);
        } catch (Exception ex) {

        }

    }

    public int getValueSelected(){
        try {
            return Integer.parseInt(getText());
        } catch (Exception e) {
            return 0;
        }
    }
    @Override
    public void setText(String text) {
        setText(text, true);
        if (valueSelectedListener != null) {
            valueSelectedListener.accept(getValueSelected(), textValue);
        }
    }

    public void setText(String text, boolean refresh) {
        super.setText(text);
        if (refresh) {
            buscador.go();
        }

    }

    private final void setSelected(int id, String desc) {
        this.setText(String.valueOf(id), false);
        setTextValue(desc);
        repaint();
    }
//
//    private void setupLabel() {
//
////        Container parent = this.getParent();
//        getParent().add(label);
//        posicionaLabel();
//
//    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
        repaint();
    }

//    private void posicionaLabel() {
//
//        int x = this.getX() + this.getWidth() + 6;
//        int y = ((int) (this.getHeight() + 16) / 2) + this.getY() - 16;
//        label.setLocation(x, y);
//        label.setText("teste");
////        label.setLocation(0,0);
//        label.setLabelFor(this);
//
//        // label.setBounds(x, y, 16, 16);
////            label.setLocation(Integer.parseInt(JOptionPane.showInputDialog("x")), Integer.parseInt(JOptionPane.showInputDialog("y")));
//    }
    private JCampoBusca buscador;

    public void setBuscador(ThrowingFunction<Integer, String> a) {

        boolean wasOne = listener != null;
        this.listener = a;

        if (!wasOne) {
            buscador = new JCampoBusca(this, () -> {
                if (listener != null) {
                    String v = "";
                    if (utils.Utils.isNumber(this.getText())) {
                        v = listener.apply(Integer.parseInt(this.getText()));
                    }
                    setTextValue(v);
                }
            }
            );
            //Para não dar nullExceptionPointer, executa isso quando a janela está criada
            EventQueue.invokeLater(() -> {

                if (buscador != null) {
                    buscador.go();
                }
            });

//            buscador.go();
        }
    }
}
